package net.mrscauthd.beyond_earth.blocks.machines;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.blocks.machines.entity.OxygenLoaderBlockEntity;

public class OxygenLoaderBlock extends AbstractMachineBlock {


    public OxygenLoaderBlock(Settings settings) {
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
    public OxygenLoaderBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OxygenLoaderBlockEntity(pos, state);
    }
}