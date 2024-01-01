package earth.terrarium.adastra.common.blocks.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum PipeProperty implements StringRepresentable {
    NONE,
    NORMAL,
    INSERT,
    EXTRACT;

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return getSerializedName();
    }

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isNormal() {
        return this == NORMAL;
    }

    public boolean isInsert() {
        return this == INSERT;
    }

    public boolean isExtract() {
        return this == EXTRACT;
    }
}
