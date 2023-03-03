package earth.terrarium.ad_astra.common.entity.system;

import earth.terrarium.ad_astra.common.item.armor.SpaceSuit;
import earth.terrarium.ad_astra.common.registry.ModDamageSource;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.util.ModUtils;
import earth.terrarium.ad_astra.common.util.OxygenUtils;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class EntityOxygenSystem {
    public static void oxygenTick(LivingEntity entity, ServerLevel level) {
        if (!AdAstraConfig.doOxygen) {
            return;
        }
        if (entity.isInvertedHealAndHarm()) {
            return;
        }

        if (ModUtils.checkTag(entity, ModTags.LIVES_WITHOUT_OXYGEN)) {
            return;
        }

        if (OxygenUtils.levelHasOxygen(level) && !entity.isUnderWater()) {
            return;
        }

        boolean entityHasOxygen = OxygenUtils.entityHasOxygen(level, entity);
        boolean hasOxygenatedSpaceSuit = SpaceSuit.hasOxygenatedSpaceSuit(entity) && SpaceSuit.hasFullSet(entity);

        if (entityHasOxygen && hasOxygenatedSpaceSuit && entity.isUnderWater() && !entity.canBreatheUnderwater() && !entity.hasEffect(MobEffects.WATER_BREATHING)) {
            consumeOxygen(entity);
            return;
        }

        if (!entityHasOxygen) {
            if (hasOxygenatedSpaceSuit) {
                consumeOxygen(entity);
            } else if (!ModUtils.armourIsOxygenated(entity)) {
                entity.hurt(ModDamageSource.OXYGEN, AdAstraConfig.oxygenDamage);
                entity.setAirSupply(-40);
            }
        }
    }

    private static void consumeOxygen(LivingEntity entity) {
        if (entity.getLevel().getGameTime() % 3 == 0) {
            entity.setAirSupply(Math.min(entity.getMaxAirSupply(), entity.getAirSupply() + 4 * 10));
            SpaceSuit.consumeSpaceSuitOxygen(entity, FluidHooks.buckets(1) / 1000);
        }
    }
}
