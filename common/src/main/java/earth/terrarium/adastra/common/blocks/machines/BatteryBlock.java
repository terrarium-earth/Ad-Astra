package earth.terrarium.adastra.common.blocks.machines;

import earth.terrarium.adastra.common.blocks.base.DoubleMachineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

@SuppressWarnings("deprecation")
public class BatteryBlock extends DoubleMachineBlock {
    public static final IntegerProperty CHARGE = IntegerProperty.create("charge", 0, 4);

    public static final VoxelShape BOTTOM_SHAPE = Shapes.join(
        Block.box(1, 0, 1, 15, 5, 15),
        Block.box(2, 5, 2, 14, 16, 14),
        BooleanOp.OR);

    public static final VoxelShape TOP_SHAPE = Shapes.join(
        Block.box(2, 0, 2, 14, 9, 14),
        Block.box(4, 9, 4, 12, 13, 12),
        BooleanOp.OR);


    public BatteryBlock(Properties properties) {
        super(properties, true);
        registerDefaultState(stateDefinition.any()
            .setValue(FACING, Direction.NORTH)
            .setValue(POWERED, false)
            .setValue(LIT, false)
            .setValue(HALF, DoubleBlockHalf.LOWER)
            .setValue(CHARGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CHARGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? BOTTOM_SHAPE : TOP_SHAPE;
    }
}
