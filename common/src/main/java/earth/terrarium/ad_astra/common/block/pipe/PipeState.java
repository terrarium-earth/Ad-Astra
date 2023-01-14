package earth.terrarium.ad_astra.common.block.pipe;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum PipeState implements StringRepresentable {
    NONE("none"),
    NORMAL("normal"),
    INSERT("insert"),
    EXTRACT("extract");

    private final String name;

    PipeState(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }
}
