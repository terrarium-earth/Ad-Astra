package earth.terrarium.adastra.common.events.forge;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

public class ModEventsImpl {
    public static boolean entityOxygenTick(ServerLevel level, LivingEntity entity) {
        var event = new EntityTickEvent.OxygenTick(level, entity);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static boolean entityTemperatureTick(ServerLevel level, LivingEntity entity) {
        var event = new EntityTickEvent.TemperatureTick(level, entity);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static boolean entityGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        var event = new GravityTickEvent(level, entity, travelVector, movementAffectingPos);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static boolean entityZeroGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        var event = new GravityTickEvent.ZeroGravityTick(level, entity, travelVector, movementAffectingPos);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }

    public static boolean entityAcidRainTick(ServerLevel level, LivingEntity entity) {
        var event = new EntityTickEvent.AcidRainTick(level, entity);
        MinecraftForge.EVENT_BUS.post(event);
        return !event.isCanceled();
    }
}
