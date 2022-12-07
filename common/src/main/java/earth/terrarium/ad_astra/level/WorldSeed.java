package earth.terrarium.ad_astra.level;

import earth.terrarium.ad_astra.client.dimension.renderer.StarInformation;

public class WorldSeed {

    private static long seed = 0;

    public static long getSeed() {
        return seed;
    }

    public static void setSeed(long seed) {
        WorldSeed.seed = seed;
        StarInformation.STAR_CACHE.clear();
    }
}