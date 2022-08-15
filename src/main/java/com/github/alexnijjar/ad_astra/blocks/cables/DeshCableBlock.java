package com.github.alexnijjar.ad_astra.blocks.cables;

public class DeshCableBlock extends CableBlock {

    public DeshCableBlock(Settings settings) {
        super(settings);
    }

    @Override
    public double getCableSize() {
        return 0.312;
    }

    @Override
    public int getEnergyDecay() {
        return 1;
    }

    @Override
    public int getEnergyTransfer() {
        return 16;
    }

    @Override
    public long getEnergyCapacity() {
        return 1024;
    }
}