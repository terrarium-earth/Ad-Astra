package earth.terrarium.ad_astra.entities.systems;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.items.armour.SpaceSuit;
import earth.terrarium.ad_astra.registry.ModDamageSource;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.ad_astra.util.ModUtils;
import earth.terrarium.ad_astra.util.OxygenUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class EntityOxygenSystem {
    public static void oxygenTick(LivingEntity entity, ServerLevel level) {
        if (!AdAstra.CONFIG.general.doOxygen) {
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

        if (entityHasOxygen && hasOxygenatedSpaceSuit && entity.isUnderWater() && !entity.canBreatheUnderwater()) {
            consumeOxygen(entity);
            return;
        }

        if (!entityHasOxygen) {
            if (hasOxygenatedSpaceSuit) {
                consumeOxygen(entity);
            } else if (!ModUtils.armourIsOxygenated(entity)) {
                entity.hurt(ModDamageSource.OXYGEN, 1);
                entity.setAirSupply(-40);
            }
        }
    }

    private static void consumeOxygen(LivingEntity entity) {
        entity.setAirSupply(Math.min(entity.getMaxAirSupply(), entity.getAirSupply() + 4 * 10));
        SpaceSuit.consumeSpaceSuitOxygen(entity, 3 * 10);
    }
}
