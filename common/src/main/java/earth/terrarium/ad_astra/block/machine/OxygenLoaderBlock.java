package earth.terrarium.ad_astra.block.machine;

import earth.terrarium.ad_astra.block.machine.entity.OxygenLoaderBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class OxygenLoaderBlock extends AbstractMachineBlock {

    public OxygenLoaderBlock(Properties settings) {
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
    public OxygenLoaderBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new OxygenLoaderBlockEntity(pos, state);
    }
}