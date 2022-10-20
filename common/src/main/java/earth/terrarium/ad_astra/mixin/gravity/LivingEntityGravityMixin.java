package earth.terrarium.ad_astra.mixin.gravity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.util.ModUtils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;

@Mixin(LivingEntity.class)
public class LivingEntityGravityMixin {

	@Unique
	private static final double CONSTANT = 0.08;

	@Inject(method = "travel", at = @At("TAIL"))
	public void adastra_travel(CallbackInfo ci) {
		if (AdAstra.CONFIG.general.doLivingEntityGravity) {
			LivingEntity entity = (LivingEntity) (Object) this;

			Vec3d velocity = entity.getVelocity();

			if (!entity.hasNoGravity() && !entity.isTouchingWater() && !entity.isInLava() && !entity.isFallFlying() && !entity.hasStatusEffect(StatusEffects.SLOW_FALLING)) {
				double newGravity = CONSTANT * ModUtils.getPlanetGravity(entity.world);
				entity.setVelocity(velocity.getX(), velocity.getY() + CONSTANT - newGravity, velocity.getZ());
			}
		}
	}

	// Make fall damage gravity-dependant
	@ModifyVariable(method = "handleFallDamage", at = @At("HEAD"), ordinal = 1, argsOnly = true)
	private float adastra_handleFallDamage(float damageMultiplier) {
		LivingEntity entity = ((LivingEntity) (Object) this);
		return damageMultiplier * ModUtils.getPlanetGravity(entity.world);
	}
}
