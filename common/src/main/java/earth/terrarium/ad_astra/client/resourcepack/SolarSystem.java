package earth.terrarium.ad_astra.client.resourcepack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.ad_astra.data.ButtonColor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public record SolarSystem(ResourceLocation galaxy, ResourceLocation solarSystem, ResourceLocation sun, int sunScale,
                          ButtonColor buttonColor, Color ringColour) {

    public static final Codec<SolarSystem> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("galaxy").forGetter(SolarSystem::galaxy),
            ResourceLocation.CODEC.fieldOf("solar_system").forGetter(SolarSystem::solarSystem),
            ResourceLocation.CODEC.fieldOf("sun").forGetter(SolarSystem::sun),
            Codec.INT.fieldOf("sun_scale").forGetter(SolarSystem::sunScale),
            ButtonColor.CODEC.fieldOf("button_color").forGetter(SolarSystem::buttonColor),
            Color.CODEC.fieldOf("ring_color").forGetter(SolarSystem::ringColour)
    ).apply(instance, SolarSystem::new));

}