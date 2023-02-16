package earth.terrarium.ad_astra.common.block.machine;

public class SolarPanelBlock extends AnimatedMachineBlock {
    private final int energyPerTick;
    private final int capacity;

    public SolarPanelBlock(Properties builder, int energyPerTick, long capacity) {
        super(builder);
        this.energyPerTick = energyPerTick;
        this.capacity = (int) capacity;
    }

    public int getEnergyPerTick() {
        return energyPerTick;
    }

    public int getCapacity() {
        return capacity;
    }
}
