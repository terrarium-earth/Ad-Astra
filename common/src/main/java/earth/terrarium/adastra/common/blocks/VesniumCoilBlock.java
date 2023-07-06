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

@SuppressWarnings("deprecation")
public class VesniumCoilBlock extends DoubleMachineBlock {
    public static final VoxelShape BOTTOM_SHAPE = Shapes.join(
        Block.box(3, 5, 3, 13, 16, 13),
        Block.box(1, 0, 1, 15, 5, 15),
        BooleanOp.OR);

    public static final VoxelShape TOP_SHAPE = Block.box(3, 0, 3, 13, 12, 13);


    public VesniumCoilBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? BOTTOM_SHAPE : TOP_SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
