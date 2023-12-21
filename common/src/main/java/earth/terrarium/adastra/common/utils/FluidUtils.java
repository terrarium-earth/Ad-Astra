package earth.terrarium.adastra.common.utils;

import earth.terrarium.botarium.common.fluid.FluidApi;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.ItemFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
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

    public static void insertFluidItemToContainer(Container container, FluidContainer toFluidContainer, int fromSlot, int toSlot, int tank) {
        var fromStack = container.getItem(fromSlot);
        if (fromStack.isEmpty()) return;
        var toStack = container.getItem(toSlot);
        ItemStackHolder fromHolder = new ItemStackHolder(fromStack.copyWithCount(1));
        ItemFluidContainer fromFluidContainer = FluidApi.getItemFluidContainer(fromHolder);
        FluidHolder fromFluid = fromFluidContainer.getFluids().get(0);
        if (fromFluid.isEmpty()) return;

        if (!canExtractSlotToTank(fromHolder, tank, toFluidContainer.insertFluid(fromFluid, true))) return;
        if (!simulateSlotToTank(fromHolder, toStack)) return;

        if (FluidApi.moveFluid(fromFluidContainer, toFluidContainer, fromFluid, true) == 0) return;
        FluidApi.moveFluid(fromFluidContainer, toFluidContainer, fromFluid, false);

        var fromResultStack = fromHolder.getStack();

        if (toStack.isEmpty() || ItemStack.isSameItemSameTags(fromResultStack, toStack)) {
            fromStack.shrink(1);
            if (toStack.isEmpty()) {
                container.setItem(toSlot, fromResultStack);
            } else {
                toStack.grow(fromResultStack.getCount());
                insertFluidItemToContainer(container, toFluidContainer, fromSlot, toSlot, tank);
            }
        } else container.setItem(fromSlot, fromResultStack);
    }


    public static void extractContainerFluidToItem(Container container, FluidContainer fromFluidContainer, int fromSlot, int toSlot, int tank) {
        var fromStack = container.getItem(fromSlot);
        if (fromStack.isEmpty()) return;
        var toStack = container.getItem(toSlot);
        ItemStackHolder fromHolder = new ItemStackHolder(fromStack.copyWithCount(1));
        ItemFluidContainer toFluidContainer = FluidApi.getItemFluidContainer(fromHolder);
        FluidHolder fromFluid = fromFluidContainer.getFluids().get(tank);
        if (fromFluid.isEmpty()) return;

        if (!canInsertTankToSlot(fromHolder, fromFluid)) return;
        if (!simulateTankToSlot(fromHolder, toStack, fromFluid)) return;

        if (FluidApi.moveFluid(fromFluidContainer, toFluidContainer, fromFluid, true) == 0) return;
        FluidApi.moveFluid(fromFluidContainer, toFluidContainer, fromFluid, false);

        var fromResultStack = fromHolder.getStack();

        if (toStack.isEmpty() || ItemStack.isSameItemSameTags(fromResultStack, toStack)) {
            fromStack.shrink(1);
            if (toStack.isEmpty()) {
                container.setItem(toSlot, fromResultStack);
            } else {
                toStack.grow(fromResultStack.getCount());
                insertFluidItemToContainer(container, toFluidContainer, fromSlot, toSlot, tank);
            }
        } else container.setItem(fromSlot, fromResultStack);
    }

    public static boolean canInsertItem(ItemStack stack, ItemStack to) {
        return to.isEmpty() || (ItemStack.isSameItemSameTags(stack, to) && to.getCount() + stack.getCount() <= to.getMaxStackSize());
    }

    public static boolean simulateSlotToTank(ItemStackHolder holder, ItemStack outputStack) {
        ItemStack resultStack = getExtractedFluidItem(holder);
        if (resultStack.isEmpty()) return false;
        return canInsertItem(resultStack, outputStack);
    }

    public static boolean simulateTankToSlot(ItemStackHolder holder, ItemStack outputStack, FluidHolder tankFluid) {
        ItemStack resultStack = getFilledFluidItem(holder, tankFluid);
        if (resultStack.isEmpty()) return false;
        return canInsertItem(resultStack, outputStack);
    }

    public static ItemStack getExtractedFluidItem(ItemStackHolder holder) {
        ItemFluidContainer handler = FluidApi.getItemFluidContainer(holder);
        if (handler == null) return ItemStack.EMPTY;

        handler.extractFluid(handler.getFluids().get(0), false);
        return holder.getStack();
    }

    public static ItemStack getFilledFluidItem(ItemStackHolder holder, FluidHolder fluid) {
        ItemFluidContainer handler = FluidApi.getItemFluidContainer(holder);
        if (handler == null) return ItemStack.EMPTY;

        handler.insertFluid(fluid, false);
        return holder.getStack();
    }

    public static boolean canInsertTankToSlot(ItemStackHolder holder, FluidHolder toInsert) {
        ItemFluidContainer handler = FluidApi.getItemFluidContainer(holder);
        return handler.insertFluid(toInsert, false) != 0;
    }

    public static boolean canExtractSlotToTank(ItemStackHolder holder, int tank, long toExtract) {
        ItemFluidContainer handler = FluidApi.getItemFluidContainer(holder);
        FluidHolder fluid = FluidHooks.newFluidHolder(handler.getFluids().get(tank).getFluid(), toExtract, null);
        return handler.extractFluid(fluid, false).getFluidAmount() != 0;
    }
}
