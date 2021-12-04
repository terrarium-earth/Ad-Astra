package net.mrscauthd.boss_tools.machines;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.loot.LootContext;
import net.minecraft.particles.ParticleTypes;
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
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeType;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.RocketPart;
import net.mrscauthd.boss_tools.crafting.WorkbenchingRecipe;
import net.mrscauthd.boss_tools.gui.screens.nasaworkbench.NasaWorkbenchGui;
import net.mrscauthd.boss_tools.inventory.ItemHandlerHelper2;
import net.mrscauthd.boss_tools.inventory.RocketPartsItemHandler;
import net.mrscauthd.boss_tools.inventory.StackCacher;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;

public class NASAWorkbenchBlock {

	public static final int SLOT_PARTS = 0;

	public static List<RocketPart> getBasicPartOrders() {
		List<RocketPart> parts = new ArrayList<>();
		parts.add(ModInnet.ROCKET_PART_NOSE.get());
		parts.add(ModInnet.ROCKET_PART_BODY.get());
		parts.add(ModInnet.ROCKET_PART_TANK.get());
		parts.add(ModInnet.ROCKET_PART_FIN_LEFT.get());
		parts.add(ModInnet.ROCKET_PART_FIN_RIGHT.get());
		parts.add(ModInnet.ROCKET_PART_ENGINE.get());
		return parts;
	}

	public static int getBasicPartSlots() {
		return getBasicPartOrders().stream().collect(Collectors.summingInt(p -> p.getSlots()));
	}

	public static class CustomBlock extends Block implements IWaterLoggable {
		public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
		public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

		public CustomBlock() {
			super(Block.Properties.create(Material.IRON).sound(SoundType.METAL).hardnessAndResistance(5f, 1f).setLightLevel(s -> 1).harvestLevel(1).harvestTool(ToolType.PICKAXE).setRequiresTool().notSolid().setOpaque((bs, br, bp) -> false));
			this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(WATERLOGGED, false));
		}

		@Override
		public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
			return true;
		}

		@Override
		public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
			Vector3d offset = state.getOffset(world, pos);
			switch ((Direction) state.get(FACING)) {
			case SOUTH:
			default:
				return VoxelShapes.or(makeCuboidShape(16, 0, 16, 0, 19.2, 0)).withOffset(offset.x, offset.y, offset.z);
			case NORTH:
				return VoxelShapes.or(makeCuboidShape(0, 0, 0, 16, 19.2, 16)).withOffset(offset.x, offset.y, offset.z);
			case EAST:
				return VoxelShapes.or(makeCuboidShape(16, 0, 0, 0, 19.2, 16)).withOffset(offset.x, offset.y, offset.z);
			case WEST:
				return VoxelShapes.or(makeCuboidShape(0, 0, 16, 16, 19.2, 0)).withOffset(offset.x, offset.y, offset.z);
			}
		}

		@Override
		protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
			builder.add(FACING, WATERLOGGED);
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
			boolean flag = context.getWorld().getFluidState(context.getPos()).getFluid() == Fluids.WATER;
			return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(WATERLOGGED, flag);
		}

		@SuppressWarnings("deprecation")
		@Override
		public FluidState getFluidState(BlockState state) {
			return state.get(WATERLOGGED) ? Fluids.WATER.getStillFluidState(false) : super.getFluidState(state);
		}

		@SuppressWarnings("deprecation")
		@Override
		public BlockState updatePostPlacement(BlockState state, Direction facing, BlockState facingState, IWorld world, BlockPos currentPos, BlockPos facingPos) {
			if (state.get(WATERLOGGED)) {
				world.getPendingFluidTicks().scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickRate(world));
			}
			return super.updatePostPlacement(state, facing, facingState, world, currentPos, facingPos);
		}

		@Override
		public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
			@SuppressWarnings("deprecation")
			List<ItemStack> dropsOriginal = super.getDrops(state, builder);
			if (!dropsOriginal.isEmpty()) {
				return dropsOriginal;
			} else {
				return Collections.singletonList(new ItemStack(this));
			}
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
			return tileentity.cacheRecipes() != null ? 15 : 0;
		}
	}

	public static class CustomTileEntity extends AbstractMachineTileEntity {

		private StackCacher itemStackCacher;
		private WorkbenchingRecipe cachedRecipe;
		private List<WorkbenchingRecipe> possibleRecipes;
		private Set<ItemStack> invalidCache;

		private int prevRedstone;
		private int currRedstone;

		private RocketPartsItemHandler partsItemHandler;

		public CustomTileEntity() {
			super(ModInnet.NASA_WORKBENCH.get());

			this.itemStackCacher = new StackCacher();
			this.cachedRecipe = null;
			this.possibleRecipes = new ArrayList<>();
			this.invalidCache = new HashSet<>();

			this.prevRedstone = 0;
			this.currRedstone = 0;
		}

		public RocketPartsItemHandler getPartsItemHandler() {
			return this.partsItemHandler;
		}

		public List<RocketPart> getPartOrders() {
			return getBasicPartOrders();
		}

		public int getPartsSlot() {
			return SLOT_PARTS;
		}

		@Override
		protected void createItemHandlers() {
			super.createItemHandlers();

			this.partsItemHandler = new RocketPartsItemHandler(this.getItemHandler(), this.getPartsSlot(), this.getPartOrders());
		}

		@Override
		protected int getInitialInventorySize() {
			return super.getInitialInventorySize() + this.getPartsItemHandler().getSlots();
		}

		@Override
		public void setInventorySlotContents(int p_70299_1_, ItemStack p_70299_2_) {
			super.setInventorySlotContents(p_70299_1_, p_70299_2_);
			this.cacheRecipes();
		}

		@Override
		public Container createMenu(int id, PlayerInventory player) {
			return new NasaWorkbenchGui.GuiContainer(id, player, this);
		}

		@Override
		protected void getSlotsForFace(Direction direction, List<Integer> slots) {
			super.getSlotsForFace(direction, slots);

			RocketPartsItemHandler partsItemHandler = this.getPartsItemHandler();

			for (int i = 0; i < partsItemHandler.getSlots(); i++) {
				slots.add(partsItemHandler.getParentSlotIndex(i));
			}
		}

		@Override
		protected boolean onCanInsertItem(int index, ItemStack stack, Direction direction) {
			int find = this.findAvailableSlot(stack);

			if (find == index) {
				return true;
			}

			return super.onCanInsertItem(index, stack, direction);
		}

		public int findAvailableSlot(ItemStack itemStack) {
			this.cacheRecipes();

			ItemStack single = ItemHandlerHelper.copyStackWithSize(itemStack, 1);

			if (this.invalidCache.contains(single)) {
				return -1;
			}

			for (WorkbenchingRecipe recipe : this.possibleRecipes) {
				int slot = this.findAvailableSlot(recipe, itemStack);

				if (slot != -1) {
					return slot;
				}
			}

			this.invalidCache.add(single);
			return -1;
		}

		public int findAvailableSlot(WorkbenchingRecipe recipe, ItemStack itemStack) {
			Map<RocketPart, List<Ingredient>> recipeParts = recipe.getParts();
			RocketPartsItemHandler partsItemHandler = this.getPartsItemHandler();

			for (Entry<RocketPart, IItemHandlerModifiable> entry : partsItemHandler.getSubHandlers().entrySet()) {
				RocketPart part = entry.getKey();
				IItemHandlerModifiable subHandler = entry.getValue();
				List<Ingredient> ingredients = recipeParts.get(part);

				if (ingredients != null) {
					for (int i = 0; i < ingredients.size(); i++) {
						if (ingredients.get(i).test(itemStack) && subHandler.getStackInSlot(i).isEmpty()) {
							return partsItemHandler.getParentSlotIndex(part, i);
						}
					}
				}
			}

			return -1;
		}

		public WorkbenchingRecipe cacheRecipes() {
			RocketPartsItemHandler partsItemHandler = this.getPartsItemHandler();
			List<ItemStack> stacks = ItemHandlerHelper2.getStacks(partsItemHandler);

			if (!this.itemStackCacher.test(stacks)) {
				this.itemStackCacher.set(stacks);

				BossToolsRecipeType<WorkbenchingRecipe> recipeType = this.getRecipeType();
				this.cachedRecipe = recipeType.findFirst(this.getWorld(), r -> r.test(partsItemHandler, false));
				this.possibleRecipes.clear();
				recipeType.filter(this.getWorld(), r -> r.test(partsItemHandler, true)).forEach(this.possibleRecipes::add);

				this.invalidCache.clear();
			}

			return this.cachedRecipe;
		}

		public BossToolsRecipeType<WorkbenchingRecipe> getRecipeType() {
			return BossToolsRecipeTypes.WORKBENCHING;
		}

		@Override
		protected void tickProcessing() {
			this.spawnParticles();

			this.updateRedstoneState();

			if (this.prevRedstone == 0 && this.currRedstone > 0) {
				this.outputToBottom();
			}

		}

		protected void updateRedstoneState() {
			this.prevRedstone = this.currRedstone;
			this.currRedstone = this.getWorld().getRedstonePowerFromNeighbors(this.getPos());
		}

		protected void outputToBottom() {

			WorkbenchingRecipe recipe = this.cacheRecipes();

			if (recipe == null) {
				return;
			}

			IItemHandler bottomItemHandler = this.getBottomTileEntityItemHandler();

			if (bottomItemHandler == null) {
				return;
			}

			ItemStack output = recipe.getOutput();

			if (ItemHandlerHelper.insertItem(bottomItemHandler, output, true).isEmpty()) {
				ItemHandlerHelper.insertItem(bottomItemHandler, output, false);
				this.consumeIngredient();
			}
		}

		private IItemHandler getBottomTileEntityItemHandler() {
			TileEntity bottomTileEntity = this.getWorld().getTileEntity(this.getPos().down());

			if (bottomTileEntity != null) {
				return bottomTileEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
			} else {
				return null;
			}
		}

		protected void spawnParticles() {
			if (this.possibleRecipes.size() > 0 && !ItemHandlerHelper2.isEmpty(this.getPartsItemHandler())) {

				World world = this.getWorld();

				if (world instanceof ServerWorld) {
					ServerWorld serverWorld = (ServerWorld) world;
					BlockPos pos = this.getPos();
					serverWorld.spawnParticle(ParticleTypes.CRIT, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 10, 0.1D, 0.1D, 0.1D, 0.1D);
				}
			}
		}

		public boolean consumeIngredient() {
			WorkbenchingRecipe recipe = this.cacheRecipes();

			if (recipe == null) {
				return false;
			}

			RocketPartsItemHandler partsItemHandler = this.getPartsItemHandler();

			for (RocketPart part : recipe.getParts().keySet()) {
				IItemHandlerModifiable subHandler = partsItemHandler.getSubHandlers().get(part);

				for (int i = 0; i < part.getSlots(); i++) {
					subHandler.extractItem(i, 1, false);
				}
			}

			World world = this.getWorld();

			if (world instanceof ServerWorld) {
				ServerWorld serverWorld = (ServerWorld) world;
				BlockPos pos = this.getPos();

				serverWorld.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundCategory.NEUTRAL, 1.0F, 1.0F);
				serverWorld.spawnParticle(ParticleTypes.TOTEM_OF_UNDYING, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 100, 0.1D, 0.1D, 0.1D, 0.7D);
			}

			return true;
		}

		@Override
		public boolean hasSpaceInOutput() {
			return true;
		}

	}
}
