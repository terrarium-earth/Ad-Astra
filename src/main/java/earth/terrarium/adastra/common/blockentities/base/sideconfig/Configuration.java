package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import net.minecraft.network.chat.Component;

import java.util.Locale;

public enum Configuration {
    NONE,
    PUSH,
    PULL,
    PUSH_PULL;


    public boolean canPush() {
        return this == PUSH || this == PUSH_PULL;
    }

    public boolean canPull() {
        return this == PULL || this == PUSH_PULL;
    }

    public Configuration next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public Configuration previous() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }

    public Component translation() {
        return Component.translatable("side_config.ad_astra.type.%s".formatted(name().toLowerCase(Locale.ROOT)));
    }
}
