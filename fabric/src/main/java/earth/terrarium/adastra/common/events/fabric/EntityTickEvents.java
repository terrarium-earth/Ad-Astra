package earth.terrarium.adastra.common.events.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityTickEvents {

    public static final Event<Ticked> TEMPERATURE_TICK = EventFactory.createArrayBacked(Ticked.class, listeners -> (level, entity) -> {
        for (Ticked listener : listeners) {
            if (!listener.tick(level, entity)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<Ticked> OXYGEN_TICK = EventFactory.createArrayBacked(Ticked.class, listeners -> (level, entity) -> {
        for (Ticked listener : listeners) {
            if (!listener.tick(level, entity)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<GravityTicked> GRAVITY_TICK = EventFactory.createArrayBacked(GravityTicked.class, listeners -> (level, entity, travelVector, movementAffectingPos) -> {
        for (GravityTicked listener : listeners) {
            if (!listener.tick(level, entity, travelVector, movementAffectingPos)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<GravityTicked> ZERO_GRAVITY_TICK = EventFactory.createArrayBacked(GravityTicked.class, listeners -> (level, entity, travelVector, movementAffectingPos) -> {
        for (GravityTicked listener : listeners) {
            if (!listener.tick(level, entity, travelVector, movementAffectingPos)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<Ticked> ACID_RAIN_TICK = EventFactory.createArrayBacked(Ticked.class, listeners -> (level, entity) -> {
        for (Ticked listener : listeners) {
            if (!listener.tick(level, entity)) {
                return false;
            }
        }
        return true;
    });

    @FunctionalInterface
    public interface Ticked {
        boolean tick(ServerLevel level, LivingEntity entity);
    }

    @FunctionalInterface
    public interface GravityTicked {
        boolean tick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos);
    }
}
