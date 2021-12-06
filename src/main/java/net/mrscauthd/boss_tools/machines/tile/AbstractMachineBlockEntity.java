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

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
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
import net.mrscauthd.boss_tools.gauge.IGaugeValuesProvider;

public abstract class AbstractMachineBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer, IEnergyStorageHolder, IGaugeValuesProvider {

	public static final String KEY_ACTIVATED = "activated";

	private Map<Object, Object> selectedPrimaries;
	private Map<ResourceLocation, IEnergyStorage> energyStorages;
	private final Map<ResourceLocation, IFluidHandler> fluidHandlers;
	private final Map<ResourceLocation, PowerSystem> powerSystems;
	private NonNullList<ItemStack> stacks = null;
	private final LazyOptional<? extends IItemHandler>[] itemHandlers;

	private boolean processedInThisTick = false;

	public AbstractMachineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);

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
	public Component getDefaultName() {
		ResourceLocation registryName = this.getType().getRegistryName();
		return new TranslatableComponent("container." + registryName.getNamespace() + "." + registryName.getPath());
	}

	protected void createItemHandlers() {

	}

	public boolean isPowerEnoughForOperation() {
		return this.getPowerSystems().values().stream().allMatch(PowerSystem::isPowerEnoughForOperation);
	}

	/**
	 * 
	 * @return null : fail on consume from any PowerSystem <br>
	 *         nonNull : each PowerSystem's consumed power
	 */
	@Nullable
	public Map<PowerSystem, Integer> consumePowerForOperation() {
		if (this.isPowerEnoughForOperation()) {
			return this.getPowerSystems().values().stream().collect(Collectors.toMap(ps -> ps, PowerSystem::consumeForOperation));
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
		return this.getPowerSystems().values().stream().collect(Collectors.summingInt(PowerSystem::getUsingSlots));
	}

	@Override
	public void load(CompoundTag compound) {
		super.load(compound);

		this.stacks = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
		ContainerHelper.loadAllItems(compound, this.stacks);

		this.deserializeCompoents(this.getEnergyStorages(), compound.getCompound("energyStorages"));
		this.deserializeCompoents(this.getFluidHandlers(), compound.getCompound("fluidHandlers"));
		this.deserializeCompoents(this.getPowerSystems(), compound.getCompound("powerSystems"));
	}

	public <T> void deserializeCompoents(Map<ResourceLocation, T> registry, CompoundTag compound) {
		for (Entry<ResourceLocation, T> entry : registry.entrySet()) {
			this.deserializeComponent(entry.getKey(), entry.getValue(), compound.get(entry.getKey().toString()));
		}
	}

	@SuppressWarnings("unchecked")
	public <T> void deserializeComponent(ResourceLocation name, T component, Tag tag) {
		if (component instanceof INBTSerializable<?>) {
			((INBTSerializable<Tag>) component).deserializeNBT(tag);
		} else if (component instanceof EnergyStorage) {
			((EnergyStorage) component).deserializeNBT(tag);
		} else if (component instanceof FluidTank) {
			((FluidTank) component).readFromNBT((CompoundTag) tag);
		}

	}

	@Override
	public CompoundTag save(CompoundTag compound) {
		super.save(compound);

		ContainerHelper.saveAllItems(compound, this.stacks);

		compound.put("energyStorages", this.serializeComponents(this.getEnergyStorages()));
		compound.put("fluidHandlers", this.serializeComponents(this.getFluidHandlers()));
		compound.put("powerSystems", this.serializeComponents(this.getPowerSystems()));

		return compound;
	}

	public <T> CompoundTag serializeComponents(Map<ResourceLocation, T> registry) {
		CompoundTag compound = new CompoundTag();

		for (Entry<ResourceLocation, T> entry : registry.entrySet()) {
			compound.put(entry.getKey().toString(), this.serializeComponent(entry.getKey(), entry.getValue()));
		}

		return compound;
	}

	@SuppressWarnings("unchecked")
	public <T> Tag serializeComponent(ResourceLocation name, T component) {
		if (component instanceof INBTSerializable<?>) {
			return ((INBTSerializable<Tag>) component).serializeNBT();
		} else if (component instanceof EnergyStorage) {
			return ((EnergyStorage) component).serializeNBT();
		} else if (component instanceof FluidTank) {
			return ((FluidTank) component).writeToNBT(new CompoundTag());
		}

		return null;
	}

	@Override
	public int getContainerSize() {
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
	public boolean canPlaceItem(int index, ItemStack stack) {
		return this.onCanPlaceItemThroughFace(index, stack, null);
	}

	@Override
	public final boolean canPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		boolean result = this.onCanPlaceItemThroughFace(index, stack, direction);

		// Check required because Hopper, it can ignore inventory stack limit
		if (result == true) {
			ItemStack stackInSlot = this.getItem(index);

			if (!stackInSlot.isEmpty() && ItemHandlerHelper.canItemStacksStack(stackInSlot, stack)) {
				int limit = Math.min(stack.getMaxStackSize(), this.getMaxStackSize());
				if (stackInSlot.getCount() + stack.getCount() > limit) {
					return false;
				}
			}
		}

		return result;
	}

	protected boolean onCanPlaceItemThroughFace(int index, ItemStack stack, @Nullable Direction direction) {
		return this.getPowerSystems().values().stream().anyMatch(ps -> ps.canInsertItem(direction, index, stack));
	}

	@Override
	public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
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
		if (!this.isRemoved()) {
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
	public void setRemoved() {
		super.setRemoved();
		Arrays.stream(this.itemHandlers).forEach(h -> h.invalidate());
	}

	public void tick() {

		if (this.getLevel().isClientSide()) {
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

		Level level = this.getLevel();
		BlockPos pos = this.getBlockPos();
		BlockState state = this.getBlockState();
		boolean activated = this.isActivated();

		if (state.hasProperty(property) && state.getValue(property).booleanValue() != activated) {
			level.setBlock(pos, state.setValue(property, activated), 3);
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
		return (T) this.selectedPrimaries.computeIfAbsent(map,
				k -> this.selectPrimaryComponent((Map<ResourceLocation, T>) k));
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
			this.setChanged();
		}
	}

	@Override
	public void setChanged() {
		super.setChanged();

		Level level = this.getLevel();
		
		if (level instanceof ServerLevel) {
			ServerLevel serverLevel = (ServerLevel) level;
			serverLevel.getChunkSource().blockChanged(this.getBlockPos());
		}
	}

	@Override
	public ClientboundBlockEntityDataPacket getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag() {
		return this.save(new CompoundTag());
	}

	@Override
	public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
		this.load(pkt.getTag());
	}

	@Override
	public void onEnergyChanged(IEnergyStorage energyStorage, int energyDelta) {
		this.setChanged();
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
			int limit = Math.min(recipeOutput.getMaxStackSize(), this.getMaxStackSize());
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

	@Override
	public List<IGaugeValue> getGaugeValues() {
		List<IGaugeValue> list = new ArrayList<>();
		this.getPowerSystems().values().stream().map(PowerSystem::getGaugeValues).forEach(list::addAll);
		return list;
	}

}
