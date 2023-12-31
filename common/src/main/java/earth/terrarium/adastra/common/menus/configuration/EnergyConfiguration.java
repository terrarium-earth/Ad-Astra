package earth.terrarium.adastra.common.menus.configuration;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.botarium.common.energy.base.EnergyContainer;

public record EnergyConfiguration(
    int index,
    int x,
    int y,
    EnergyContainer container
) implements MenuConfiguration {

    @Override
    public ConfigurationType type() {
        return ConfigurationType.ENERGY;
    }
}
