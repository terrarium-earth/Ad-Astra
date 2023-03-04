package earth.terrarium.ad_astra.common.block.machine;

public class CompressorBlock extends AbstractMachineBlock {

    public CompressorBlock(Properties properties) {
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