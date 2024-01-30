package earth.terrarium.adastra.datagen.provider.server;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.common.constants.PlanetConstants;
import earth.terrarium.adastra.datagen.provider.base.ModCodecProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;


public class ModPlanetProvider extends ModCodecProvider<Planet> {
    public static final ResourceKey<Registry<Planet>> PLANET_REGISTRY = ResourceKey.createRegistryKey(new ResourceLocation(AdAstra.MOD_ID, "planets"));

    public ModPlanetProvider(PackOutput packOutput) {
        super(packOutput, Planet.CODEC, PLANET_REGISTRY);
    }

    @Override
    protected void build(BiConsumer<ResourceLocation, Planet> consumer) {
        orbit(consumer, Planet.EARTH_ORBIT, PlanetConstants.SPACE_SOLAR_POWER, PlanetConstants.SOLAR_SYSTEM, 1);
        orbit(consumer, Planet.MOON_ORBIT, PlanetConstants.MOON_ORBIT_SOLAR_POWER, PlanetConstants.SOLAR_SYSTEM, 1);
        orbit(consumer, Planet.MARS_ORBIT, PlanetConstants.MARS_ORBIT_SOLAR_POWER, PlanetConstants.SOLAR_SYSTEM, 2);
        orbit(consumer, Planet.VENUS_ORBIT, PlanetConstants.VENUS_ORBIT_SOLAR_POWER, PlanetConstants.SOLAR_SYSTEM, 3);
        orbit(consumer, Planet.MERCURY_ORBIT, PlanetConstants.MERCURY_ORBIT_SOLAR_POWER, PlanetConstants.SOLAR_SYSTEM, 3);
        orbit(consumer, Planet.GLACIO_ORBIT, PlanetConstants.GLACIO_ORBIT_SOLAR_POWER, PlanetConstants.PROXIMA_CENTAURI, 4);

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "earth"),
            new Planet(
                Level.OVERWORLD,
                true,
                PlanetConstants.EARTH_TEMPERATURE,
                PlanetConstants.EARTH_GRAVITY,
                PlanetConstants.EARTH_SOLAR_POWER,
                PlanetConstants.SOLAR_SYSTEM,
                Optional.of(Planet.EARTH_ORBIT),
                1,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "moon"),
            new Planet(Planet.MOON,
                false,
                PlanetConstants.MOON_TEMPERATURE,
                PlanetConstants.MOON_GRAVITY,
                PlanetConstants.MOON_SOLAR_POWER,
                PlanetConstants.SOLAR_SYSTEM,
                Optional.of(Planet.MOON_ORBIT),
                1,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "mars"),
            new Planet(Planet.MARS,
                false,
                PlanetConstants.MARS_TEMPERATURE,
                PlanetConstants.MARS_GRAVITY,
                PlanetConstants.MARS_SOLAR_POWER,
                PlanetConstants.SOLAR_SYSTEM,
                Optional.of(Planet.MARS_ORBIT),
                2,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "venus"),
            new Planet(Planet.VENUS,
                false,
                PlanetConstants.VENUS_TEMPERATURE,
                PlanetConstants.VENUS_GRAVITY,
                PlanetConstants.VENUS_SOLAR_POWER,
                PlanetConstants.SOLAR_SYSTEM,
                Optional.of(Planet.VENUS_ORBIT),
                3,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "mercury"),
            new Planet(Planet.MERCURY,
                false,
                PlanetConstants.MERCURY_TEMPERATURE,
                PlanetConstants.MERCURY_GRAVITY,
                PlanetConstants.MERCURY_SOLAR_POWER,
                PlanetConstants.SOLAR_SYSTEM,
                Optional.of(Planet.MERCURY_ORBIT),
                3,
                List.of()
            )
        );

        consumer.accept(
            new ResourceLocation(AdAstra.MOD_ID, "glacio"),
            new Planet(Planet.GLACIO,
                true,
                PlanetConstants.GLACIO_TEMPERATURE,
                PlanetConstants.GLACIO_GRAVITY,
                PlanetConstants.GLACIO_SOLAR_POWER,
                PlanetConstants.PROXIMA_CENTAURI,
                Optional.of(Planet.GLACIO_ORBIT),
                4,
                List.of()
            )
        );
    }

    private static void orbit(BiConsumer<ResourceLocation, Planet> consumer, ResourceKey<Level> planet, int solarPower, ResourceLocation galaxy, int tier) {
        consumer.accept(
            planet.location(),
            new Planet(planet,
                false,
                PlanetConstants.SPACE_TEMPERATURE,
                PlanetConstants.SPACE_GRAVITY,
                solarPower,
                galaxy,
                Optional.empty(), tier,
                List.of()
            )
        );
    }

    @Override
    public @NotNull String getName() {
        return "Planets";
    }
}
