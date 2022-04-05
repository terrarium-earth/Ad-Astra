package net.mrscauthd.beyond_earth.world;

public class WorldSeed {

    private static long SEED = 0;

    public static long getSeed() {
        return SEED;
    }

    public static void setSeed(long seed) {
        SEED = seed;
    }
}
