package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public enum Configuration {
    NONE(GuiUtils.SQUARE_BUTTON),
    PUSH(MachineScreen.PUSH_BUTTON),
    PULL(MachineScreen.PULL_BUTTON),
    PUSH_PULL(MachineScreen.PUSH_PULL_BUTTON);

    private final ResourceLocation icon;

    Configuration(ResourceLocation icon) {
        this.icon = icon;
    }

    public ResourceLocation icon() {
        return this.icon;
    }

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
        return Component.translatable("side_config.adastra.type.%s".formatted(name().toLowerCase()));
    }
}
