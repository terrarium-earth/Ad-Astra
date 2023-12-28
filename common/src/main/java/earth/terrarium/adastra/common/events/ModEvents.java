package earth.terrarium.adastra.common.events;

import com.teamresourceful.resourcefullib.common.exceptions.NotImplementedException;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class ModEvents {

    /**
     * @return false to prevent ticking of oxygen.
     */
    @ExpectPlatform
    public static boolean entityOxygenTick(ServerLevel level, LivingEntity entity) {
        throw new NotImplementedException();
    }

    /**
     * @return false to prevent ticking of temperature.
     */
    @ExpectPlatform
    public static boolean entityTemperatureTick(ServerLevel level, LivingEntity entity) {
        throw new NotImplementedException();
    }

    /**
     * @return false to prevent ticking of gravity.
     */
    @ExpectPlatform
    public static boolean entityGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        throw new NotImplementedException();
    }

    /**
     * @return false to prevent ticking of zero gravity.
     */
    @ExpectPlatform
    public static boolean entityZeroGravityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos) {
        throw new NotImplementedException();
    }

    /**
     * @return false to prevent ticking of gravity.
     */
    @ExpectPlatform
    public static boolean entityAcidRainTick(ServerLevel level, LivingEntity entity) {
        throw new NotImplementedException();
    }
}
