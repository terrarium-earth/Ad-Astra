package earth.terrarium.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import earth.terrarium.ad_astra.client.screen.OverlayConfigScreen;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.OptionsList;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.VideoSettingsScreen;
import net.minecraft.network.chat.Component;

@Mixin(VideoSettingsScreen.class)
public abstract class VideoSettingsScreenMixin extends OptionsSubScreen {

    public VideoSettingsScreenMixin(Screen screen, Options options, Component component) {
        super(screen, options, component);
    }

    @Shadow
    private OptionsList list;

    @Inject(method = "init*", at = @At("TAIL"))
    public void adastra_init(CallbackInfo ci) {
        this.list.addBig(new OptionInstance<>("options.ad_astra.overlay_config", OptionInstance.noTooltip(), (caption, value) -> Component.translatable("gui.ad_astra.text.open"), OptionInstance.BOOLEAN_VALUES, false, v -> {
            this.minecraft.setScreen(new OverlayConfigScreen(this));
        }));
    }
}
