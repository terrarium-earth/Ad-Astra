package earth.terrarium.ad_astra.client.dimension.forge;

import earth.terrarium.ad_astra.client.dimension.rendering.DimensionEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class ClientModSkiesImpl {
    public static final Map<ResourceLocation, DimensionSpecialEffects> DIMENSION_SPECIAL_EFFECTS = new HashMap<>();

    public static void registerDimensionEffects(ResourceKey<Level> id, DimensionEffects effects) {
        DIMENSION_SPECIAL_EFFECTS.put(id.location(), effects);
    }
}
