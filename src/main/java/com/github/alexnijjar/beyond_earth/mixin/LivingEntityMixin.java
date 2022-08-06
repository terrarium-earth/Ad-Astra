package com.github.alexnijjar.beyond_earth.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.registry.ModDamageSource;
import com.github.alexnijjar.beyond_earth.registry.ModTags;
import com.github.alexnijjar.beyond_earth.util.ModUtils;
import com.github.alexnijjar.beyond_earth.util.OxygenUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> ci) {

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
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        LivingEntity entity = ((LivingEntity) (Object) this);

        if (source.isFire() || source.equals(DamageSource.HOT_FLOOR)) {
            if ((entity.isOnFire() || source.equals(DamageSource.HOT_FLOOR))) {
                if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE)) {
                    ci.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    public void baseTick(CallbackInfo ci) {

        LivingEntity entity = ((LivingEntity) (Object) this);

        if (entity.world instanceof ServerWorld world) {
            Entity vehicle = entity.getVehicle();
            boolean isCreative = false;

            if (entity instanceof PlayerEntity player) {
                isCreative = player.isCreative() || player.isSpectator();
            }

            if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE)) {
                return;
            }

            if (BeyondEarth.CONFIG.general.acidRainBurns) {
                // Venus acid rain.
                if (((EntityInvoker) entity).invokeIsBeingRainedOn() && entity.world.getRegistryKey().equals(ModUtils.VENUS_KEY)) {
                    boolean affectedByRain = true;

                    if (vehicle instanceof VehicleEntity vehicleEntity) {
                        affectedByRain = !vehicleEntity.fullyConcealsRider();
                    }

                    if (affectedByRain && !isCreative) {
                        entity.damage(ModDamageSource.ACID_RAIN, 2);
                        if (entity.world.random.nextBoolean() && entity.world.random.nextBoolean() && entity.world.random.nextBoolean()) {
                            entity.setOnFireFor(1);
                        }
                    }
                }
            }

            if (BeyondEarth.CONFIG.general.doOxygen) {
                if (!ModUtils.checkTag(entity, ModTags.LIVES_WITHOUT_OXYGEN)) {
                    boolean hasOxygen = false;
                    boolean hasNetheriteSpaceSuit = false;

                    if (!isCreative) {
                        if (entity instanceof PlayerEntity player) {

                            if (SpaceSuit.hasFullSet(player) && SpaceSuit.hasOxygenatedSpaceSuit(player)) {
                                ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
                                if (chest.getItem() instanceof SpaceSuit suit) {
                                    long oxygen = suit.getAmount(chest);

                                    if (oxygen > 0) {
                                        if (!OxygenUtils.worldHasOxygen(world, player) || entity.isSubmergedInWater()) {
                                            // Allow the player to breath underwater.
                                            player.setAir(Math.min(player.getMaxAir(), player.getAir() + 4));
                                            hasOxygen = true;
                                            entity.setFrozenTicks(0);
                                            hasNetheriteSpaceSuit = NetheriteSpaceSuit.hasFullSet(player);
                                            suit.setAmount(chest, oxygen - 3);
                                        }
                                    }
                                }
                            }
                        }

                        if (!OxygenUtils.worldHasOxygen(world, entity)) {
                            float temperature = ModUtils.getWorldTemperature(world);
                            // Freeze the player in extremely cold temperatures.
                            if (temperature <= -65.0f) {
                                if (!hasOxygen) {
                                    if (entity.canFreeze() && !entity.isUndead()) {
                                        // Particles.
                                        Random random = entity.world.getRandom();
                                        ModUtils.spawnForcedParticles((world), ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1, entity.getZ(), 1, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05,
                                                (double) MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336, 0);

                                        // Freeze effect, like powdered snow
                                        entity.setFrozenTicks(Math.min(entity.getMinFreezeDamageTicks(), entity.getFrozenTicks() + 5));
                                        entity.damage(ModDamageSource.OXYGEN, 2);
                                        entity.setAir(-20);
                                    }
                                    // Turn skeletons into strays
                                    if (entity instanceof SkeletonEntity skeleton) {
                                        skeleton.convertTo(EntityType.STRAY, true);
                                    }
                                }
                                // Burn the player in extremely hot temperatures
                            } else if (temperature > 70.0f) {
                                if (!hasOxygen || (!hasNetheriteSpaceSuit && !entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE))) {
                                    if (!entity.isFireImmune()) {
                                        entity.damage(ModDamageSource.OXYGEN, 2);
                                        entity.setAir(-20);
                                        entity.setOnFireFor(10);
                                    }
                                }
                            } else if (!hasOxygen) {
                                entity.damage(ModDamageSource.OXYGEN, 1);
                                entity.setAir(-20);
                            }
                        }
                    }
                }
            }
        }
    }
}