package earth.terrarium.adastra.common.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class IndustrialLampBlock extends FaceAttachedHorizontalDirectionalBlock {
    public static final VoxelShape NORTH_SHAPE = Shapes.join(
        Block.box(0, 3, 14, 16, 13, 16),
        Block.box(1, 4, 8, 15, 12, 14), BooleanOp.OR);

    public static final VoxelShape EAST_SHAPE = Shapes.join(
        Block.box(0, 3, 0, 2, 13, 16),
        Block.box(2, 4, 1, 8, 12, 15), BooleanOp.OR);

    public static final VoxelShape SOUTH_SHAPE = Shapes.join(
        Block.box(0, 3, 0, 16, 13, 2),
        Block.box(1, 4, 2, 15, 12, 8), BooleanOp.OR);

    public static final VoxelShape WEST_SHAPE = Shapes.join(
        Block.box(14, 3, 0, 16, 13, 16),
        Block.box(8, 4, 1, 14, 12, 15), BooleanOp.OR);

    public static final VoxelShape UP_SHAPE = Shapes.join(
        Block.box(0, 14, 3, 16, 16, 13),
        Block.box(1, 8, 4, 15, 14, 12), BooleanOp.OR);

    public static final VoxelShape DOWN_SHAPE = Shapes.join(
        Block.box(0, 0, 3, 16, 2, 13),
        Block.box(1, 2, 4, 15, 8, 12), BooleanOp.OR);

    public IndustrialLampBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(FACE, AttachFace.WALL));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACE)) {
            case FLOOR -> DOWN_SHAPE;
            case WALL -> switch (state.getValue(FACING)) {
                case NORTH -> NORTH_SHAPE;
                case EAST -> EAST_SHAPE;
                case SOUTH -> SOUTH_SHAPE;
                case WEST -> WEST_SHAPE;
                default -> UP_SHAPE;
            };
            case CEILING -> UP_SHAPE;
        };
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type) {
        return false;
    }
}
