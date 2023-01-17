package earth.terrarium.ad_astra.mixin.gravity;

import earth.terrarium.ad_astra.common.system.GravitySystem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingHook.class)
public abstract class FishingHookMixin {

    @Unique
    private static final double CONSTANT = -0.03;

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra_tick(CallbackInfo ci) {
        Entity entity = (Entity) (Object) this;
        if (!entity.isNoGravity()) {
            Vec3 velocity = entity.getDeltaMovement();
            double newGravity = CONSTANT * GravitySystem.getEntityGravity(entity);
            entity.setDeltaMovement(velocity.x(), velocity.y() - CONSTANT + newGravity, velocity.z());
        }
    }
}
