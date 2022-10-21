package earth.terrarium.ad_astra.client.dimension.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferRenderer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormats;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer;
import earth.terrarium.ad_astra.mixin.client.WorldRendererAccessor;
import earth.terrarium.ad_astra.world.WorldSeed;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class SkyUtil {

    // Scales the planet as you fall closer to it.
    public static float getScale() {
        MinecraftClient client = MinecraftClient.getInstance();
        float distance = (float) (-3000.0f + client.player.getY() * 4.5f);
        float scale = 100 * (0.2f - distance / 10000.0f);
        return Math.max(scale, 0.5f);
    }

    public static void preRender(ClientWorld world, WorldRenderer worldRenderer, Camera camera, Matrix4f projectionMatrix, BufferBuilder bufferBuilder, PlanetSkyRenderer.SunsetColour colourType, int sunsetAngle, MatrixStack matrices, float tickDelta) {

        // Render colours.
        RenderSystem.disableTexture();
        Vec3d vec3d = world.getSkyColor(camera.getPos(), tickDelta);
        float f = (float) vec3d.getX();
        float g = (float) vec3d.getY();
        float h = (float) vec3d.getZ();
        BackgroundRenderer.setShaderFogColor();
        RenderSystem.depthMask(false);

        RenderSystem.setShaderColor(f, g, h, 1.0f);
        ((WorldRendererAccessor) worldRenderer).getLightSkyBuffer().setShader(matrices.peek().getModel(), projectionMatrix, RenderSystem.getShader());

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        renderColouring(colourType, bufferBuilder, matrices, world, tickDelta, world.getSkyAngle(tickDelta), sunsetAngle);
        RenderSystem.blendFuncSeparate(GlStateManager.class_4535.SRC_ALPHA, GlStateManager.class_4534.ONE, GlStateManager.class_4535.ONE, GlStateManager.class_4534.ZERO);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableTexture();
    }

    public static void postRender(GameRenderer renderer, ClientWorld world, float tickDelta) {

        Vec3d vec3d = world.getSkyColor(renderer.getCamera().getPos(), tickDelta);
        float f = (float) vec3d.getX();
        float g = (float) vec3d.getY();
        float h = (float) vec3d.getZ();

        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);

        if (world.getSkyProperties().isAlternateSkyColor()) {
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

    public static void startRendering(MatrixStack matrices, Vec3f rotation) {

        matrices.push();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Rotation
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(rotation.getY()));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(rotation.getZ()));
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(rotation.getX()));
    }

    private static void endRendering(MatrixStack matrices) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        matrices.pop();
    }

    // For rendering textures in the sky
    public static void render(MatrixStack matrices, BufferBuilder bufferBuilder, Identifier texture, Color colour, Vec3f rotation, float scale, boolean blending) {

        startRendering(matrices, rotation);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);

        RenderSystem.disableTexture();

        if (blending) {
            RenderSystem.enableBlend();
        } else {
            RenderSystem.disableBlend();
        }

        Matrix4f positionMatrix = matrices.peek().getModel();
        RenderSystem.setShaderTexture(0, texture);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(positionMatrix, -scale, 100.0f, -scale).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), 255).uv(1.0f, 0.0f).next();
        bufferBuilder.vertex(positionMatrix, scale, 100.0f, -scale).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), 255).uv(0.0f, 0.0f).next();
        bufferBuilder.vertex(positionMatrix, scale, 100.0f, scale).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), 255).uv(0.0f, 1.0f).next();
        bufferBuilder.vertex(positionMatrix, -scale, 100.0f, scale).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), 255).uv(1.0f, 1.0f).next();
        BufferRenderer.drawWithShader(bufferBuilder.end());

        endRendering(matrices);
    }

    public static BufferBuilder.RenderedBuffer renderStars(BufferBuilder buffer, int stars, boolean colouredStars) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        StarInformation info = StarInformation.STAR_CACHE.apply(WorldSeed.getSeed(), stars);
        for (int i = 0; i < stars; ++i) {
            Vec3f vec3f = info.getParam1(i);
            float d = vec3f.getX();
            float e = vec3f.getY();
            float f = vec3f.getZ();
            float g = info.getMultiplier(i);
            float h = d * d + e * e + f * f;
            if (h >= 1 || h <= 0.01f) continue;
            h = 1f / MathHelper.sqrt(h);
            d *= h;
            e *= h;
            f *= h;
            float j = d * 100.0f;
            float k = e * 100.0f;
            float l = f * 100.0f;
            float m = (float) Math.atan2(d, f);
            float n = MathHelper.sin(m);
            float o = MathHelper.cos(m);
            float p = (float) Math.atan2(MathHelper.sqrt(d * d + f * f), e);
            float q = MathHelper.sin(p);
            float r = MathHelper.cos(p);
            float s = info.getRandomPi(i);
            float t = MathHelper.sin(s);
            float u = MathHelper.cos(s);

            for (int v = 0; v < 4; ++v) {
                float x = ((v & 2) - 1) * g;
                float y = ((v + 1 & 2) - 1) * g;
                float aa = x * u - y * t;
                float ac = y * u + x * t;
                float ae = aa * -r;

                Color colour = info.getColour(i, v, colouredStars);
                buffer.vertex(j + ae * n - ac * o, k + aa * q, l + ac * n + ae * o).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), colour.getIntAlpha()).next();
            }
        }
        return buffer.end();
    }

    // Custom blue sunset and sunrise
    public static float[] getMarsColour(float skyAngle) {
        float[] colours = new float[4];

        float cosine = MathHelper.cos(skyAngle * ((float) Math.PI * 2f)) - 0.0f;
        if (cosine >= -0.4f && cosine <= 0.4f) {
            float c = (cosine + 0.0f) / 0.4f * 0.5f + 0.5f;
            float sine = 1.0f - (1.0f - MathHelper.sin(c * (float) Math.PI)) * 0.99f;
            sine *= sine;
            colours[0] = c * 0.3f;
            colours[1] = c * c * 0.6f + 0.55f;
            colours[2] = c * c * 0.0f + 0.8f;
            colours[3] = sine;
            return colours;
        } else {
            return null;
        }
    }

    public static void renderColouring(PlanetSkyRenderer.SunsetColour type, BufferBuilder bufferBuilder, MatrixStack matrices, ClientWorld world, float tickDelta, float skyAngle, int sunsetAngle) {

        float[] fogColours = switch (type) {
            case VANILLA -> world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
            case MARS -> getMarsColour(skyAngle);
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

            Matrix4f matrix4f = matrices.peek().getModel();
            bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
            bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(fogColours[0], fogColours[1], fogColours[2], fogColours[3]).next();

            for (int i = 0; i <= 16; ++i) {
                float o = (float) i * ((float) Math.PI * 2) / 16.0f;
                float cosine = MathHelper.cos(o);
                bufferBuilder.vertex(matrix4f, MathHelper.sin(o) * 120.0f, cosine * 120.0f, -cosine * 40.0f * fogColours[3]).color(fogColours[0], fogColours[1], fogColours[2], 0.0f).next();
            }

            BufferRenderer.draw(bufferBuilder.end());
            matrices.pop();
        }
    }
}