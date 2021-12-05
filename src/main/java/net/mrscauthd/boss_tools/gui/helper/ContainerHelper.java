package net.mrscauthd.boss_tools.gui.helper;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Function;

public class ContainerHelper {
	public static void addInventorySlots(AbstractContainerMenu container, Inventory inv, int left, int top, Function<Slot, Slot> addSlot) {
		addInventorySlots(container, inv, left, top, top + 58, addSlot);
	}

	public static void addInventorySlots(AbstractContainerMenu container, Inventory inv, int left, int top, int hotbarY, Function<Slot, Slot> addSlot) {
		int rows = 3;
		int cols = 9;
		int offsetX = 18;
		int offsetY = 18;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++) {
				addSlot.apply(new Slot(inv, col + (row + 1) * cols, left + col * offsetX, top + row * offsetY));
			}
		}

		for (int col = 0; col < cols; ++col) {
			addSlot.apply(new Slot(inv, col, left + col * offsetX, hotbarY));
		}
	}

	public static ItemStack transferStackInSlot(AbstractContainerMenu container, Player player, int slotNumber, int containerIndex, Container inventory, IMergeItemStack mergeItemStack) {
		int containerSize = inventory.getContainerSize();
		return transferStackInSlot(container, player, slotNumber, containerIndex, containerSize, mergeItemStack);
	}

	public static ItemStack transferStackInSlot(AbstractContainerMenu container, Player player, int slotNumber, int containerIndex, int containerSize, IMergeItemStack mergeItemStack) {
		ItemStack itemStack = ItemStack.EMPTY;
		List<Slot> inventorySlots = container.slots;
		Slot slot = inventorySlots.get(slotNumber);

		if (slot != null && slot.hasItem()) {
			ItemStack slotStack = slot.getItem();
			itemStack = slotStack.copy();

			int playerInventoryStartIndex = containerIndex + containerSize;
			
			// Click Player Inventory
			if (slotNumber < playerInventoryStartIndex) {
				if (!mergeItemStack.mergeItemStack(slotStack, playerInventoryStartIndex, inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}

			}
			// Click Container Inventory
			else if (!mergeItemStack.mergeItemStack(slotStack, containerIndex, playerInventoryStartIndex, false)) {
				return ItemStack.EMPTY;
			}

			if (slotStack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}

		return itemStack;
	}

	public static ItemStack transferStackInSlot(AbstractContainerMenu container, Player player, int slotNumber, Container inventory, IMergeItemStack mergeItemStack) {
		return transferStackInSlot(container, player, slotNumber, 0, inventory, mergeItemStack);
	}

}
