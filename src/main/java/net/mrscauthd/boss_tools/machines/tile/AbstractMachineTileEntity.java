package net.mrscauthd.boss_tools.machines.tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import net.mrscauthd.boss_tools.capability.EnergyStorageBasic;
import net.mrscauthd.boss_tools.capability.IEnergyStorageHolder;
import net.mrscauthd.boss_tools.crafting.FluidIngredient;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public abstract class AbstractMachineTileEntity extends LockableLootTileEntity implements ISidedInventory, ITickableTileEntity, IEnergyStorageHolder {

	public static final String KEY_ACTIVATED = "activated";

	private Map<Object, Object> selectedPrimaries;
	private Map<ResourceLocation, IEnergyStorage> energyStorages;
	private final Map<ResourceLocation, IFluidHandler> fluidHandlers;
	private final Map<ResourceLocation, PowerSystem> powerSystems;
	private NonNullList<ItemStack> stacks = null;
	private final LazyOptional<? extends IItemHandler>[] itemHandlers;

	private boolean processedInThisTick = false;

	public AbstractMachineTileEntity(TileEntityType<?> type) {
		super(type);

		this.selectedPrimaries = new HashMap<>();

		NamedComponentRegistry<IEnergyStorage> energyRegistry = new NamedComponentRegistry<>();
		this.createEnergyStorages(energyRegistry);
		this.energyStorages = Collections.unmodifiableMap(energyRegistry);

		NamedComponentRegistry<IFluidHandler> fluidRegistry = new NamedComponentRegistry<>();
		this.createFluidHandlers(fluidRegistry);
		this.fluidHandlers = Collections.unmodifiableMap(fluidRegistry);

		PowerSystemRegistry powerSystemMap = new PowerSystemRegistry();
		this.createPowerSystems(powerSystemMap);
		this.powerSystems = Collections.unmodifiableMap(powerSystemMap);
		this.itemHandlers = SidedInvWrapper.create(this, Direction.values());
		this.createItemHandlers();
		this.stacks = NonNullList.<ItemStack>withSize(this.getInitialInventorySize(), ItemStack.EMPTY);
	}

	@Override
	public ITextComponent getDefaultName() {
		ResourceLocation registryName = this.getType().getRegistryName();
		return new TranslationTextComponent("container." + registryName.getNamespace() + "." + registryName.getPath());
	}

	protected void createItemHandlers() {

	}

	public boolean isPowerEnoughForOperation() {
		return this.getPowerSystems().values().stream().allMatch(ps -> ps.isPowerEnoughForOperation());
	}

	/**
	 * 
	 * @return null : fail on consume from any PowerSystem <br>
	 *         nonNull : each PowerSystem's consumed power
	 */
	@Nullable
	public Map<PowerSystem, Integer> consumePowerForOperation() {
		if (this.isPowerEnoughForOperation()) {
			return this.getPowerSystems().values().stream().collect(Collectors.toMap(ps -> ps, ps -> ps.consumeForOperation()));
		} else {
			return null;
		}
	}

	public int getPowerPerTick(PowerSystem powerSystem, int base) {
		return base;
	}

	public int getPowerForOperation(PowerSystem powerSystem, int base) {
		return base;
	}

	protected int getInitialInventorySize() {
		return this.getPowerSystems().values().stream().collect(Collectors.summingInt(ps -> ps.getUsingSlots()));
	}

	@Override
	public void read(BlockState blockState, CompoundNBT compound) {
		super.read(blockState, compound);

		if (!this.checkLootAndRead(compound)) {
			this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
		}

		ItemStackHelper.loadAllItems(compound, this.stacks);

		this.deserializeCompoents(this.getEnergyStorages(), compound.getCompound("energyStorages"));
		this.deserializeCompoents(this.getFluidHandlers(), compound.getCompound("fluidHandlers"));
		this.deserializeCompoents(this.getPowerSystems(), compound.getCompound("powerSystems"));
	}

	public <T> void deserializeCompoents(Map<ResourceLocation, T> registry, CompoundNBT compound) {
		for (Entry<ResourceLocation, T> entry : registry.entrySet()) {
			this.deserializeComponent(entry.getKey(), entry.getValue(), compound.get(entry.getKey().toString()));
		}
	}

	@SuppressWarnings("unchecked")
	public <T> void deserializeComponent(ResourceLocation name, T component, INBT nbt) {
		if (component instanceof INBTSerializable<?>) {
			((INBTSerializable<INBT>) component).deserializeNBT(nbt);
		} else if (component instanceof EnergyStorage) {
			CapabilityEnergy.ENERGY.readNBT((EnergyStorage) component, null, nbt);
		} else if (component instanceof FluidTank) {
			CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.readNBT((FluidTank) component, null, nbt);
		}

	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		super.write(compound);

		if (!this.checkLootAndWrite(compound)) {
			ItemStackHelper.saveAllItems(compound, this.stacks);
		}

		compound.put("energyStorages", this.serializeComponents(this.getEnergyStorages()));
		compound.put("fluidHandlers", this.serializeComponents(this.getFluidHandlers()));
		compound.put("powerSystems", this.serializeComponents(this.getPowerSystems()));

		return compound;
	}

	public <T> CompoundNBT serializeComponents(Map<ResourceLocation, T> registry) {
		CompoundNBT compound = new CompoundNBT();

		for (Entry<ResourceLocation, T> entry : registry.entrySet()) {
			compound.put(entry.getKey().toString(), this.serializeComponent(entry.getKey(), entry.getValue()));
		}

		return compound;
	}

	@SuppressWarnings("unchecked")
	public <T> INBT serializeComponent(ResourceLocation name, T component) {
		if (component instanceof INBTSerializable<?>) {
			return ((INBTSerializable<INBT>) component).serializeNBT();
		} else if (component instanceof EnergyStorage) {
			return CapabilityEnergy.ENERGY.writeNBT((EnergyStorage) component, null);
		} else if (component instanceof FluidTank) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.writeNBT((FluidTank) component, null);
		}

		return null;
	}

	@Override
	public int getSizeInventory() {
		return stacks.size();
	}

	@Override
	protected NonNullList<ItemStack> getItems() {
		return this.stacks;
	}

	@Override
	protected void setItems(NonNullList<ItemStack> stacks) {
		this.stacks = stacks;
	}

	protected void getSlotsForFace(Direction direction, List<Integer> slots) {
		this.getPowerSystems().values().stream().forEach(ps -> ps.getSlotsForFace(direction, slots));
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		List<Integer> slots = new ArrayList<>();
		this.getSlotsForFace(side, slots);
		return Ints.toArray(slots);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return this.onCanInsertItem(index, stack, null);
	}

	@Override
	public final boolean canInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
		boolean result = this.onCanInsertItem(index, stack, direction);

		// Check required because Hopper, it can ignore inventory stack limit
		if (result == true) {
			ItemStack stackInSlot = this.getStackInSlot(index);

			if (!stackInSlot.isEmpty() && ItemHandlerHelper.canItemStacksStack(stackInSlot, stack)) {
				int limit = Math.min(stack.getMaxStackSize(), this.getInventoryStackLimit());
				if (stackInSlot.getCount() + stack.getCount() > limit) {
					return false;
				}
			}
		}

		return result;
	}

	protected boolean onCanInsertItem(int index, ItemStack stack, @Nullable Direction direction) {
		return this.getPowerSystems().values().stream().anyMatch(ps -> ps.canInsertItem(direction, index, stack));
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		return this.getPowerSystems().values().stream().anyMatch(ps -> ps.canExtractItem(direction, index, stack));
	}

	public <T> LazyOptional<T> getCapabilityItemHandler(Capability<T> capability, @Nullable Direction facing) {
		if (facing != null) {
			return this.itemHandlers[facing.ordinal()].cast();
		} else {
			return super.getCapability(capability, facing);
		}

	}

	public <T> LazyOptional<T> getCapabilityEnergy(Capability<T> capability, @Nullable Direction facing) {
		IEnergyStorage energyStorage = this.getPrimaryEnergyStorage();

		if (energyStorage != null) {
			return LazyOptional.of(() -> energyStorage).cast();
		}

		return LazyOptional.empty();
	}

	public <T> LazyOptional<T> getCapabilityFluidHandler(Capability<T> capability, @Nullable Direction facing) {
		IFluidHandler fluidHandler = this.getPrimaryFluidHandler();

		if (fluidHandler != null) {
			return LazyOptional.of(() -> fluidHandler).cast();
		}

		return LazyOptional.empty();
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
		if (!this.removed) {
			if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
				LazyOptional<T> optional = this.getCapabilityItemHandler(capability, facing);
				if (optional != null && optional.isPresent()) {
					return optional;
				}
			} else if (capability == CapabilityEnergy.ENERGY) {
				LazyOptional<T> optional = this.getCapabilityEnergy(capability, facing);
				if (optional != null && optional.isPresent()) {
					return optional;
				}
			} else if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
				LazyOptional<T> optional = this.getCapabilityFluidHandler(capability, facing);
				if (optional != null && optional.isPresent()) {
					return optional;
				}
			}

			for (PowerSystem powerSystem : this.getPowerSystems().values()) {
				LazyOptional<T> optional = powerSystem.getCapability(capability, facing);
				if (optional != null && optional.isPresent()) {
					return optional;
				}
			}
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public void remove() {
		super.remove();
		Arrays.stream(this.itemHandlers).forEach(h -> h.invalidate());
	}

	@Override
	public void tick() {
		World world = this.getWorld();

		if (world.isRemote()) {
			return;
		}

		this.onTickProcessingPre();

		if (this.canTickProcessing()) {
			this.tickProcessing();
		}

		this.onTickProcessingPost();

		this.updatePowerSystem();

		this.updateActivated();
		this.refreshBlockActivatedChanged();
	}

	protected void updatePowerSystem() {
		this.getPowerSystems().values().forEach(ps -> ps.update());
	}

	protected BooleanProperty getBlockActivatedProperty() {
		return BlockStateProperties.LIT;
	}

	protected void refreshBlockActivatedChanged() {
		BooleanProperty property = this.getBlockActivatedProperty();

		if (property == null) {
			return;
		}

		World world = this.getWorld();
		BlockPos pos = this.getPos();
		BlockState state = this.getBlockState();
		boolean activated = this.isActivated();

		if (state.hasProperty(property) && state.get(property).booleanValue() != activated) {
			world.setBlockState(pos, state.with(property, activated), 3);
		}
	}

	protected void onTickProcessingPre() {
		this.processedInThisTick = false;
	}

	protected boolean canTickProcessing() {
		return true;
	}

	protected abstract void tickProcessing();

	protected void onTickProcessingPost() {

	}

	public void updateActivated() {
		this.setActivated(this.canActivated());
	}

	protected boolean canActivated() {
		List<PowerSystem> powerSystems = Lists.newArrayList(this.getPowerSystems().values());

		if (powerSystems.size() == 1) {
			PowerSystem primary = powerSystems.get(0);

			if (primary instanceof PowerSystemFuel) {
				return primary.isPowerEnoughForOperation();
			}
		}

		return this.processedInThisTick;
	}

	@Nonnull
	protected void createPowerSystems(PowerSystemRegistry map) {

	}

	public Map<ResourceLocation, PowerSystem> getPowerSystems() {
		return this.powerSystems;
	}

	@Nullable
	protected void createFluidHandlers(NamedComponentRegistry<IFluidHandler> registry) {

	}

	public Map<ResourceLocation, IFluidHandler> getFluidHandlers() {
		return this.fluidHandlers;
	}

	public IFluidHandler getPrimaryFluidHandler() {
		return this.getPrimaryComponent(this.getFluidHandlers());
	}

	@Nullable
	protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {

	}

	public Map<ResourceLocation, IEnergyStorage> getEnergyStorages() {
		return this.energyStorages;
	}

	@Nullable
	public IEnergyStorage getPrimaryEnergyStorage() {
		return this.getPrimaryComponent(this.getEnergyStorages());
	}

	protected IEnergyStorage createEnergyStorageCommon() {
		return new EnergyStorageBasic(this, 9000, 200, 200);
	}

	@SuppressWarnings("unchecked")
	@Nonnull
	public <T> T getPrimaryComponent(Map<ResourceLocation, T> map) {
		return (T) this.selectedPrimaries.computeIfAbsent(map, k -> this.selectPrimaryComponent((Map<ResourceLocation, T>) k));
	}

	protected <T> T selectPrimaryComponent(Map<ResourceLocation, T> map) {
		if (map.containsKey(NamedComponentRegistry.UNNAMED)) {
			return map.get(NamedComponentRegistry.UNNAMED);
		} else {
			return map.values().stream().findFirst().orElse(null);
		}
	}

	public IItemHandlerModifiable getItemHandler() {
		return (IItemHandlerModifiable) this.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null).resolve().get();
	}

	public boolean isActivated() {
		return this.getTileData().getBoolean(KEY_ACTIVATED);
	}

	protected void setActivated(boolean activated) {
		if (this.isActivated() != activated) {
			this.getTileData().putBoolean(KEY_ACTIVATED, activated);
			this.markDirty();
		}
	}

	@Override
	public void markDirty() {
		super.markDirty();

		World world = this.getWorld();
		if (world instanceof ServerWorld) {
			ServerWorld serverWorld = (ServerWorld) world;
			serverWorld.getChunkProvider().markBlockChanged(this.getPos());
		}
	}

	@Override
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
	}

	@Override
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		this.read(this.getBlockState(), pkt.getNbtCompound());
	}

	@Override
	public void onEnergyChanged(IEnergyStorage energyStorage, int energyDelta) {
		this.markDirty();
	}

	protected boolean isProcessedInThisTick() {
		return processedInThisTick;
	}

	protected void setProcessedInThisTick() {
		this.processedInThisTick = true;
	}

	public abstract boolean hasSpaceInOutput();

	public boolean nullOrMatch(@Nullable Direction direction, Direction... matches) {
		return direction == null || ArrayUtils.contains(matches, direction);
	}

	public boolean hasSpaceInOutput(ItemStack recipeOutput, ItemStack output) {
		if (output.isEmpty()) {
			return true;
		} else if (ItemHandlerHelper.canItemStacksStack(output, recipeOutput)) {
			int limit = Math.min(recipeOutput.getMaxStackSize(), this.getInventoryStackLimit());
			return (output.getCount() + recipeOutput.getCount()) <= limit;
		}

		return false;
	}

	public boolean hasSpaceInOutput(FluidIngredient recipeOutput, IFluidTank tank) {
		return this.hasSpaceInOutput(recipeOutput, tank.getFluid(), tank.getCapacity());
	}

	public boolean hasSpaceInOutput(FluidIngredient recipeOutput, FluidStack output, int capacity) {
		if (output.isEmpty()) {
			return true;
		} else if (recipeOutput.testFluid(output.getFluid())) {
			return (output.getAmount() + recipeOutput.getAmount()) <= capacity;
		}

		return false;
	}

	public List<IGaugeValue> getFluidHandlerGaugeValues(IFluidHandler fluidHandler) {
		List<IGaugeValue> list = new ArrayList<>();

		for (int i = 0; i < fluidHandler.getTanks(); i++) {
			list.add(GaugeValueHelper.getFluid(fluidHandler.getFluidInTank(i), fluidHandler.getTankCapacity(i)));
		}

		return list;
	}

	public List<IGaugeValue> getGaugeValues() {
		List<IGaugeValue> list = new ArrayList<>();
		this.getPowerSystems().values().stream().map(PowerSystem::getGaugeValues).forEach(list::addAll);
		return list;
	}

}
