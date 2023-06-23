package earth.terrarium.ad_astra.common.world;

public class SoundUtil {

    private static boolean shouldPlay = false;

    public static boolean getSound() {
        return shouldPlay;
    }

    public static void setSound(boolean value) {
        shouldPlay = value;
    }
}