package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    public void ad_astra$shouldRender(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {

        // Make rocket invisible.
        if (entity instanceof Rocket rocket) {
            if (rocket.getPhase() == 3) {
                cir.setReturnValue(false);
            }
        }

        if (entity.getVehicle() instanceof Vehicle vehicle) {
            if (!vehicle.shouldRenderPlayer()) {
                cir.setReturnValue(false);
            }

            // Make player that is in the planet selection screen invisible.
            if (entity.getVehicle() instanceof Rocket rocket) {
                if (rocket.getPhase() == 3) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
