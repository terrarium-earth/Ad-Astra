package com.github.alexnijjar.ad_astra.items;

import java.util.List;

import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.FluidHoldingItem;
import earth.terrarium.botarium.api.fluid.ItemFilteredFluidContainer;
import earth.terrarium.botarium.api.fluid.ItemFluidContainer;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;

public interface FluidContainingItem extends FluidHoldingItem {

	default boolean insertIntoTank(SimpleUpdatingFluidContainer tank, ItemStack stack) {
		return tank.insertFluid(getTank(stack), false) == this.getAmount(stack);
	}

	long getTankSize();

	List<Fluid> getInputFluids();

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
		this.getTank(stack).setAmount(amount);
	}

	default Fluid getFluid(ItemStack stack) {
		return this.getTank(stack).getFluid();
	}

	default void setFluid(ItemStack stack, Fluid fluid) {
		this.getTank(stack).setFluid(fluid);
	}
}
