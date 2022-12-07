package earth.terrarium.ad_astra.block.machine;

import earth.terrarium.ad_astra.block.machine.entity.WaterPumpBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;

public class WaterPumpBlock extends AbstractMachineBlock {

    public WaterPumpBlock(Properties settings) {
        super(settings);
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

    @Override
    public WaterPumpBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WaterPumpBlockEntity(pos, state);
    }
}