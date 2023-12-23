package earth.terrarium.adastra.common.planets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Optional;

public record Planet(ResourceKey<Level> dimension,
                     boolean oxygen, short temperature,
                     float gravity, int solarPower,
                     ResourceLocation solarSystem,
                     Optional<ResourceKey<Level>> orbit, int tier) {

    public static final ResourceKey<Level> EARTH_ORBIT = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "earth_orbit"));
    public static final ResourceKey<Level> MOON_ORBIT = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon_orbit"));
    public static final ResourceKey<Level> MARS_ORBIT = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars_orbit"));
    public static final ResourceKey<Level> VENUS_ORBIT = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus_orbit"));
    public static final ResourceKey<Level> MERCURY_ORBIT = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury_orbit"));
    public static final ResourceKey<Level> GLACIO_ORBIT = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio_orbit"));

    public static final ResourceKey<Level> MOON = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon"));
    public static final ResourceKey<Level> MARS = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars"));
    public static final ResourceKey<Level> VENUS = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus"));
    public static final ResourceKey<Level> MERCURY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury"));
    public static final ResourceKey<Level> GLACIO = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio"));

    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(Planet::dimension),
        Codec.BOOL.fieldOf("oxygen").forGetter(Planet::oxygen),
        Codec.SHORT.fieldOf("temperature").forGetter(Planet::temperature),
        Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity),
        Codec.INT.fieldOf("solar_power").forGetter(Planet::solarPower),
        ResourceLocation.CODEC.fieldOf("solar_system").forGetter(Planet::solarSystem),
        ResourceKey.codec(Registries.DIMENSION).optionalFieldOf("orbit").forGetter(Planet::orbit),
        Codec.INT.fieldOf("tier").forGetter(Planet::tier)
    ).apply(instance, Planet::new));

    public ResourceKey<Level> orbitIfPresent() {
        return orbit.orElse(dimension);
    }

    public boolean isSpace() {
        return orbit.isEmpty();
    }

    public ResourceLocation dimensionLocation() {
        return dimension.location();
    }
}
