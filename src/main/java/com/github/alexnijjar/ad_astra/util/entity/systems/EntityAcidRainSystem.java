package com.github.alexnijjar.ad_astra.util.entity.systems;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.ad_astra.mixin.EntityInvoker;
import com.github.alexnijjar.ad_astra.registry.ModDamageSource;
import com.github.alexnijjar.ad_astra.registry.ModTags;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;

public class EntityAcidRainSystem {

    public static void acidRainTick(LivingEntity entity, ServerWorld world) {
        if (!AdAstra.CONFIG.general.acidRainBurns) {
            return;
        }

        if (!entity.world.getRegistryKey().equals(ModUtils.VENUS_KEY)) {
            return;
        }

        if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE)) {
            return;
        }

        // Let the entity survive in a fully sealed vehicle
        if (entity.getVehicle() instanceof VehicleEntity vehicle && vehicle.fullyConcealsRider()) {
            return;
        }

       causeDamage(entity, world);
    }

    private static void causeDamage(LivingEntity entity, ServerWorld world) {
        if (((EntityInvoker) entity).invokeIsBeingRainedOn()) {
            entity.damage(ModDamageSource.ACID_RAIN, 3);
            // Infrequently set the entity on fire
            if (world.random.nextInt(8) == 0) {
                entity.setOnFireFor(1);
            }
        }
    }
}
