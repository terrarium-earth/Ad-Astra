package earth.terrarium.ad_astra.common.block.door;

import net.minecraft.util.StringRepresentable;

import java.util.Locale;

public enum LocationState implements StringRepresentable {
    TOP_LEFT,
    TOP,
    TOP_RIGHT,
    LEFT,
    CENTER,
    RIGHT,
    BOTTOM_LEFT,
    BOTTOM,
    BOTTOM_RIGHT,
    ;


    public String toString() {
        return getSerializedName();
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
