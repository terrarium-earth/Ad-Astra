package earth.terrarium.ad_astra.container;

import earth.terrarium.botarium.api.Updatable;
import earth.terrarium.botarium.api.fluid.FluidHolder;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;

import java.util.function.BiPredicate;
import java.util.function.Function;

public class ExportingFluidTank extends SimpleUpdatingFluidContainer {
    public ExportingFluidTank(Updatable updatable, Function<Integer, Long> maxAmount, int tanks, BiPredicate<Integer, FluidHolder> fluidFilter) {
        super(updatable, maxAmount, tanks, fluidFilter);
    }

    public ExportingFluidTank(Updatable updatable, long maxAmount, int tanks, BiPredicate<Integer, FluidHolder> fluidFilter) {
        super(updatable, maxAmount, tanks, fluidFilter);
    }

    @Override
    public boolean allowsInsertion() {
        return false;
    }
}
