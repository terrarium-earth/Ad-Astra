package net.mrscauthd.boss_tools.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.mrscauthd.boss_tools.compat.hwyla.HwylaCompat;
import net.mrscauthd.boss_tools.compat.mekanism.MekanismCompat;
import net.mrscauthd.boss_tools.compat.theoneprobe.TOPCompat;
import net.mrscauthd.boss_tools.compat.tinkers.TinkersCompat;

public class CompatibleManager {

	public static final List<CompatibleMod> MODS;
	public static final TinkersCompat TINKERS;
	public static final TOPCompat TOP;
	public static final HwylaCompat HWYLA;
	public static final MekanismCompat MEKANISM;

	static {
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(TINKERS = new TinkersCompat());
		mods.add(TOP = new TOPCompat());
		mods.add(HWYLA = new HwylaCompat());
		mods.add(MEKANISM = new MekanismCompat());

		MODS = Collections.unmodifiableList(mods);
	}

	public static void loadAll() {
		for (CompatibleMod mod : MODS) {
			mod.tryLoad();
		}
	}
}
