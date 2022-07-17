package com.github.alexnijjar.beyond_earth.gui;

import com.github.alexnijjar.beyond_earth.items.vehicles.VehicleItem;

import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;

/**
 * A slot that prevents placing items with an inventory. For example, a rocket with an inventory or a shulker box. This is to
 * prevent cheating with infinite slots.
 */
public class NoInventorySlot extends Slot {

    public NoInventorySlot(Inventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        Item item = stack.getItem();
        return !(item instanceof VehicleItem) && !stack.getOrCreateNbt().contains("Items") && !(item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof ShulkerBoxBlock);
    }
}