package earth.terrarium.adastra.api.systems;

import earth.terrarium.adastra.api.ApiHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Set;

public interface OxygenApi {

    OxygenApi API = ApiHelper.load(OxygenApi.class);

    /**
     * Returns whether the given level has oxygen.
     *
     * @param level The level to check.
     * @return Whether the level has oxygen.
     */
    boolean hasOxygen(Level level);

    /**
     * Returns whether the given level has oxygen.
     *
     * @param level The level to check.
     * @return Whether the level has oxygen.
     */
    boolean hasOxygen(ResourceKey<Level> level);

    /**
     * Returns whether the given position has oxygen.
     *
     * @param level The level to check.
     * @param pos   The position to check.
     * @return Whether the position has oxygen.
     */
    boolean hasOxygen(Level level, BlockPos pos);

    /**
     * Returns whether the given entity has oxygen.
     *
     * @param entity The entity to check.
     * @return Whether the entity has oxygen.
     */
    boolean hasOxygen(Entity entity);

    /**
     * Sets the position's oxygen presence to the given value.
     *
     * @param level  The level to check.
     * @param pos    The position to add oxygen to.
     * @param oxygen The oxygen to set.
     */
    void setOxygen(Level level, BlockPos pos, boolean oxygen);

    /**
     * Sets the oxygen presence of the given positions to the given value.
     *
     * @param level  The level to check.
     * @param positions    The position to add oxygen to.
     * @param oxygen The oxygen to set.
     */
    void setOxygen(Level level, Set<BlockPos> positions, boolean oxygen);

    /**
     * Sets the oxygen of the given position to the dimension's default oxygen.
     *
     * @param level The level to check.
     * @param pos   The position to remove oxygen from.
     */
    void removeOxygen(Level level, BlockPos pos);

    /**
     * Sets the oxygen of the given position to the dimension's default oxygen.
     *
     * @param level     The level to check.
     * @param positions The positions to remove oxygen from.
     */
    void removeOxygen(Level level, Set<BlockPos> positions);

    void entityTick(ServerLevel level, LivingEntity entity);
}
