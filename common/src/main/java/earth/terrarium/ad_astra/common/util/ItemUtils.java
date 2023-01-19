package earth.terrarium.ad_astra.common.util;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.function.IntPredicate;

public class ItemUtils {
    public static ItemStack addItem(Container container, ItemStack stack, IntPredicate slotFilter) {
        ItemStack itemStack = stack.copy();
        moveItemToOccupiedSlotsWithSameType(container, itemStack, slotFilter);
        if (itemStack.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            moveItemToEmptySlots(container, itemStack, slotFilter);
            return itemStack.isEmpty() ? ItemStack.EMPTY : itemStack;
        }
    }

    private static void moveItemToOccupiedSlotsWithSameType(Container container, ItemStack stack, IntPredicate slotFilter) {
        for (int i = 0; i < container.getContainerSize(); ++i) {
            if (slotFilter.test(i)) {
                ItemStack itemStack = container.getItem(i);
                if (ItemStack.isSameItemSameTags(itemStack, stack)) {
                    moveItemsBetweenStacks(container, stack, itemStack);
                    if (stack.isEmpty()) {
                        return;
                    }
                }
            }
        }
    }

    private static void moveItemsBetweenStacks(Container container, ItemStack stack, ItemStack other) {
        int i = Math.min(container.getMaxStackSize(), other.getMaxStackSize());
        int j = Math.min(stack.getCount(), i - other.getCount());
        if (j > 0) {
            other.grow(j);
            stack.shrink(j);
            container.setChanged();
        }
    }

    private static void moveItemToEmptySlots(Container container, ItemStack stack, IntPredicate slotFilter) {
        for (int i = 0; i < container.getContainerSize(); ++i) {
            if (slotFilter.test(i)) {
                ItemStack itemStack = container.getItem(i);
                if (itemStack.isEmpty()) {
                    container.setItem(i, stack.copy());
                    stack.setCount(0);
                    return;
                }
            }
        }
    }
}
