package earth.terrarium.ad_astra.data;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public record Planet(String translation, Identifier galaxy, Identifier solarSystem, RegistryKey<World> world, RegistryKey<World> orbitWorld, RegistryKey<World> parentWorld, int rocketTier, float gravity, boolean hasAtmosphere, int daysInYear, float temperature, long solarPower, long orbitSolarPower, boolean hasOxygen, ButtonColour buttonColour) {
    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("translation").forGetter(Planet::translation),
        Identifier.CODEC.fieldOf("galaxy").forGetter(Planet::galaxy),
        Identifier.CODEC.fieldOf("solar_system").forGetter(Planet::solarSystem),
        RegistryKey.codec(Registry.WORLD_KEY).fieldOf("world").forGetter(Planet::world),
        RegistryKey.codec(Registry.WORLD_KEY).fieldOf("orbit_world").forGetter(Planet::orbitWorld),
        RegistryKey.codec(Registry.WORLD_KEY).optionalFieldOf("parent_world").forGetter(Planet::getParentWorld),
        Codec.INT.fieldOf("rocket_tier").forGetter(Planet::rocketTier),
        Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity),
        Codec.BOOL.fieldOf("has_atmosphere").forGetter(Planet::hasAtmosphere),
        Codec.INT.fieldOf("days_in_year").forGetter(Planet::daysInYear),
        Codec.FLOAT.fieldOf("temperature").forGetter(Planet::temperature),
        Codec.LONG.fieldOf("solar_power").forGetter(Planet::solarPower),
        Codec.LONG.fieldOf("orbit_solar_power").forGetter(Planet::orbitSolarPower),
        Codec.BOOL.fieldOf("has_oxygen").forGetter(Planet::hasOxygen),
        ButtonColour.CODEC.fieldOf("button_color").forGetter(Planet::buttonColour)
    ).apply(instance, Planet::new));

    public Planet(String translation, Identifier galaxy, Identifier solarSystem, RegistryKey<World> world, RegistryKey<World> orbitWorld, Optional<RegistryKey<World>> parentWorld, int rocketTier, float gravity, boolean hasAtmosphere, int daysInYear, float temperature, long solarPower, long orbitSolarPower, boolean hasOxygen, ButtonColour buttonColour) {
        this(translation, galaxy, solarSystem, world, orbitWorld, parentWorld.orElse(null), rocketTier, gravity, hasAtmosphere, daysInYear, temperature, solarPower, orbitSolarPower, hasOxygen, buttonColour);
    }

    private Optional<RegistryKey<World>> getParentWorld() {
        return Optional.ofNullable(parentWorld);
    }
}
