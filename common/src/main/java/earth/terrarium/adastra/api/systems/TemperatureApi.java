package earth.terrarium.adastra.api.systems;

import earth.terrarium.adastra.api.ApiHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Set;

public interface TemperatureApi {

    TemperatureApi API = ApiHelper.load(TemperatureApi.class);

    /**
     * Returns the temperature of the given level.
     *
     * @param level The level to check.
     * @return The temperature of the level.
     */
    short getTemperature(Level level);

    /**
     * Returns the temperature of the given level.
     *
     * @param level The level to check.
     * @return The temperature of the level.
     */
    short getTemperature(ResourceKey<Level> level);

    /**
     * Returns the temperature of the given position.
     *
     * @param level The level to check.
     * @param pos   The position to check.
     * @return The temperature of the position.
     */
    short getTemperature(Level level, BlockPos pos);

    /**
     * Returns the temperature of the given entity.
     *
     * @param entity The entity to check.
     * @return The temperature of the entity.
     */
    short getTemperature(Entity entity);

    /**
     * Sets the position's temperature to the given value.
     *
     * @param level       The level to check.
     * @param pos         The position to check.
     * @param temperature The temperature to set.
     */
    void setTemperature(Level level, BlockPos pos, short temperature);

    /**
     * Sets the temperature of the given positions to the given value.
     *
     * @param level       The level to check.
     * @param positions   The position to check.
     * @param temperature The temperature to set.
     */
    void setTemperature(Level level, Set<BlockPos> positions, short temperature);

    /**
     * Sets the temperature of the given position to the dimension's default temperature.
     *
     * @param level The level to check.
     * @param pos   The position to check.
     */
    void removeTemperature(Level level, BlockPos pos);

    /**
     * Sets the temperature of the given positions to the dimension's default temperature.
     *
     * @param level     The level to check.
     * @param positions The position to check.
     */
    void removeTemperature(Level level, Set<BlockPos> positions);

    /**
     * Returns whether the temperature of the given position is within a liveable range.
     *
     * @param level The level to check.
     * @param pos   The position to check.
     * @return If the temperature is liveable or not.
     */
    boolean isLiveable(Level level, BlockPos pos);

    /**
     * Returns whether the temperature of the given position is too hot to be liveable.
     *
     * @param level The level to check.
     * @param pos   The position to check.
     * @return If the temperature is too hot or not.
     */
    boolean isHot(Level level, BlockPos pos);

    /**
     * Returns whether the temperature of the given position is too cold to be liveable.
     *
     * @param level The level to check.
     * @param pos   The position to check.
     * @return If the temperature is too cold or not.
     */
    boolean isCold(Level level, BlockPos pos);

    void entityTick(ServerLevel level, LivingEntity entity);
}
