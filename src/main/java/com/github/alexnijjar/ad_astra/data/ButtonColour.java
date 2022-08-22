package com.github.alexnijjar.ad_astra.data;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ColourHolder;

public enum ButtonColour {
    WHITE(255, 255, 255), GREY(128, 128, 128), BLACK(0, 0, 0), RED(230, 46, 0), DARK_RED(128, 0, 0), ORANGE(255, 102, 0), YELLOW(255, 255, 0), GREEN(0, 179, 0), DARK_GREEN(0, 102, 0), BLUE(0, 184, 230), DARK_BLUE(0, 102, 204), PURPLE(102, 0, 255);

    private final ColourHolder colour;

    private ButtonColour(int r, int g, int b) {
        this.colour = new ColourHolder(r / 255.0f, g / 255.0f, b / 255.0f);
    }

    public ColourHolder getColour() {
        return this.colour;
    }

    public static ButtonColour stringToColour(String string) {
        try {
            return ButtonColour.valueOf(string.toUpperCase());
        } catch (IllegalArgumentException e) {
            AdAstra.LOGGER.error('\"' + string + "\" is not a valid colour! Please choose one of the following colours: " + ButtonColour.values(), e);
            e.printStackTrace();
            return ButtonColour.WHITE;
        }
    }
}
