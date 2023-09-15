package earth.terrarium.adastra.common.blockentities.base;

import earth.terrarium.adastra.client.utils.GuiUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Locale;

public enum RedstoneControl {
    ALWAYS_ON(GuiUtils.REDSTONE_ALWAYS_ON),
    ON_WHEN_POWERED(GuiUtils.REDSTONE_ON_WHEN_POWERED),
    ON_WHEN_NOT_POWERED(GuiUtils.REDSTONE_ON_WHEN_NOT_POWERED),
    NEVER_ON(GuiUtils.REDSTONE_NEVER_ON);

    private final ResourceLocation icon;

    RedstoneControl(ResourceLocation icon) {
        this.icon = icon;
    }

    public ResourceLocation icon() {
        return this.icon;
    }

    public boolean canPower(boolean powered) {
        return switch (this) {
            case ALWAYS_ON -> true;
            case ON_WHEN_POWERED -> powered;
            case ON_WHEN_NOT_POWERED -> !powered;
            case NEVER_ON -> false;
        };
    }

    public Component translation() {
        return Component.translatable("tooltip.adastra.redstone_control.%s".formatted(name().toLowerCase(Locale.ROOT)));
    }

    public RedstoneControl next() {
        return values()[(ordinal() + 1) % values().length];
    }

    public RedstoneControl previous() {
        return values()[(ordinal() - 1 + values().length) % values().length];
    }
}
