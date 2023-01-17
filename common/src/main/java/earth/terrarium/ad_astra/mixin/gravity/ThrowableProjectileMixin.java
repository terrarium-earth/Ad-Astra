package earth.terrarium.ad_astra.mixin.gravity;

import earth.terrarium.ad_astra.common.system.GravitySystem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrowableProjectile.class)
public abstract class ThrowableProjectileMixin {
    @Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
    public void adastra_getGravity(CallbackInfoReturnable<Float> cir) {
        cir.setReturnValue(0.03f * GravitySystem.getEntityGravity((Entity) (Object) this));
    }
}
