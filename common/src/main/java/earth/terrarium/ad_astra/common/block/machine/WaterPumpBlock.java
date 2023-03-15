package earth.terrarium.ad_astra.common.block.machine;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class WaterPumpBlock extends AbstractMachineBlock {

    public WaterPumpBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState state = this.defaultBlockState().setValue(POWERED, false);
        return this.useFacing() ? state.setValue(FACING, ctx.getHorizontalDirection()) : state;
    }
}