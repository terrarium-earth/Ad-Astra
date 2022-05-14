package net.mrscauthd.beyond_earth.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.mrscauthd.beyond_earth.entities.vehicles.VehicleEntity;
import net.mrscauthd.beyond_earth.items.vehicles.RocketItem;

@Mixin(BipedEntityModel.class)
@SuppressWarnings("unchecked")
public abstract class BipedEntityModelMixin {

    @Inject(method = "setAngles", at = @At("HEAD"))
    public void setAnglesHead(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
        BipedEntityModel<PlayerEntity> model = ((BipedEntityModel<PlayerEntity>) (Object) this);
        if (livingEntity.getVehicle() instanceof VehicleEntity vehicle) {
            // Disable the sitting pose while standing in a rocket.
            model.riding = vehicle.shouldSit();
        }
    }

    @Inject(method = "setAngles", at = @At("TAIL"))
    public void setAnglesTail(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo info) {
        BipedEntityModel<PlayerEntity> model = ((BipedEntityModel<PlayerEntity>) (Object) this);
        Item currentItem = livingEntity.getStackInHand(livingEntity.getActiveHand()).getItem();
        if (currentItem instanceof RocketItem && !(livingEntity.getVehicle() instanceof VehicleEntity)) {
            // Move the arms so that it looks like the player is holding the rocket in the air with both arms.
            model.rightArm.pitch = -2.8f;
            model.leftArm.pitch = model.rightArm.pitch;
        }
    }
}
