package earth.terrarium.adastra.common.blocks.pipes;

public interface Pipe {

    long transferRate();

    Type type();

    enum Type {
        ENERGY,
        FLUID,
    }
}
