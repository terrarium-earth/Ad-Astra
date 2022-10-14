package com.github.alexnijjar.ad_astra.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.util.math.Vec3d;

@Mixin(BoatEntity.class)
public abstract class BoatEntityMixin {

	@Unique
	private static final double CONSTANT = -0.04;

	@Inject(method = "updateVelocity", at = @At("TAIL"))
	public void adastra_updateVelocity(CallbackInfo ci) {
		if (AdAstra.CONFIG.general.doEntityGravity) {
			Entity entity = (Entity) (Object) this;
			if (!entity.hasNoGravity()) {
				Vec3d velocity = entity.getVelocity();
				double newGravity = CONSTANT * ModUtils.getPlanetGravity(entity.world);
				entity.setVelocity(velocity.getX(), velocity.getY() - CONSTANT + newGravity, velocity.getZ());
			}
		}
	}
}