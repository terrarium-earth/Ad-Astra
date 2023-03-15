package earth.terrarium.ad_astra.common.block.machine;

public class CryoFreezerBlock extends AbstractMachineBlock {

    public CryoFreezerBlock(Properties properties) {
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