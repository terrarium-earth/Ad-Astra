package net.mrscauthd.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayerEntity;
import net.mrscauthd.beyond_earth.client.screens.PlayerOverlayScreen;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    public void baseTick(CallbackInfo info) {

        ClientPlayerEntity player = ((ClientPlayerEntity) (Object) this);

        // Rocket.
        if (player.getVehicle() instanceof RocketEntity rocket) {
            PlayerOverlayScreen.shouldRenderBar = true;
            if (rocket.isFlying()) {
                PlayerOverlayScreen.countdownSeconds = rocket.getCountdownSeconds();

            }
        } else {
            PlayerOverlayScreen.shouldRenderBar = false;
            PlayerOverlayScreen.countdownSeconds = 0;
        }
    }
}
