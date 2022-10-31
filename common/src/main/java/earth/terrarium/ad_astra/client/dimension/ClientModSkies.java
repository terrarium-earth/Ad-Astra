package earth.terrarium.ad_astra.client.dimension;

import dev.architectury.injectables.annotations.ExpectPlatform;
import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.dimension.rendering.DimensionEffects;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.NotImplementedException;

@Environment(EnvType.CLIENT)
public class ClientModSkies {

    public static void register() {
        for (PlanetSkyRenderer skyRenderer : AdAstraClient.skyRenderers) {
            registerDimensionEffects(skyRenderer.dimension(), new DimensionEffects(skyRenderer));
        }
    }

    @ExpectPlatform
    public static void registerDimensionEffects(ResourceKey<Level> id, DimensionEffects effects) {
        throw new NotImplementedException();
    }
}