package net.mrscauthd.beyond_earth.client.renderer.sky;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GraphicsMode;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.mixin.WorldRendererAccessor;
import net.mrscauthd.beyond_earth.world.WorldSeed;

import java.util.Random;

public class SkyUtil {

    public static float getScale() {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            float distance = (float) (-3000.0f + player.getY() * 6.0f);
            float scale = 100 * (0.2f - distance / 10000.0f);
            return Math.max(scale, 4.0f);
        }
        return 0.0f;
    }

    public static void render(Identifier texture, boolean disableBlending, float scale, Vec3f euler, MatrixStack matrices, ClientWorld world, float tickDelta, WorldRenderContext context) {

        RenderSystem.disableTexture();
        Vec3d skyColor = world.getSkyColor(MinecraftClient.getInstance().gameRenderer.getCamera().getPos(), tickDelta);
        float f = (float) skyColor.x;
        float g = (float) skyColor.y;
        float h = (float) skyColor.z;
        BackgroundRenderer.clearFog();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        RenderSystem.depthMask(false);
        Shader shader = RenderSystem.getShader();
        // TODO: SHADER BREAKS RENDERING
        ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getLightSkyBuffer().setShader(matrices.peek().getPositionMatrix(), context.projectionMatrix(), shader);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        SkyUtil.renderColouring(bufferBuilder, matrices, world, tickDelta);
        RenderSystem.enableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);

        matrices.push();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Rotation.
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(euler.getY()));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(euler.getZ()));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(euler.getX()));

        Matrix4f positionMatrix = matrices.peek().getPositionMatrix();

        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        if (disableBlending) {
            RenderSystem.disableBlend();
        }
        RenderSystem.setShaderTexture(0, texture);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(positionMatrix, -scale, (float) 100.0, -scale).texture(0.0f, 0.0f).next();
        bufferBuilder.vertex(positionMatrix, scale, (float) 100.0, -scale).texture(1.0f, 0.0f).next();
        bufferBuilder.vertex(positionMatrix, scale, (float) 100.0, scale).texture(1.0f, 1.0f).next();
        bufferBuilder.vertex(positionMatrix, -scale, (float) 100.0, scale).texture(0.0f, 1.0f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        RenderSystem.enableBlend();

        RenderSystem.disableTexture();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        matrices.pop();

        RenderSystem.disableTexture();
        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);

        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            double d = player.getCameraPosVec(tickDelta).y - world.getLevelProperties().getSkyDarknessHeight(world);
            if (d < 0.0) {
                matrices.push();
                matrices.translate(0.0, 12.0, 0.0);
                // TODO: SHADER BREAKS RENDERING
                ((WorldRendererAccessor) MinecraftClient.getInstance().worldRenderer).getDarkSkyBuffer().setShader(matrices.peek().getPositionMatrix(), context.projectionMatrix(), shader);
                matrices.pop();
            }
        }

        RenderSystem.disableTexture();
        if (world.getDimensionEffects().isAlternateSkyColor()) {
            RenderSystem.setShaderColor(f * 0.2f + 0.04f, g * 0.2f + 0.04f, h * 0.6f + 0.1f, 1.0f);
        } else {
            RenderSystem.setShaderColor(f, g, h, 1.0f);
        }
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
    }

    private static void renderColouring(BufferBuilder bufferBuilder, MatrixStack matrices, ClientWorld world, float tickDelta) {

        float[] fogColours = world.getDimensionEffects().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
        if (fogColours != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableTexture();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            matrices.push();
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            float sine = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0f ? 180.0f : 0.0f;
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(sine));
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f));

            Matrix4f matrix4f = matrices.peek().getPositionMatrix();
            bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(fogColours[0], fogColours[1], fogColours[2], fogColours[3]).next();

            for (int i = 0; i <= 16; ++i) {
                float o = (float) i * ((float) Math.PI * 2) / 16.0f;
                float cosine = MathHelper.cos(o);
                bufferBuilder.vertex(matrix4f, MathHelper.sin(o) * 120.0f, cosine * 120.0f, -cosine * 40.0f * fogColours[3]).color(fogColours[0], fogColours[1], fogColours[2], 0.0f).next();
            }
            bufferBuilder.end();
            BufferRenderer.draw(bufferBuilder);
            matrices.pop();
        }
    }

    public static VertexBuffer renderStars(VertexBuffer starsBuffer, int fancyStars, int fastStars, MatrixStack matrices, float skyAngle, boolean advancedTranslucency) {

        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-30.0F));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(0.0F));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(skyAngle * 360.0F));

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (starsBuffer != null) {
            starsBuffer.close();
        }
        starsBuffer = new VertexBuffer();
        SkyUtil.renderStars(advancedTranslucency, bufferBuilder, fancyStars, fastStars);
        bufferBuilder.end();
        starsBuffer.upload(bufferBuilder);

        RenderSystem.setShaderColor(0.8f, 0.8f, 0.8f, 0.8f);
        BackgroundRenderer.clearFog();
        starsBuffer.setShader(matrices.peek().getPositionMatrix(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionShader());

        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        matrices.pop();
        return starsBuffer;
    }

    private static void renderStars(boolean advancedTranslucency, BufferBuilder buffer, int fancyStars, int fastStars) {
        Random random = new Random(WorldSeed.getSeed());
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < (advancedTranslucency || MinecraftClient.getInstance().options.graphicsMode.equals(GraphicsMode.FANCY) ? fancyStars : fastStars); ++i) {
            double d = random.nextFloat() * 2.0f - 1.0f;
            double e = random.nextFloat() * 2.0f - 1.0f;
            double f = random.nextFloat() * 2.0f - 1.0f;
            double g = 0.15f + random.nextFloat() * 0.1f;
            double h = d * d + e * e + f * f;
            if (!(h < 1.0) || !(h > 0.01)) continue;
            h = 1.0 / Math.sqrt(h);
            double j = (d *= h) * 100.0;
            double k = (e *= h) * 100.0;
            double l = (f *= h) * 100.0;
            double m = Math.atan2(d, f);
            double n = Math.sin(m);
            double o = Math.cos(m);
            double p = Math.atan2(Math.sqrt(d * d + f * f), e);
            double q = Math.sin(p);
            double r = Math.cos(p);
            double s = random.nextDouble() * Math.PI * 2.0;
            double t = Math.sin(s);
            double u = Math.cos(s);
            for (int v = 0; v < 4; ++v) {
                double x = (double) ((v & 2) - 1) * g;
                double y = (double) ((v + 1 & 2) - 1) * g;
                double aa = x * u - y * t;
                double ac = y * u + x * t;
                double ad = aa * q + 0.0 * r;
                double ae = 0.0 * q - aa * r;
                double af = ae * n - ac * o;
                double ah = ac * n + ae * o;
                buffer.vertex(j + af, k + ad, l + ah).next();
            }
        }
    }
}
