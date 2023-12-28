package earth.terrarium.adastra.common.events.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class GravityTickEvent extends Event {
    protected final Level level;
    protected final LivingEntity entity;
    protected final Vec3 travelVector;
    protected final BlockPos movementAffectingPos;

    public GravityTickEvent(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        this.level = level;
        this.entity = entity;
        this.travelVector = travelVector;
        this.movementAffectingPos = movementAffectingPos;
    }

    public Level level() {
        return this.level;
    }

    public LivingEntity entity() {
        return this.entity;
    }

    public Vec3 travelVector() {
        return this.travelVector;
    }

    public BlockPos movementAffectingPos() {
        return this.movementAffectingPos;
    }

    @Cancelable
    public static class ZeroGravityTick extends GravityTickEvent {
        public ZeroGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
            super(level, entity, travelVector, movementAffectingPos);
        }
    }
}
