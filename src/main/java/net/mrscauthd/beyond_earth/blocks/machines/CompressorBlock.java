package net.mrscauthd.beyond_earth.blocks.machines;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.blocks.machines.entity.CompressorBlockEntity;

public class CompressorBlock extends AbstractMachineBlock {


    public CompressorBlock(Settings settings) {
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
    public CompressorBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CompressorBlockEntity(pos, state);
    }
}