package net.mrscauthd.boss_tools.flag;

import static net.mrscauthd.boss_tools.block.helper.VoxelShapeHelper.boxSimple;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class FlagBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
	public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
	public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
	public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

	public FlagBlock(BlockBehaviour.Properties builder) {
		super(builder);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, false));
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		Vec3 offset = state.getOffset(world, pos);

		if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
			switch ((Direction) state.getValue(FACING)) {
			case SOUTH:
			default:
				return Shapes.or(boxSimple(14.5, 0, 9, 12.5, 1, 7), boxSimple(14, 1, 8.5, 13, 16, 7.5)).move(offset.x, offset.y, offset.z);
			case NORTH:
				return Shapes.or(boxSimple(1.5, 0, 7, 3.5, 1, 9), boxSimple(2, 1, 7.5, 3, 16, 8.5)).move(offset.x, offset.y, offset.z);
			case EAST:
				return Shapes.or(boxSimple(9, 0, 1.5, 7, 1, 3.5), boxSimple(8.5, 1, 2, 7.5, 16, 3)).move(offset.x, offset.y, offset.z);
			case WEST:
				return Shapes.or(boxSimple(7, 0, 14.5, 9, 1, 12.5), boxSimple(7.5, 1, 14, 8.5, 16, 13)).move(offset.x, offset.y, offset.z);
			}
		} else {
			switch ((Direction) state.getValue(FACING)) {
			case SOUTH:
			default:
				return Shapes.or(boxSimple(14, 0, 8.5, 13, 16, 7.5), boxSimple(14, 7, 8.5, 1, 15, 7.5)).move(offset.x, offset.y, offset.z);
			case NORTH:
				return Shapes.or(boxSimple(2, 0, 7.5, 3, 16, 8.5), boxSimple(2, 7, 7.5, 15, 15, 8.5)).move(offset.x, offset.y, offset.z);
			case EAST:
				return Shapes.or(boxSimple(8.5, 0, 2, 7.5, 16, 3), boxSimple(8.5, 7, 2, 7.5, 15, 15)).move(offset.x, offset.y, offset.z);
			case WEST:
				return Shapes.or(boxSimple(7.5, 0, 14, 8.5, 16, 13), boxSimple(7.5, 7, 14, 8.5, 15, 1)).move(offset.x, offset.y, offset.z);
			}
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
		DoubleBlockHalf doubleblockhalf = stateIn.getValue(HALF);
		if (stateIn.getValue(WATERLOGGED)) {
			worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
		}
		if (facing.getAxis() == Direction.Axis.Y && doubleblockhalf == DoubleBlockHalf.LOWER == (facing == Direction.UP)) {
			return facingState.is(this) && facingState.getValue(HALF) != doubleblockhalf ? stateIn.setValue(FACING, facingState.getValue(FACING)) : Blocks.AIR.defaultBlockState();
		} else {
			return doubleblockhalf == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !stateIn.canSurvive(worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
		}
	}

	@Override
	public void playerWillDestroy(Level worldIn, BlockPos pos, BlockState state, Player player) {
		if (!worldIn.isClientSide && player.isCreative()) {
			removeBottomHalf(worldIn, pos, state, player);
		}
		super.playerWillDestroy(worldIn, pos, state, player);
	}

	protected static void removeBottomHalf(Level world, BlockPos pos, BlockState state, Player player) {
		DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
		if (doubleblockhalf == DoubleBlockHalf.UPPER) {
			BlockPos blockpos = pos.below();
			BlockState blockstate = world.getBlockState(blockpos);
			if (blockstate.getBlock() == state.getBlock() && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
				world.setBlock(blockpos, Blocks.AIR.defaultBlockState(), 35);
				world.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
			}
		}
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext p_49820_) {
		BlockPos blockpos = p_49820_.getClickedPos();
		Level level = p_49820_.getLevel();

		if (blockpos.getY() < level.getMaxBuildHeight() - 1 && p_49820_.getLevel().getBlockState(blockpos.above()).canBeReplaced(p_49820_)) {
			boolean flag = p_49820_.getLevel().getFluidState(p_49820_.getClickedPos()).is(Fluids.WATER);
			return this.defaultBlockState().setValue(FACING, p_49820_.getHorizontalDirection()).setValue(HALF, DoubleBlockHalf.LOWER).setValue(WATERLOGGED, flag);
		} else {
			return null;
		}
	}

	@Override
	public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(worldIn, pos, state, placer, stack);
		worldIn.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER), 3);

		BlockEntity tileentity = worldIn.getBlockEntity(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ()));

		if (tileentity instanceof FlagTileEntity) {
			FlagTileEntity flagtileentity = (FlagTileEntity) tileentity;

			CompoundTag compoundnbt = new CompoundTag();
			NbtUtils.writeGameProfile(compoundnbt, new GameProfile(placer.getUUID(), placer.getName().getString()));
			flagtileentity.getTileData().put("FlagOwner", compoundnbt);

			if (placer instanceof Player) {
				Player player = (Player) placer;

				flagtileentity.setPlayerProfile(player.getGameProfile());
			}
		}
	}

	@Override
	public BlockEntity newBlockEntity(BlockPos p_153215_, BlockState p_153216_) {
		return new FlagTileEntity(p_153215_, p_153216_);
	}

	@Override
	public boolean isPathfindable(BlockState p_60475_, BlockGetter p_60476_, BlockPos p_60477_, PathComputationType p_60478_) {
		return false;
	}

	@OnlyIn(Dist.CLIENT)
	public FlagBlock.ISkullType getSkullType() {
		return FlagBlock.Types.PLAYER;
	}

	public interface ISkullType {
	}

	public static enum Types implements FlagBlock.ISkullType {
		PLAYER
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
		BlockPos blockpos = pos.below();
		BlockState blockstate = worldIn.getBlockState(blockpos);
		return state.getValue(HALF) == DoubleBlockHalf.LOWER ? blockstate.isFaceSturdy(worldIn, blockpos, Direction.UP) : blockstate.is(this);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState p_60584_) {
		return PushReaction.DESTROY;
	}

	@Override
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	@Override
	public long getSeed(BlockState state, BlockPos pos) {
		return Mth.getSeed(pos.getX(), pos.above(state.getValue(HALF) == DoubleBlockHalf.LOWER ? 0 : 1).getY(), pos.getZ());
	}

	@SuppressWarnings("deprecation")
	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> p_49915_) {
		p_49915_.add(HALF, FACING, WATERLOGGED);
	}
}