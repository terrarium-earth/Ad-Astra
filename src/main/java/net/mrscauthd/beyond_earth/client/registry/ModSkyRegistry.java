package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.mrscauthd.beyond_earth.client.renderer.sky.SimpleSky;
import net.mrscauthd.beyond_earth.registry.ModBlocks;
import net.mrscauthd.beyond_earth.registry.ModItems;
import net.mrscauthd.beyond_earth.util.ModUtils;

@Environment(EnvType.CLIENT)
public class ModSkyRegistry {

    @Environment(EnvType.CLIENT)
    public static void register() {

        // Earth Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.EARTH_ORBIT_KEY, new SimpleSky() {
            @Override
            public void render(WorldRenderContext context) {
                super.render(context);

                this.sky.renderStars();
                this.sky.renderEarthOrbit();
                this.sky.renderSun();
            }
        });

        // Moon.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MOON_KEY, new SimpleSky() {
            @Override
            public void render(WorldRenderContext context) {
                super.render(context);

                this.sky.renderStars();
                this.sky.renderEarth();
                this.sky.renderSun();
            }
        });

        // Moon Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MOON_ORBIT_KEY, new SimpleSky() {
            @Override
            public void render(WorldRenderContext context) {
                super.render(context);

                this.sky.renderStars();
                this.sky.renderMoonOrbit();
                this.sky.renderSun();
            }
        });

        // Mars Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MARS_ORBIT_KEY, new SimpleSky() {
            @Override
            public void render(WorldRenderContext context) {
                super.render(context);

                this.sky.renderStars();
                this.sky.renderMarsOrbit();
                this.sky.renderSun();
            }
        });

        // Venus Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.VENUS_ORBIT_KEY, new SimpleSky() {
            @Override
            public void render(WorldRenderContext context) {
                super.render(context);

                this.sky.renderStars();
                this.sky.renderVenusOrbit();
                this.sky.renderSun();
            }
        });

        // Mercury Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.MERCURY_ORBIT_KEY, new SimpleSky() {
            @Override
            public void render(WorldRenderContext context) {
                super.render(context);

                this.sky.renderStars();
                this.sky.renderMercuryOrbit();
                this.sky.renderSun();
            }
        });

        // Glacio Orbit.
        DimensionRenderingRegistry.registerSkyRenderer(ModUtils.GLACIO_ORBIT_KEY, new SimpleSky() {
            @Override
            public void render(WorldRenderContext context) {
                super.render(context);

                this.sky.renderStars();
                this.sky.renderGlacioOrbit();
                this.sky.renderSun();
            }
        });

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
