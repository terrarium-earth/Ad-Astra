package earth.terrarium.ad_astra.common.block.machine;

import earth.terrarium.ad_astra.common.block.machine.entity.CryoFreezerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CryoFreezerBlock extends AbstractMachineBlock {

    public CryoFreezerBlock(Properties properties) {
        super(properties);
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
    public CryoFreezerBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CryoFreezerBlockEntity(pos, state);
    }
}