package earth.terrarium.adastra.common.menus.configuration;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.botarium.common.fluid.base.FluidContainer;

public record FluidConfiguration(
    int index,
    int x,
    int y,
    FluidContainer container,
    int tank
) implements MenuConfiguration {

    @Override
    public ConfigurationType type() {
        return ConfigurationType.FLUID;
    }
}
