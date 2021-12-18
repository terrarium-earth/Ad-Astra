package net.mrscauthd.beyond_earth.inventory;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ItemHandlerHelper2 {

	public static boolean isEmpty(IItemHandler handler) {
		for (int i = 0; i < handler.getSlots(); i++) {
			if (!handler.getStackInSlot(i).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	public static NonNullList<ItemStack> getStacks(IItemHandler handler) {
		NonNullList<ItemStack> list = NonNullList.withSize(handler.getSlots(), ItemStack.EMPTY);

		for (int i = 0; i < handler.getSlots(); i++) {
			ItemStack stack = handler.getStackInSlot(i);
			list.set(i, stack);
		}

		return list;
	}

	private ItemHandlerHelper2() {

	}
}
