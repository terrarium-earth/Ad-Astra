package com.github.alexnijjar.ad_astra.container;

import earth.terrarium.botarium.api.Updatable;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;

import java.util.function.BiPredicate;
import java.util.function.Function;

public class ImportingFluidTank extends SimpleUpdatingFluidContainer {
    public ImportingFluidTank(Updatable updatable, Function<Integer, Long> maxAmount, int tanks, BiPredicate<Integer, FluidHolder> fluidFilter) {
        super(updatable, maxAmount, tanks, fluidFilter);
    }

    public ImportingFluidTank(Updatable updatable, long maxAmount, int tanks, BiPredicate<Integer, FluidHolder> fluidFilter) {
        super(updatable, maxAmount, tanks, fluidFilter);
    }

    @Override
    public boolean allowsExtraction() {
        return false;
    }
}
