package earth.terrarium.ad_astra.client.dimension.renderer;

import com.teamresourceful.resourcefullib.common.caches.CacheableBiFunction;
import com.teamresourceful.resourcefullib.common.color.Color;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.Random;

public class StarInformation {
    public static final Color BASE_COLOUR = new Color(255, 255, 255, 255);
    public static final Color[] STAR_COLOURS = new Color[]{
            BASE_COLOUR,
            new Color(204, 238, 255, 255),
            new Color(204, 153, 255, 255),
            new Color(255, 255, 153, 255),
            new Color(255, 204, 102, 255)
    };
    public static final CacheableBiFunction<Long, Integer, StarInformation> STAR_CACHE = new CacheableBiFunction<>(StarInformation::new);
    private final Vector3f[] param1;
    private final float[] multiplier;
    private final float[] randomPi;
    private final Color[][] colour;

    public StarInformation(long seed, int stars) {
        param1 = new Vector3f[stars];
        multiplier = new float[stars];
        randomPi = new float[stars];
        colour = new Color[stars][4];

        Random random = new Random(seed);
        for (int i = 0; i < stars; i++) {
            float d = random.nextFloat() * 2.0f - 1.0f;
            float e = random.nextFloat() * 2.0f - 1.0f;
            float f = random.nextFloat() * 2.0f - 1.0f;
            param1[i] = new Vector3f(d, e, f);
            multiplier[i] = 0.15f + random.nextFloat() * 0.01f;
            randomPi[i] = random.nextFloat() * Mth.TWO_PI;
            for (int j = 0; j < 4; j++) {
                colour[i][j] = STAR_COLOURS[random.nextInt(STAR_COLOURS.length)];
            }
        }
    }

    public Vector3f getParam1(int i) {
        return param1[i];
    }

    public float getMultiplier(int i) {
        return multiplier[i];
    }

    public float getRandomPi(int i) {
        return randomPi[i];
    }

    public Color getColour(int i, int j, boolean coloured) {
        return coloured ? colour[i][j] : BASE_COLOUR;
    }
}
