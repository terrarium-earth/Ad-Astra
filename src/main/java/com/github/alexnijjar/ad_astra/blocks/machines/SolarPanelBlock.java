package com.github.alexnijjar.beyond_earth.blocks.machines;

import com.github.alexnijjar.beyond_earth.blocks.machines.entity.SolarPanelBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class SolarPanelBlock extends AbstractMachineBlock {

    public SolarPanelBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean useFacing() {
        return true;
    }

    @Override
    public SolarPanelBlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SolarPanelBlockEntity(pos, state);
    }
}