package earth.terrarium.ad_astra.mixin;

import earth.terrarium.ad_astra.common.entity.system.EntityAcidRainSystem;
import earth.terrarium.ad_astra.common.entity.system.EntityOxygenSystem;
import earth.terrarium.ad_astra.common.entity.system.EntityTemperatureSystem;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    public void ad_astra$handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {

        LivingEntity entity = ((LivingEntity) (Object) this);

        if (entity.getVehicle() instanceof Vehicle vehicle) {
            if (!vehicle.canRiderTakeFallDamage()) {
                cir.setReturnValue(false);
            }
        }

        if (fallDistance <= 3 / ModUtils.getEntityGravity(entity)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void ad_astra$damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = ((LivingEntity) (Object) this);

        if (source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.HOT_FLOOR)) {
            if ((entity.isOnFire() || source.is(DamageTypes.HOT_FLOOR))) {
                if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE)) {
                    cir.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void ad_astra$tick(CallbackInfo ci) {

        LivingEntity entity = ((LivingEntity) (Object) this);
        Level level = entity.level;
        if (!level.isClientSide) {
            if (level.getGameTime() % 10 == 0) {
                if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
                    return;
                }

                EntityOxygenSystem.oxygenTick(entity, (ServerLevel) level);

                if (!ModUtils.isSpacelevel(level)) {
                    return;
                }

                EntityTemperatureSystem.temperatureTick(entity, (ServerLevel) level);
                EntityAcidRainSystem.acidRainTick(entity, (ServerLevel) level);
            }
        }
    }
}