package earth.terrarium.adastra.common.utils;

import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

public class FluidUtils {

    public static FluidHolder getTank(ItemStack stack) {
        return getTank(stack, 0);
    }

    public static FluidHolder getTank(ItemStack stack, int tank) {
        return FluidApi.getItemFluidContainer(new ItemStackHolder(stack)).getFluids().get(tank);
    }

    public static boolean hasFluid(ItemStack stack) {
        return hasFluid(stack, 0);
    }

    public static boolean hasFluid(ItemStack stack, int tank) {
        return !getTank(stack, tank).isEmpty();
    }

    public static Fluid getFluid(ItemStack stack) {
        return getFluid(stack, 0);
    }

    public static Fluid getFluid(ItemStack stack, int tank) {
        return getTank(stack, tank).getFluid();
    }

    public static long getFluidAmount(ItemStack stack) {
        return getFluidAmount(stack, 0);
    }

    public static long getFluidAmount(ItemStack stack, int tank) {
        return getTank(stack, tank).getFluidAmount();
    }

    public static long getTankCapacity(ItemStack stack) {
        return getTankCapacity(stack, 0);
    }

    public static long getTankCapacity(ItemStack stack, int tank) {
        return FluidApi.getItemFluidContainer(new ItemStackHolder(stack)).getTankCapacity(tank);
    }

    public static long insert(ItemStackHolder stack, FluidHolder fluid) {
        return FluidApi.getItemFluidContainer(stack).insertFluid(fluid, false);
    }

    public static FluidHolder extract(ItemStackHolder stack, FluidHolder fluid) {
        return FluidApi.getItemFluidContainer(stack).extractFluid(fluid, false);
    }

    /**
     * Moves fluid from a stack to a fluid container
     *
     * @param container      The container that has the stack
     * @param fluidContainer The fluid container to move the fluid to
     * @param slot           The slot of the stack
     * @param resultSlot     The slot where the emptied stack will be moved to if successful
     * @param tank           The fluid container tank to insert the fluid to.
     */
    public static void moveItemToContainer(Container container, FluidContainer fluidContainer, int slot, int resultSlot, int tank) {
        var stack = container.getItem(slot);
        // Don't do anything if the stack is empty or doesn't contain a fluid container.
        if (stack.isEmpty() || !FluidApi.isFluidContainingItem(stack)) return;
        var resultStack = container.getItem(resultSlot);

        // Get the fluid container from the item
        ItemStackHolder stackHolder = new ItemStackHolder(stack.copyWithCount(1));
        FluidContainer itemFluidContainer = FluidApi.getItemFluidContainer(stackHolder);
        FluidHolder amount = itemFluidContainer.getFluids().get(tank).copyHolder();
        if (amount.isEmpty()) return;

        // Don't do anything if the resulting emptied stack can not be moved to the result slot.
        if (!resultStack.isEmpty()) {
            var emptyStack = getEmptyStack(stackHolder, amount);
            if (!ItemUtils.canAddItem(emptyStack, resultStack)) return;
        }

        // Move the fluid from the item to the container
        if (FluidApi.moveFluid(itemFluidContainer, fluidContainer, amount, true) == 0) return;
        FluidApi.moveFluid(itemFluidContainer, fluidContainer, amount, false);

        var result = stackHolder.getStack();

        if (resultStack.isEmpty()) {
            // If the result slot is empty, move the item there.
            stack.shrink(1);
            container.setItem(resultSlot, result);
        } else if (ItemUtils.canAddItem(result, resultStack)) {
            // Or else increment the result slot.
            stack.shrink(1);
            resultStack.grow(1);
        }
    }

    /**
     * Moves fluid from a fluid container to a stack
     *
     * @param container      The container that has the stack
     * @param fluidContainer The fluid container to move the fluid from
     * @param slot           The slot of the stack
     * @param resultSlot     The slot where the filled stack will be moved to if successful
     * @param tank           The fluid container tank to extract the fluid from
     */
    public static void moveContainerToItem(Container container, FluidContainer fluidContainer, int slot, int resultSlot, int tank) {
        var stack = container.getItem(slot);
        // Don't do anything if the stack is empty or doesn't contain a fluid container.
        if (stack.isEmpty() || !FluidApi.isFluidContainingItem(stack)) return;
        var resultStack = container.getItem(resultSlot);

        // Get the fluid container from the item
        ItemStackHolder stackHolder = new ItemStackHolder(stack.copyWithCount(1));
        ItemFluidContainer itemFluidContainer = FluidApi.getItemFluidContainer(stackHolder);
        FluidHolder amount = fluidContainer.getFluids().get(tank).copyHolder();
        if (amount.isEmpty()) return;

        // Don't do anything if the resulting filled stack can not be moved to the result slot.
        if (!resultStack.isEmpty()) {
            var filledStack = getFilledStack(stackHolder, amount);
            if (!ItemUtils.canAddItem(filledStack, resultStack)) return;
        }

        if (FluidApi.moveFluid(fluidContainer, itemFluidContainer, amount, true) == 0) return;
        FluidApi.moveFluid(fluidContainer, itemFluidContainer, amount, false);

        var result = stackHolder.getStack();

        if (resultStack.isEmpty()) {
            // If the result slot is empty, move the item there.
            stack.shrink(1);
            container.setItem(resultSlot, result);
        } else if (ItemUtils.canAddItem(result, resultStack)) {
            // Or else increment the result slot.
            stack.shrink(1);
            resultStack.grow(1);
        }
    }

    /**
     * Gets the filled version of an item container by simulating the insertion of the fluid and returning the result stack.
     *
     * @param emptyStack The empty item stack
     * @param amount     The fluid to insert
     * @return The filled item stack
     */
    public static ItemStack getFilledStack(ItemStackHolder emptyStack, FluidHolder amount) {
        var copy = emptyStack.copy();
        var container = FluidApi.getItemFluidContainer(copy);
        container.insertFluid(amount, false);
        return copy.getStack();
    }

    /**
     * Gets the empty version of an item container by simulating the extraction of the fluid and returning the result stack.
     *
     * @param filledStack The filled item stack
     * @param amount      The fluid to extract
     * @return The empty item stack
     */
    public static ItemStack getEmptyStack(ItemStackHolder filledStack, FluidHolder amount) {
        var copy = filledStack.copy();
        var container = FluidApi.getItemFluidContainer(copy);
        container.extractFluid(amount, false);
        return copy.getStack();
    }
}