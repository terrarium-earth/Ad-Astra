package net.mrscauthd.beyond_earth.client.renderer.sky;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;

@Environment(EnvType.CLIENT)
public abstract class SimpleSky implements DimensionRenderingRegistry.SkyRenderer {

    public BeyondEarthSky sky = null;

    @Override
    public void render(WorldRenderContext context) {
        if (sky == null) {
            sky = new BeyondEarthSky();
        }
        this.sky.updateRenderer(context);
    }
}