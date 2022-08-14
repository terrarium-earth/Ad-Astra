package com.github.alexnijjar.ad_astra.blocks.cables;

public class DeshCableBlock extends CableBlock {

    public DeshCableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public double getCableSize() {
        return 0.300;
    }

    @Override
    public int getEnergyDecay() {
        return 1;
    }

    @Override
    public int getEnergyTransfer() {
        return Integer.MAX_VALUE;
    }

    @Override
    public long getEnergyCapacity() {
        return 1024;
    }
}