package earth.terrarium.adastra.api.events;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;

public final class AdAstraEvents {
    private static final List<OxygenTickEvent> OXYGEN_TICK_LISTENERS = new ArrayList<>();
    private static final List<TemperatureTickEvent> TEMPERATURE_TICK_LISTENERS = new ArrayList<>();
    private static final List<HotTemperatureTickEvent> HOT_TEMPERATURE_TICK_LISTENERS = new ArrayList<>();
    private static final List<ColdTemperatureTickEvent> COLD_TEMPERATURE_TICK_LISTENERS = new ArrayList<>();
    private static final List<GravityTickEvent> GRAVITY_TICK_LISTENERS = new ArrayList<>();
    private static final List<ZeroGravityTickEvent> ZERO_GRAVITY_TICK_LISTENERS = new ArrayList<>();
    private static final List<AcidRainTickEvent> ACID_RAIN_TICK_LISTENERS = new ArrayList<>();
    private static final List<EnvironmentTickEvent> ENVIRONMENT_TICK_LISTENERS = new ArrayList<>();

    @FunctionalInterface
    public interface OxygenTickEvent {
        boolean tick(ServerLevel level, LivingEntity entity);

        static void register(OxygenTickEvent listener) {
            OXYGEN_TICK_LISTENERS.add(listener);
        }

        /**
         * @return false to prevent ticking of oxygen
         */
        @ApiStatus.Internal
        static boolean post(ServerLevel level, LivingEntity entity) {
            for (var listener : OXYGEN_TICK_LISTENERS) {
                if (!listener.tick(level, entity)) {
                    return false;
                }
            }
            return true;
        }
    }

    @FunctionalInterface
    public interface TemperatureTickEvent {
        boolean tick(ServerLevel level, LivingEntity entity);

        static void register(TemperatureTickEvent listener) {
            TEMPERATURE_TICK_LISTENERS.add(listener);
        }

        /**
         * @return false to prevent ticking of temperature
         */
        @ApiStatus.Internal
        static boolean post(ServerLevel level, LivingEntity entity) {
            for (var listener : TEMPERATURE_TICK_LISTENERS) {
                if (!listener.tick(level, entity)) {
                    return false;
                }
            }
            return true;
        }
    }

    @FunctionalInterface
    public interface HotTemperatureTickEvent {
        boolean tick(ServerLevel level, LivingEntity entity);

        static void register(HotTemperatureTickEvent listener) {
            HOT_TEMPERATURE_TICK_LISTENERS.add(listener);
        }

        /**
         * @return false to prevent the entity from burning
         */
        @ApiStatus.Internal
        static boolean post(ServerLevel level, LivingEntity entity) {
            for (var listener : HOT_TEMPERATURE_TICK_LISTENERS) {
                if (!listener.tick(level, entity)) {
                    return false;
                }
            }
            return true;
        }
    }

    public interface ColdTemperatureTickEvent {
        boolean tick(ServerLevel level, LivingEntity entity);

        static void register(ColdTemperatureTickEvent listener) {
            COLD_TEMPERATURE_TICK_LISTENERS.add(listener);
        }

        /**
         * @return false to prevent the entity from freezing
         */
        @ApiStatus.Internal
        static boolean post(ServerLevel level, LivingEntity entity) {
            for (var listener : COLD_TEMPERATURE_TICK_LISTENERS) {
                if (!listener.tick(level, entity)) {
                    return false;
                }
            }
            return true;
        }
    }

    @FunctionalInterface
    public interface GravityTickEvent {
        boolean tick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos);

        static void register(GravityTickEvent listener) {
            GRAVITY_TICK_LISTENERS.add(listener);
        }

        /**
         * @return false to prevent ticking of gravity
         */
        @ApiStatus.Internal
        static boolean post(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
            for (var listener : GRAVITY_TICK_LISTENERS) {
                if (!listener.tick(level, entity, travelVector, movementAffectingPos)) {
                    return false;
                }
            }
            return true;
        }
    }

    @FunctionalInterface
    public interface ZeroGravityTickEvent {
        boolean tick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos);

        static void register(ZeroGravityTickEvent listener) {
            ZERO_GRAVITY_TICK_LISTENERS.add(listener);
        }

        /**
         * @return false to prevent ticking of zero gravity
         */
        @ApiStatus.Internal
        static boolean post(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
            for (var listener : ZERO_GRAVITY_TICK_LISTENERS) {
                if (!listener.tick(level, entity, travelVector, movementAffectingPos)) {
                    return false;
                }
            }
            return true;
        }
    }

    @FunctionalInterface
    public interface AcidRainTickEvent {
        boolean tick(ServerLevel level, LivingEntity entity);

        static void register(AcidRainTickEvent listener) {
            ACID_RAIN_TICK_LISTENERS.add(listener);
        }

        /**
         * @return false to prevent ticking of acid rain
         */
        @ApiStatus.Internal
        static boolean post(ServerLevel level, LivingEntity entity) {
            for (var listener : ACID_RAIN_TICK_LISTENERS) {
                if (!listener.tick(level, entity)) {
                    return false;
                }
            }
            return true;
        }
    }

    @FunctionalInterface
    public interface EnvironmentTickEvent {
        boolean tick(ServerLevel level, BlockPos pos, BlockState state, short temperature);

        static void register(EnvironmentTickEvent listener) {
            ENVIRONMENT_TICK_LISTENERS.add(listener);
        }

        /**
         * Random planet tick for breaking plants, torches, freezing water, etc. on planets.
         *
         * @return false to prevent ticking of environment
         */
        @ApiStatus.Internal
        static boolean post(ServerLevel level, BlockPos pos, BlockState state, short temperature) {
            for (var listener : ENVIRONMENT_TICK_LISTENERS) {
                if (!listener.tick(level, pos, state, temperature)) {
                    return false;
                }
            }
            return true;
        }
    }
}
