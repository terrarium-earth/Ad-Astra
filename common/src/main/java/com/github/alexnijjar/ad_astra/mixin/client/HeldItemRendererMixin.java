package com.github.alexnijjar.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.items.vehicles.VehicleItem;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
	@Inject(method = "renderItem*", at = @At("HEAD"), cancellable = true)
	public void adastra_renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
		if (stack != null && !stack.isEmpty()) {
			// Disable item rendering while in a vehicle
			if (entity.getVehicle() instanceof VehicleEntity vehicle) {
				if (vehicle.fullyConcealsRider()) {
					ci.cancel();
				}
			}

			// disable item rendering while holding a vehicle item and swimming
			if (stack.getItem() instanceof VehicleItem) {
				if (entity.getPose().equals(EntityPose.SWIMMING)) {
					ci.cancel();
				}

				// Only render the main hand stack if the player is holding a vehicle in both the main hand and off hand
				Item mainStack = entity.getMainHandStack().getItem();
				if (leftHanded && stack.getItem().equals(entity.getOffHandStack().getItem()) && mainStack instanceof VehicleItem) {
					ci.cancel();
				}
			}
		}
	}
}
