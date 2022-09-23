package com.github.alexnijjar.ad_astra.entities.systems;

import org.apache.commons.lang3.Range;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.registry.ModTags;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import com.github.alexnijjar.ad_astra.util.OxygenUtils;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;

public class EntityTemperatureSystem {
    public static void temperatureTick(LivingEntity entity, ServerWorld world) {
        if (!AdAstra.CONFIG.general.doOxygen) {
            return;
        }
        if (!entity.getType().equals(EntityType.SKELETON)) {
            if (entity.isUndead()) {
                return;
            }

            if (ModUtils.checkTag(entity, ModTags.LIVES_WITHOUT_OXYGEN)) {
                return;
            }
        }

        float temperature = ModUtils.getWorldTemperature(world);

        // Normal temperature when inside of an oxygen bubble. This should probably be changed so that a separate machine is required to manage temperature.
        if (OxygenUtils.inDistributorBubble(world, entity.getBlockPos())) {
            temperature = 20.0f;
        }

        Range<Integer> temperatureResistance = Range.between(-60, 70);
        if (SpaceSuit.hasFullSet(entity)) {
            temperatureResistance = ((SpaceSuit) entity.getArmorItems().iterator().next().getItem()).getTemperatureThreshold();
        }

        if (temperature > temperatureResistance.getMaximum() && !entity.isFireImmune() && !entity.hasStatusEffect(StatusEffects.FIRE_RESISTANCE) && !NetheriteSpaceSuit.hasFullSet(entity) && !ModUtils.armourIsHeatResistant(entity)) {
            burnEntity(entity, world);
        } else if (temperature < temperatureResistance.getMinimum() && !ModUtils.armourIsFreezeResistant(entity)) {
            freezeEntity(entity, world);
        }
    }

    private static void burnEntity(LivingEntity entity, ServerWorld world) {
        entity.damage(DamageSource.ON_FIRE, 2);
        entity.setOnFireFor(10);
    }

    private static void freezeEntity(LivingEntity entity, ServerWorld world) {
        entity.damage(DamageSource.FREEZE, 1);
        entity.setFrozenTicks(Math.min(entity.getMinFreezeDamageTicks() + 20, entity.getFrozenTicks() + 5 * 10));
        RandomGenerator random = entity.world.getRandom();
        ModUtils.spawnForcedParticles((world), ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1, entity.getZ(), 1, MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05, (double) MathHelper.nextBetween(random, -1.0f, 1.0f) * 0.083333336, 0);

        // Turn skeletons into strays
        if (entity instanceof SkeletonEntity skeleton) {
            skeleton.convertTo(EntityType.STRAY, true);
        }
    }
}
