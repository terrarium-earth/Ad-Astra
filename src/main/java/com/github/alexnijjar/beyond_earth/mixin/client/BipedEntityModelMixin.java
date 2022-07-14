package com.github.alexnijjar.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.vehicles.VehicleItem;
import com.github.alexnijjar.beyond_earth.registry.ModItems;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

@Mixin(BipedEntityModel.class)
@SuppressWarnings("unchecked")
public abstract class BipedEntityModelMixin {

    @Inject(method = "setAngles", at = @At("HEAD"))
    public void setAnglesHead(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        BipedEntityModel<PlayerEntity> model = ((BipedEntityModel<PlayerEntity>) (Object) this);
        if (livingEntity.getVehicle() instanceof VehicleEntity vehicle) {
            // Disable the sitting pose while standing in a rocket.
            model.riding = vehicle.shouldSit();
        }
    }

    @Inject(method = "setAngles", at = @At("TAIL"))
    public void setAnglesTail(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        BipedEntityModel<PlayerEntity> model = ((BipedEntityModel<PlayerEntity>) (Object) this);
        Item currentItem = livingEntity.getStackInHand(livingEntity.getActiveHand()).getItem();

        if (currentItem != null) {
            boolean mainHand = true;
            if (!(currentItem instanceof VehicleItem)) {
                mainHand = false;
                currentItem = livingEntity.getOffHandStack().getItem();
            }

            if (!livingEntity.getPose().equals(EntityPose.SWIMMING)) {
                if (currentItem instanceof VehicleItem || ModItems.TIER_1_ROVER.equals(currentItem)) {
                    // Move the arms so that it looks like the player is holding the rocket in the air with both arms.
                    if (mainHand) {
                        model.rightArm.pitch = -2.8f;
                        model.leftArm.pitch = model.rightArm.pitch;
                    } else {
                        model.leftArm.pitch = -2.8f;
                        model.rightArm.pitch = model.leftArm.pitch;
                    }
                }
            }
        }
    }
}
