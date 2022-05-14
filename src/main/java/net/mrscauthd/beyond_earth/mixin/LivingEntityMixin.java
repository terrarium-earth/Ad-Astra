package net.mrscauthd.beyond_earth.mixin;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.client.screens.PlayerOverlayScreen;
import net.mrscauthd.beyond_earth.entities.mobs.ModEntity;
import net.mrscauthd.beyond_earth.entities.vehicles.LanderEntity;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;
import net.mrscauthd.beyond_earth.items.armour.SpaceSuit;
import net.mrscauthd.beyond_earth.registry.ModArmour;
import net.mrscauthd.beyond_earth.util.ModDamageSource;
import net.mrscauthd.beyond_earth.util.ModUtils;

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
        return damageMultiplier * ModUtils.getPlanetGravity(false, entity.world.getRegistryKey());
    }

    @Inject(method = "handleFallDamage", at = @At("HEAD"), cancellable = true)
    public void handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource, CallbackInfoReturnable<Boolean> info) {
        LivingEntity entity = ((LivingEntity) (Object) this);
        if (fallDistance <= 3 / ModUtils.getPlanetGravity(false, entity.world.getRegistryKey())) {
            info.setReturnValue(false);
        }
    }

    @Inject(method = "baseTick", at = @At("TAIL"))
    public void baseTick(CallbackInfo info) {

        LivingEntity entity = ((LivingEntity) (Object) this);

        Entity vehicle = entity.getVehicle();
        boolean isCreative = false;

        // Venus acid rain.
        if (entity.isTouchingWaterOrRain() && entity.world.getRegistryKey().equals(ModUtils.VENUS_KEY)) {
            boolean affectedByRain = true;
            if (entity instanceof PlayerEntity player) {
                isCreative = player.isCreative();
                if (isCreative || vehicle instanceof RocketEntity || vehicle instanceof LanderEntity) {
                    affectedByRain = false;
                }
            }
            if (affectedByRain) {
                entity.damage(ModDamageSource.ACID_RAIN, 2);
                if (entity.world.random.nextBoolean() && entity.world.random.nextBoolean()) {
                    entity.setOnFireFor(1);
                }
            }
        }

        // All of the entities in Beyond Earth are aliens so they don't need an oxygen system.
        if (!(entity instanceof ModEntity)) {
            World world = entity.world;
            boolean hasOxygen = false;
            boolean hasNetheriteSpaceSuit = false;

            if (entity instanceof PlayerEntity player) {
                PlayerOverlayScreen.shouldRenderOxygen = ModUtils.hasFullSpaceSet(player);
                isCreative = player.isCreative();

                if (ModUtils.hasFullSpaceSet(player) && ModUtils.hasOxygenatedSpaceSuit(player)) {
                    ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
                    NbtCompound nbt = chest.getNbt();

                    if (nbt.contains("Oxygen")) {
                        int oxygen = nbt.getInt("Oxygen");

                        if (oxygen > 0) {
                            if (!ModUtils.dimensionHasOxygen(false, world) || entity.isSubmergedInWater()) {
                                if (!isCreative) {
                                    nbt.putInt("Oxygen", nbt.getInt("Oxygen") - 1);
                                }
                                // Allow the player to breath underwater.
                                player.setAir(275);
                                hasOxygen = true;
                                hasNetheriteSpaceSuit = ModUtils.hasFullNetheriteSpaceSet(player);
                            }
                        }
                        boolean isNetherite = chest.isOf(ModArmour.NETHERITE_SPACE_SUIT);
                        if (oxygen > SpaceSuit.getMaxOxygen(isNetherite)) {
                            nbt.putInt("Oxygen", SpaceSuit.getMaxOxygen(isNetherite));
                        }

                        // Render oxygen info.
                        if (player instanceof ClientPlayerEntity clientPlayer) {
                            double ratio = oxygen / (float) SpaceSuit.getMaxOxygen(isNetherite);
                            PlayerOverlayScreen.oxygenRatio = ratio;
                        }
                    }
                }
            }

            if (!isCreative) {
                if (!ModUtils.dimensionHasOxygen(false, world)) {
                    float temperature = ModUtils.getDimensionTemperature(false, world);
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