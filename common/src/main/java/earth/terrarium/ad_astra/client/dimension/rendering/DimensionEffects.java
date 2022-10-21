package earth.terrarium.ad_astra.client.dimension.rendering;

import earth.terrarium.ad_astra.client.dimension.rendering.base.DimensionRenderer;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer;
import earth.terrarium.ad_astra.util.ColourUtils;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;

public class DimensionEffects extends SkyProperties implements DimensionRenderer {
    private final PlanetSkyRenderer renderer;
    private final ModSkyRenderer skyRenderer;

    public DimensionEffects(PlanetSkyRenderer renderer) {
        super(192, true, SkyProperties.SkyType.NORMAL, false, false);
        this.renderer = renderer;
        this.skyRenderer = new ModSkyRenderer(renderer);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        if (renderer.effects().type().equals(PlanetSkyRenderer.DimensionEffectType.COLORED_HORIZON)) {
            return ColourUtils.toVector(renderer.effects().colour());
        }
        return color.multiply(sunHeight * 0.94f + 0.06f, sunHeight * 0.94f + 0.06f, sunHeight * 0.91f + 0.09f);
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return renderer.effects().type().isFoggy();
    }

    @Override
    public float[] getFogColorOverride(float skyAngle, float tickDelta) {
        PlanetSkyRenderer.DimensionEffectType type = renderer.effects().type();
        if (type == PlanetSkyRenderer.DimensionEffectType.FOGGY_REVERSED || type == PlanetSkyRenderer.DimensionEffectType.NONE) {
            return null;
        }
        return super.getFogColorOverride(skyAngle, tickDelta);
    }

    @Override
    public boolean renderClouds(ClientWorld world, int ticks, float tickDelta, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f projectionMatrix) {
        return switch (renderer.cloudEffects()) {
            case NONE -> true;
            case VANILLA -> false;
            case VENUS -> {
                VenusCloudRenderer.render(world, ticks, tickDelta, matrices, cameraX, cameraY, cameraZ, projectionMatrix);
                yield true;
            }
        };
    }

    @Override
    public boolean shouldRenderClouds() {
        return renderer.cloudEffects() != PlanetSkyRenderer.CloudEffects.VANILLA;
    }

    @Override
    public boolean renderSky(ClientWorld world, int ticks, float tickDelta, MatrixStack matrices, Camera camera, Matrix4f projectionMatrix, boolean foggy, Runnable setupFog) {
        setupFog.run();
        skyRenderer.render(world, ticks, tickDelta, matrices, camera, projectionMatrix, foggy);
        return true;
    }

    @Override
    public boolean shouldRenderSky() {
        return true;
    }

    @Override
    public boolean renderSnowAndRain(ClientWorld world, int ticks, float tickDelta, LightmapTextureManager manager, double cameraX, double cameraY, double cameraZ) {
        return switch (renderer.weatherEffects()) {
            case NONE -> true;
            case VANILLA -> false;
            case VENUS -> {
                ModWeatherRenderer.render(world, ticks, tickDelta, manager, cameraX, cameraY, cameraZ);
                yield true;
            }
        };
    }

    @Override
    public boolean shouldRenderSnowAndRain() {
        return renderer.weatherEffects() != PlanetSkyRenderer.WeatherEffects.VANILLA;
    }
}
