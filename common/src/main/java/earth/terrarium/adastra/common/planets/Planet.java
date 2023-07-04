package earth.terrarium.adastra.common.planets;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record Planet(ResourceKey<Level> dimension, boolean oxygen, short temperature, float gravity, int solarPower) {
    public static final ResourceKey<Level> SPACE = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "space"));

    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceKey.codec(Registries.DIMENSION).fieldOf("dimension").forGetter(Planet::dimension),
        Codec.BOOL.fieldOf("oxygen").forGetter(Planet::oxygen),
        Codec.SHORT.fieldOf("temperature").forGetter(Planet::temperature),
        Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity),
        Codec.INT.fieldOf("solar_power").forGetter(Planet::solarPower)
    ).apply(instance, Planet::new));
}
