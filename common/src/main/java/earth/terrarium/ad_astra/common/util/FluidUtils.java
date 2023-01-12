package earth.terrarium.ad_astra.common.util;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;

import java.util.function.Predicate;

public class FluidUtils {

    public static void insertItemFluidToTank(BlockEntity block, Container container, int inputSlot, int outputSlot, Predicate<Fluid> filter) {
        ItemStack input = container.getItem(inputSlot);
        ItemStack output = container.getItem(outputSlot);

        ItemStack stack = input.copy();
        stack.setCount(1);
        ItemStackHolder holder = new ItemStackHolder(stack);
        FluidHolder fluid = FluidHooks.safeGetItemFluidManager(holder.getStack()).map(h -> h.getFluidInTank(0)).orElse(null);

        if (fluid != null && !fluid.isEmpty() && filter.test(fluid.getFluid()) && FluidHooks.moveItemToBlockFluid(holder, block, null, fluid) != 0) {
            if (holder.isDirty()) {
                input.shrink(1);
                if (output.isEmpty() || (ItemStack.isSameItemSameTags(output, holder.getStack()) && holder.getStack().getCount() + output.getCount() <= output.getMaxStackSize())) {
                    if (output.isEmpty()) {
                        container.setItem(outputSlot, holder.getStack());
                    } else {
                        output.grow(holder.getStack().getCount());
                    }
                }
            }
        }
    }
}