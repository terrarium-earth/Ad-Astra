package net.mrscauthd.astrocraft.gui.helper;

import net.mrscauthd.astrocraft.util.Rectangle2d;

@FunctionalInterface
public interface IPlacer {
	Rectangle2d place(int index, int left, int top, int mod);
}
