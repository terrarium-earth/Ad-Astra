package earth.terrarium.ad_astra.mixin.gravity;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ThrownExperienceBottle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrownExperienceBottle.class)
public abstract class ExperienceBottleEntityMixin {
    @Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
    public void adastra_getGravity(CallbackInfoReturnable<Float> ci) {
        if (AdAstra.CONFIG.general.doEntityGravity) {
            ci.setReturnValue(0.07f * ModUtils.getPlanetGravity(((Entity) (Object) this).getLevel()));

        }
    }
}