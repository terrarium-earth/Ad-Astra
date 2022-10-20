package earth.terrarium.ad_astra.data;

import earth.terrarium.ad_astra.AdAstra;
import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.color.Color;

import java.util.Arrays;

public enum ButtonColour {
	WHITE(255, 255, 255, 255), //
	GREY(128, 128, 128), //
	BLACK(0, 0, 0), //
	RED(230, 46, 0), //
	DARK_RED(128, 0, 0), //
	ORANGE(255, 102, 0), //
	YELLOW(255, 255, 0), //
	GREEN(0, 179, 0), //
	DARK_GREEN(0, 102, 0), //
	TURQUOISE(64, 224, 208), //
	LIGHT_BLUE(153, 255, 255), //
	BLUE(0, 184, 230), //
	DARK_BLUE(0, 51, 153), //
	PURPLE(153, 51, 255);

	public static final Codec<ButtonColour> CODEC = Codec.STRING.xmap(ButtonColour::stringToColour, ButtonColour::toString);

	private final Color colour;
	public static final short ALPHA = 154;

	ButtonColour(int r, int g, int b) {
		this(r, g, b, ALPHA);
	}

	ButtonColour(int r, int g, int b, int a) {
		this.colour = new Color(r, g, b, a);
	}

	public Color getColour() {
		return this.colour;
	}

	public static ButtonColour stringToColour(String string) {
		try {
			return ButtonColour.valueOf(string.toUpperCase());
		} catch (IllegalArgumentException e) {
			AdAstra.LOGGER.error('\"' + string + "\" is not a valid colour! Please choose one of the following colours: " + Arrays.toString(ButtonColour.values()), e);
			e.printStackTrace();
			return ButtonColour.WHITE;
		}
	}
}
