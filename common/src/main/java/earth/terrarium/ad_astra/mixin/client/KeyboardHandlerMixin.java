package earth.terrarium.ad_astra.mixin.client;

import com.mojang.blaze3d.platform.InputConstants;
import earth.terrarium.ad_astra.client.registry.ClientModKeybindings;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    // Avoids keybind conflicts
    @Inject(method = "keyPress", at = @At("TAIL"))
    public void adastra_keyPress(long l, int i, int j, int k, int m, CallbackInfo ci) {
        InputConstants.Key key = InputConstants.getKey(i, j);
        if (InputConstants.KEY_SPACE == key.getValue()) {
            ClientModKeybindings.launchRocket();
        }
    }
}
