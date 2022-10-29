package earth.terrarium.ad_astra.items;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingItem;
import earth.terrarium.botarium.api.fluid.ItemFilteredFluidContainer;
import earth.terrarium.botarium.api.fluid.ItemFluidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

import java.util.function.BiPredicate;

public interface FluidContainingItem extends FluidHoldingItem {

    long getTankSize();

    default FluidHolder getTank(ItemStack stack) {
        return this.getFluidContainer(stack).getFluids().get(0);
    }

    default long getFluidAmount(ItemStack stack) {
        return getTank(stack).getFluidAmount();
    }

    default void setFluidAmount(ItemStack stack, long amount) {
        getTank(stack).setAmount(amount);
    }

    default Fluid getFluid(ItemStack stack) {
        return getTank(stack).getFluid();
    }

    default void setFluid(ItemStack stack, Fluid fluid) {
        getTank(stack).setFluid(fluid);
    }

    default void insert(ItemStack stack, FluidHolder fluid) {
        getFluidContainer(stack).insertFluid(fluid, false);
    }

    BiPredicate<Integer, FluidHolder> getFilter();

    @Override
    default ItemFluidContainer getFluidContainer(ItemStack stack) {
        return new ItemFilteredFluidContainer(this.getTankSize(), 1, stack, getFilter());
    }
}
