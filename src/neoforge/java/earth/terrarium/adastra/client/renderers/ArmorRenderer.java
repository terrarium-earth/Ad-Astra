package earth.terrarium.adastra.client.renderers;

import earth.terrarium.adastra.client.ClientPlatformUtils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public record ArmorRenderer(ResourceLocation texture, ModelLayerLocation layer,
                            ClientPlatformUtils.ArmorFactory factory) {
}
