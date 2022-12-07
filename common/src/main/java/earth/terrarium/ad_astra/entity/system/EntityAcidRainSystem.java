package earth.terrarium.ad_astra.entity.system;

import earth.terrarium.ad_astra.config.AdAstraConfig;
import earth.terrarium.ad_astra.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.mixin.EntityInvoker;
import earth.terrarium.ad_astra.registry.ModDamageSource;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.ad_astra.util.ModUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;

public class EntityAcidRainSystem {

    public static void acidRainTick(LivingEntity entity, ServerLevel level) {
        if (!AdAstraConfig.acidRainBurns) {
            return;
        }

        if (!entity.level.dimension().equals(ModUtils.VENUS_KEY)) {
            return;
        }

        if (ModUtils.checkTag(entity, ModTags.FIRE_IMMUNE)) {
            return;
        }

        // Let the entity survive in a fully sealed vehicle
        if (entity.getVehicle() instanceof Vehicle vehicle && !vehicle.fullyConcealsRider()) {
            return;
        }

        causeDamage(entity, level);
    }

    private static void causeDamage(LivingEntity entity, ServerLevel level) {
        if (((EntityInvoker) entity).invokeIsInRain()) {
            entity.hurt(ModDamageSource.ACID_RAIN, AdAstraConfig.acidRainDamage);
            // Infrequently set the entity on fire
            if (level.random.nextInt(8) == 0) {
                entity.setSecondsOnFire(1);
            }
        }
    }
}
