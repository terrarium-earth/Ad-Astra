package net.mrscauthd.boss_tools.compat.mekanism;

import net.minecraft.util.ResourceLocation;
import net.mrscauthd.boss_tools.compat.CompatibleMod;

import ResourceLocation;

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