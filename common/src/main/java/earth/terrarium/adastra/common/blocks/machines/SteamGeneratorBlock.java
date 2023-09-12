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
public class SteamGeneratorBlock extends DoubleMachineBlock {
    public static final VoxelShape BOTTOM_NORTH_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(3, 10, 3, 13, 16, 13),
        Block.box(4, 5, 4, 12, 10, 12),
        Block.box(12, 5, 6, 14, 16, 10),
        Block.box(2, 5, 6, 4, 16, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_EAST_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(3, 10, 3, 13, 16, 13),
        Block.box(4, 5, 4, 12, 10, 12),
        Block.box(6, 5, 12, 10, 16, 14),
        Block.box(6, 5, 2, 10, 16, 4)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_SOUTH_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(3, 10, 3, 13, 16, 13),
        Block.box(4, 5, 4, 12, 10, 12),
        Block.box(2, 5, 6, 4, 16, 10),
        Block.box(12, 5, 6, 14, 16, 10)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape BOTTOM_WEST_SHAPE = Stream.of(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(3, 10, 3, 13, 16, 13),
        Block.box(4, 5, 4, 12, 10, 12),
        Block.box(6, 5, 2, 10, 16, 4),
        Block.box(6, 5, 12, 10, 16, 14)
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public static final VoxelShape TOP_SHAPE = Block.box(3, 0, 3, 13, 12, 13);


    public SteamGeneratorBlock(Properties properties) {
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
