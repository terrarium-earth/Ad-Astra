package earth.terrarium.adastra.common.blockentities.base.sideconfig;

import earth.terrarium.adastra.client.screens.base.MachineScreen;
import earth.terrarium.adastra.client.utils.GuiUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public enum Configuration {
    NONE(GuiUtils.SQUARE_BUTTON),
    INPUT(MachineScreen.INPUT_BUTTON),
    OUTPUT(MachineScreen.OUTPUT_BUTTON),
    INPUT_OUTPUT(MachineScreen.INPUT_OUTPUT_BUTTON);

    private final ResourceLocation icon;

    Configuration(ResourceLocation icon) {
        this.icon = icon;
    }

    public ResourceLocation icon() {
        return this.icon;
    }

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isInput() {
        return this == INPUT || this == INPUT_OUTPUT;
    }

    public boolean isOutput() {
        return this == OUTPUT || this == INPUT_OUTPUT;
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
