package earth.terrarium.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.items.HoldableOverHead;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;

@Mixin(BipedEntityModel.class)
@SuppressWarnings("unchecked")
public abstract class BipedEntityModelMixin {

	@Inject(method = "setAngles*", at = @At("HEAD"))
	public void adastra_setAnglesHead(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
		BipedEntityModel<PlayerEntity> model = ((BipedEntityModel<PlayerEntity>) (Object) this);
		if (livingEntity.getVehicle() instanceof VehicleEntity vehicle) {
			// Disable the sitting pose while standing in a rocket.
			model.riding = vehicle.shouldSit();
		}
	}

	// Make it look like the player is holding the vehicle above their head
	@Inject(method = "setAngles*", at = @At("TAIL"))
	public void adastra_setAnglesTail(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
		if (livingEntity.getPose().equals(EntityPose.SWIMMING)) {
			return;
		}

		BipedEntityModel<LivingEntity> model = ((BipedEntityModel<LivingEntity>) (Object) this);
		Item mainHandItem = livingEntity.getMainHandStack().getItem();
		Item offhandItem = livingEntity.getOffHandStack().getItem();

		// Move the arms so that it looks like the player is holding the vehicle in the air with both arms.
		if (mainHandItem instanceof HoldableOverHead) {
			model.rightArm.pitch = -2.8f;
			model.leftArm.pitch = model.rightArm.pitch;
		} else if (offhandItem instanceof HoldableOverHead) {
			model.leftArm.pitch = -2.8f;
			model.rightArm.pitch = model.leftArm.pitch;
		}
	}
}
