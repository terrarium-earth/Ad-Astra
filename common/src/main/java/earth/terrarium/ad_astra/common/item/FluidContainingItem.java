package earth.terrarium.ad_astra.common.item;


import earth.terrarium.botarium.common.fluid.base.FluidAttachment;
import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.common.fluid.impl.SimpleFluidContainer;
import earth.terrarium.botarium.common.fluid.impl.WrappedItemFluidContainer;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;
import earth.terrarium.botarium.common.item.ItemStackHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.function.BiPredicate;

public interface FluidContainingItem extends FluidAttachment.Item {

    long getTankSize();

    default FluidHolder getTank(ItemStack stack) {
        return FluidHooks.getItemFluidManager(stack).getFluidInTank(0);
    }

    default long getFluidAmount(ItemStack stack) {
        return getTank(stack).getFluidAmount();
    }

    default Fluid getFluid(ItemStack stack) {
        return getTank(stack).getFluid();
    }

    default void insert(ItemStackHolder stack, FluidHolder fluid) {
        FluidHooks.getItemFluidManager(stack.getStack()).insertFluid(stack, fluid, false);
    }

    default void extract(ItemStackHolder stack, FluidHolder fluid) {
        FluidHooks.getItemFluidManager(stack.getStack()).extractFluid(stack, fluid, false);
    }

    BiPredicate<Integer, FluidHolder> getFilter();

    @Override
    default WrappedItemFluidContainer getFluidContainer(ItemStack stack) {
        return new WrappedItemFluidContainer(stack, new SimpleFluidContainer(this.getTankSize(), 1, getFilter()));
    }
}