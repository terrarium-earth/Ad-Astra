package earth.terrarium.ad_astra.common.block.pipe;


import earth.terrarium.ad_astra.common.block.BasicEntityBlock;

public class PipeDuctBlock extends BasicEntityBlock implements Pipe {
    private final long transferRate;

    public PipeDuctBlock(long transferRate, Properties properties) {
        super(properties);
        this.transferRate = transferRate;
    }

    @Override
    public long getTransferRate() {
        return transferRate;
    }
}
