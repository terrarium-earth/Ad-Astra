package net.mrscauthd.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin {

    @Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
    public void getGravity(CallbackInfoReturnable<Float> info) {
        info.setReturnValue(ModUtils.getMixinGravity(0.05f, this));
    }
}