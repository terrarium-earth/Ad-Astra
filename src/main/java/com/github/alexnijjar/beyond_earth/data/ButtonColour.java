package com.github.alexnijjar.beyond_earth.data;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.util.ColourHolder;

public enum ButtonColour {
    WHITE(255, 255, 255, 255), GREY(128, 128, 128, ButtonColour.ALPHA), BLACK(0, 0, 0, ButtonColour.ALPHA), RED(230, 46, 0, ButtonColour.ALPHA), DARK_RED(128, 0, 0, ButtonColour.ALPHA), ORANGE(255, ButtonColour.ALPHA, 0, ButtonColour.ALPHA),
    YELLOW(255, 255, 0, ButtonColour.ALPHA), GREEN(0, 179, 0, ButtonColour.ALPHA), DARK_GREEN(0, ButtonColour.ALPHA, 0, ButtonColour.ALPHA), BLUE(0, 184, 230, ButtonColour.ALPHA), DARK_BLUE(0, ButtonColour.ALPHA, 204, ButtonColour.ALPHA),
    PURPLE(ButtonColour.ALPHA, 0, 255, ButtonColour.ALPHA);

    private final ColourHolder colour;
    public static final short ALPHA = 154;

    private ButtonColour(int r, int g, int b, int a) {
        this.colour = new ColourHolder(r / 255.0f, g / 255.0f, b / 255.0f, a / 255.0f);
    }

    public ColourHolder getColour() {
        return this.colour;
    }

    public static ButtonColour stringToColour(String string) {
        try {
            return ButtonColour.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            BeyondEarth.LOGGER.error('\"' + string + "\" is not a valid colour! Please choose one of the following colours: " + ButtonColour.values(), e);
            e.printStackTrace();
            return ButtonColour.WHITE;
        }
    }
}
