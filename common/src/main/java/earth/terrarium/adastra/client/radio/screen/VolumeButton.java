package earth.terrarium.adastra.client.radio.screen;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.config.RadioConfig;
import earth.terrarium.adastra.client.utils.Debouncer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class VolumeButton extends Button {

    private static final Debouncer VOLUME_DEBOUNCER = new Debouncer();

    private static final ResourceLocation VOLUME_UP = new ResourceLocation(AdAstra.MOD_ID, "radio/volume_up");
    private static final ResourceLocation VOLUME_DOWN = new ResourceLocation(AdAstra.MOD_ID, "radio/volume_down");
    private static final ResourceLocation VOLUME_UP_HOVER = new ResourceLocation(AdAstra.MOD_ID, "radio/volume_up_hover");
    private static final ResourceLocation VOLUME_DOWN_HOVER = new ResourceLocation(AdAstra.MOD_ID, "radio/volume_down_hover");

    private final ResourceLocation normal;
    private final ResourceLocation hovered;

    public VolumeButton(int x, int y, int width, int height, int amount) {
        super(x, y, width, height, CommonComponents.EMPTY, button -> {
            int volume = RadioConfig.volume + amount * (Screen.hasShiftDown() ? 10 : 1);
            int before = RadioConfig.volume;
            RadioConfig.volume = Mth.clamp(volume, 0, 100);
            if (before == RadioConfig.volume) return;
            VOLUME_DEBOUNCER.debounce(VolumeButton::saveConfigOnThread, 5000);
        }, DEFAULT_NARRATION);
        this.normal = amount > 0 ? VOLUME_UP : VOLUME_DOWN;
        this.hovered = amount > 0 ? VOLUME_UP_HOVER : VOLUME_DOWN_HOVER;
    }

    @Override
    public void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        ResourceLocation sprite = this.isHoveredOrFocused() ? this.hovered : this.normal;
        graphics.blitSprite(sprite, this.getX(), this.getY(), this.width, this.height);
    }

    private static void saveConfigOnThread() {
        Minecraft.getInstance().tell(() -> AdAstra.CONFIGURATOR.saveConfig(AdAstraConfigClient.class));
    }
}
