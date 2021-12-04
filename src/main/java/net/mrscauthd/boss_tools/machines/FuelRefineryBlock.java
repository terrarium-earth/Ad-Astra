package net.mrscauthd.boss_tools.machines;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.network.NetworkHooks;
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
	public static class CustomBlock extends Block {
		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty ACTIAVATED = BlockStateProperties.LIT;

		public CustomBlock() {
			super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 1f).setLightLevel(s -> 0).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool());
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(ACTIAVATED, Boolean.valueOf(false)));
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING, ACTIAVATED);
		}

		public BlockState rotate(BlockState state, Rotation rot) {
			return state.with(FACING, rot.rotate(state.get(FACING)));
		}

		@SuppressWarnings("deprecation")
		public BlockState mirror(BlockState state, Mirror mirrorIn) {
			return state.rotate(mirrorIn.toRotation(state.get(FACING)));
		}

		@Override
		public BlockState getStateForPlacement(BlockItemUseContext context) {
			return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
		}

		@Override
		public PushReaction getPushReaction(BlockState state) {
			return PushReaction.BLOCK;
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			@SuppressWarnings("deprecation")
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty()) {
				return dropsOriginal;
			} else {
				return Collections.singletonList(new ItemStack(this, 1));
			}
		}

		@Override
		public int getOpacity(BlockState state, IBlockReader worldIn, BlockPos pos) {
			return state.get(ACTIAVATED) ? 12 : 0;
		}

		@Override
		public int getLightValue(BlockState state, IBlockReader world, BlockPos pos) {
			return state.get(ACTIAVATED) ? 12 : 0;
		}

		@Override
		public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {
			if (entity instanceof ServerPlayerEntity) {
				NetworkHooks.openGui((ServerPlayerEntity) entity, this.getContainer(state, world, pos), pos);
				return ActionResultType.CONSUME;
			} else {
				return ActionResultType.SUCCESS;
			}
		}

		@Override
		public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);
			return tileEntity instanceof INamedContainerProvider ? (INamedContainerProvider) tileEntity : null;
		}

		@Override
		public boolean hasTileEntity(BlockState state) {
			return true;
		}

		@Override
		public TileEntity createTileEntity(BlockState state, IBlockReader world) {
			return new CustomTileEntity();
		}

		@SuppressWarnings("deprecation")
		@Override
		public boolean eventReceived(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
			super.eventReceived(state, world, pos, eventID, eventParam);
			TileEntity tileentity = world.getTileEntity(pos);
			return tileentity == null ? false : tileentity.receiveClientEvent(eventID, eventParam);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
			if (state.getBlock() != newState.getBlock()) {
				CustomTileEntity tileentity = (CustomTileEntity) world.getTileEntity(pos);
				InventoryHelper.dropInventoryItems(world, pos, (CustomTileEntity) tileentity);
				world.updateComparatorOutputLevel(pos, this);
				super.onReplaced(state, world, pos, newState, isMoving);
			}
		}

		@Override
		public boolean hasComparatorInputOverride(BlockState state) {
			return true;
		}

		@Override
		public int getComparatorInputOverride(BlockState blockState, World world, BlockPos pos) {
			CustomTileEntity tileentity = (CustomTileEntity) world.getTileEntity(pos);
			return Container.calcRedstoneFromInventory(tileentity);
		}

	}

	// Fuel Refinery Tile Entity
	public static class CustomTileEntity extends AbstractMachineTileEntity {
		private FluidTank inputTank;
		private FluidTank outputTank;

		private StackCacher recipeCacher;
		private FuelRefiningRecipe cachedRecipe;

		public CustomTileEntity() {
			super(ModInnet.FUEL_REFINERY.get());

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
		public int getInventoryStackLimit() {
			return 1;
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new FuelRefineryGui.GuiContainer(id, player, this);
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
		protected boolean onCanInsertItem(int index, ItemStack stack, Direction direction) {
			if (this.isSourceSlot(index)) {
				return FluidUtil2.canDrain(stack);
			} else if (this.isSinkSlot(index)) {
				FluidTank tank = this.slotToTank(index);
				return FluidUtil2.canFill(stack, tank.getFluid().getFluid());
			}

			return super.onCanInsertItem(index, stack, direction);
		}

		@Override
		public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
			if (this.isSourceSlot(index)) {
				return !FluidUtil2.canDrain(stack);
			} else if (this.isSinkSlot(index)) {
				FluidTank tank = this.slotToTank(index);
				return !FluidUtil2.canFill(stack, tank.getFluid().getFluid());
			}

			return super.canExtractItem(index, stack, direction);
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
				this.cachedRecipe = this.getRecipeType().findFirst(this.getWorld(), r -> r.test(fluidStack));
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