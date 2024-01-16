package earth.terrarium.adastra.common.utils;

import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.item.ItemStack;

import java.util.stream.IntStream;

public class ItemUtils {

    /**
     * Pushes items from one container to another.
     *
     * @param from      The container to pull items from.
     * @param to        The container to push items to.
     * @param fromSlots The slots to pull items from.
     * @param direction The direction to push items to.
     */
    public static void push(Container from, Container to, int[] fromSlots, Direction direction) {
        if (inventoryFull(to)) return;
        for (int slot : fromSlots) {
            ItemStack stack = from.getItem(slot);
            if (stack.isEmpty()) continue;
            if (!addItem(to, stack.copyWithCount(1), IntStream.range(0, to.getContainerSize()).toArray(), direction)) {
                continue;
            }
            from.removeItem(slot, 1);
            from.setChanged();
            return;
        }
    }

    /**
     * Pulls items from one container to another.
     *
     * @param from      The container to pull items from.
     * @param to        The container to push items to.
     * @param toSlots   The slots to push items to.
     * @param direction The direction to pull items from.
     */
    public static void pull(Container from, Container to, int[] toSlots, Direction direction) {
        if (inventoryFull(to)) return;
        for (int i = 0; i < from.getContainerSize(); i++) {
            ItemStack stack = from.getItem(i);
            if (stack.isEmpty()) continue;
            if (!addItem(to, stack.copyWithCount(1), toSlots, direction)) continue;
            from.removeItem(i, 1);
            to.setChanged();
            return;
        }
    }

    /**
     * Checks if a container is full.
     *
     * @param container The container to check.
     * @return True if the container is full, false otherwise.
     */
    public static boolean inventoryFull(Container container) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty()) return false;
            if (stack.getCount() < stack.getMaxStackSize()) return false;
        }
        return true;
    }

    /**
     * Adds an item to a container.
     *
     * @param to        The container to push items to.
     * @param stack     The stack to add.
     * @param toSlots   The slots to push items to.
     * @param direction The direction to push items to.
     * @return True if the item was added, false otherwise.
     */
    private static boolean addItem(Container to, ItemStack stack, int[] toSlots, Direction direction) {
        for (int slot : toSlots) {
            if (to instanceof WorldlyContainer worldlyContainer) {
                int[] slots = worldlyContainer.getSlotsForFace(direction);
                if (IntStream.of(slots).noneMatch(i -> i == slot)) continue;
            }
            var toStack = to.getItem(slot);
            if (toStack.isEmpty()) {
                to.setItem(slot, stack);
                return true;
            } else if (canAddItem(toStack, stack)) {
                toStack.grow(1);
                return true;
            }
        }
        return false;
    }

    /**
     * Adds an item to a container.
     *
     * @param container The container to add the item to.
     * @param output    The output item.
     * @param slots     The potential slots to add the item to. The item will be added to the first available slot.
     */
    public static void addItem(Container container, ItemStack output, int... slots) {
        for (int slot : slots) {
            ItemStack input = container.getItem(slot);
            if (input.isEmpty()) {
                container.setItem(slot, output.copy());
                return;
            } else if (canAddItem(input, output)) {
                input.grow(output.getCount());
                return;
            }
        }
    }

    /**
     * Checks if an item can be added to another item.
     * An item can be added if the output item is empty or the stacks can be merged.
     *
     * @param input  The input item.
     * @param output The output item.
     * @return True if the item can be added, false otherwise.
     */
    public static boolean canAddItem(ItemStack input, ItemStack output) {
        return input.isEmpty()
            || (ItemStack.isSameItemSameTags(input, output)
            && output.getCount() + input.getCount() <= input.getMaxStackSize());
    }

    /**
     * Checks if an item can be added to a container.
     *
     * @param container The container to check.
     * @param output    The output item.
     * @param slots     The potential slots to add the item to.
     * @return True if the item can be added, false otherwise.
     */
    public static boolean canAddItem(Container container, ItemStack output, int... slots) {
        for (int slot : slots) {
            ItemStack input = container.getItem(slot);
            if (canAddItem(input, output)) return true;
        }
        return false;
    }
}
