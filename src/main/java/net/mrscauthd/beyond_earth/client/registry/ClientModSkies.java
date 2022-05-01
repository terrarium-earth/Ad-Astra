package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry.CloudRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.mrscauthd.beyond_earth.client.BeyondEarthClient;
import net.mrscauthd.beyond_earth.client.renderer.sky.ModSky;
import net.mrscauthd.beyond_earth.client.renderer.sky.cloud_renderer.ModCloudRenderer;
import net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects.ModDimensionEffects;
import net.mrscauthd.beyond_earth.client.renderer.sky.weather_renderer.ModWeatherRenderer;
import net.mrscauthd.beyond_earth.client.resource_pack.SkyRenderer;
import net.mrscauthd.beyond_earth.util.ColourHolder;

@Environment(EnvType.CLIENT)
public class ClientModSkies {

        public static void register() {

                for (SkyRenderer skyRenderer : BeyondEarthClient.skyRenderers) {

                        ModSky sky = new ModSky();
                        sky.setStars(skyRenderer.starsRenderer());
                        sky.setSunsetColour(skyRenderer.sunsetColour());
                        sky.setSkyObjects(skyRenderer.skyObjects());
                        sky.setHorizonAngle(skyRenderer.horizonAngle());
                        DimensionRenderingRegistry.registerSkyRenderer(skyRenderer.dimension(), sky);

                        // Dimension Effects.
                        switch (skyRenderer.effects().type()) {
                        case SIMPLE -> DimensionRenderingRegistry.registerDimensionEffects(skyRenderer.dimension().getValue(), new ModDimensionEffects());
                        case FOGGY -> DimensionRenderingRegistry.registerDimensionEffects(skyRenderer.dimension().getValue(), new ModDimensionEffects() {
                                @Override
                                public boolean useThickFog(int camX, int camY) {
                                        return true;
                                }
                        });
                        case COLORED_HORIZON -> DimensionRenderingRegistry.registerDimensionEffects(skyRenderer.dimension().getValue(), new ModDimensionEffects() {
                                @Override
                                public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
                                        return ColourHolder.toVector(skyRenderer.effects().colour());
                                }
                        });
                        }

                        // Cloud renderer.
                        switch (skyRenderer.cloudEffects()) {
                        case NONE -> DimensionRenderingRegistry.registerCloudRenderer(skyRenderer.dimension(), new CloudRenderer() {
                                @Override
                                public void render(WorldRenderContext context) {
                                }
                        });
                        case VANILLA -> {
                        }
                        case VENUS -> DimensionRenderingRegistry.registerCloudRenderer(skyRenderer.dimension(), new ModCloudRenderer().withVenus());
                        }

                        // Weather renderer
                        switch (skyRenderer.weatherEffects()) {
                        case NONE -> {
                        }
                        case VENUS -> DimensionRenderingRegistry.registerWeatherRenderer(skyRenderer.dimension(), new ModWeatherRenderer().withVenus());
                        }
                }
        }
}