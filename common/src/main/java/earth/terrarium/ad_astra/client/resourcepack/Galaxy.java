package earth.terrarium.ad_astra.client.resourcepack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.ad_astra.data.ButtonColour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public record Galaxy(ResourceLocation galaxy, ResourceLocation texture, ButtonColour buttonColour, int scale) {
    public static final Codec<Galaxy> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("galaxy").forGetter(Galaxy::galaxy),
            ResourceLocation.CODEC.fieldOf("texture").forGetter(Galaxy::texture),
            ButtonColour.CODEC.fieldOf("button_color").forGetter(Galaxy::buttonColour),
            Codec.INT.fieldOf("scale").orElse(1).forGetter(Galaxy::scale)
    ).apply(instance, Galaxy::new));
}