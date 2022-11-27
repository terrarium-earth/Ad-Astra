package earth.terrarium.ad_astra.mixin.client;

import com.mojang.blaze3d.platform.InputConstants;
import earth.terrarium.ad_astra.client.registry.ClientModKeybindings;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public class KeyMappingMixin {

    // Avoids keybind conflicts
    @Inject(method = "click", at = @At("TAIL"))
    private static void adastra_click(InputConstants.Key key, CallbackInfo ci) {
        if (InputConstants.KEY_SPACE == key.getValue()) {
            ClientModKeybindings.launchRocket();
        }
    }
}
