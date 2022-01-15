package net.mrscauthd.beyond_earth.world.chunk.seedfixer;

public class SeedFixer {

    private static long SEED = 0;

    /**
     * Call this in a mod's BiomeSource, ChunkGenerator, etc to have access to the default seed given to the server.
     * @return The server's default seed if it exist. Otherwise, returns 0
     */
    public static long getSeed() {
        return SEED;
    }

    /**
     * DO NOT USE THIS METHOD
     * For internal Forge use only
     */
    public static long setSeedInternal(long seed) {
        SEED = seed;
        return seed;
    }
}