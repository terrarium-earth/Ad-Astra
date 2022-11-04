package earth.terrarium.ad_astra.client.dimension.rendering.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;

public interface DimensionRenderer {

    boolean renderClouds(ClientLevel level, int ticks, float tickDelta, PoseStack poseStack, double cameraX, double cameraY, double cameraZ, Matrix4f projectionMatrix);

    boolean renderSky(ClientLevel level, int ticks, float tickDelta, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean foggy, Runnable setupFog);

    boolean renderSnowAndRain(ClientLevel level, int ticks, float tickDelta, LightTexture manager, double cameraX, double cameraY, double cameraZ);

    boolean shouldRenderClouds();

    boolean shouldRenderSky();

    boolean shouldRenderSnowAndRain();
}
