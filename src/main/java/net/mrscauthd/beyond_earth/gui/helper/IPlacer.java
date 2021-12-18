package net.mrscauthd.beyond_earth.gui.helper;

import net.mrscauthd.beyond_earth.util.Rectangle2d;

@FunctionalInterface
public interface IPlacer {
	Rectangle2d place(int index, int left, int top, int mod);
}
