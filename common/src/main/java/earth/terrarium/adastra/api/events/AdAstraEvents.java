package earth.terrarium.adastra.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public final class AdAstraEvents {
    private static final List<OxygenTickEvent> OXYGEN_TICK_LISTENERS = new ArrayList<>();
    private static final List<TemperatureTickEvent> TEMPERATURE_TICK_LISTENERS = new ArrayList<>();
    private static final List<GravityTickEvent> GRAVITY_TICK_LISTENERS = new ArrayList<>();
    private static final List<ZeroGravityTickEvent> ZERO_GRAVITY_TICK_LISTENERS = new ArrayList<>();
    private static final List<AcidRainTickEvent> ACID_RAIN_TICK_LISTENERS = new ArrayList<>();

    /**
     * @return false to prevent ticking of oxygen
     */
    public static boolean entityOxygenTick(ServerLevel level, LivingEntity entity) {
        for (var listener : OXYGEN_TICK_LISTENERS) {
            if (!listener.tick(level, entity)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return false to prevent ticking of temperature
     */
    public static boolean entityTemperatureTick(ServerLevel level, LivingEntity entity) {
        for (var listener : TEMPERATURE_TICK_LISTENERS) {
            if (!listener.tick(level, entity)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return false to prevent ticking of gravity
     */
    public static boolean entityGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        for (var listener : GRAVITY_TICK_LISTENERS) {
            if (!listener.tick(level, entity, travelVector, movementAffectingPos)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return false to prevent ticking of zero gravity
     */
    public static boolean entityZeroGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        for (var listener : ZERO_GRAVITY_TICK_LISTENERS) {
            if (!listener.tick(level, entity, travelVector, movementAffectingPos)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return false to prevent ticking of acid rain
     */
    public static boolean entityAcidRainTick(ServerLevel level, LivingEntity entity) {
        for (var listener : ACID_RAIN_TICK_LISTENERS) {
            if (!listener.tick(level, entity)) {
                return false;
            }
        }
        return true;
    }

    @FunctionalInterface
    public interface OxygenTickEvent {
        boolean tick(ServerLevel level, LivingEntity entity);

        static void register(OxygenTickEvent listener) {
            OXYGEN_TICK_LISTENERS.add(listener);
        }
    }

    @FunctionalInterface
    public interface TemperatureTickEvent {
        boolean tick(ServerLevel level, LivingEntity entity);

        static void register(TemperatureTickEvent listener) {
            TEMPERATURE_TICK_LISTENERS.add(listener);
        }
    }

    @FunctionalInterface
    public interface GravityTickEvent {
        boolean tick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos);

        static void register(GravityTickEvent listener) {
            GRAVITY_TICK_LISTENERS.add(listener);
        }
    }

    @FunctionalInterface
    public interface ZeroGravityTickEvent {
        boolean tick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos);

        static void register(ZeroGravityTickEvent listener) {
            ZERO_GRAVITY_TICK_LISTENERS.add(listener);
        }
    }

    @FunctionalInterface
    public interface AcidRainTickEvent {
        boolean tick(ServerLevel level, LivingEntity entity);

        static void register(AcidRainTickEvent listener) {
            ACID_RAIN_TICK_LISTENERS.add(listener);
        }
    }
}
