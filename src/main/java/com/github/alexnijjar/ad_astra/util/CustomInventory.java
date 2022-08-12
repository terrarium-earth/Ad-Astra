package com.github.alexnijjar.ad_astra.util;

import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;

/**
 * A custom simple inventory that remembers the order the items were placed in. Source:
 * https://discord.com/channels/507304429255393322/807617284129423370/957830913226575902
 */
public class CustomInventory extends SimpleInventory {

    public CustomInventory(ItemStack... items) {
        super(items);
    }

    public CustomInventory(int size) {
        super(size);
    }

    @Override
    public void readNbtList(NbtList nbtList) {
        for (int slot = 0; slot < nbtList.size(); ++slot) {
            ItemStack stack = ItemStack.fromNbt(nbtList.getCompound(slot));
            this.setStack(slot, stack);
        }
    }

    @Override
    public NbtList toNbtList() {
        NbtList nbtList = new NbtList();
        for (int slot = 0; slot < this.size(); ++slot) {
            ItemStack stack = this.getStack(slot);
            nbtList.add(stack.writeNbt(new NbtCompound()));
        }
        return nbtList;
    }
}