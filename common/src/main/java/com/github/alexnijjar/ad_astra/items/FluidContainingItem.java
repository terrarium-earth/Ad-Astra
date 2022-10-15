package com.github.alexnijjar.ad_astra.items;

import java.util.List;

import earth.terrarium.botarium.api.fluid.*;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

public interface FluidContainingItem extends FluidHoldingItem {

	default boolean insertIntoTank(SimpleUpdatingFluidContainer tank, ItemStack stack) {
		return tank.insertFluid(getTank(stack), false) == this.getAmount(stack);
	}

	long getTankSize();

	default FluidHolder getTank(ItemStack stack) {
		return this.getFluidContainer(stack).getFluids().get(0);
	}

	@Override
	default ItemFluidContainer getFluidContainer(ItemStack stack) {
		return new ItemFilteredFluidContainer(this.getTankSize(), 1, stack, (amount, fluid) -> true);
	}

	default long getAmount(ItemStack stack) {
		return this.getTank(stack).getFluidAmount();
	}

	default void setAmount(ItemStack stack, long amount) {
		FluidHolder tank = getTank(stack);
		if (!tank.isEmpty()) {
			tank.setAmount(amount);
			getFluidContainer(stack).setFluid(0, tank);
		}
	}

	default Fluid getFluid(ItemStack stack) {
		return this.getTank(stack).getFluid();
	}

	default void setFluid(ItemStack stack, FluidHolder fluid) {
		getFluidContainer(stack).setFluid(0, fluid);
	}
}
