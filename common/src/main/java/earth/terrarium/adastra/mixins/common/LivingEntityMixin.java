package earth.terrarium.adastra.mixins.common;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.client.utils.ClientData;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.entities.base.RadioHolder;
import earth.terrarium.adastra.common.handlers.base.PlanetData;
import net.minecraft.Optionull;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void adastra$tick(CallbackInfo ci) {
        if (!(level() instanceof ServerLevel level)) return;
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player p && (p.isCreative() || p.isSpectator())) return;
        OxygenApi.API.entityTick(level, entity);
        TemperatureApi.API.entityTick(level, entity);
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void adastra$travel(Vec3 travelVector, CallbackInfo ci) {
        float gravity = GravityApi.API.getGravity(this);
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player player) {
            if (player.getAbilities().flying) return;
            if (level().isClientSide()) {
                gravity = Optionull.mapOrDefault(ClientData.localData, PlanetData::gravity, PlanetConstants.EARTH_GRAVITY);
            }
        }

        if (gravity <= PlanetConstants.ZERO_GRAVITY_THRESHOLD) {
            GravityApi.API.entityTick(this.level(), entity, travelVector, getBlockPosBelowThatAffectsMyMovement());
            ci.cancel();
        } else {
            if (this.isInWater() || this.isInLava() || entity.isFallFlying() || entity.hasEffect(MobEffects.SLOW_FALLING)) {
                return;
            }
            float newGravity = 0.08f * gravity;
            Vec3 velocity = this.getDeltaMovement();
            this.setDeltaMovement(velocity.x(), velocity.y() + 0.08f - newGravity, velocity.z());
        }
    }

    @Inject(method = "stopRiding", at = @At("HEAD"))
    private void adastra$stopRiding(CallbackInfo ci) {
        //noinspection ConstantValue
        if ((Object) this instanceof Player p
            && p.level().isClientSide()
            && p.getVehicle() instanceof RadioHolder) {
            RadioHandler.stop();
        }
    }

    @ModifyVariable(
        method = "causeFallDamage",
        at = @At("HEAD"),
        ordinal = 1,
        argsOnly = true)
    private float adastra$causeFallDamage(float multiplier) {
        return multiplier * GravityApi.API.getGravity(this);
    }

    @Inject(method = "causeFallDamage", at = @At("HEAD"), cancellable = true)
    private void adastra$causeFallDamage(float fallDistance, float multiplier, DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (!(fallDistance <= 3 / GravityApi.API.getGravity(this))) return;
        cir.setReturnValue(false);
    }
}
