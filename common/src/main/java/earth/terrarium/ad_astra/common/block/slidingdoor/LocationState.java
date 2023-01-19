package earth.terrarium.ad_astra.common.block.slidingdoor;

import net.minecraft.util.StringRepresentable;

public enum LocationState implements StringRepresentable {
    TOP_LEFT("top_left"),
    TOP("top"),
    TOP_RIGHT("top_right"),
    LEFT("left"),
    CENTER("center"),
    RIGHT("right"),
    BOTTOM_LEFT("bottom_left"),
    BOTTOM("bottom"),
    BOTTOM_RIGHT("bottom_right");

    private final String name;

    LocationState(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}
