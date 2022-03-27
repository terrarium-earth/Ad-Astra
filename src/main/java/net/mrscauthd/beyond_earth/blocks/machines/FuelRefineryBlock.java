package net.mrscauthd.beyond_earth.blocks.machines;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.mrscauthd.beyond_earth.blocks.machines.entity.FuelRefineryBlockEntity;

public class FuelRefineryBlock extends AbstractMachineBlock {


    public FuelRefineryBlock(Settings settings) {
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
    public FuelRefineryBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FuelRefineryBlockEntity(pos, state);
    }
}