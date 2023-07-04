package earth.terrarium.adastra.common.blocks;

import earth.terrarium.adastra.common.blocks.base.MachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class OxygenDistributorBlock extends MachineBlock {
    public static final EnumProperty<AttachFace> FACE = BlockStateProperties.ATTACH_FACE;

    public static final VoxelShape BOTTOM_SHAPE = Shapes.join(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(4, 5, 4, 12, 15, 12),
        BooleanOp.OR);

    public static final VoxelShape TOP_SHAPE = Shapes.join(
        Block.box(1, 11, 1, 15, 16, 15),
        Block.box(4, 1, 4, 12, 11, 12),
        BooleanOp.OR);

    public static final VoxelShape NORTH_SIDE_SHAPE = Shapes.join(
        Block.box(1, 1, 11, 15, 15, 16),
        Block.box(4, 4, 1, 12, 12, 11),
        BooleanOp.OR);

    public static final VoxelShape EAST_SIDE_SHAPE = Shapes.join(
        Block.box(0, 1, 1, 5, 15, 15),
        Block.box(5, 4, 4, 15, 12, 12),
        BooleanOp.OR);

    public static final VoxelShape SOUTH_SIDE_SHAPE = Shapes.join(
        Block.box(1, 1, 0, 15, 15, 5),
        Block.box(4, 4, 5, 12, 12, 15),
        BooleanOp.OR);

    public static final VoxelShape WEST_SIDE_SHAPE = Shapes.join(
        Block.box(11, 1, 1, 16, 15, 15),
        Block.box(1, 4, 4, 11, 12, 12),
        BooleanOp.OR);

    public OxygenDistributorBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(LIT, false)
            .setValue(FACE, AttachFace.FLOOR));
    }

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

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, LIT, FACE);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        for (Direction direction : context.getNearestLookingDirections()) {
            BlockState state;
            if (direction.getAxis() == Direction.Axis.Y) {
                state = this.defaultBlockState().setValue(FACE, direction == Direction.UP ? AttachFace.CEILING : AttachFace.FLOOR).setValue(FACING, context.getHorizontalDirection());
            } else {
                state = this.defaultBlockState().setValue(FACE, AttachFace.WALL).setValue(FACING, direction.getOpposite());
            }

            if (state.canSurvive(context.getLevel(), context.getClickedPos())) {
                return state;
            }
        }

        return null;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
