package earth.terrarium.ad_astra.mixin.gravity;

import earth.terrarium.ad_astra.common.system.GravitySystem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Boat.class)
public abstract class BoatMixin {

    @Unique
    private static final double CONSTANT = -0.04;

    @Inject(method = "floatBoat", at = @At("TAIL"))
    public void adastra_updateVelocity(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (!entity.isNoGravity()) {
            Vec3 velocity = entity.getDeltaMovement();
            double newGravity = CONSTANT * GravitySystem.getEntityGravity(entity);
            entity.setDeltaMovement(velocity.x(), velocity.y() - CONSTANT + newGravity, velocity.z());
        }
    }
}
