package net.mrscauthd.boss_tools.gui.helper;

import java.util.List;
import java.util.function.Function;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;

public class ContainerHelper {
	public static void addInventorySlots(Container container, PlayerInventory inv, int left, int top, Function<Slot, Slot> addSlot) {
		addInventorySlots(container, inv, left, top, top + 58, addSlot);
	}

	public static void addInventorySlots(Container container, PlayerInventory inv, int left, int top, int hotbarY, Function<Slot, Slot> addSlot) {
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

	public static ItemStack transferStackInSlot(Container container, PlayerEntity player, int slotNumber, int containerIndex, IInventory inventory, IMergeItemStack mergeItemStack) {
		int containerSize = inventory.getSizeInventory();
		return transferStackInSlot(container, player, slotNumber, containerIndex, containerSize, mergeItemStack);
	}

	public static ItemStack transferStackInSlot(Container container, PlayerEntity player, int slotNumber, int containerIndex, int containerSize, IMergeItemStack mergeItemStack) {
		ItemStack itemStack = ItemStack.EMPTY;
		List<Slot> inventorySlots = container.inventorySlots;
		Slot slot = inventorySlots.get(slotNumber);

		if (slot != null && slot.getHasStack()) {
			ItemStack slotStack = slot.getStack();
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
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemStack;
	}

	public static ItemStack transferStackInSlot(Container container, PlayerEntity player, int slotNumber, IInventory inventory, IMergeItemStack mergeItemStack) {
		return transferStackInSlot(container, player, slotNumber, 0, inventory, mergeItemStack);
	}

}
