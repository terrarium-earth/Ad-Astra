package earth.terrarium.ad_astra.client.dimension.rendering;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.teamresourceful.resourcefullib.common.color.Color;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer;
import earth.terrarium.ad_astra.mixin.client.LevelRendererAccessor;
import earth.terrarium.ad_astra.world.WorldSeed;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;

@Environment(EnvType.CLIENT)
public class SkyUtil {

    // Scales the planet as you fall closer to it.
    public static float getScale() {
        Minecraft client = Minecraft.getInstance();
        float distance = (float) (-3000.0f + client.player.getY() * 4.5f);
        float scale = 100 * (0.2f - distance / 10000.0f);
        return Math.max(scale, 0.5f);
    }

    public static void preRender(ClientLevel level, LevelRenderer levelRenderer, Camera camera, Matrix4f projectionMatrix, BufferBuilder bufferBuilder, PlanetSkyRenderer.SunsetColour colourType, int sunsetAngle, PoseStack poseStack, float tickDelta) {

        // Render colours.
        RenderSystem.disableTexture();
        Vec3 vec3d = level.getSkyColor(camera.getPosition(), tickDelta);
        float f = (float) vec3d.x();
        float g = (float) vec3d.y();
        float h = (float) vec3d.z();
        FogRenderer.levelFogColor();
        RenderSystem.depthMask(false);

        RenderSystem.setShaderColor(f, g, h, 1.0f);
        VertexBuffer skyBuffer = ((LevelRendererAccessor) levelRenderer).getSkyBuffer();
        skyBuffer.bind();
        skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, RenderSystem.getShader());
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        renderColouring(colourType, bufferBuilder, poseStack, level, tickDelta, level.getTimeOfDay(tickDelta), sunsetAngle);
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.enableTexture();
    }

    public static void postRender(GameRenderer renderer, ClientLevel level, float tickDelta) {

        Vec3 vec3d = level.getSkyColor(renderer.getMainCamera().getPosition(), tickDelta);
        float f = (float) vec3d.x();
        float g = (float) vec3d.y();
        float h = (float) vec3d.z();

        RenderSystem.setShaderColor(0.0f, 0.0f, 0.0f, 1.0f);

        if (level.effects().hasGround()) {
            RenderSystem.setShaderColor(f * 0.2f + 0.04f, g * 0.2f + 0.04f, h * 0.6f + 0.1f, 1.0f);
        } else {
            RenderSystem.setShaderColor(f, g, h, 1.0f);
        }
        RenderSystem.enableTexture();
        RenderSystem.depthMask(true);
    }

    public static boolean isSubmerged(Camera camera) {
        Minecraft client = Minecraft.getInstance();
        LocalPlayer player = client.player;

        FogType cameraSubmersionType = camera.getFluidInCamera();
        return cameraSubmersionType.equals(FogType.POWDER_SNOW) || cameraSubmersionType.equals(FogType.LAVA) || player.hasEffect(MobEffects.BLINDNESS);
    }

    public static void startRendering(PoseStack poseStack, Vector3f rotation) {

        poseStack.pushPose();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);

        // Rotation
        poseStack.mulPose(Vector3f.YP.rotationDegrees(rotation.y()));
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(rotation.z()));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(rotation.x()));
    }

    private static void endRendering(PoseStack poseStack) {
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
        poseStack.popPose();
    }

    // For rendering textures in the sky
    public static void render(PoseStack poseStack, BufferBuilder bufferBuilder, ResourceLocation texture, Color colour, Vector3f rotation, float scale, boolean blending) {

        startRendering(poseStack, rotation);
        RenderSystem.setShader(GameRenderer::getPositionColorTexShader);

        RenderSystem.disableTexture();

        if (blending) {
            RenderSystem.enableBlend();
        } else {
            RenderSystem.disableBlend();
        }

        Matrix4f positionMatrix = poseStack.last().pose();
        RenderSystem.setShaderTexture(0, texture);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
        bufferBuilder.vertex(positionMatrix, -scale, 100.0f, -scale).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), 255).uv(1.0f, 0.0f).endVertex();
        bufferBuilder.vertex(positionMatrix, scale, 100.0f, -scale).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), 255).uv(0.0f, 0.0f).endVertex();
        bufferBuilder.vertex(positionMatrix, scale, 100.0f, scale).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), 255).uv(0.0f, 1.0f).endVertex();
        bufferBuilder.vertex(positionMatrix, -scale, 100.0f, scale).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), 255).uv(1.0f, 1.0f).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());

        endRendering(poseStack);
    }

    public static BufferBuilder.RenderedBuffer renderStars(BufferBuilder buffer, int stars, boolean colouredStars) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
        StarInformation info = StarInformation.STAR_CACHE.apply(WorldSeed.getSeed(), stars);
        for (int i = 0; i < stars; ++i) {
            Vector3f vec3f = info.getParam1(i);
            float d = vec3f.x();
            float e = vec3f.y();
            float f = vec3f.z();
            float g = info.getMultiplier(i);
            float h = d * d + e * e + f * f;
            if (h >= 1 || h <= 0.01f) continue;
            h = 1f / Mth.sqrt(h);
            d *= h;
            e *= h;
            f *= h;
            float j = d * 100.0f;
            float k = e * 100.0f;
            float l = f * 100.0f;
            float m = (float) Math.atan2(d, f);
            float n = Mth.sin(m);
            float o = Mth.cos(m);
            float p = (float) Math.atan2(Mth.sqrt(d * d + f * f), e);
            float q = Mth.sin(p);
            float r = Mth.cos(p);
            float s = info.getRandomPi(i);
            float t = Mth.sin(s);
            float u = Mth.cos(s);

            for (int v = 0; v < 4; ++v) {
                float x = ((v & 2) - 1) * g;
                float y = ((v + 1 & 2) - 1) * g;
                float aa = x * u - y * t;
                float ac = y * u + x * t;
                float ae = aa * -r;

                Color colour = info.getColour(i, v, colouredStars);
                buffer.vertex(j + ae * n - ac * o, k + aa * q, l + ac * n + ae * o).color(colour.getIntRed(), colour.getIntGreen(), colour.getIntBlue(), colour.getIntAlpha()).endVertex();
            }
        }
        return buffer.end();
    }

    // Custom blue sunset and sunrise
    public static float[] getMarsColour(float skyAngle) {
        float[] colours = new float[4];

        float cosine = Mth.cos(skyAngle * ((float) Math.PI * 2f)) - 0.0f;
        if (cosine >= -0.4f && cosine <= 0.4f) {
            float c = (cosine + 0.0f) / 0.4f * 0.5f + 0.5f;
            float sine = 1.0f - (1.0f - Mth.sin(c * (float) Math.PI)) * 0.99f;
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

    public static void renderColouring(PlanetSkyRenderer.SunsetColour type, BufferBuilder bufferBuilder, PoseStack poseStack, ClientLevel level, float tickDelta, float skyAngle, int sunsetAngle) {

        float[] fogColours = switch (type) {
            case VANILLA -> level.effects().getSunriseColor(level.getSunAngle(tickDelta), tickDelta);
            case MARS -> getMarsColour(skyAngle);
        };
        if (fogColours != null) {
            RenderSystem.setShader(GameRenderer::getPositionColorShader);
            RenderSystem.disableTexture();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            poseStack.pushPose();
            poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0f));
            float sine = Mth.sin(level.getSunAngle(tickDelta)) < 0.0f ? 180.0f : sunsetAngle;
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(sine));
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(90.0f));

            Matrix4f matrix4f = poseStack.last().pose();
            bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
            bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(fogColours[0], fogColours[1], fogColours[2], fogColours[3]).endVertex();

            for (int i = 0; i <= 16; ++i) {
                float o = (float) i * ((float) Math.PI * 2) / 16.0f;
                float cosine = Mth.cos(o);
                bufferBuilder.vertex(matrix4f, Mth.sin(o) * 120.0f, cosine * 120.0f, -cosine * 40.0f * fogColours[3]).color(fogColours[0], fogColours[1], fogColours[2], 0.0f).endVertex();
            }

            BufferUploader.draw(bufferBuilder.end());
            poseStack.popPose();
        }
    }
}