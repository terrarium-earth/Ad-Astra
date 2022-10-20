package earth.terrarium.ad_astra.util;

import com.teamresourceful.resourcefullib.common.color.Color;
import net.minecraft.util.math.Vec3d;

public final class ColourUtils {

    public static Color lighten(Color colour, float amount) {
        return new Color((int) (colour.getIntRed() + amount * 255),
                (int) (colour.getIntGreen() + amount * 255),
                (int) (colour.getIntBlue() + amount * 255),
                colour.getIntAlpha());
    }

    public static Vec3d toVector(Color colour) {
        return new Vec3d(colour.getIntRed() / 255.0f, colour.getIntGreen() / 255.0f, colour.getIntBlue() / 255.0f);
    }
}
