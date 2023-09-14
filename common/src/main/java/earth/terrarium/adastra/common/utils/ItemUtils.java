package earth.terrarium.adastra.common.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;

import java.util.stream.IntStream;

public class ItemUtils {

    public static void push(Container from, Container to, int[] fromSlots, Direction direction) {
        if (inventoryFull(to)) return;
        for (int slot : fromSlots) {
            ItemStack stack = from.getItem(slot);
            if (stack.isEmpty()) continue;
            if (!addItem(from, to, stack.copy(), IntStream.range(0, to.getContainerSize()).toArray(), direction)) {
                continue;
            }
            from.removeItem(slot, 1);
            from.setChanged();
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
                if (!worldlyContainer.canTakeItemThroughFace(slot, fromStack, direction)) continue;
            }
            if (to instanceof WorldlyContainer worldlyContainer) {
                int[] slots = worldlyContainer.getSlotsForFace(direction);
                if (IntStream.of(slots).noneMatch(i -> i == slot)) continue;
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

    public static boolean canAddItem(ItemStack input, ItemStack output) {
        return input.isEmpty() || (ItemStack.isSameItemSameTags(input, output) && output.getCount() + input.getCount() <= input.getMaxStackSize());
    }

    public static void addItem(Container container, int slot, ItemStack output) {
        ItemStack input = container.getItem(slot);
        if (input.isEmpty()) {
            container.setItem(slot, output.copy());
        } else if (ItemStack.isSameItemSameTags(input, output)) {
            input.grow(output.getCount());
        }
    }
}
