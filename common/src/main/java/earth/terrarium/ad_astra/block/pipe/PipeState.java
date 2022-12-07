package earth.terrarium.ad_astra.block.pipe;

import net.minecraft.util.StringRepresentable;

public enum PipeState implements StringRepresentable {
    NONE("none"), NORMAL("normal"), INSERT("insert"), EXTRACT("extract");

    private final String name;

    PipeState(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
