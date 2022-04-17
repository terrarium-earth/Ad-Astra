package net.mrscauthd.beyond_earth.mixin.gravity;

import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.mrscauthd.beyond_earth.util.ModUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ExperienceBottleEntity.class)
public abstract class ExperienceBottleEntityMixin {


    @Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
    public void getGravity(CallbackInfoReturnable<Float> info) {
        ExperienceBottleEntity entity = ((ExperienceBottleEntity) (Object) this);
        float gravity = ModUtils.getPlanetGravity(entity.world.getRegistryKey());

        info.setReturnValue(0.07f * gravity);
    }
}
