package earth.terrarium.ad_astra.blocks.machines;

import earth.terrarium.ad_astra.blocks.machines.entity.WaterPumpBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class WaterPumpBlock extends AbstractMachineBlock {

    public static VoxelShape SOUTH_SHAPE = Shapes.empty();
    public static VoxelShape NORTH_SHAPE = Shapes.empty();
    public static VoxelShape EAST_SHAPE = Shapes.empty();
    public static VoxelShape WEST_SHAPE = Shapes.empty();

    static {
        NORTH_SHAPE = Shapes.joinUnoptimized(NORTH_SHAPE, Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanOp.OR);
        NORTH_SHAPE = Shapes.joinUnoptimized(NORTH_SHAPE, Shapes.box(0.343749, 0.34375, 0, 0.65625, 0.65625, 0.0625), BooleanOp.OR);
        NORTH_SHAPE = Shapes.joinUnoptimized(NORTH_SHAPE, Shapes.box(0.375, 0.375, 0.0625, 0.625, 0.625, 0.375), BooleanOp.OR);
        NORTH_SHAPE = Shapes.joinUnoptimized(NORTH_SHAPE, Shapes.box(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanOp.OR);
        NORTH_SHAPE = Shapes.joinUnoptimized(NORTH_SHAPE, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);

        SOUTH_SHAPE = Shapes.joinUnoptimized(SOUTH_SHAPE, Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanOp.OR);
        SOUTH_SHAPE = Shapes.joinUnoptimized(SOUTH_SHAPE, Shapes.box(0.343749, 0.34375, 0.9375, 0.65625, 0.65625, 1), BooleanOp.OR);
        SOUTH_SHAPE = Shapes.joinUnoptimized(SOUTH_SHAPE, Shapes.box(0.375, 0.375, 0.625, 0.625, 0.625, 0.9375), BooleanOp.OR);
        SOUTH_SHAPE = Shapes.joinUnoptimized(SOUTH_SHAPE, Shapes.box(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanOp.OR);
        SOUTH_SHAPE = Shapes.joinUnoptimized(SOUTH_SHAPE, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);

        EAST_SHAPE = Shapes.joinUnoptimized(EAST_SHAPE, Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanOp.OR);
        EAST_SHAPE = Shapes.joinUnoptimized(EAST_SHAPE, Shapes.box(0.9375, 0.34375, 0.343749, 1, 0.65625, 0.65625), BooleanOp.OR);
        EAST_SHAPE = Shapes.joinUnoptimized(EAST_SHAPE, Shapes.box(0.625, 0.375, 0.375, 0.9375, 0.625, 0.625), BooleanOp.OR);
        EAST_SHAPE = Shapes.joinUnoptimized(EAST_SHAPE, Shapes.box(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanOp.OR);
        EAST_SHAPE = Shapes.joinUnoptimized(EAST_SHAPE, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);

        WEST_SHAPE = Shapes.joinUnoptimized(WEST_SHAPE, Shapes.box(0.34375, 0, 0.34375, 0.65625, 0.0625, 0.65625), BooleanOp.OR);
        WEST_SHAPE = Shapes.joinUnoptimized(WEST_SHAPE, Shapes.box(0, 0.34375, 0.343749, 0.0625, 0.65625, 0.65625), BooleanOp.OR);
        WEST_SHAPE = Shapes.joinUnoptimized(WEST_SHAPE, Shapes.box(0.0625, 0.375, 0.375, 0.375, 0.625, 0.625), BooleanOp.OR);
        WEST_SHAPE = Shapes.joinUnoptimized(WEST_SHAPE, Shapes.box(0.34375, 0.8125, 0.34375, 0.65625, 1, 0.65625), BooleanOp.OR);
        WEST_SHAPE = Shapes.joinUnoptimized(WEST_SHAPE, Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);
    }

    public WaterPumpBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case EAST -> EAST_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> throw new IllegalStateException("Unexpected value: " + state.getValue(FACING));
        };
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = this.defaultBlockState().setValue(POWERED, false);
        return this.useFacing() ? state.setValue(FACING, ctx.getHorizontalDirection()) : state;
    }

    @Override
    public WaterPumpBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WaterPumpBlockEntity(pos, state);
    }
}