package earth.terrarium.adastra.common.blocks.machines;

import earth.terrarium.adastra.common.blocks.base.DoubleMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public class HydraulicPressBlock extends DoubleMachineBlock {
    public static final VoxelShape BOTTOM_NORTH_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(4, 5, 4, 12, 7, 12),
        Block.box(3, 0, 14, 13, 14, 16),
        Block.box(2, 5, 2, 14, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_EAST_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(4, 5, 4, 12, 7, 12),
        Block.box(0, 0, 3, 2, 14, 13),
        Block.box(2, 5, 2, 14, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_SOUTH_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(4, 5, 4, 12, 7, 12),
        Block.box(3, 0, 0, 13, 14, 2),
        Block.box(2, 5, 2, 14, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_WEST_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(4, 5, 4, 12, 7, 12),
        Block.box(14, 0, 3, 16, 14, 13),
        Block.box(2, 5, 2, 14, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape TOP_SHAPE = Block.box(2, 0, 2, 14, 16, 14);


    public HydraulicPressBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return switch (state.getValue(FACING)) {
                case NORTH -> BOTTOM_NORTH_SHAPE;
                case EAST -> BOTTOM_EAST_SHAPE;
                case SOUTH -> BOTTOM_SOUTH_SHAPE;
                case WEST -> BOTTOM_WEST_SHAPE;
                default -> null;
            };
        } else {
            return TOP_SHAPE;
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
