package earth.terrarium.adastra.common.utils;

import earth.terrarium.botarium.common.fluid.base.BotariumFluidBlock;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidHandler;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidItemHandler;
import earth.terrarium.botarium.common.fluid.impl.WrappedBlockFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;

public class FluidUtils {

    public static FluidHolder getTank(ItemStack stack) {
        return getTank(stack, 0);
    }

    public static FluidHolder getTank(ItemStack stack, int tank) {
        return FluidHooks.getItemFluidManager(stack).getFluidInTank(0);
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
        return FluidHooks.getItemFluidManager(stack).getTankCapacity(tank);
    }

    public static long insert(ItemStackHolder stack, FluidHolder fluid) {
        return FluidHooks.getItemFluidManager(stack.getStack()).insertFluid(stack, fluid, false);
    }

    public static FluidHolder extract(ItemStackHolder stack, FluidHolder fluid) {
        return FluidHooks.getItemFluidManager(stack.getStack()).extractFluid(stack, fluid, false);
    }

    public static <T extends BlockEntity & Container & BotariumFluidBlock<WrappedBlockFluidContainer>> void insertSlotToTank(T container, int inputSlot, int outputSlot, int tank) {
        ItemStack inputStack = container.getItem(inputSlot);
        if (inputStack.isEmpty()) return;
        ItemStack outputStack = container.getItem(outputSlot);

        PlatformFluidItemHandler inputSlotHandler = FluidHooks.safeGetItemFluidManager(inputStack).orElse(null);
        if (inputSlotHandler == null) return;

        PlatformFluidHandler outputTankHandler = FluidHooks.getBlockFluidManager(container, null);
        FluidHolder fluid = inputSlotHandler.getFluidInTank(tank);
        if (fluid.isEmpty()) return;

        ItemStackHolder holder = getSingleItemHolder(inputStack);
        if (!canExtractTankToSlot(inputStack, tank, outputTankHandler.insertFluid(fluid, true))) return;
        if (!simulateSlotToTank(inputStack, outputStack)) return;

        if (FluidHooks.moveItemToBlockFluid(holder, container, null, fluid) == 0) return;
        ItemStack result = holder.getStack();

        if (outputStack.isEmpty() || ItemStack.isSameItemSameTags(result, outputStack)) {
            inputStack.shrink(1);
            if (outputStack.isEmpty()) {
                container.setItem(outputSlot, result);
            } else {
                outputStack.grow(result.getCount());
                insertSlotToTank(container, inputSlot, outputSlot, tank);
            }
        } else {
            container.setItem(inputSlot, result);
        }
    }

    public static <T extends BlockEntity & Container & BotariumFluidBlock<WrappedBlockFluidContainer>> void extractTankToSlot(T container, int inputSlot, int outputSlot, int tank) {
        ItemStack inputStack = container.getItem(inputSlot);
        if (inputStack.isEmpty()) return;
        ItemStack outputStack = container.getItem(outputSlot);

        PlatformFluidHandler inputTankHandler = FluidHooks.getBlockFluidManager(container, null);

        PlatformFluidItemHandler outputSlotHandler = FluidHooks.safeGetItemFluidManager(inputStack).orElse(null);
        if (outputSlotHandler == null) return;
        FluidHolder fluid = inputTankHandler.getFluidInTank(tank);
        if (fluid.isEmpty()) return;

        ItemStackHolder holder = getSingleItemHolder(inputStack);
        if (!canInsertSlotToTank(inputStack, fluid)) return;
        if (!simulateTankToSlot(inputStack, outputStack, fluid)) return;

        if (FluidHooks.moveBlockToItemFluid(container, null, holder, fluid) == 0) return;
        ItemStack result = holder.getStack();

        if (outputStack.isEmpty() || ItemStack.isSameItemSameTags(result, outputStack)) {
            inputStack.shrink(1);
            if (outputStack.isEmpty()) {
                container.setItem(outputSlot, result);
            } else {
                outputStack.grow(result.getCount());
                extractTankToSlot(container, inputSlot, outputSlot, tank);
            }
        } else {
            container.setItem(inputSlot, result);
        }
    }

    public static ItemStackHolder getSingleItemHolder(ItemStack stack) {
        ItemStack copy = stack.copy();
        copy.setCount(1);
        return new ItemStackHolder(copy);
    }

    public static boolean canInsertItem(ItemStack stack, ItemStack to) {
        return to.isEmpty() || (ItemStack.isSameItemSameTags(stack, to) && to.getCount() + stack.getCount() <= to.getMaxStackSize());
    }


    public static boolean simulateSlotToTank(ItemStack inputStack, ItemStack outputStack) {
        ItemStack resultStack = getExtractedFluidItem(inputStack);
        if (resultStack.isEmpty()) return false;
        return canInsertItem(resultStack, outputStack);
    }

    public static boolean simulateTankToSlot(ItemStack inputStack, ItemStack outputStack, FluidHolder tankFluid) {
        ItemStack resultStack = getFilledFluidItem(inputStack, tankFluid);
        if (resultStack.isEmpty()) return false;
        return canInsertItem(resultStack, outputStack);
    }

    public static ItemStack getExtractedFluidItem(ItemStack stack) {
        ItemStackHolder holder = getSingleItemHolder(stack);
        PlatformFluidItemHandler handler = FluidHooks.safeGetItemFluidManager(holder.getStack()).orElse(null);
        if (handler == null) return ItemStack.EMPTY;

        handler.extractFluid(holder, handler.getFluidInTank(0), false);
        return holder.getStack();
    }

    public static ItemStack getFilledFluidItem(ItemStack stack, FluidHolder fluid) {
        ItemStackHolder holder = getSingleItemHolder(stack);
        PlatformFluidItemHandler handler = FluidHooks.safeGetItemFluidManager(holder.getStack()).orElse(null);
        if (handler == null) return ItemStack.EMPTY;

        handler.insertFluid(holder, fluid, false);
        return holder.getStack();
    }

    public static boolean canInsertSlotToTank(ItemStack stack, FluidHolder toInsert) {
        ItemStackHolder holder = getSingleItemHolder(stack);
        PlatformFluidItemHandler handler = FluidHooks.getItemFluidManager(holder.getStack());

        if (handler.insertFluid(holder, toInsert, false) == 0) return false;
        return holder.isDirty();
    }

    public static boolean canExtractTankToSlot(ItemStack stack, int tank, long toExtract) {
        ItemStackHolder holder = getSingleItemHolder(stack);
        PlatformFluidItemHandler handler = FluidHooks.getItemFluidManager(holder.getStack());

        if (handler.extractFluid(holder, FluidHooks.newFluidHolder(handler.getFluidInTank(tank).getFluid(), toExtract, null), false).getFluidAmount() == 0) {
            return false;
        }
        return holder.isDirty();
    }
}
