package net.mrscauthd.beyond_earth.client.renderer.sky;

import java.util.Random;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.mrscauthd.beyond_earth.client.resource_pack.SkyRenderer;
import net.mrscauthd.beyond_earth.mixin.WorldRendererAccessor;
import net.mrscauthd.beyond_earth.world.WorldSeed;

@Environment(EnvType.CLIENT)
public class SkyUtil {

    public static float getScale() {
        MinecraftClient client = MinecraftClient.getInstance();
        float distance = (float) (-3000.0f + client.player.getY() * 6.0f);
        float scale = 100 * (0.2f - distance / 10000.0f);
        return Math.max(scale, 4.0f);
    }

    public static void preRender(WorldRenderContext context, BufferBuilder bufferBuilder, SkyRenderer.SunsetColour colourType, int sunsetAngle, MatrixStack matrices, ClientWorld world, float tickDelta) {

        // Render colours.
        RenderSystem.disableTexture();
        Vec3d vec3d = world.getSkyColor(context.gameRenderer().getCamera().getPos(), tickDelta);
        float f = (float) vec3d.x;
        float g = (float) vec3d.y;
        float h = (float) vec3d.z;
        BackgroundRenderer.setFogBlack();
        RenderSystem.depthMask(false);

        RenderSystem.setShaderColor(f, g, h, 1.0f);
        ((WorldRendererAccessor) context.worldRenderer()).getLightSkyBuffer().setShader(context.matrixStack().peek().getPositionMatrix(), context.projectionMatrix(), RenderSystem.getShader());

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        renderColouring(colourType, bufferBuilder, matrices, world, tickDelta, context.world().getSkyAngle(tickDelta), sunsetAngle);
        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableTexture();
    }

    public static void postRender(WorldRenderContext context, MatrixStack matrices, ClientWorld world, float tickDelta) {

        Vec3d vec3d = world.getSkyColor(context.gameRenderer().getCamera().getPos(), tickDelta);
        float f = (float) vec3d.x;
        float g = (float) vec3d.y;
        float h = (float) vec3d.z;

        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);
        MinecraftClient client = MinecraftClient.getInstance();
        double cameraPos = client.player.getCameraPosVec(tickDelta).y - world.getLevelProperties().getSkyDarknessHeight(world);
        if (cameraPos < 0.0) {
            matrices.push();
            matrices.translate(0.0, 12.0, 0.0);
            ((WorldRendererAccessor) context.worldRenderer()).getDarkSkyBuffer().setShader(context.matrixStack().peek().getPositionMatrix(), context.projectionMatrix(), RenderSystem.getShader());
            matrices.pop();
        }
        if (world.getDimensionEffects().isAlternateSkyColor()) {
            RenderSystem.setShaderColor(f * 0.2f + 0.04f, g * 0.2f + 0.04f, h * 0.6f + 0.1f, 1.0f);
        } else {
            RenderSystem.setShaderColor(f, g, h, 1.0f);
        }
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
    }

    public static boolean isSubmerged(Camera camera) {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayerEntity player = client.player;

        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        return cameraSubmersionType.equals(CameraSubmersionType.POWDER_SNOW) || cameraSubmersionType.equals(CameraSubmersionType.LAVA) || player.hasStatusEffect(StatusEffects.BLINDNESS);
    }

    private static void startRendering(MatrixStack matrices, Vec3f euler) {

        matrices.push();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Rotation.
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(euler.getY()));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(euler.getZ()));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(euler.getX()));
    }

    private static void endRendering(MatrixStack matrices) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        matrices.pop();
    }

    public static void render(WorldRenderContext context, BufferBuilder bufferBuilder, Identifier texture, Vec3f euler, float scale, boolean blending) {

        startRendering(context.matrixStack(), euler);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        RenderSystem.disableTexture();

        if (blending) {
            RenderSystem.enableBlend();
        } else {
            RenderSystem.disableBlend();
        }

        Matrix4f positionMatrix = context.matrixStack().peek().getPositionMatrix();
        RenderSystem.setShaderTexture(0, texture);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        bufferBuilder.vertex(positionMatrix, -scale, (float) 100.0, -scale).texture(0.0f, 0.0f).next();
        bufferBuilder.vertex(positionMatrix, scale, (float) 100.0, -scale).texture(1.0f, 0.0f).next();
        bufferBuilder.vertex(positionMatrix, scale, (float) 100.0, scale).texture(1.0f, 1.0f).next();
        bufferBuilder.vertex(positionMatrix, -scale, (float) 100.0, scale).texture(0.0f, 1.0f).next();
        bufferBuilder.end();
        BufferRenderer.draw(bufferBuilder);

        endRendering(context.matrixStack());
    }

    public static VertexBuffer renderStars(WorldRenderContext context, BufferBuilder bufferBuilder, VertexBuffer starsBuffer, int stars, boolean fixedStarColour) {

        startRendering(context.matrixStack(), new Vec3f(-30.0f, 0.0f, context.world().getSkyAngle(context.tickDelta()) * 360.0f));
        RenderSystem.setShader(GameRenderer::getPositionShader);

        if (starsBuffer != null) {
            starsBuffer.close();
        }

        starsBuffer = new VertexBuffer();
        SkyUtil.renderStars(bufferBuilder, stars);
        bufferBuilder.end();
        starsBuffer.upload(bufferBuilder);

        if (!fixedStarColour) {
            float rot = context.world().method_23787(context.tickDelta());
            RenderSystem.setShaderColor(rot, rot, rot, rot);
        } else {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        }

        BackgroundRenderer.clearFog();
        starsBuffer.setShader(context.matrixStack().peek().getPositionMatrix(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionShader());
        context.matrixStack().pop();
        return starsBuffer;
    }

    private static void renderStars(BufferBuilder buffer, int stars) {
        Random random = new Random(WorldSeed.getSeed());
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < stars; ++i) {
            double d = random.nextFloat() * 2.0f - 1.0f;
            double e = random.nextFloat() * 2.0f - 1.0f;
            double f = random.nextFloat() * 2.0f - 1.0f;
            double g = 0.15f + random.nextFloat() * 0.1f;
            double h = d * d + e * e + f * f;
            if (!(h < 1.0) || !(h > 0.01))
                continue;
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

    public static void renderColouring(SkyRenderer.SunsetColour type, BufferBuilder bufferBuilder, MatrixStack matrices, ClientWorld world, float tickDelta, float skyAngle, int sunsetAngle) {

        float[] fogColours = switch (type) {
        case VANILLA -> world.getDimensionEffects().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
        case MARS -> ModSky.getMarsColour(skyAngle);
        };
        if (fogColours != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableTexture();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            matrices.push();
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f));
            float sine = MathHelper.sin(world.getSkyAngleRadians(tickDelta)) < 0.0f ? 180.0f : sunsetAngle;
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
}