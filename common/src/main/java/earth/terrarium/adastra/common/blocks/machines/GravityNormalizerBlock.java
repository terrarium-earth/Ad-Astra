package earth.terrarium.adastra.common.blocks.machines;

import earth.terrarium.adastra.common.blocks.base.SidedMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class GravityNormalizerBlock extends SidedMachineBlock {
    public static final VoxelShape BOTTOM_SHAPE = Block.box(1, 0, 1, 15, 5, 15);

    public static final VoxelShape TOP_SHAPE = Block.box(1, 11, 1, 15, 16, 15);

    public static final VoxelShape NORTH_SIDE_SHAPE = Block.box(1, 1, 11, 15, 15, 16);

    public static final VoxelShape EAST_SIDE_SHAPE = Block.box(0, 1, 1, 5, 15, 15);

    public static final VoxelShape SOUTH_SIDE_SHAPE = Block.box(1, 1, 0, 15, 15, 5);

    public static final VoxelShape WEST_SIDE_SHAPE = Block.box(11, 1, 1, 16, 15, 15);

    public GravityNormalizerBlock(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("DuplicatedCode")
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACE)) {
            case FLOOR -> BOTTOM_SHAPE;
            case CEILING -> TOP_SHAPE;
            case WALL -> switch (state.getValue(FACING)) {
                case NORTH -> NORTH_SIDE_SHAPE;
                case EAST -> EAST_SIDE_SHAPE;
                case SOUTH -> SOUTH_SIDE_SHAPE;
                case WEST -> WEST_SIDE_SHAPE;
                default -> Shapes.block();
            };
        };
    }
}
