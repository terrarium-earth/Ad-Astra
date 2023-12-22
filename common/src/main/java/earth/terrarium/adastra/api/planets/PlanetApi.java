package earth.terrarium.adastra.api.planets;

import earth.terrarium.adastra.api.ApiHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public interface PlanetApi {

    PlanetApi API = ApiHelper.load(PlanetApi.class);

    /**
     * Returns true if the given level is a planet.
     *
     * @param level The level to check.
     * @return True if the level is a planet.
     */
    boolean isPlanet(Level level);

    /**
     * Returns true if the given level is a planet.
     *
     * @param level The level to check.
     * @return True if the level is a planet.
     */
    boolean isPlanet(ResourceKey<Level> level);

    /**
     * Returns true if the given level is orbit.
     *
     * @param level The level to check.
     * @return True if the level is orbit.
     */
    boolean isSpace(Level level);

    /**
     * Returns true if the given level is orbit.
     *
     * @param level The level to check.
     * @return True if the level is orbit.
     */
    boolean isSpace(ResourceKey<Level> level);

    /**
     * Returns true if the given level is a planet or orbit.
     *
     * @param level The level to check.
     * @return True if the level is a planet or orbit.
     */
    boolean isExtraterrestrial(Level level);

    /**
     * Returns true if the given level is a planet or orbit.
     *
     * @param level The level to check.
     * @return True if the level is a planet or orbit.
     */
    boolean isExtraterrestrial(ResourceKey<Level> level);

    /**
     * Returns the solar power energy result of the given level.
     *
     * @param level The level to check.
     * @return The solar power energy result of the given level.
     */
    long getSolarPower(Level level);

    /**
     * Returns the solar power energy result of the given level.
     *
     * @param level The level to check.
     * @return The solar power energy result of the given level.
     */
    long getSolarPower(ResourceKey<Level> level);
}
