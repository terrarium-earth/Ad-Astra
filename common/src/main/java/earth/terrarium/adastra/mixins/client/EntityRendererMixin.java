package earth.terrarium.adastra.mixins.client;

import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.entities.vehicles.Vehicle;
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
    public void adastra$shouldRender(Entity entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {

        // Make rocket invisible when player is in planets screen.
        if (entity instanceof Rocket rocket) {
            if (rocket.getY() > Rocket.ATMOSPHERE_LEAVE) {
                cir.setReturnValue(false);
            }
        }

        if (entity.getVehicle() instanceof Vehicle vehicle) {
            if (vehicle.hideRider()) {
                cir.setReturnValue(false);
            }

            // Make players that are in the planet selection screen invisible.
            if (entity.getVehicle() instanceof Rocket rocket) {
                if (rocket.getY() > Rocket.ATMOSPHERE_LEAVE) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}
