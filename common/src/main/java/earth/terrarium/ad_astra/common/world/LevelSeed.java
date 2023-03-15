package earth.terrarium.ad_astra.common.world;

import earth.terrarium.ad_astra.client.dimension.renderer.StarInformation;

public class LevelSeed {

    private static long seed = 0;

    public static long getSeed() {
        return seed;
    }

    public static void setSeed(long seed) {
        LevelSeed.seed = seed;
        StarInformation.STAR_CACHE.clear();
    }
}