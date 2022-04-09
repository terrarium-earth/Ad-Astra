package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.client.renderer.sky.SimpleSky;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class ModSkyRegistry {

    public static final RegistryKey<World> EARTH_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("earth_orbit"));
    public static final RegistryKey<World> GLACIO_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("glacio"));
    public static final RegistryKey<World> GLACIO_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("glacio_orbit"));
    public static final RegistryKey<World> MARS_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("mars"));
    public static final RegistryKey<World> MARS_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("mars_orbit"));
    public static final RegistryKey<World> MERCURY_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("mercury"));
    public static final RegistryKey<World> MERCURY_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("mercury_orbit"));
    public static final RegistryKey<World> MOON_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("moon"));
    public static final RegistryKey<World> MOON_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("moon_orbit"));
    public static final RegistryKey<World> VENUS_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("venus"));
    public static final RegistryKey<World> VENUS_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("venus_orbit"));

    @Environment(EnvType.CLIENT)
    public static void register() {

        // Moon.
        DimensionRenderingRegistry.registerSkyRenderer(MOON_KEY, new SimpleSky() {
            @Override
            public void render(WorldRenderContext context) {
                super.render(context);
                this.sky.renderColouring();
                this.sky.renderStars();
                this.sky.renderEarth();
                this.sky.renderSun();
            }
        });



        // Remove clouds from all dimensions.
        class CloudlessSky implements DimensionRenderingRegistry.CloudRenderer {
            @Override
            public void render(WorldRenderContext context) {
            }
        }

        DimensionRenderingRegistry.registerCloudRenderer(EARTH_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(GLACIO_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(GLACIO_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(MARS_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(MARS_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(MERCURY_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(MERCURY_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(MOON_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(MOON_ORBIT_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(VENUS_KEY, new CloudlessSky());
        DimensionRenderingRegistry.registerCloudRenderer(VENUS_ORBIT_KEY, new CloudlessSky());
    }
}
