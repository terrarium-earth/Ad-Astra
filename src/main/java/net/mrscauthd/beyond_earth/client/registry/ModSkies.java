package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.math.Vec3d;
import net.mrscauthd.beyond_earth.client.renderer.sky.*;
import net.mrscauthd.beyond_earth.client.renderer.sky.cloud_renderer.ModCloudRenderer;
import net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects.FoggyDimensionEffects;
import net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects.GlacioDimensionEffects;
import net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects.HorizonDimensionEffects;
import net.mrscauthd.beyond_earth.client.renderer.sky.dimension_effects.ModDimensionEffects;
import net.mrscauthd.beyond_earth.client.renderer.sky.weather_renderer.ModWeatherRenderer;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Environment(EnvType.CLIENT)
public class ModSkies {

    @Environment(EnvType.CLIENT)
    public static void register() {

        // Moon.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MOON_KEY, new ModSky().withStars().withSun().withEarth(9.0f));
        // Mars.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MARS_KEY, new MarsSky().withVanillaStars().withSun().withEarth(1.0f).withPhobos().withDeimos());
        // Venus.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.VENUS_KEY, new VenusSky().withVanillaStars().withSun().withMercury().withEarth(2.0f));
        // Mercury.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MERCURY_KEY, new ModSky().withStars().withSun(60));
        // Glacio.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.GLACIO_KEY, new GlacioSky().withVanillaStars().withSun().withVicinus());

        // Earth Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.EARTH_ORBIT_KEY, new OrbitSky().withStars().withSun().withEarthOrbit());
        // Moon Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MOON_ORBIT_KEY, new OrbitSky().withStars().withSun().withMoonOrbit());
        // Mars Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MARS_ORBIT_KEY, new OrbitSky().withStars().withSun().withMarsOrbit());
        // Venus Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.VENUS_ORBIT_KEY, new OrbitSky().withStars().withSun().withVenusOrbit());
        // Mercury Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MERCURY_ORBIT_KEY, new OrbitSky().withStars().withSun().withMercuryOrbit());
        // Glacio Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.GLACIO_ORBIT_KEY, new OrbitSky().withStars().withSun(60).withGlacioOrbit());

        // Dimension effects.
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.MOON_KEY.getValue(), new ModDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.MARS_KEY.getValue(), new FoggyDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.VENUS_KEY.getValue(), new FoggyDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.MERCURY_KEY.getValue(), new HorizonDimensionEffects(new Vec3d(0.572549019608, 0.082352941176, 0.082352941176)));
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.GLACIO_KEY.getValue(), new GlacioDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.EARTH_ORBIT_KEY.getValue(), new ModDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.MOON_ORBIT_KEY.getValue(), new ModDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.MARS_ORBIT_KEY.getValue(), new ModDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.VENUS_ORBIT_KEY.getValue(), new ModDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.MERCURY_ORBIT_KEY.getValue(), new ModDimensionEffects());
        DimensionRenderingRegistry.registerDimensionEffects(ModUtils.GLACIO_ORBIT_KEY.getValue(), new ModDimensionEffects());

        // Cloud renderers.
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.VENUS_KEY, new ModCloudRenderer().withVenus());
        // Weather renderers.
        DimensionRenderingRegistry.registerWeatherRenderer(ModUtils.VENUS_KEY, new ModWeatherRenderer().withVenus());

        // Removes clouds from all dimensions except Glacio.

        class CloudlessSky implements DimensionRenderingRegistry.CloudRenderer {
            @Override
            public void render(WorldRenderContext context) {
            }
        }

        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.EARTH_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.MOON_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.MOON_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.MARS_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.MARS_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.VENUS_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.MERCURY_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.MERCURY_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.GLACIO_ORBIT_KEY, new CloudlessSky());
    }
}
