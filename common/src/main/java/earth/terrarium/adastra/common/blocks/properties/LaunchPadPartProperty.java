package earth.terrarium.adastra.common.blocks.properties;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public enum LaunchPadPartProperty implements StringRepresentable {
    TOP_LEFT(-1, 1),
    TOP(0, 1),
    TOP_RIGHT(1, 1),
    LEFT(-1, 0),
    CENTER(0, 0),
    RIGHT(1, 0),
    BOTTOM_LEFT(-1, -1),
    BOTTOM(0, -1),
    BOTTOM_RIGHT(1, -1);

    private final int xOffset;
    private final int yOffset;

    LaunchPadPartProperty(int xOffset, int yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int xOffset() {
        return this.xOffset;
    }

    public int yOffset() {
        return this.yOffset;
    }

    public boolean isController() {
        return this == CENTER;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name().toLowerCase(Locale.ROOT);
    }

    @Override
    public String toString() {
        return getSerializedName();
    }
}
