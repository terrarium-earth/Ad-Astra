package earth.terrarium.adastra.common.blocks.machines;

import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class WaterPumpBlock extends MachineBlock {

    public static final VoxelShape SHAPE_NORTH = Stream.of(
        Block.box(0, 0, 0, 16, 4, 16),
        Block.box(0, 4, 0, 9, 16, 16),
        Block.box(9, 4, 5, 16, 15, 15),
        Block.box(11, 4, 1, 14, 9, 4),
        Block.box(11, 9, 1, 14, 12, 5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_EAST = Stream.of(
        Block.box(0, 0, 0, 16, 4, 16),
        Block.box(0, 4, 0, 16, 16, 9),
        Block.box(1, 4, 9, 11, 15, 16),
        Block.box(12, 4, 11, 15, 9, 14),
        Block.box(11, 9, 11, 15, 12, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_SOUTH = Stream.of(
        Block.box(0, 0, 0, 16, 4, 16),
        Block.box(7, 4, 0, 16, 16, 16),
        Block.box(0, 4, 1, 7, 15, 11),
        Block.box(2, 4, 12, 5, 9, 15),
        Block.box(2, 9, 11, 5, 12, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape SHAPE_WEST = Stream.of(
        Block.box(0, 0, 0, 16, 4, 16),
        Block.box(0, 4, 7, 16, 16, 16),
        Block.box(9, 4, 0, 16, 15, 7),
        Block.box(11, 4, 2, 14, 9, 5),
        Block.box(11, 9, 2, 14, 12, 5)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public WaterPumpBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> SHAPE_NORTH;
            case EAST -> SHAPE_EAST;
            case SOUTH -> SHAPE_SOUTH;
            case WEST -> SHAPE_WEST;
            default -> super.getShape(state, level, pos, context);
        };
    }
}
