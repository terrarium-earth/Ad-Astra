package earth.terrarium.adastra.common.events.fabric;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ModEventsImpl {
    public static boolean entityOxygenTick(ServerLevel level, LivingEntity entity) {
        return EntityTickEvents.OXYGEN_TICK.invoker().tick(level, entity);
    }

    public static boolean entityTemperatureTick(ServerLevel level, LivingEntity entity) {
        return EntityTickEvents.TEMPERATURE_TICK.invoker().tick(level, entity);
    }

    public static boolean entityGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        return EntityTickEvents.GRAVITY_TICK.invoker().tick(level, entity, travelVector, movementAffectingPos);
    }

    public static boolean entityZeroGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        return EntityTickEvents.ZERO_GRAVITY_TICK.invoker().tick(level, entity, travelVector, movementAffectingPos);
    }

    public static boolean entityAcidRainTick(ServerLevel level, LivingEntity entity) {
        return EntityTickEvents.ACID_RAIN_TICK.invoker().tick(level, entity);
    }
}
