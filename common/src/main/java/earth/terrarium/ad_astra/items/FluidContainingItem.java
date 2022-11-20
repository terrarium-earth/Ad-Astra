package earth.terrarium.ad_astra.items;

import earth.terrarium.botarium.api.fluid.*;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import java.util.function.BiPredicate;

public interface FluidContainingItem extends FluidHoldingItem {

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
    default ItemFluidContainer getFluidContainer(ItemStack stack) {
        return new ItemFilteredFluidContainer(this.getTankSize(), 1, stack, getFilter());
    }
}
