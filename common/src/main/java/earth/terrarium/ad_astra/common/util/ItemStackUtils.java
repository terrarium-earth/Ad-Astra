package earth.terrarium.ad_astra.common.util;

import net.minecraft.world.item.ItemStack;

public class ItemStackUtils {

    public static ItemStack deriveCount(ItemStack item, int newCount) {
        item = item.copy();
        item.setCount(newCount);
        return item;
    }

    private ItemStackUtils() {

    }
}
