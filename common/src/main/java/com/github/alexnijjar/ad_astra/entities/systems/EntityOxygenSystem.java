package com.github.alexnijjar.ad_astra.entities.systems;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.items.armour.SpaceSuit;
import com.github.alexnijjar.ad_astra.registry.ModDamageSource;
import com.github.alexnijjar.ad_astra.registry.ModTags;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import com.github.alexnijjar.ad_astra.util.OxygenUtils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

public class EntityOxygenSystem {
    public static void oxygenTick(LivingEntity entity, ServerWorld world) {
        if (!AdAstra.CONFIG.general.doOxygen) {
            return;
        }
        if (entity.isUndead()) {
            return;
        }

        if (ModUtils.checkTag(entity, ModTags.LIVES_WITHOUT_OXYGEN)) {
            return;
        }

        if (OxygenUtils.worldHasOxygen(world) && !entity.isSubmergedInWater()) {
            return;
        }

        boolean entityHasOxygen = OxygenUtils.entityHasOxygen(world, entity);
        boolean hasOxygenatedSpaceSuit = SpaceSuit.hasOxygenatedSpaceSuit(entity) && SpaceSuit.hasFullSet(entity);

        if (entityHasOxygen && hasOxygenatedSpaceSuit && entity.isSubmergedInWater() && !entity.canBreatheInWater()) {
            consumeOxygen(entity, world);
            return;
        }

        if (!entityHasOxygen) {
            if (hasOxygenatedSpaceSuit) {
                consumeOxygen(entity, world);
            } else if (!ModUtils.armourIsOxygenated(entity)) {
                entity.damage(ModDamageSource.OXYGEN, 1);
                entity.setAir(-40);
            }
        }
    }

    private static void consumeOxygen(LivingEntity entity, ServerWorld world) {
        entity.setAir(Math.min(entity.getMaxAir(), entity.getAir() + 4 * 10));
        SpaceSuit.consumeSpaceSuitOxygen(entity, 3 * 10);
    }
}
