package net.mrscauthd.beyond_earth.compat.mekanism;

import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.compat.CompatibleMod;

public class MekanismCompat extends CompatibleMod {
	public static final String MODID = "mekanism";

	public static ResourceLocation rl(String path) {
		return new ResourceLocation(MODID, path);
	}

	@Override
	public String getModID() {
		return MODID;
	}

	@Override
	protected void onLoad() {

	}

}