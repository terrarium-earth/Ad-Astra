package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import net.minecraft.network.chat.Component;

public enum ConfigurationType {
    SLOT,
    ENERGY,
    FLUID,
    ;

    public Component translation() {
        return Component.translatable("side_config.adastra.type.%s".formatted(name().toLowerCase()));
    }
}
