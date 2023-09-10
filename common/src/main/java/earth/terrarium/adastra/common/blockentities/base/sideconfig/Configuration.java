package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import net.minecraft.network.chat.Component;

public enum Configuration {
    NONE,
    INPUT,
    OUTPUT,
    INPUT_OUTPUT,
    ;

    public Configuration next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Configuration previous() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }

    public Component translation() {
        return Component.translatable("side_config.adastra.type.%s".formatted(name().toLowerCase()));
    }
}
