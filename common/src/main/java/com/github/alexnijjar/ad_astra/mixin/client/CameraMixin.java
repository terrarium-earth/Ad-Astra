package com.github.alexnijjar.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;

import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;

@Mixin(Camera.class)
public abstract class CameraMixin {

	@Inject(method = "update", at = @At("TAIL"))
	public void adastra_update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
		if (AdAstra.CONFIG.vehicles.moveCameraInVehicle) {
			if (thirdPerson && focusedEntity.getVehicle() instanceof VehicleEntity vehicle) {
				if (vehicle.doHighFov()) {
					this.moveBy(-this.clipToSpace(12.0), 0.0, 0.0);
				}
			}
		}
	}

	@Shadow
	protected abstract void moveBy(double x, double y, double z);

	@Shadow
	protected abstract double clipToSpace(double desiredCameraDistance);
}