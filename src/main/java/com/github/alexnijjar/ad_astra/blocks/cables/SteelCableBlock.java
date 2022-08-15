package com.github.alexnijjar.ad_astra.blocks.cables;

public class SteelCableBlock extends CableBlock {

    public SteelCableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public double getCableSize() {
        return 0.344;
    }

    @Override
    public int getEnergyDecay() {
        return 2;
    }

    @Override
    public int getEnergyTransfer() {
        return 32;
    }

    @Override
    public long getEnergyCapacity() {
        return 32;
    }
}