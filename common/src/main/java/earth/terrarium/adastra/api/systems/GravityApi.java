package earth.terrarium.adastra.api.systems;

import earth.terrarium.adastra.api.ApiHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface GravityApi {

    GravityApi API = ApiHelper.load(GravityApi.class);

    /**
     * Returns the gravity of the given level.
     *
     * @param level The level to check.
     * @return The gravity of the level.
     */
    float getGravity(Level level);

    /**
     * Returns the gravity of the given level.
     *
     * @param level The level to check.
     * @return The gravity of the level.
     */
    float getGravity(ResourceKey<Level> level);

    /**
     * Returns the gravity of the given position.
     *
     * @param level The level to check.
     * @param pos   The position to check.
     * @return The gravity of the position.
     */
    float getGravity(Level level, BlockPos pos);


    /**
     * Returns the gravity of the given entity.
     *
     * @param entity The entity to check.
     * @return The gravity of the entity.
     */
    float getGravity(Entity entity);


    /**
     * Sets the position's gravity to the given value.
     *
     * @param level   The level to check.
     * @param pos     The position to check.
     * @param gravity The gravity to set.
     */
    void setGravity(Level level, BlockPos pos, float gravity);

    void entityTick(Level level, LivingEntity entity, Vec3 travelVector, BlockPos movementAffectingPos);
}
