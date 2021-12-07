package net.mrscauthd.boss_tools.machines;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
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
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineBlockEntity;

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

	public static class CustomBlock extends AbstractMachineBlock<NASAWorkbenchBlockEntity> implements SimpleWaterloggedBlock {

		public CustomBlock() {
			super(Block.Properties.of(Material.METAL).sound(SoundType.METAL).strength(5f, 1f).lightLevel(s -> 1).requiresCorrectToolForDrops().noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
		}
		
		@Override
		protected boolean useFacing() {
			return true;
		}
		
		@Override
		protected BlockState buildDefaultState() {
			return super.buildDefaultState().setValue(BlockStateProperties.WATERLOGGED, false);
		}
		
		@Override
		public boolean skipRendering(BlockState p_60532_, BlockState p_60533_, Direction p_60534_) {
			return true;
		}

		@Override
		public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
			Vec3 offset = state.getOffset(level, pos);
			switch ((Direction) state.getValue(FACING)) {
			case SOUTH:
			default:
				return Shapes.or(box(16, 0, 16, 0, 19.2, 0)).move(offset.x, offset.y, offset.z);
			case NORTH:
				return Shapes.or(box(0, 0, 0, 16, 19.2, 16)).move(offset.x, offset.y, offset.z);
			case EAST:
				return Shapes.or(box(16, 0, 0, 0, 19.2, 16)).move(offset.x, offset.y, offset.z);
			case WEST:
				return Shapes.or(box(0, 0, 16, 16, 19.2, 0)).move(offset.x, offset.y, offset.z);
			}
		}

		@Override
		protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
			super.createBlockStateDefinition(builder);
			builder.add(BlockStateProperties.WATERLOGGED);
		}
		
		@Override
		public BlockState getStateForPlacement(BlockPlaceContext context) {
			boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
			return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, flag);
		}

		@SuppressWarnings("deprecation")
		@Override
		public FluidState getFluidState(BlockState state) {
			return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
			if (state.getValue(BlockStateProperties.WATERLOGGED)) {
				level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
			}
			
			return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
		}
		
		@Override
		public NASAWorkbenchBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
			return new NASAWorkbenchBlockEntity(pos, state);
		}

		@Override
		public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
			NASAWorkbenchBlockEntity blockEntity = (NASAWorkbenchBlockEntity) level.getBlockEntity(pos);
			return blockEntity.cacheRecipes() != null ? 15 : 0;
		}
	}

	public static class NASAWorkbenchBlockEntity extends AbstractMachineBlockEntity {

		private StackCacher itemStackCacher;
		private WorkbenchingRecipe cachedRecipe;
		private List<WorkbenchingRecipe> possibleRecipes;
		private Set<ItemStack> invalidCache;

		private int prevRedstone;
		private int currRedstone;

		private RocketPartsItemHandler partsItemHandler;

		public NASAWorkbenchBlockEntity(BlockPos pos, BlockState state) {
			super(ModInnet.NASA_WORKBENCH.get(), pos, state);

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
		public void setItem(int p_59616_, ItemStack p_59617_) {
			super.setItem(p_59616_, p_59617_);
			this.cacheRecipes();
		}

		@Override
		public AbstractContainerMenu createMenu(int id, Inventory inventory) {
			return new NasaWorkbenchGui.GuiContainer(id, inventory, this);
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
		protected boolean onCanPlaceItemThroughFace(int index, ItemStack stack, Direction direction) {
			int find = this.findAvailableSlot(stack);

			if (find == index) {
				return true;
			}

			return super.onCanPlaceItemThroughFace(index, stack, direction);
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
				this.cachedRecipe = recipeType.findFirst(this.getLevel(), r -> r.test(partsItemHandler, false));
				this.possibleRecipes.clear();
				recipeType.filter(this.getLevel(), r -> r.test(partsItemHandler, true)).forEach(this.possibleRecipes::add);

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
			this.currRedstone = this.getLevel().getBestNeighborSignal(this.getBlockPos());
		}

		protected void outputToBottom() {

			WorkbenchingRecipe recipe = this.cacheRecipes();

			if (recipe == null) {
				return;
			}

			IItemHandler bottomItemHandler = this.getBottomBlockEntityItemHandler();

			if (bottomItemHandler == null) {
				return;
			}

			ItemStack output = recipe.getOutput();

			if (ItemHandlerHelper.insertItem(bottomItemHandler, output, true).isEmpty()) {
				ItemHandlerHelper.insertItem(bottomItemHandler, output, false);
				this.consumeIngredient();
			}
		}

		private IItemHandler getBottomBlockEntityItemHandler() {
			BlockEntity bottomBlockEntity = this.getLevel().getBlockEntity(this.getBlockPos().below());

			if (bottomBlockEntity != null) {
				return bottomBlockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
			} else {
				return null;
			}
		}

		protected void spawnParticles() {
			if (this.possibleRecipes.size() > 0 && !ItemHandlerHelper2.isEmpty(this.getPartsItemHandler())) {

				Level level = this.getLevel();

				if (level instanceof ServerLevel) {
					ServerLevel serverLevel = (ServerLevel) level;
					BlockPos pos = this.getBlockPos();
					serverLevel.sendParticles(ParticleTypes.CRIT, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 10, 0.1D, 0.1D, 0.1D, 0.1D);
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

			Level level = this.getLevel();

			if (level instanceof ServerLevel) {
				ServerLevel serverLevel = (ServerLevel) level;
				BlockPos pos = this.getBlockPos();

				serverLevel.playSound(null, pos, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.totem.use")), SoundSource.NEUTRAL, 1.0F, 1.0F);
				serverLevel.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, pos.getX() + 0.5D, pos.getY() + 1.5D, pos.getZ() + 0.5D, 100, 0.1D, 0.1D, 0.1D, 0.7D);
			}

			return true;
		}

		@Override
		public boolean hasSpaceInOutput() {
			return true;
		}

	}
}
