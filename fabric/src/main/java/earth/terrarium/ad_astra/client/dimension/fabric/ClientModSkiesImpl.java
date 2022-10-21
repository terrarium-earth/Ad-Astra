package earth.terrarium.ad_astra.client.dimension.fabric;

import earth.terrarium.ad_astra.client.dimension.rendering.DimensionEffects;
import earth.terrarium.ad_astra.mixin.client.WorldRendererAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ClientModSkiesImpl {
    public static void registerDimensionEffects(RegistryKey<World> id, DimensionEffects effects) {
        DimensionRenderingRegistry.registerDimensionEffects(id.getValue(), effects);
        if (effects.shouldRenderClouds()) {

            DimensionRenderingRegistry.registerCloudRenderer(id, context -> {
                Vec3d cameraPos = context.camera().getPos();
                effects.renderClouds(context.world(), getTicks(), context.tickDelta(), context.matrixStack(), cameraPos.x, cameraPos.y, cameraPos.z, context.projectionMatrix());
            });
        }

        if (effects.shouldRenderSky()) {
            DimensionRenderingRegistry.registerSkyRenderer(id, context -> effects.renderSky(context.world(), getTicks(), context.tickDelta(), context.matrixStack(), context.camera(), context.projectionMatrix(), false, () -> {
            }));
        }

        if (effects.shouldRenderSnowAndRain()) {
            DimensionRenderingRegistry.registerWeatherRenderer(id, context -> {
                Vec3d cameraPos = context.camera().getPos();
                effects.renderSnowAndRain(context.world(), getTicks(), context.tickDelta(), context.lightmapTextureManager(), cameraPos.x, cameraPos.y, cameraPos.z);
            });
        }
    }

    private static int getTicks() {
        return ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getTicks();
    }
}
