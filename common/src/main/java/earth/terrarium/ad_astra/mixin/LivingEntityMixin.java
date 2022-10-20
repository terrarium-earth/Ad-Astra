package earth.terrarium.ad_astra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import earth.terrarium.ad_astra.entities.systems.EntityAcidRainSystem;
import earth.terrarium.ad_astra.entities.systems.EntityOxygenSystem;
import earth.terrarium.ad_astra.entities.systems.EntityTemperatureSystem;
import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.ad_astra.util.ModUtils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
	public void adastra_handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> ci) {

		LivingEntity entity = ((LivingEntity) (Object) this);

		if (entity.getVehicle() instanceof VehicleEntity vehicle) {
			if (!vehicle.canRiderTakeFallDamage()) {
				ci.setReturnValue(false);
			}
		}

		if (fallDistance <= 3 / ModUtils.getPlanetGravity(entity.world)) {
			ci.setReturnValue(false);
		}
	}

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	public void adastra_damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
		LivingEntity entity = ((LivingEntity) (Object) this);

		if (source.isFire() || source.equals(DamageSource.HOT_FLOOR)) {
			if ((entity.isOnFire() || source.equals(DamageSource.HOT_FLOOR))) {
				if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE)) {
					ci.setReturnValue(false);
				}
			}
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	public void adastra_tick(CallbackInfo ci) {

		LivingEntity entity = ((LivingEntity) (Object) this);
		World world = entity.world;
		if (!world.isClient) {
			if (world.getTime() % 10 == 0) {
				if (entity instanceof PlayerEntity player && (player.isCreative() || player.isSpectator())) {
					return;
				}

				EntityOxygenSystem.oxygenTick(entity, (ServerWorld) world);

				if (!ModUtils.isSpaceWorld(world)) {
					return;
				}

				EntityTemperatureSystem.temperatureTick(entity, (ServerWorld) world);
				EntityAcidRainSystem.acidRainTick(entity, (ServerWorld) world);
			}
		}
	}
}