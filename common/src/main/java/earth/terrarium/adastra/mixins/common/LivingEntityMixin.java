package earth.terrarium.adastra.mixins.common;

import earth.terrarium.adastra.api.systems.GravityApi;
import earth.terrarium.adastra.api.systems.OxygenApi;
import earth.terrarium.adastra.api.systems.TemperatureApi;
import earth.terrarium.adastra.client.utils.ClientData;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.common.events.ModEvents;
import earth.terrarium.adastra.common.handlers.base.PlanetData;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import earth.terrarium.adastra.common.registry.ModDamageSources;
import earth.terrarium.adastra.common.tags.ModBiomeTags;
import earth.terrarium.adastra.common.tags.ModEntityTypeTags;
import net.minecraft.Optionull;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

        if (entity.tickCount % 20 == 0) {
            if (ModEvents.entityOxygenTick(level, entity)) {
                OxygenApi.API.entityTick(level, entity);
            }

            if (ModEvents.entityTemperatureTick(level, entity)) {
                TemperatureApi.API.entityTick(level, entity);
            }
        }

        if (entity.tickCount % 10 == 0
            && level().getBiome(blockPosition()).is(ModBiomeTags.HAS_ACID_RAIN)
            && !getType().is(ModEntityTypeTags.CAN_SURVIVE_ACID_RAIN)
            && adastra$isInRain()
        ) {
            if (ModEvents.entityAcidRainTick(level, entity)) {
                entity.hurt(ModDamageSources.create(level(), ModDamageSources.ACID_RAIN), 3);
                playSound(SoundEvents.GENERIC_BURN, 0.4f, 2 + random.nextFloat() * 0.4f);
            }
        }
    }

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void adastra$travel(Vec3 travelVector, CallbackInfo ci) {
        float gravity = GravityApi.API.getGravity(this);
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity instanceof Player player) {
            if (player.getAbilities().flying) return;
            if (level().isClientSide()) {
                gravity = Optionull.mapOrDefault(ClientData.getLocalData(), PlanetData::gravity, PlanetConstants.EARTH_GRAVITY);
            }
        }

        var movementAffectingPos = getBlockPosBelowThatAffectsMyMovement();
        if (gravity <= PlanetConstants.ZERO_GRAVITY_THRESHOLD) {
            if (ModEvents.entityZeroGravityTick(level(), entity, travelVector, movementAffectingPos)) {
                GravityApi.API.entityTick(level(), entity, travelVector, movementAffectingPos);
                ci.cancel();
            }
        } else if (ModEvents.entityGravityTick(level(), entity, travelVector, movementAffectingPos)) {
            if (this.isInWater()
                || this.isInLava()
                || entity.isFallFlying()
                || entity.hasEffect(MobEffects.SLOW_FALLING)) {
                return;
            }
            float newGravity = 0.08f * gravity;
            Vec3 velocity = this.getDeltaMovement();
            this.setDeltaMovement(velocity.x(), velocity.y() + 0.08f - newGravity, velocity.z());
        }
    }

    @Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
    public void adastra$hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity entity = ((LivingEntity) (Object) this);

        if ((source.is(DamageTypeTags.IS_FIRE) || source.is(DamageTypes.HOT_FLOOR)) && SpaceSuitItem.hasFullNetheriteSet(entity)) {
            entity.setRemainingFireTicks(0);
            cir.setReturnValue(false);
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

    @Unique
    private boolean adastra$isInRain() {
        var pos = blockPosition();
        return level().isRainingAt(pos) || level().isRainingAt(BlockPos.containing(pos.getX(), getBoundingBox().maxY, pos.getZ()));
    }
}
