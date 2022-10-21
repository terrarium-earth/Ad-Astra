package earth.terrarium.ad_astra.client.dimension.forge;

import earth.terrarium.ad_astra.client.dimension.rendering.DimensionEffects;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

public class ClientModSkiesImpl {
    public static final Map<Identifier, SkyProperties> SKY_PROPERTIES = new HashMap<>();

    public static void registerDimensionEffects(RegistryKey<World> id, DimensionEffects effects) {
        SKY_PROPERTIES.put(id.getValue(), effects);
    }
}
