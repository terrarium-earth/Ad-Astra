package net.mrscauthd.beyond_earth.world.chunk.seedfixer;

public class SeedFixer {

    private static long SEED = 0;

    public static long getSeed() {
        return SEED;
    }

    public static long setSeedInternal(long seed) {
        SEED = seed;
        return seed;
    }
}