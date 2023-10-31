package earth.terrarium.ad_astra.common.entity.system;

import org.jetbrains.annotations.NotNull;

import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import earth.terrarium.ad_astra.common.registry.ModDamageSource;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class EntityOxygenSystem {
    @NotNull
    public static EntityOxygenStatus getOxygenStatus(LivingEntity entity, Level level) {
        if (!AdAstraConfig.doOxygen) {
            return EntityOxygenStatus.IGNORE;
        }
        if (entity.isInvertedHealAndHarm()) {
            return EntityOxygenStatus.IGNORE;
        }

        if (ModUtils.checkTag(entity, ModTags.LIVES_WITHOUT_OXYGEN)) {
            return EntityOxygenStatus.IGNORE;
        }

        if (OxygenUtils.levelHasOxygen(level) && !entity.isUnderWater()) {
            return EntityOxygenStatus.IGNORE;
        }

        boolean entityHasOxygen = OxygenUtils.entityHasOxygen(level, entity);
        boolean hasOxygenatedSpaceSuit = SpaceSuit.hasOxygenatedSpaceSuit(entity) && SpaceSuit.hasFullSet(entity);

        if (entityHasOxygen && hasOxygenatedSpaceSuit && entity.isUnderWater() && !entity.canBreatheUnderwater() && !entity.hasEffect(MobEffects.WATER_BREATHING)) {
            return EntityOxygenStatus.CONSUME;
        }

        if (!entityHasOxygen) {
            if (hasOxygenatedSpaceSuit) {
                return EntityOxygenStatus.CONSUME;
            } else if (!ModUtils.armourIsOxygenated(entity)) {
                return EntityOxygenStatus.DAMAGE;
            }
        }

        return EntityOxygenStatus.IGNORE;
    }

    public static void oxygenTick(LivingEntity entity, ServerLevel level) {
        EntityOxygenStatus status = getOxygenStatus(entity, level);
        if (status == EntityOxygenStatus.CONSUME) {
            consumeOxygen(entity);
        } else if (status == EntityOxygenStatus.DAMAGE) {
            entity.hurt(ModDamageSource.OXYGEN, AdAstraConfig.oxygenDamage);
            entity.setAirSupply(-40);
        }
    }

    private static void consumeOxygen(LivingEntity entity) {
        if (entity.getLevel().getGameTime() % 3 == 0) {
            entity.setAirSupply(Math.min(entity.getMaxAirSupply(), entity.getAirSupply() + 4 * 10));
            SpaceSuit.consumeSpaceSuitOxygen(entity, FluidHooks.buckets(1) / 1000);
        }
    }
}
