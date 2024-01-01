package earth.terrarium.adastra.common.blocks.pipes;

public interface TransferablePipe {

    long transferRate();

    Type type();

    enum Type {
        ENERGY,
        FLUID,
    }
}
