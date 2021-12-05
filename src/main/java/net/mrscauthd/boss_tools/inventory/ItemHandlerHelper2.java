package net.mrscauthd.boss_tools.inventory;

import java.util.ArrayList;
import java.util.List;

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

	public static List<ItemStack> getStacks(IItemHandler handler) {
		List<ItemStack> list = new ArrayList<>();

		for (int i = 0; i < handler.getSlots(); i++) {
			list.add(handler.getStackInSlot(i));
		}

		return list;
	}

	private ItemHandlerHelper2() {

	}
}
