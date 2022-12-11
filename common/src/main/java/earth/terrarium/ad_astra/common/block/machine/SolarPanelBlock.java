package earth.terrarium.ad_astra.common.block.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.SolarPanelBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class SolarPanelBlock extends AbstractMachineBlock {

    public SolarPanelBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    protected boolean useLit() {
        return true;
    }

    @Override
    public int getBrightness() {
        return 0;
    }

    @Override
    public SolarPanelBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SolarPanelBlockEntity(pos, state);
    }
}