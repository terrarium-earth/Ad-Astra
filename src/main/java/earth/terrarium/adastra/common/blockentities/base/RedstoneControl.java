package earth.terrarium.adastra.common.blockentities.base;

import net.minecraft.network.chat.Component;

import java.util.Locale;

public enum RedstoneControl {
    ALWAYS_ON,
    ON_WHEN_POWERED,
    ON_WHEN_NOT_POWERED,
    NEVER_ON;

    public boolean canPower(boolean powered) {
        return switch (this) {
            case ALWAYS_ON -> true;
            case ON_WHEN_POWERED -> powered;
            case ON_WHEN_NOT_POWERED -> !powered;
            case NEVER_ON -> false;
        };
    }

    public Component translation() {
        return Component.translatable("tooltip.ad_astra.redstone_control.%s".formatted(name().toLowerCase(Locale.ROOT)));
    }

    public RedstoneControl next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public RedstoneControl previous() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }
}
