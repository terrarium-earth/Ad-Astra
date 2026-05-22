package earth.terrarium.adastra.common.menus.configuration;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;
import earth.terrarium.common_storage_lib.storage.base.ValueStorage;

public record EnergyConfiguration(
    int index,
    int x,
    int y,
    ValueStorage container
) implements MenuConfiguration {

    @Override
    public ConfigurationType type() {
        return ConfigurationType.ENERGY;
    }
}
