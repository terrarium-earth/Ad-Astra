package earth.terrarium.ad_astra.client.resourcepack;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.ad_astra.data.ButtonColour;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public record Galaxy(Identifier galaxy, Identifier texture, ButtonColour buttonColour, int scale) {
    public static final Codec<Galaxy> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("galaxy").forGetter(Galaxy::galaxy),
            Identifier.CODEC.fieldOf("texture").forGetter(Galaxy::texture),
            ButtonColour.CODEC.fieldOf("button_color").forGetter(Galaxy::buttonColour),
            Codec.INT.fieldOf("scale").orElse(1).forGetter(Galaxy::scale)
    ).apply(instance, Galaxy::new));
}