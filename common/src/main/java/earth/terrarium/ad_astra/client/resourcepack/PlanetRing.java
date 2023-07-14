package earth.terrarium.ad_astra.client.resourcepack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public record PlanetRing(ResourceLocation galaxy, ResourceLocation solarSystem, ResourceLocation texture, int speed,
                         int scale,
                         double radius) {

    public static final Codec<PlanetRing> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("galaxy").forGetter(PlanetRing::galaxy),
        ResourceLocation.CODEC.fieldOf("solar_system").forGetter(PlanetRing::solarSystem),
        ResourceLocation.CODEC.fieldOf("texture").forGetter(PlanetRing::texture),
        Codec.INT.fieldOf("speed").forGetter(PlanetRing::speed),
        Codec.INT.fieldOf("scale").forGetter(PlanetRing::scale),
        Codec.DOUBLE.fieldOf("radius").forGetter(PlanetRing::radius)
    ).apply(instance, PlanetRing::new));
}