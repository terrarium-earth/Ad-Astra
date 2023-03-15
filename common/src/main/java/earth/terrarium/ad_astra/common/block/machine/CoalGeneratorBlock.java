package earth.terrarium.ad_astra.common.block.machine;

public class CoalGeneratorBlock extends AbstractMachineBlock {

    public CoalGeneratorBlock(Properties properties) {
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
