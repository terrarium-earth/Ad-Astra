package net.mrscauthd.astrocraft.gui.helper;

import net.minecraft.world.item.ItemStack;

@FunctionalInterface
public interface IMergeItemStack {
	boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection);
}
