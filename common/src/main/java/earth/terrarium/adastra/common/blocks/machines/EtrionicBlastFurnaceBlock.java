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
public class EtrionicBlastFurnaceBlock extends MachineBlock {

    public static final VoxelShape BOTTOM_NORTH_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(5, 5, 11, 13, 16, 13),
        Block.box(3, 5, 3, 11, 16, 5),
        Block.box(3, 5, 5, 5, 16, 13),
        Block.box(11, 5, 3, 13, 16, 11),
        Block.box(3, 0, 0, 13, 14, 3),
        Block.box(5, 16, 5, 11, 16, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_EAST_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(3, 5, 5, 5, 16, 13),
        Block.box(11, 5, 3, 13, 16, 11),
        Block.box(3, 5, 3, 11, 16, 5),
        Block.box(5, 5, 11, 13, 16, 13),
        Block.box(13, 0, 3, 16, 14, 13),
        Block.box(5, 16, 5, 11, 16, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_SOUTH_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(11, 5, 3, 13, 16, 11),
        Block.box(3, 5, 3, 11, 16, 5),
        Block.box(3, 5, 5, 5, 16, 13),
        Block.box(5, 5, 11, 13, 16, 13),
        Block.box(3, 0, 13, 13, 14, 16),
        Block.box(5, 16, 5, 11, 16, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_WEST_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(3, 5, 3, 11, 16, 5),
        Block.box(3, 5, 5, 5, 16, 13),
        Block.box(11, 5, 3, 13, 16, 11),
        Block.box(5, 5, 11, 13, 16, 13),
        Block.box(0, 0, 3, 3, 14, 13),
        Block.box(5, 16, 5, 11, 16, 11)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public EtrionicBlastFurnaceBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> BOTTOM_NORTH_SHAPE;
            case EAST -> BOTTOM_EAST_SHAPE;
            case SOUTH -> BOTTOM_SOUTH_SHAPE;
            case WEST -> BOTTOM_WEST_SHAPE;
            default -> null;
        };
    }
}
