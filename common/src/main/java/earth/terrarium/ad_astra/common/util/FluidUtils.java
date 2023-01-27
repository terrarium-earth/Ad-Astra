package earth.terrarium.ad_astra.common.util;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidHandler;
import earth.terrarium.botarium.common.fluid.base.PlatformFluidItemHandler;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;

import java.util.Optional;
import java.util.function.Predicate;

public class FluidUtils {

    public static void insertItemFluidToTank(BlockEntity block, Container container, int inputSlot, int outputSlot, Predicate<Fluid> filter) {
        ItemStack input = container.getItem(inputSlot);
        ItemStack output = container.getItem(outputSlot);

        ItemStack stack = input.copy();
        stack.setCount(1);
        ItemStackHolder holder = new ItemStackHolder(stack);
        FluidHolder fluid = FluidHooks.safeGetItemFluidManager(holder.getStack()).map(h -> h.getFluidInTank(0)).orElse(null);

        Optional<PlatformFluidItemHandler> platformFluidItemHandler = FluidHooks.safeGetItemFluidManager(holder.getStack());
        Optional<PlatformFluidHandler> platformFluidBlockHandler = FluidHooks.safeGetBlockFluidManager(block, null);
        if (platformFluidItemHandler.isPresent() && platformFluidBlockHandler.isPresent()) {
            if (platformFluidBlockHandler.get().insertFluid(platformFluidItemHandler.get().extractFluid(holder, platformFluidItemHandler.get().getFluidInTank(0), true), true) < platformFluidItemHandler.get().getFluidInTank(0).getFluidAmount()) {
                return;
            }
        }

        if (fluid != null && !fluid.isEmpty() && filter.test(fluid.getFluid()) && FluidHooks.moveItemToBlockFluid(holder, block, null, fluid) != 0) {
            if (holder.isDirty()) {
                input.shrink(1);
                if (output.isEmpty() || (ItemStack.isSameItemSameTags(output, holder.getStack()) && holder.getStack().getCount() + output.getCount() <= output.getMaxStackSize())) {
                    if (output.isEmpty()) {
                        container.setItem(outputSlot, holder.getStack());
                    } else {
                        output.grow(holder.getStack().getCount());
                        insertItemFluidToTank(block, container, inputSlot, outputSlot, filter);
                    }
                }
            }
        }
    }


    public static void extractTankFluidToItem(BlockEntity block, Container container, int inputSlot, int outputSlot, int tank, Predicate<Fluid> filter) {
        ItemStack input = container.getItem(inputSlot);
        ItemStack output = container.getItem(outputSlot);

        ItemStack stack = input.copy();
        stack.setCount(1);
        ItemStackHolder holder = new ItemStackHolder(stack);
        FluidHolder fluid = FluidHooks.safeGetBlockFluidManager(block, null).map(h -> h.getFluidInTank(tank)).orElse(null);

        if (fluid != null && !fluid.isEmpty() && filter.test(fluid.getFluid()) && FluidHooks.moveBlockToItemFluid(block, null, holder, fluid) != 0) {
            if (holder.isDirty()) {
                input.shrink(1);
                if (output.isEmpty() || (ItemStack.isSameItemSameTags(output, holder.getStack()) && holder.getStack().getCount() + output.getCount() <= output.getMaxStackSize())) {
                    if (output.isEmpty()) {
                        container.setItem(outputSlot, holder.getStack());
                    } else {
                        output.grow(holder.getStack().getCount());
                        extractTankFluidToItem(block, container, inputSlot, outputSlot, tank, filter);
                    }
                }
            }
        }
    }

    public static FluidHolder getTank(ItemStack stack) {
        return FluidHooks.getItemFluidManager(stack).getFluidInTank(0);
    }

    public static long getFluidAmount(ItemStack stack) {
        return getTank(stack).getFluidAmount();
    }

    public static Fluid getFluid(ItemStack stack) {
        return getTank(stack).getFluid();
    }

    public static long insert(ItemStackHolder stack, FluidHolder fluid) {
        return FluidHooks.getItemFluidManager(stack.getStack()).insertFluid(stack, fluid, false);
    }

    public static FluidHolder extract(ItemStackHolder stack, FluidHolder fluid) {
        return FluidHooks.getItemFluidManager(stack.getStack()).extractFluid(stack, fluid, false);
    }
}
