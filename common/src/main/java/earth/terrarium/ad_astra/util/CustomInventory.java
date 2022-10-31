package earth.terrarium.ad_astra.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;

/**
 * A custom simple inventory that remembers the order the items were placed in. Source: <a href="https://discord.com/channels/507304429255393322/807617284129423370/957830913226575902">...</a>
 */
public class CustomInventory extends SimpleContainer {

    public CustomInventory(ItemStack... items) {
        super(items);
    }

    public CustomInventory(int size) {
        super(size);
    }

    @Override
    public void fromTag(ListTag nbtList) {
        for (int slot = 0; slot < nbtList.size(); ++slot) {
            ItemStack stack = ItemStack.of(nbtList.getCompound(slot));
            this.setItem(slot, stack);
        }
    }

    @Override
    public ListTag createTag() {
        ListTag nbtList = new ListTag();
        for (int slot = 0; slot < this.getContainerSize(); ++slot) {
            ItemStack stack = this.getItem(slot);
            nbtList.add(stack.save(new CompoundTag()));
        }
        return nbtList;
    }
}