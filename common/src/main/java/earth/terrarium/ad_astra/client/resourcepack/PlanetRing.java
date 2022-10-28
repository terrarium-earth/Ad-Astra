package earth.terrarium.ad_astra.client.resourcepack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record PlanetRing(Identifier galaxy, Identifier solarSystem, Identifier texture, int speed, int scale,
                         double radius) {

    public static final Codec<PlanetRing> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("galaxy").forGetter(PlanetRing::galaxy),
            Identifier.CODEC.fieldOf("solar_system").forGetter(PlanetRing::solarSystem),
            Identifier.CODEC.fieldOf("texture").forGetter(PlanetRing::texture),
            Codec.INT.fieldOf("speed").forGetter(PlanetRing::speed),
            Codec.INT.fieldOf("scale").forGetter(PlanetRing::scale),
            Codec.DOUBLE.fieldOf("radius").forGetter(PlanetRing::radius)
    ).apply(instance, PlanetRing::new));
}