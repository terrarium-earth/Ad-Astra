package earth.terrarium.ad_astra.client.resourcepack;

import earth.terrarium.ad_astra.data.ButtonColour;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.color.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record SolarSystem(Identifier galaxy, Identifier solarSystem, Identifier sun, int sunScale, ButtonColour buttonColour, Color ringColour) {

    public static final Codec<SolarSystem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
        Identifier.CODEC.fieldOf("galaxy").forGetter(SolarSystem::galaxy),
        Identifier.CODEC.fieldOf("solar_system").forGetter(SolarSystem::solarSystem),
        Identifier.CODEC.fieldOf("sun").forGetter(SolarSystem::sun),
        Codec.INT.fieldOf("sun_scale").forGetter(SolarSystem::sunScale),
        ButtonColour.CODEC.fieldOf("button_color").forGetter(SolarSystem::buttonColour),
        Color.CODEC.fieldOf("ring_color").forGetter(SolarSystem::ringColour)
    ).apply(instance, SolarSystem::new));

}