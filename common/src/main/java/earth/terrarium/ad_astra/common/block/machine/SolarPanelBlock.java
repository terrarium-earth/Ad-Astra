package earth.terrarium.ad_astra.common.block.machine;

public class SolarPanelBlock extends AbstractMachineBlock {

    public SolarPanelBlock(Properties properties) {
        super(properties);
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
    public int getBrightness() {
        return 0;
    }
}