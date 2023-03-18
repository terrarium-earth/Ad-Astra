package earth.terrarium.ad_astra.common.util;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStackUtils {

    public static ItemStack deriveCount(ItemStack item, int newCount) {
        item = item.copy();
        item.setCount(newCount);
        return item;
    }

    public static String getRegistryPath(ItemStack item) {
        return getRegistryPath(item.getItem());
    }

    public static String getRegistryPath(Item item) {
        return Registry.ITEM.getKey(item).getPath();
    }

    private ItemStackUtils() {

    }
}
