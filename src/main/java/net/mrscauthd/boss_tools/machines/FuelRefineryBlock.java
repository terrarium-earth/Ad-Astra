package net.mrscauthd.boss_tools.machines;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.FluidIngredient;
import net.mrscauthd.boss_tools.crafting.FuelRefiningRecipe;
import net.mrscauthd.boss_tools.fluid.FluidUtil2;
import net.mrscauthd.boss_tools.gui.screens.fuelrefinery.FuelRefineryGui;
import net.mrscauthd.boss_tools.inventory.StackCacher;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class FuelRefineryBlock {
	public static final int ENERGY_PER_TICK = 1;
	public static final int TANK_CAPACITY = 3000;
	public static final int TRANSFER_PER_TICK = 256;
	public static final ResourceLocation TANK_INPUT = new ResourceLocation(BossToolsMod.ModId, "input");
	public static final ResourceLocation TANK_OUTPUT = new ResourceLocation(BossToolsMod.ModId, "output");
	public static final int SLOT_INPUT_SOURCE = 0;
	public static final int SLOT_OUTPUT_SINK = 1;
	public static final int SLOT_INPUT_SINK = 2;
	public static final int SLOT_OUTPUT_SOURCE = 3;

	// Fuel Refinery Block
	public static class CustomBlock extends AbstractMachineBlock<CustomTileEntity> {

		public CustomBlock() {
			super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 0).requiresCorrectToolForDrops());
		}

		@Override
		protected boolean useLit() {
			return true;
		}
		
		@Override
		protected boolean useFacing() {
			return true;
		}

		@Override
		public CustomTileEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new CustomTileEntity(pos, state);
		}

	}

	// Fuel Refinery Tile Entity
	public static class CustomTileEntity extends AbstractMachineTileEntity {
		private FluidTank inputTank;
		private FluidTank outputTank;

		private StackCacher recipeCacher;
		private FuelRefiningRecipe cachedRecipe;

		public CustomTileEntity(BlockPos pos, BlockState state) {
			super(ModInnet.FUEL_REFINERY.get(), pos, state);

			this.recipeCacher = new StackCacher();
			this.cachedRecipe = null;
		}

		@Override
		protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry) {
			super.createEnergyStorages(registry);
			registry.put(this.createEnergyStorageCommon());
		}

		@Override
		protected void createFluidHandlers(NamedComponentRegistry<IFluidHandler> registry) {
			super.createFluidHandlers(registry);
			this.inputTank = (FluidTank) registry.computeIfAbsent(this.getInputTankName(), k -> this.creatTank(k));
			this.outputTank = (FluidTank) registry.computeIfAbsent(this.getOutputTankName(), k -> this.creatTank(k));
		}

		protected int getInitialTankCapacity(ResourceLocation name) {
			return TANK_CAPACITY;
		}

		protected FluidTank creatTank(ResourceLocation name) {
			return new FluidTank(this.getInitialTankCapacity(name)) {
				@Override
				protected void onContentsChanged() {
					super.onContentsChanged();
					CustomTileEntity.this.markDirty();
				}
			};
		}

		@Override
		protected void createPowerSystems(PowerSystemRegistry map) {
			super.createPowerSystems(map);
			map.put(new PowerSystemEnergyCommon(this) {
				@Override
				public int getBasePowerForOperation() {
					return CustomTileEntity.this.getBasePowerForOperation();
				}
			});
		}

		public int getBasePowerForOperation() {
			return ENERGY_PER_TICK;
		}

		@Override
		public int getMaxStackSize() {
			return 1;
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inventory) {
			return new FuelRefineryGui.GuiContainer(id, inventory, this);
		}

		@Override
		protected void tickProcessing() {
			this.drainSources();
			this.consumeIngredients();
			this.fillSinks();
		}

		public boolean consumeIngredients() {
			FuelRefiningRecipe recipe = this.cacheRecipe();

			if (recipe != null) {
				FluidIngredient recipeOutput = recipe.getOutput();

				if (this.hasSpaceInOutput(recipeOutput)) {
					if (this.consumePowerForOperation() != null) {
						this.getInputTank().drain(recipe.getInput().getAmount(), FluidAction.EXECUTE);
						this.getOutputTank().fill(recipeOutput.toStack(), FluidAction.EXECUTE);
						this.setProcessedInThisTick();
						return true;
					}
				}
			}

			return false;
		}

		protected void drainSources() {
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			int transferPerTick = this.getTransferPerTick();
			FluidUtil2.drainSource(itemHandler, this.getInputSourceSlot(), this.getInputTank(), transferPerTick);
			FluidUtil2.drainSource(itemHandler, this.getOutputSourceSlot(), this.getOutputTank(), transferPerTick);
		}

		protected void fillSinks() {
			IItemHandlerModifiable itemHandler = this.getItemHandler();
			int transferPerTick = this.getTransferPerTick();
			FluidUtil2.fillSink(itemHandler, this.getInputSinkSlot(), this.getInputTank(), transferPerTick);
			FluidUtil2.fillSink(itemHandler, this.getOutputSinkSlot(), this.getOutputTank(), transferPerTick);
		}

		@Override
		public <T> LazyOptional<T> getCapabilityFluidHandler(Capability<T> capability, @Nullable Direction facing) {
			if (facing == Direction.DOWN || facing == null) {
				return LazyOptional.of(this::getOutputTank).cast();
			} else {
				return LazyOptional.of(this::getInputTank).cast();
			}
		}

		@Override
		protected void getSlotsForFace(Direction direction, List<Integer> slots) {
			super.getSlotsForFace(direction, slots);
			slots.add(this.getOutputSourceSlot());
			slots.add(this.getInputSourceSlot());
			slots.add(this.getOutputSinkSlot());
		}

		@Override
		protected boolean onCanPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
			if (this.isSourceSlot(index)) {
				return FluidUtil2.canDrain(stack);
			} else if (this.isSinkSlot(index)) {
				FluidTank tank = this.slotToTank(index);
				return FluidUtil2.canFill(stack, tank.getFluid().getFluid());
			}

			return super.onCanPlaceItemThroughFace(index, stack, direction);
		}

		@Override
		public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
			if (this.isSourceSlot(index)) {
				return !FluidUtil2.canDrain(stack);
			} else if (this.isSinkSlot(index)) {
				FluidTank tank = this.slotToTank(index);
				return !FluidUtil2.canFill(stack, tank.getFluid().getFluid());
			}

			return super.canTakeItemThroughFace(index, stack, direction);
		}

		@Override
		public boolean hasSpaceInOutput() {
			FuelRefiningRecipe recipe = this.cacheRecipe();
			return recipe != null && this.hasSpaceInOutput(recipe.getOutput());
		}

		public boolean hasSpaceInOutput(FluidIngredient recipeOutput) {
			return hasSpaceInOutput(recipeOutput, this.getOutputTank());
		}

		public FuelRefiningRecipe cacheRecipe() {
			FluidStack fluidStack = this.getInputTank().getFluid();

			if (fluidStack.isEmpty()) {
				this.recipeCacher.set(fluidStack);
				this.cachedRecipe = null;
			} else if (!this.recipeCacher.test(fluidStack)) {
				this.recipeCacher.set(fluidStack);
				this.cachedRecipe = this.getRecipeType().findFirst(this.getLevel(), r -> r.test(fluidStack));
			}

			return this.cachedRecipe;
		}

		public BossToolsRecipeType<? extends FuelRefiningRecipe> getRecipeType() {
			return BossToolsRecipeTypes.FUELREFINING;
		}

		@Override
		protected int getInitialInventorySize() {
			return super.getInitialInventorySize() + 4;
		}

		public int getInputSourceSlot() {
			return SLOT_INPUT_SOURCE;
		}

		public int getInputSinkSlot() {
			return SLOT_INPUT_SINK;
		}

		public int getOutputSourceSlot() {
			return SLOT_OUTPUT_SOURCE;
		}

		public int getOutputSinkSlot() {
			return SLOT_OUTPUT_SINK;
		}

		public boolean isSourceSlot(int slot) {
			return slot == this.getInputSourceSlot() || slot == this.getOutputSourceSlot();
		}

		public boolean isSinkSlot(int slot) {
			return slot == this.getInputSinkSlot() || slot == this.getOutputSinkSlot();
		}

		public FluidTank slotToTank(int slot) {
			if (slot == this.getInputSourceSlot() || slot == this.getInputSinkSlot()) {
				return this.getInputTank();
			} else if (slot == this.getOutputSourceSlot() || slot == this.getOutputSinkSlot()) {
				return this.getOutputTank();
			} else {
				return null;
			}
		}

		public ResourceLocation slotToTankName(int slot) {
			if (slot == this.getInputSourceSlot() || slot == this.getInputSinkSlot()) {
				return this.getInputTankName();
			} else if (slot == this.getOutputSourceSlot() || slot == this.getOutputSinkSlot()) {
				return this.getOutputTankName();
			} else {
				return null;
			}
		}

		public ResourceLocation getInputTankName() {
			return TANK_INPUT;
		}

		public FluidTank getInputTank() {
			return this.inputTank;
		}

		public ResourceLocation getOutputTankName() {
			return TANK_OUTPUT;
		}

		public FluidTank getOutputTank() {
			return this.outputTank;
		}

		public int getTransferPerTick() {
			return TRANSFER_PER_TICK;
		}

	}
}