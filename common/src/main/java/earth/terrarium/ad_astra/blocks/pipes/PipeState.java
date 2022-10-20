package earth.terrarium.ad_astra.blocks.pipes;

import net.minecraft.util.StringIdentifiable;

public enum PipeState implements StringIdentifiable {
    NONE("none"), NORMAL("normal"), INSERT("insert"), EXTRACT("extract");

    private final String name;

    PipeState(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
