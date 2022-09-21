package com.github.alexnijjar.ad_astra.world;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class SoundUtil {

	private static boolean shouldPlay = false;

	public static boolean getSound() {
		return shouldPlay;
	}

	public static void setSound(boolean value) {
		shouldPlay = value;
	}
}