package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.mrscauthd.beyond_earth.client.renderer.sky.MarsSky;
import net.mrscauthd.beyond_earth.client.renderer.sky.ModSky;
import net.mrscauthd.beyond_earth.client.renderer.sky.OrbitSky;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Environment(EnvType.CLIENT)
public class ModSkies {

    @Environment(EnvType.CLIENT)
    public static void register() {

        // Moon.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MOON_KEY, new ModSky().withStars().withSun().withEarth(9.0f));
        // Mars.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MARS_KEY, new MarsSky().withVanillaStars().withSun());
//        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MARS_KEY, new MarsSky().withVanillaStars().withSun().withEarth(1.0f).withPhobos().withDeimos());

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
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.GLACIO_ORBIT_KEY, new OrbitSky().withStars().withSun().withGlacioOrbit());

        // Remove clouds from all dimensions except Glacio.
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
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.VENUS_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.VENUS_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.MERCURY_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.MERCURY_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(ModUtils.GLACIO_ORBIT_KEY, new CloudlessSky());
    }
}
