package com.github.alexnijjar.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.vehicles.VehicleItem;
import com.github.alexnijjar.beyond_earth.registry.ModItems;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "renderItem", at = @At("HEAD"), cancellable = true)
    public void renderItem(LivingEntity entity, ItemStack stack, ModelTransformation.Mode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
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

                // Rotate the rocket a bit so that the tier 3 model doesn't clip into the player's head
                if (stack.getItem().equals(ModItems.TIER_3_ROCKET)) {
                    matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-20));
                }

                // Only render the main hand stack if the player is holding a vehicle in both the main hand and off hand
                Item mainStack = entity.getMainHandStack().getItem();
                if (leftHanded && stack.getItem().equals(entity.getOffHandStack().getItem()) && mainStack instanceof VehicleItem) {
                    ci.cancel();
                }
            }
        }
    }

    public void updateHeldItems(CallbackInfo ci) {
        // Disable item rendering while in a vehicle
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player != null && client.player.getMainHandStack() != null && !client.player.getMainHandStack().isEmpty()) {
            if (client.player.getVehicle() instanceof RocketEntity || client.player.getVehicle() instanceof LanderEntity) {
                ci.cancel();
            }

            // disable item rendering while holding a vehicle item and swimming
            if (client.player.getMainHandStack().getItem() instanceof VehicleItem && client.player.getPose().equals(EntityPose.SWIMMING)) {
                ci.cancel();
            }
        }
    }
}
