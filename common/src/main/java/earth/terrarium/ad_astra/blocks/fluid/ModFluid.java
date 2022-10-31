package earth.terrarium.ad_astra.blocks.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;

public abstract class ModFluid extends FlowingFluid {

    @Override
    public boolean isSame(Fluid fluid) {
        return fluid.equals(getSource()) || fluid.equals(getFlowing());
    }

    @Override
    protected boolean canConvertToSource() {
        return false;
    }

    @Override
    protected void beforeDestroyingBlock(LevelAccessor level, BlockPos pos, BlockState state) {
        final BlockEntity blockEntity = state.hasBlockEntity() ? level.getBlockEntity(pos) : null;
        Block.dropResources(state, level, pos, blockEntity);
    }

    @Override
    protected boolean canBeReplacedWith(FluidState fluidState, BlockGetter blockView, BlockPos blockPos, Fluid fluid, Direction direction) {
        return false;
    }

    @Override
    protected int getSlopeFindDistance(LevelReader levelView) {
        return 4;
    }

    @Override
    protected int getDropOff(LevelReader levelView) {
        return 1;
    }

    @Override
    public int getTickDelay(LevelReader levelView) {
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        return 100.0f;
    }
}