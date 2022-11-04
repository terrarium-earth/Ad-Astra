package earth.terrarium.ad_astra.client.dimension.fabric;

import earth.terrarium.ad_astra.client.dimension.rendering.DimensionEffects;
import earth.terrarium.ad_astra.mixin.client.LevelRendererAccessor;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ClientModSkiesImpl {
    public static void registerDimensionEffects(ResourceKey<Level> id, DimensionEffects effects) {
        DimensionRenderingRegistry.registerDimensionEffects(id.location(), effects);
        if (effects.shouldRenderClouds()) {

            DimensionRenderingRegistry.registerCloudRenderer(id, context -> {
                Vec3 cameraPos = context.camera().getPosition();
                effects.renderClouds(context.world(), getTicks(), context.tickDelta(), context.matrixStack(), cameraPos.x, cameraPos.y, cameraPos.z, context.projectionMatrix());
            });
        }

        if (effects.shouldRenderSky()) {
            DimensionRenderingRegistry.registerSkyRenderer(id, context -> effects.renderSky(context.world(), getTicks(), context.tickDelta(), context.matrixStack(), context.camera(), context.projectionMatrix(), false, () -> {
            }));
        }

        if (effects.shouldRenderSnowAndRain()) {
            DimensionRenderingRegistry.registerWeatherRenderer(id, context -> {
                Vec3 cameraPos = context.camera().getPosition();
                effects.renderSnowAndRain(context.world(), getTicks(), context.tickDelta(), context.lightmapTextureManager(), cameraPos.x, cameraPos.y, cameraPos.z);
            });
        }
    }

    private static int getTicks() {
        return ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getTicks();
    }
}
