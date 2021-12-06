package net.mrscauthd.boss_tools.compat.hwyla;

import net.minecraft.resources.ResourceLocation;
import net.mrscauthd.boss_tools.compat.CompatibleMod;

public class HwylaCompat extends CompatibleMod {
	public static final String MODID = "waila";

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
