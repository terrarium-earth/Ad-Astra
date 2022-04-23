package net.mrscauthd.beyond_earth.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldEvents;
import net.mrscauthd.beyond_earth.world.SoundUtil;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    // Cancel the portal sound when the player falls out of orbit.
    @Inject(method = "processWorldEvent", at = @At("HEAD"), cancellable = true)
    public void processWorldEvent(PlayerEntity source, int eventId, BlockPos pos, int data, CallbackInfo info) {

        if (SoundUtil.getSound()) {
            // Portal id is 1032.
            if (eventId == WorldEvents.TRAVEL_THROUGH_PORTAL) {
                SoundUtil.setSound(false);
                info.cancel();
            }
        }
    }
}