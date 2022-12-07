package earth.terrarium.ad_astra.client.dimension.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import com.mojang.math.Matrix4f;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.mixin.client.LevelRendererAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class VenusCloudRenderer {
    private static final ResourceLocation VENUS_CLOUD_TEXTURE = new ResourceLocation(AdAstra.MOD_ID, "textures/sky/venus/clouds.png");

    public static void render(ClientLevel level, int ticks, float tickDelta, PoseStack poseStack, double cameraX, double cameraY, double cameraZ, Matrix4f projectionMatrix) {

        Minecraft client = Minecraft.getInstance();
        LevelRendererAccessor renderer = (LevelRendererAccessor) client.levelRenderer;

        float f = level.effects().getCloudHeight();
        if (!Float.isNaN(f)) {
            RenderSystem.disableCull();
            RenderSystem.enableBlend();
            RenderSystem.enableDepthTest();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            RenderSystem.depthMask(true);
            double e = ((float) ticks + tickDelta) * 0.03f;
            double i = (cameraX + e) / 12.0;
            double j = f - (float) cameraY + 0.33f;
            double k = cameraZ / 12.0 + 0.33f;
            i -= Mth.floor(i / 2048.0) * 2048;
            k -= Mth.floor(k / 2048.0) * 2048;
            float l = (float) (i - (double) Mth.floor(i));
            float m = (float) (j / 4.0 - (double) Mth.floor(j / 4.0)) * 4.0f;
            float n = (float) (k - (double) Mth.floor(k));
            Vec3 colour = level.getCloudColor(tickDelta);
            int o = (int) Math.floor(i);
            int p = (int) Math.floor(j / 4.0);
            int q = (int) Math.floor(k);
            if (o != renderer.getPrevCloudX() || p != renderer.getPrevCloudY() || q != renderer.getPrevCloudZ() || client.options.getCloudsType() != renderer.getPrevCloudsType() || renderer.getPrevCloudColor().distanceToSqr(colour) > 2.0E-4) {
                renderer.setPrevCloudX(o);
                renderer.setPrevCloudY(p);
                renderer.setPrevCloudZ(q);
                renderer.setPrevCloudColor(colour);
                renderer.setPrevCloudsType(client.options.getCloudsType());
                renderer.setGenerateClouds(true);
            }

            if (renderer.getGenerateClouds()) {
                renderer.setGenerateClouds(false);
                BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
                if (renderer.getCloudBuffer() != null) {
                    renderer.getCloudBuffer().close();
                }

                renderer.setCloudBuffer(new VertexBuffer());
                BufferBuilder.RenderedBuffer renderedBuffer = renderer.invokeBuildClouds(bufferBuilder, i, j, k, colour);
                renderer.getCloudBuffer().bind();
                renderer.getCloudBuffer().upload(renderedBuffer);
                VertexBuffer.unbind();
            }

            RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
            RenderSystem.setShaderTexture(0, VENUS_CLOUD_TEXTURE);
            FogRenderer.levelFogColor();
            poseStack.pushPose();
            poseStack.scale(12.0f, 1.0f, 12.0f);
            poseStack.translate(-l, m, -n);
            if (renderer.getCloudBuffer() != null) {
                renderer.getCloudBuffer().bind();
                int r = renderer.getPrevCloudsType().equals(CloudStatus.FANCY) ? 0 : 1;

                for (int s = r; s < 2; ++s) {
                    if (s == 0) {
                        RenderSystem.colorMask(false, false, false, false);
                    } else {
                        RenderSystem.colorMask(true, true, true, true);
                    }

                    ShaderInstance shaderProgram = RenderSystem.getShader();
                    renderer.getCloudBuffer().drawWithShader(poseStack.last().pose(), projectionMatrix, shaderProgram);
                }

                VertexBuffer.unbind();
            }

            poseStack.popPose();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
        }
    }
}