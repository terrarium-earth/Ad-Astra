package net.mrscauthd.beyond_earth.cable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.mrscauthd.beyond_earth.machines.AbstractMachineBlock;

public class EnergyCable extends AbstractMachineBlock<EnergyCableTileEntity> {

    public EnergyCable(Properties p_49224_) {
        super(p_49224_);
    }

    @Override
    public EnergyCableTileEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new EnergyCableTileEntity(pos, state);
    }
}
