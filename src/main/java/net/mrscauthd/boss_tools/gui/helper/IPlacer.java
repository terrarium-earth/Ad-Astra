package net.mrscauthd.boss_tools.gui.helper;

import net.minecraft.client.renderer.Rect2i;

@FunctionalInterface
public interface IPlacer {
	Rect2i place(int index, int left, int top, int mod);
}
