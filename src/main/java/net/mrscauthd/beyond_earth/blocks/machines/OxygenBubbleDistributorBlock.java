package net.mrscauthd.beyond_earth.blocks.machines;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.blocks.machines.entity.OxygenBubbleDistributorBlockEntity;

public class OxygenBubbleDistributorBlock extends AbstractMachineBlock {

    public OxygenBubbleDistributorBlock(Settings settings) {
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
    public OxygenBubbleDistributorBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OxygenBubbleDistributorBlockEntity(pos, state);
    }
}