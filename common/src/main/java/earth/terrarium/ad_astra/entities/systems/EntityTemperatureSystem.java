package earth.terrarium.ad_astra.entities.systems;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.items.armour.NetheriteSpaceSuit;
import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Skeleton;
import org.apache.commons.lang3.Range;

public class EntityTemperatureSystem {
    public static void temperatureTick(LivingEntity entity, ServerLevel level) {
        if (!AdAstra.CONFIG.general.doOxygen) {
            return;
        }
        if (!entity.getType().equals(EntityType.SKELETON)) {
            if (entity.isInvertedHealAndHarm()) {
                return;
            }

            if (ModUtils.checkTag(entity, ModTags.LIVES_WITHOUT_OXYGEN)) {
                return;
            }
        }

        float temperature = ModUtils.getWorldTemperature(level);

        // Normal temperature when inside an oxygen bubble. This should probably be changed so that a separate machine is required to manage temperature.
        if (OxygenUtils.inDistributorBubble(level, entity.blockPosition())) {
            temperature = 20.0f;
        }

        Range<Integer> temperatureResistance = Range.between(-60, 70);
        if (SpaceSuit.hasFullSet(entity)) {
            temperatureResistance = ((SpaceSuit) entity.getArmorSlots().iterator().next().getItem()).getTemperatureThreshold();
        }

        if (temperature > temperatureResistance.getMaximum() && !entity.fireImmune() && !entity.hasEffect(MobEffects.FIRE_RESISTANCE) && !NetheriteSpaceSuit.hasFullSet(entity) && !ModUtils.armourIsHeatResistant(entity)) {
            burnEntity(entity);
        } else if (temperature < temperatureResistance.getMinimum() && !ModUtils.armourIsFreezeResistant(entity)) {
            freezeEntity(entity, level);
        }
    }

    private static void burnEntity(LivingEntity entity) {
        entity.hurt(DamageSource.ON_FIRE, AdAstra.CONFIG.general.heatDamage);
        entity.setSecondsOnFire(10);
    }

    private static void freezeEntity(LivingEntity entity, ServerLevel level) {
        entity.hurt(DamageSource.FREEZE, AdAstra.CONFIG.general.freezeDamage);
        entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze() + 20, entity.getTicksFrozen() + 5 * 10));
        RandomSource random = entity.level.getRandom();
        ModUtils.spawnForcedParticles((level), ParticleTypes.SNOWFLAKE, entity.getX(), entity.getY() + 1, entity.getZ(), 1, Mth.randomBetween(random, -1.0f, 1.0f) * 0.083333336f, 0.05, (double) Mth.randomBetween(random, -1.0f, 1.0f) * 0.083333336, 0);

        // Turn skeletons into strays
        if (entity instanceof Skeleton skeleton) {
            skeleton.convertTo(EntityType.STRAY, true);
        }
    }
}
