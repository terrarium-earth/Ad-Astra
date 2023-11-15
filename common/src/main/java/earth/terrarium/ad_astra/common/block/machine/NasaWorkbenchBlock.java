package earth.terrarium.ad_astra.common.block.machine;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;

public class NasaWorkbenchBlock extends AbstractMachineBlock {

    public NasaWorkbenchBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        return true;
    }

    @Override
    public boolean removeOutput() {
        return true;
    }
}