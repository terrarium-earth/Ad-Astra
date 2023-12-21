package earth.terrarium.adastra.common.planets;

import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import net.minecraft.Optionull;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class PlanetApiImpl implements PlanetApi {
    @Override
    public boolean isPlanet(Level level) {
        return isPlanet(level.dimension());
    }

    @Override
    public boolean isPlanet(ResourceKey<Level> level) {
        return AdAstraData.isPlanet(level.location());
    }

    @Override
    public boolean isSpace(Level level) {
        return isSpace(level.dimension());
    }

    @Override
    public boolean isSpace(ResourceKey<Level> level) {
        return AdAstraData.isSpace(level.location());
    }

    @Override
    public boolean isExtraterrestrial(Level level) {
        return isExtraterrestrial(level.dimension());
    }

    @Override
    public boolean isExtraterrestrial(ResourceKey<Level> level) {
        return isSpace(level) || isPlanet(level);
    }

    @Override
    public long getSolarPower(Level level) {
        return getSolarPower(level.dimension());
    }

    @Override
    public long getSolarPower(ResourceKey<Level> level) {
        return Optionull.mapOrDefault(AdAstraData.getPlanet(level), Planet::solarPower, PlanetConstants.EARTH_SOLAR_POWER);
    }
}
