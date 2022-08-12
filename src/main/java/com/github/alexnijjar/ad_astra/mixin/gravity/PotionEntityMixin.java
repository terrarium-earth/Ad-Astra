package com.github.alexnijjar.ad_astra.mixin.gravity;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionEntity.class)
public abstract class PotionEntityMixin {
	@Inject(method = "getGravity", at = @At("HEAD"), cancellable = true)
	public void getGravity(CallbackInfoReturnable<Float> ci) {
		if (AdAstra.CONFIG.general.doEntityGravity) {
			ci.setReturnValue(ModUtils.getMixinGravity(0.05f, this));
		}
	}
}