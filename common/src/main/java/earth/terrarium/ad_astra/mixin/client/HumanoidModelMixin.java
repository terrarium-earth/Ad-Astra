package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.entities.vehicles.Vehicle;
import earth.terrarium.ad_astra.items.HoldableOverHead;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
@SuppressWarnings("unchecked")
public abstract class HumanoidModelMixin {

    @Inject(method = "setupAnim*", at = @At("HEAD"))
    public void adastra_setupAnimHead(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        HumanoidModel<Player> model = ((HumanoidModel<Player>) (Object) this);
        if (livingEntity.getVehicle() instanceof Vehicle vehicle) {
            // Disable the sitting pose while standing in a rocket.
            model.riding = vehicle.shouldSit();
        }
    }

    // Make it look like the player is holding the vehicle above their head
    @Inject(method = "setupAnim*", at = @At("TAIL"))
    public void adastra_setupAnimTail(LivingEntity livingEntity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (livingEntity.getPose().equals(Pose.SWIMMING)) {
            return;
        }

        HumanoidModel<LivingEntity> model = ((HumanoidModel<LivingEntity>) (Object) this);
        Item mainHandItem = livingEntity.getMainHandItem().getItem();
        Item offhandItem = livingEntity.getOffhandItem().getItem();

        // Move the arms so that it looks like the player is holding the vehicle in the air with both arms.
        if (mainHandItem instanceof HoldableOverHead) {
            model.rightArm.xRot = -2.8f;
            model.leftArm.xRot = model.rightArm.xRot;
        } else if (offhandItem instanceof HoldableOverHead) {
            model.leftArm.xRot = -2.8f;
            model.rightArm.xRot = model.leftArm.xRot;
        }
    }
}
