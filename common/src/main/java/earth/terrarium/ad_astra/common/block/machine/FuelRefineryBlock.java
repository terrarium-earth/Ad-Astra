package earth.terrarium.ad_astra.common.block.machine;

public class FuelRefineryBlock extends AbstractMachineBlock {

    public FuelRefineryBlock(Properties properties) {
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
}