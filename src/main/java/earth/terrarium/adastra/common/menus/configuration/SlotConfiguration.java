package earth.terrarium.adastra.common.menus.configuration;

import earth.terrarium.adastra.common.blockentities.base.sideconfig.ConfigurationType;

public record SlotConfiguration(
    int index,
    int x,
    int y,
    int width,
    int height
) implements MenuConfiguration {

    public SlotConfiguration(int index, int x, int y) {
        this(index, x, y, 16, 16);
    }

    @Override
    public ConfigurationType type() {
        return ConfigurationType.SLOT;
    }
}
