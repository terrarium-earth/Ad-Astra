package com.github.alexnijjar.ad_astra.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.registry.ModTags;
import com.github.alexnijjar.ad_astra.util.ModDamageSource;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08))
    public double setGravity(double value) {
        if (AdAstra.CONFIG.world.doLivingEntityGravity) {
            return ModUtils.getMixinGravity(value, this);
        } else {
            return value;
        }
    }

    // Make fall damage gravity-dependant.
    @ModifyVariable(method = "handleFallDamage", at = @At("HEAD"), ordinal = 1)
    private float handleFallDamage(float damageMultiplier) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        return damageMultiplier * ModUtils.getPlanetGravity(entity.world);
    }

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
                isCreative = player.isCreative();
            }

            if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE)) {
                return;
            }

            if (AdAstra.CONFIG.world.acidRainBurns) {
                // Venus acid rain.
                if (((EntityInvoker) entity).invokeIsBeingRainedOn() && entity.world.getRegistryKey().equals(ModUtils.VENUS_KEY)) {
                    boolean affectedByRain = true;

                    if (vehicle instanceof VehicleEntity vehicleEntity) {
                        affectedByRain = !vehicleEntity.fullyConcealsRider();
                    }

                    if (affectedByRain && !isCreative) {
                        entity.damage(ModDamageSource.ACID_RAIN, 2);
                        if (entity.world.random.nextBoolean() && entity.world.random.nextBoolean()) {
                            entity.setOnFireFor(1);
                        }
                    }
                }
            }

            if (AdAstra.CONFIG.world.doOxygen) {
                if (!ModUtils.checkTag(entity, ModTags.LIVES_WITHOUT_OXYGEN)) {
                    boolean hasOxygen = false;
                    boolean hasNetheriteSpaceSuit = false;

                    if (!isCreative) {
                        if (entity instanceof PlayerEntity player) {

                            if (ModUtils.hasFullSpaceSet(player) && ModUtils.hasOxygenatedSpaceSuit(player)) {
                                ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
                                if (chest.getItem() instanceof SpaceSuit suit) {
                                    long oxygen = suit.getAmount(chest);

                                    if (oxygen > 0) {
                                        if (!ModUtils.worldHasOxygen(world, player) || entity.isSubmergedInWater()) {
                                            // Allow the player to breath underwater.
                                            player.setAir(275);
                                            hasOxygen = true;
                                            entity.setFrozenTicks(0);
                                            hasNetheriteSpaceSuit = ModUtils.hasFullNetheriteSpaceSet(player);
                                            suit.setAmount(chest, oxygen - 3);
                                        }
                                    }
                                }
                            }
                        }

                        if (!ModUtils.worldHasOxygen(world, entity)) {
                            float temperature = ModUtils.getWorldTemperature(world);
                            // Freeze the player in extremely cold temperatures.
                            if (temperature <= -65.0f) {
                                if (!hasOxygen) {
                                    if (entity.canFreeze() && !entity.isUndead()) {
                                        // Particles.
                                        Random random = entity.world.getRandom();
                                        ModUtils.spawnForcedParticles((world), ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1, entity.getZ(), 1, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05,
                                                (double) MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336, 0);

                                        // Freeze effect, like powdered snow.
                                        if (entity.getFrozenTicks() < 300) {
                                            entity.setFrozenTicks(entity.getFrozenTicks() + 20);
                                        }
                                        entity.damage(ModDamageSource.OXYGEN, 2);
                                    }
                                    // Turn skeletons into strays.
                                    if (entity instanceof SkeletonEntity skeleton) {
                                        skeleton.convertTo(EntityType.STRAY, true);
                                    }
                                }
                                // Burn the player in extremely hot temperatures.
                            } else if (temperature > 70.0f) {
                                if (!hasOxygen || !hasNetheriteSpaceSuit) {
                                    if (!entity.isFireImmune()) {
                                        entity.damage(ModDamageSource.OXYGEN, 2);
                                        entity.setOnFireFor(10);
                                    }
                                }
                            } else if (!hasOxygen) {
                                entity.damage(ModDamageSource.OXYGEN, 1);
                            }
                            entity.setAir(-20);
                        }
                    }
                }
            }
        }
    }
}