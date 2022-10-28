package earth.terrarium.ad_astra.util;

public class MathUtil {

    public static float invLerp(float delta, float start, float end) {
        return (delta - end) / (start - end);
    }
}
