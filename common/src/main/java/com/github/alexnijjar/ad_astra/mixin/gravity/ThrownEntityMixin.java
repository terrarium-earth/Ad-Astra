package com.github.alexnijjar.ad_astra.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;

@Mixin(ThrownEntity.class)
public abstract class ThrownEntityMixin {
	@Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
	public void adastra_getGravity(CallbackInfoReturnable<Float> ci) {
		if (AdAstra.CONFIG.general.doEntityGravity) {
			ci.setReturnValue(0.03f * ModUtils.getPlanetGravity(((Entity) (Object) this).getWorld()));
		}
	}
}