package earth.terrarium.adastra.common.events.forge;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.Cancelable;

public class EntityTickEvent extends EntityEvent {

    protected final ServerLevel level;
    protected final LivingEntity entity;

    public EntityTickEvent(ServerLevel level, LivingEntity entity) {
        super(entity);
        this.level = level;
        this.entity = entity;
    }

    public ServerLevel level() {
        return this.level;
    }

    public LivingEntity entity() {
        return this.entity;
    }

    @Cancelable
    public static class OxygenTick extends EntityTickEvent {
        public OxygenTick(ServerLevel level, LivingEntity entity) {
            super(level, entity);
        }
    }

    @Cancelable
    public static class TemperatureTick extends EntityTickEvent {
        public TemperatureTick(ServerLevel level, LivingEntity entity) {
            super(level, entity);
        }
    }

    @Cancelable
    public static class AcidRainTick extends EntityTickEvent {
        public AcidRainTick(ServerLevel level, LivingEntity entity) {
            super(level, entity);
        }
    }
}
