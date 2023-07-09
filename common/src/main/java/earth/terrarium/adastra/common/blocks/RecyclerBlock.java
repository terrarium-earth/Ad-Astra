package earth.terrarium.adastra.common.blocks;

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
public class RecyclerBlock extends DoubleMachineBlock {
    public static final VoxelShape BOTTOM_X_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(2, 5, 2, 14, 11, 14),
        Block.box(2, 11, 1, 14, 16, 15)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_Z_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(2, 5, 2, 14, 11, 14),
        Block.box(1, 11, 2, 15, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape TOP_X_SHAPE = Block.box(2, 0, 1, 14, 13, 15);

    public static final VoxelShape TOP_Z_SHAPE = Block.box(1, 0, 2, 15, 13, 14);

    public RecyclerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            return switch (state.getValue(FACING).getAxis()) {
                case X -> BOTTOM_X_SHAPE;
                case Z -> BOTTOM_Z_SHAPE;
                default -> null;
            };
        } else {
            return switch (state.getValue(FACING).getAxis()) {
                case X -> TOP_X_SHAPE;
                case Z -> TOP_Z_SHAPE;
                default -> null;
            };
        }
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
