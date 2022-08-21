package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.client.AdAstraClient;
import com.github.alexnijjar.ad_astra.client.renderer.sky.ModSky;
import com.github.alexnijjar.ad_astra.client.renderer.sky.cloud_renderer.ModCloudRenderer;
import com.github.alexnijjar.ad_astra.client.renderer.sky.dimension_effects.ModDimensionEffects;
import com.github.alexnijjar.ad_astra.client.renderer.sky.weather_renderer.ModWeatherRenderer;
import com.github.alexnijjar.ad_astra.client.resource_pack.SkyRenderer;
import com.github.alexnijjar.ad_astra.util.ColourHolder;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry.CloudRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ClientModSkies {

        public static void register() {

                for (SkyRenderer skyRenderer : AdAstraClient.skyRenderers) {

                        ModSky sky = new ModSky();
                        sky.setStars(skyRenderer.starsRenderer());
                        sky.setSunsetColour(skyRenderer.sunsetColour());
                        sky.setSkyObjects(skyRenderer.skyObjects());
                        sky.setHorizonAngle(skyRenderer.horizonAngle());
                        sky.disableRenderingWhileRaining(!skyRenderer.weatherEffects().equals(SkyRenderer.WeatherEffects.NONE));
                        DimensionRenderingRegistry.registerSkyRenderer(skyRenderer.dimension(), sky);

                        // Dimension Effects
                        switch (skyRenderer.effects().type()) {
                        case SIMPLE -> DimensionRenderingRegistry.registerDimensionEffects(skyRenderer.dimension().getValue(), new ModDimensionEffects());
                        case NONE -> DimensionRenderingRegistry.registerDimensionEffects(skyRenderer.dimension().getValue(), new ModDimensionEffects() {
                                public float[] getFogColorOverride(float skyAngle, float tickDelta) {
                                        return null;
                                }
                        });
                        case FOGGY_REVERSED -> DimensionRenderingRegistry.registerDimensionEffects(skyRenderer.dimension().getValue(), new ModDimensionEffects() {
                                @Override
                                public boolean useThickFog(int camX, int camY) {
                                        return true;
                                }

                                @Override
                                public float[] getFogColorOverride(float skyAngle, float tickDelta) {
                                        return null;
                                }

                        });
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

                        // Cloud renderer
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