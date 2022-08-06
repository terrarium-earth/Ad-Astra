package com.github.alexnijjar.beyond_earth.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import net.minecraft.entity.projectile.thrown.PotionEntity;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin {
    @Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
    public void getGravity(CallbackInfoReturnable<Float> ci) {
        if (BeyondEarth.CONFIG.general.doEntityGravity) {
            ci.setReturnValue(ModUtils.getMixinGravity(0.05f, this));
        }
    }
}