package earth.terrarium.ad_astra.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record Planet(String translation, ResourceLocation galaxy, ResourceLocation solarSystem,
                     ResourceKey<Level> level,
                     ResourceKey<Level> orbitWorld, ResourceKey<Level> parentWorld, int rocketTier, float gravity,
                     boolean hasAtmosphere, int daysInYear, float temperature, long solarPower, long orbitSolarPower,
                     boolean hasOxygen, ButtonColor buttonColor) {

    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Codec.STRING.fieldOf("translation").forGetter(Planet::translation),
        ResourceLocation.CODEC.fieldOf("galaxy").forGetter(Planet::galaxy),
        ResourceLocation.CODEC.fieldOf("solar_system").forGetter(Planet::solarSystem),
        ResourceKey.codec(Registry.DIMENSION_REGISTRY).fieldOf("world").forGetter(Planet::level),
        ResourceKey.codec(Registry.DIMENSION_REGISTRY).fieldOf("orbit_world").forGetter(Planet::orbitWorld),
        ResourceKey.codec(Registry.DIMENSION_REGISTRY).optionalFieldOf("parent_world").forGetter(Planet::getParentlevel),
        Codec.INT.fieldOf("rocket_tier").forGetter(Planet::rocketTier),
        Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity),
        Codec.BOOL.fieldOf("has_atmosphere").forGetter(Planet::hasAtmosphere),
        Codec.INT.fieldOf("days_in_year").forGetter(Planet::daysInYear),
        Codec.FLOAT.fieldOf("temperature").forGetter(Planet::temperature),
        Codec.LONG.fieldOf("solar_power").forGetter(Planet::solarPower),
        Codec.LONG.fieldOf("orbit_solar_power").forGetter(Planet::orbitSolarPower),
        Codec.BOOL.fieldOf("has_oxygen").forGetter(Planet::hasOxygen),
        ButtonColor.CODEC.fieldOf("button_color").forGetter(Planet::buttonColor)
    ).apply(instance, Planet::new));

    public Planet(String translation, ResourceLocation galaxy, ResourceLocation solarSystem, ResourceKey<Level> level, ResourceKey<Level> orbitWorld, Optional<ResourceKey<Level>> parentWorld, int rocketTier, float gravity, boolean hasAtmosphere, int daysInYear, float temperature, long solarPower, long orbitSolarPower, boolean hasOxygen, ButtonColor buttonColor) {
        this(translation, galaxy, solarSystem, level, orbitWorld, parentWorld.orElse(null), rocketTier, gravity, hasAtmosphere, daysInYear, temperature, solarPower, orbitSolarPower, hasOxygen, buttonColor);
    }

    private Optional<ResourceKey<Level>> getParentlevel() {
        return Optional.ofNullable(parentWorld);
    }
}
