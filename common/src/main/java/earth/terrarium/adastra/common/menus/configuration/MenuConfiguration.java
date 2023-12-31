package earth.terrarium.adastra.common.menus.configuration;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;

public sealed interface MenuConfiguration permits EnergyConfiguration, FluidConfiguration, SlotConfiguration {

    int index();

    ConfigurationType type();

    int x();

    int y();
}
