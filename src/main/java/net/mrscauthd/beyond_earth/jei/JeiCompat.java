package net.mrscauthd.beyond_earth.jei;

import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.beyond_earth.compat.CompatibleMod;

public class JeiCompat extends CompatibleMod {

	public static final String MODID = "jei";

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
