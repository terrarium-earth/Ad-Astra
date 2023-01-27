package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.system.GravitySystem;
import earth.terrarium.ad_astra.common.system.OxygenSystem;
import earth.terrarium.ad_astra.common.system.SpaceMovementSystem;
import earth.terrarium.ad_astra.common.system.TemperatureSystem;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    // Entity Systems
    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra_tick(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.level instanceof ServerLevel level) {
            if (entity instanceof Player p && (p.isCreative() || p.isSpectator())) return;
            OxygenSystem.livingEntityTick(entity, level);
            TemperatureSystem.livingEntityTick(entity, level);
        }
    }

    // Gravity
    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void adastra_travel(Vec3 travelVector, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        Vec3 velocity = entity.getDeltaMovement();

        if (Planet.ORBIT.equals(entity.level.dimension()) && entity instanceof Player p && p.getAbilities().flying) return;
        if (Planet.ORBIT.equals(entity.level.dimension())) {
            SpaceMovementSystem.travel(entity, travelVector);
            ci.cancel();
        } else {
            if (!entity.isNoGravity() && !entity.isInWater() && !entity.isInLava() && !entity.isFallFlying() && !entity.hasEffect(MobEffects.SLOW_FALLING)) {
                double newGravity = 0.08 * GravitySystem.getEntityGravity(entity);
                entity.setDeltaMovement(velocity.x(), velocity.y() + 0.08 - newGravity, velocity.z());
            }
        }
    }


    // Make fall damage gravity-dependant
    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    public void adastra_causeFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if (fallDistance <= 3 / GravitySystem.getEntityGravity(entity)) {
            cir.setReturnValue(false);
        }
    }
}
