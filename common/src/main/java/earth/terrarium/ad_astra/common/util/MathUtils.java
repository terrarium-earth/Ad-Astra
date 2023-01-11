package earth.terrarium.ad_astra.common.util;

public class MathUtils {

    public static float invLerp(float delta, float start, float end) {
        return (delta - end) / (start - end);
    }
}
