package earth.terrarium.ad_astra.client.dimension.rendering.base;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Matrix4f;

public interface DimensionRenderer {

    boolean renderClouds(ClientWorld world, int ticks, float tickDelta, MatrixStack matrices, double cameraX, double cameraY, double cameraZ, Matrix4f projectionMatrix);

    boolean renderSky(ClientWorld world, int ticks, float tickDelta, MatrixStack matrices, Camera camera, Matrix4f projectionMatrix, boolean foggy, Runnable setupFog);

    boolean renderSnowAndRain(ClientWorld world, int ticks, float tickDelta, LightmapTextureManager manager, double cameraX, double cameraY, double cameraZ);

    boolean shouldRenderClouds();

    boolean shouldRenderSky();

    boolean shouldRenderSnowAndRain();
}
