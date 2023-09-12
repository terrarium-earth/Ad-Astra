package earth.terrarium.adastra.common.blockentities.base;

import net.minecraft.network.chat.Component;

public enum RedstoneControl {
    ALWAYS_ON,
    ON_WHEN_POWERED,
    ON_WHEN_NOT_POWERED,
    NEVER_ON,
    ;

    public Component translation() {
        return Component.translatable("tooltip.adastra.redstone_control.%s".formatted(name().toLowerCase()));
    }

    public RedstoneControl next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public RedstoneControl previous() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }
}
