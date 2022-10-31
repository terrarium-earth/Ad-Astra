package earth.terrarium.ad_astra.blocks.machines;

import earth.terrarium.ad_astra.blocks.machines.entity.CoalGeneratorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class CoalGeneratorBlock extends AbstractMachineBlock {

    public CoalGeneratorBlock(Properties settings) {
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
    public CoalGeneratorBlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CoalGeneratorBlockEntity(pos, state);
    }
}
