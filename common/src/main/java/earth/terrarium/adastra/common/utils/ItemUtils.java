package earth.terrarium.adastra.common.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.HopperBlockEntity;

public class ItemUtils {

    public static void push(Container from, Container to, int[] fromSlots, Direction direction) {
        if (inventoryFull(to)) return;
        for (int slot : fromSlots) {
            ItemStack stack = from.getItem(slot).copy();
            if (stack.isEmpty()) continue;
            ItemStack inserted = HopperBlockEntity.addItem(from, to, from.removeItem(slot, 1), direction);
            if (inserted.isEmpty()) continue;
            to.setChanged();
            return;
        }

    }

    public static void pull(Container from, Container to, int[] toSlots, Direction direction) {
        for (int i = 0; i < from.getContainerSize(); i++) {
            ItemStack stack = from.getItem(i);
            if (stack.isEmpty()) continue;
            if (!addItem(from, to, stack.copy(), toSlots, direction)) continue;
            from.removeItem(i, 1);
            to.setChanged();
            return;
        }
    }

    public static boolean inventoryFull(Container container) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) return false;
            if (stack.getCount() < stack.getMaxStackSize()) return false;
        }
        return true;
    }

    private static boolean addItem(Container from, Container to, ItemStack fromStack, int[] toSlots, Direction direction) {
        fromStack.setCount(1);
        for (int slot : toSlots) {
            if (from instanceof WorldlyContainer worldlyContainer) {
                if (!worldlyContainer.canPlaceItemThroughFace(slot, fromStack, direction)) continue;
            }
            ItemStack toStack = to.getItem(slot);
            if (toStack.isEmpty()) {
                to.setItem(slot, fromStack);
                return true;
            } else if (toStack.getCount() < toStack.getMaxStackSize() && ItemStack.isSameItemSameTags(toStack, fromStack)) {
                toStack.grow(1);
                return true;
            }
        }
        return false;
    }
}
