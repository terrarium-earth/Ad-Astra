package earth.terrarium.ad_astra.common.data;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.ad_astra.AdAstra;

import java.util.Arrays;
import java.util.Locale;

public enum ButtonColor {
    WHITE(255, 255, 255, 255),
    GREY(128, 128, 128),
    BLACK(0, 0, 0),
    RED(230, 46, 0),
    DARK_RED(128, 0, 0),
    ORANGE(255, 102, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 179, 0),
    DARK_GREEN(0, 102, 0),
    TURQUOISE(64, 224, 208),
    LIGHT_BLUE(153, 255, 255),
    BLUE(0, 184, 230),
    DARK_BLUE(0, 51, 153),
    PURPLE(153, 51, 255);

    public static final Codec<ButtonColor> CODEC = Codec.STRING.xmap(ButtonColor::stringToColor, ButtonColor::toString);
    public static final short ALPHA = 154;
    private final Color color;

    ButtonColor(int r, int g, int b) {
        this(r, g, b, ALPHA);
    }

    ButtonColor(int r, int g, int b, int a) {
        this.color = new Color(r, g, b, a);
    }

    public static ButtonColor stringToColor(String string) {
        try {
            return ButtonColor.valueOf(string.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            AdAstra.LOGGER.error('\"' + string + "\" is not a valid color! Please choose one of the following colors: " + Arrays.toString(ButtonColor.values()), e);
            e.printStackTrace();
            return ButtonColor.WHITE;
        }
    }

    public Color getColor() {
        return this.color;
    }
}
