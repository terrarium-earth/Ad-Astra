package com.github.alexnijjar.ad_astra.mixin.client;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

	@Inject(method = "update", at = @At("TAIL"), cancellable = true)
	public void update(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
		if (AdAstra.CONFIG.vehicles.moveCameraInVehicle) {
			CameraInvoker camera = (CameraInvoker) (Object) this;
			if (thirdPerson && focusedEntity.getVehicle() instanceof VehicleEntity vehicle) {
				if (vehicle.doHighFov()) {
					camera.invokeMoveBy(-camera.invokeClipToSpace(12.0), 0.0, 0.0);
				}
			}
		}
	}
}