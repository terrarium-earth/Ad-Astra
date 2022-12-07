package earth.terrarium.ad_astra.block.machine;

import earth.terrarium.ad_astra.block.machine.entity.FuelRefineryBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FuelRefineryBlock extends AbstractMachineBlock {

    public FuelRefineryBlock(Properties settings) {
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
    public FuelRefineryBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FuelRefineryBlockEntity(pos, state);
    }
}