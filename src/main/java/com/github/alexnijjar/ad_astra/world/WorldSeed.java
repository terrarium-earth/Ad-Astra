package com.github.alexnijjar.ad_astra.world;

public class WorldSeed {

	private static long seed = 0;

	public static long getSeed() {
		return seed;
	}

	public static void setSeed(long seed) {
		WorldSeed.seed = seed;
	}
}