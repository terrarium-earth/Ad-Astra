package earth.terrarium.adastra.common.menus.configuration;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;

public record SlotConfiguration(
    int index,
    int x,
    int y
) implements MenuConfiguration {

    @Override
    public ConfigurationType type() {
        return ConfigurationType.SLOT;
    }
}
