package earth.terrarium.ad_astra.blocks.flags;

import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@SuppressWarnings("deprecation")
public class FlagBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public FlagBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(WATERLOGGED, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {

        if (state.getValue(HALF).equals(DoubleBlockHalf.LOWER)) {
            switch (state.getValue(FACING)) {
                case NORTH -> {
                    VoxelShape shape = Shapes.empty();
                    shape = Shapes.or(shape, Shapes.box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
                    shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 1, 0.5625));
                    return shape;
                }
                case EAST -> {
                    VoxelShape shape = Shapes.empty();
                    shape = Shapes.or(shape, Shapes.box(0.375, -0.5, 0.375, 0.625, 0, 0.625));
                    shape = Shapes.or(shape, Shapes.box(0.4375, -0.5, 0.4375, 0.5625, 0.5, 0.5625));
                    return shape;
                }
                case SOUTH -> {
                    VoxelShape shape = Shapes.empty();
                    shape = Shapes.or(shape, Shapes.box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
                    shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 1, 0.5625));
                    return shape;
                }
                case WEST -> {
                    VoxelShape shape = Shapes.empty();
                    shape = Shapes.or(shape, Shapes.box(0.375, 0, 0.375, 0.625, 0.5, 0.625));
                    shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 1, 0.5625));
                    return shape;
                }
                default -> throw new IllegalStateException("Unexpected value: " + state.getValue(FACING));
            }

        } else {
            switch (state.getValue(FACING)) {
                case NORTH -> {
                    VoxelShape shape = Shapes.empty();
                    shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625));
                    shape = Shapes.or(shape, Shapes.box(-0.9375, 0.4375, 0.46875, 0.4375, 1.4375, 0.53125));
                    return shape;
                }
                case EAST -> {
                    VoxelShape shape = Shapes.empty();
                    shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625));
                    shape = Shapes.or(shape, Shapes.box(0.46875, 0.4375, -0.9375, 0.53125, 1.4375, 0.4375));
                    return shape;
                }
                case SOUTH -> {
                    VoxelShape shape = Shapes.empty();
                    shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625));
                    shape = Shapes.or(shape, Shapes.box(0.5625, 0.4375, 0.46875, 1.9375, 1.4375, 0.53125));
                    return shape;
                }
                case WEST -> {
                    VoxelShape shape = Shapes.empty();
                    shape = Shapes.or(shape, Shapes.box(0.4375, 0, 0.4375, 0.5625, 1.5, 0.5625));
                    shape = Shapes.or(shape, Shapes.box(0.46875, 0.4375, 0.5625, 0.53125, 1.4375, 1.9375));
                    return shape;
                }
                default -> throw new IllegalStateException("Unexpected value: " + state.getValue(FACING));
            }
        }
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean notify) {
        super.neighborChanged(state, level, pos, block, fromPos, notify);
        if (!level.isClientSide) {
            if (level.getBlockState(pos).getValue(HALF).equals(DoubleBlockHalf.LOWER) && level.getBlockState(pos.above()).isAir()) {
                level.destroyBlock(pos, false);
            } else if (level.getBlockState(pos).getValue(HALF).equals(DoubleBlockHalf.UPPER) && level.getBlockState(pos.below()).isAir()) {
                level.destroyBlock(pos, false);
            }
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(level, pos, state, placer, itemStack);

        boolean waterAbove = level.getFluidState(pos.above()).is(Fluids.WATER);
        level.setBlock(pos.above(), state.setValue(HALF, DoubleBlockHalf.UPPER).setValue(WATERLOGGED, waterAbove), 3);

        BlockEntity blockEntity = level.getBlockEntity(pos.above());

        if (placer instanceof Player player) {
            if (blockEntity instanceof FlagBlockEntity flagEntity) {
                GameProfile profile = player.getGameProfile();
                flagEntity.setOwner(profile);
                flagEntity.saveWithoutMetadata();
            }
        }
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FlagBlockEntity(pos, state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return PushReaction.DESTROY;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    public long getSeed(BlockState state, BlockPos pos) {
        return Mth.getSeed(pos.getX(), pos.below(state.getValue(HALF).equals(DoubleBlockHalf.LOWER) ? 0 : 1).getY(), pos.getZ());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, HALF);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportRigidBlock(level, pos.below());
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.getValue(WATERLOGGED)) {
            return Fluids.WATER.getSource(false);
        }
        return super.getFluidState(state);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getLevel().getFluidState(ctx.getClickedPos());
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, fluidState.getType().equals(Fluids.WATER));
    }
}