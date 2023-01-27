package earth.terrarium.ad_astra.common.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record Planet(ResourceKey<Level> planet, boolean oxygen, float gravity, int temperature) {
    public static final ResourceKey<Level> MOON = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "moon"));
    public static final ResourceKey<Level> MARS = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mars"));
    public static final ResourceKey<Level> VENUS = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "venus"));
    public static final ResourceKey<Level> MERCURY = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "mercury"));
    public static final ResourceKey<Level> GLACIO = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "glacio"));
    public static final ResourceKey<Level> ORBIT = ResourceKey.create(Registries.DIMENSION, new ResourceLocation(AdAstra.MOD_ID, "orbit"));

    public static final Codec<Planet> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceKey.codec(Registries.DIMENSION).fieldOf("planet").forGetter(Planet::planet),
            Codec.BOOL.fieldOf("oxygen").forGetter(Planet::oxygen),
            Codec.FLOAT.fieldOf("gravity").forGetter(Planet::gravity),
            Codec.INT.fieldOf("temperature").forGetter(Planet::temperature)
    ).apply(instance, Planet::new));
}
