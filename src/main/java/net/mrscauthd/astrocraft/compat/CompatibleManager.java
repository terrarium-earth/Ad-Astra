package net.mrscauthd.astrocraft.compat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.mrscauthd.astrocraft.compat.mekanism.MekanismCompat;
import net.mrscauthd.astrocraft.compat.theoneprobe.TOPCompat;
import net.mrscauthd.astrocraft.compat.tinkers.TinkersCompat;
import net.mrscauthd.astrocraft.compat.waila.WailaCompat;

public class CompatibleManager {

	public static final List<CompatibleMod> MODS;
	public static final TinkersCompat TINKERS;
	public static final TOPCompat TOP;
	public static final WailaCompat WAILA;
	public static final MekanismCompat MEKANISM;

	static {
		List<CompatibleMod> mods = new ArrayList<>();
		mods.add(TINKERS = new TinkersCompat());
		mods.add(TOP = new TOPCompat());
		mods.add(WAILA = new WailaCompat());
		mods.add(MEKANISM = new MekanismCompat());

		for (CompatibleMod mod : mods) {
			mod.tryLoad();
		}

		MODS = Collections.unmodifiableList(mods);
	}

	public static void visit() {

	}

}
