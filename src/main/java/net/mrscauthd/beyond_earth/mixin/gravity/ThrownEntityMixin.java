package net.mrscauthd.beyond_earth.mixin.gravity;

import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.mrscauthd.beyond_earth.util.GravityUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ThrownEntity.class)
public abstract class ThrownEntityMixin {
    @Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
    public void getGravity(CallbackInfoReturnable<Float> info) {
        info.setReturnValue(GravityUtil.getMixinGravity(0.03f, this));
    }
}
