package earth.terrarium.ad_astra.client.dimension.forge;

import earth.terrarium.ad_astra.client.dimension.rendering.DimensionEffects;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ClientModSkiesImpl {
    public static final Map<ResourceLocation, DimensionSpecialEffects> SKY_PROPERTIES = new HashMap<>();

    public static void registerDimensionEffects(ResourceKey<Level> id, DimensionEffects effects) {
        SKY_PROPERTIES.put(id.location(), effects);
    }
}
