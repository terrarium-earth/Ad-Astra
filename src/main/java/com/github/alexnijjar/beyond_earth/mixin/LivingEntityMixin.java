package com.github.alexnijjar.beyond_earth.mixin;

import java.util.Random;

import com.github.alexnijjar.beyond_earth.entities.mobs.ModEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.registry.ModTags;
import com.github.alexnijjar.beyond_earth.util.ModDamageSource;
import com.github.alexnijjar.beyond_earth.util.ModUtils;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyConstant(method = "travel", constant = @Constant(doubleValue = 0.08))
    public double setGravity(double value) {
        return ModUtils.getMixinGravity(value, this);
    }

    // Make fall damage gravity-dependant.
    @ModifyVariable(method = "handleFallDamage", at = @At("HEAD"), ordinal = 1)
    private float handleFallDamage(float damageMultiplier) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        return damageMultiplier * ModUtils.getPlanetGravity(entity.world);
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {

        LivingEntity entity = ((LivingEntity) (Object) this);

        if (entity.getVehicle() instanceof VehicleEntity vehicle) {
            if (!vehicle.canRiderTakeFallDamage()) {
                info.setReturnValue(false);
            }
        }

        if (fallDistance <= 3 / ModUtils.getPlanetGravity(entity.world)) {
            info.setReturnValue(false);
        }

        if (entity instanceof PlayerEntity player) {
            if (ModUtils.hasFullJetSuitSet(player)) {
                info.setReturnValue(false);
            }
        }
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        LivingEntity entity = ((LivingEntity) (Object) this);

        if (source.isFire() || source.equals(DamageSource.HOT_FLOOR)) {
            if ((entity.isOnFire() || source.equals(DamageSource.HOT_FLOOR))) {
                if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE_TAG)) {
                    info.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    public void baseTick(CallbackInfo info) {

        LivingEntity entity = ((LivingEntity) (Object) this);

        if (entity instanceof ArmorStandEntity) {
            return;
        }

        Entity vehicle = entity.getVehicle();
        boolean isCreative = false;

        if (entity instanceof PlayerEntity player) {
            isCreative = player.isCreative();
        }

        if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE_TAG)) {
            return;
        }

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

        // All of the living entities in Beyond Earth are aliens so they don't need an oxygen system.
        if (entity instanceof ModEntity) {
            return;
        }

        if (!(entity instanceof ModEntity)) {
            World world = entity.world;
            boolean hasOxygen = false;
            boolean hasNetheriteSpaceSuit = false;

            if (entity instanceof PlayerEntity player) {

                if (ModUtils.hasFullSpaceSet(player) && ModUtils.hasOxygenatedSpaceSuit(player)) {
                    ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
                    NbtCompound nbt = chest.getNbt();

                    if (nbt.contains("Oxygen")) {
                        int oxygen = nbt.getInt("Oxygen");

                        if (chest.getItem() instanceof SpaceSuit suit) {
                            if (oxygen > 0) {
                                if (!ModUtils.worldHasOxygen(world) || entity.isSubmergedInWater()) {
                                    if (!isCreative) {
                                        nbt.putInt("Oxygen", nbt.getInt("Oxygen") - 1);
                                    }
                                    // Allow the player to breath underwater.
                                    player.setAir(275);
                                    hasOxygen = true;
                                    entity.setFrozenTicks(0);
                                    hasNetheriteSpaceSuit = ModUtils.hasFullNetheriteSpaceSet(player);
                                }
                            }
                            if (oxygen > suit.getMaxOxygen()) {
                                nbt.putInt("Oxygen", suit.getMaxOxygen());
                            }
                        }
                    }
                }
            }

            if (!isCreative) {
                if (!ModUtils.worldHasOxygen(world)) {
                    float temperature = ModUtils.getWorldTemperature(world);
                    // Freeze the player in extremely cold temperatures.
                    if (temperature <= -65.0f) {
                        if (!hasOxygen) {
                            if (entity.canFreeze() && !entity.isUndead()) {
                                // Particles.
                                if (world.isClient) {
                                    Random random = entity.world.getRandom();
                                    world.addParticle(ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1, entity.getZ(), MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05f, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f);
                                }
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