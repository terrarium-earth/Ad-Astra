package earth.terrarium.adastra.client.dimension;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import earth.terrarium.adastra.client.utils.DimensionRenderingUtils;
import earth.terrarium.adastra.mixins.client.LevelRendererAccessor;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public class ModSkyRenderer {
    private final PlanetRenderer renderer;

    @Nullable
    private VertexBuffer starBuffer;

    public ModSkyRenderer(PlanetRenderer renderer) {
        this.renderer = renderer;
    }

    public void render(ClientLevel level, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        setupFog.run();
        if (isFoggy || inFog(camera)) return;
        if (!renderer.renderInRain() && level.isRaining()) return;
        if (starBuffer == null) createStars();
        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();

        setSkyColor(level, camera, partialTick);

        RenderSystem.depthMask(false);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();

        renderSky(bufferBuilder, level, partialTick, poseStack, projectionMatrix);

        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        poseStack.pushPose();

        renderStars(level, partialTick, poseStack, projectionMatrix, setupFog);

        RenderSystem.disableBlend();

        RenderSystem.setShaderColor(1, 1, 1, 1);
        renderer.skyRenderables().forEach(renderable -> {
            var globalRotation = switch (renderable.movementType()) {
                case STATIC -> renderable.globalRotation();
                case TIME_OF_DAY -> renderable.globalRotation().add(0, 0, level.getTimeOfDay(partialTick) * 360);
                case TIME_OF_DAY_REVERSED ->
                    renderable.globalRotation().add(0, 0, -level.getTimeOfDay(partialTick) * 360);
            };

            renderSkyRenderable(bufferBuilder, poseStack, renderable.localRotation(), globalRotation, renderable.scale(), renderable.texture(), renderable.blend());
            if (renderable.backLightScale() > 0) {
                setSkyRenderableColor(level, partialTick, renderable.backLightColor());
                renderSkyRenderable(bufferBuilder, poseStack, renderable.localRotation(), globalRotation, renderable.backLightScale(), DimensionRenderingUtils.BACKLIGHT, true);
                RenderSystem.setShaderColor(1, 1, 1, 1);
            }
        });

        RenderSystem.setShaderColor(1, 1, 1, 1);
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        poseStack.popPose();
        RenderSystem.depthMask(true);
    }

    public boolean inFog(Camera camera) {
        var levelRenderer = (LevelRendererAccessor) Minecraft.getInstance().levelRenderer;
        var fogType = camera.getFluidInCamera();
        return fogType == FogType.POWDER_SNOW
            || fogType == FogType.LAVA
            || levelRenderer.invokeDoesMobEffectBlockSky(camera);
    }

    public void setSkyColor(ClientLevel level, Camera camera, float partialTick) {
        Vec3 skyColor = level.getSkyColor(camera.getPosition(), partialTick);
        float r = (float) skyColor.x;
        float g = (float) skyColor.y;
        float b = (float) skyColor.z;
        RenderSystem.setShaderColor(r, g, b, 1);
    }

    public void renderSky(BufferBuilder bufferBuilder, ClientLevel level, float partialTick, PoseStack poseStack, Matrix4f projectionMatrix) {
        FogRenderer.levelFogColor();
        ShaderInstance shader = RenderSystem.getShader();
        if (shader == null) return;

        var skyBuffer = ((LevelRendererAccessor) Minecraft.getInstance().levelRenderer).getSkyBuffer();
        skyBuffer.bind();
        skyBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shader);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();

        float[] color = ModDimensionSpecialEffects.getSunriseColor(level.getTimeOfDay(partialTick), partialTick, renderer.sunriseColor());
        if (color != null) {
            renderSunrise(bufferBuilder, level, partialTick, poseStack, color);
        }
    }

    public void renderSunrise(BufferBuilder bufferBuilder, ClientLevel level, float partialTick, PoseStack poseStack, float[] color) {
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1, 1, 1, 1);

        poseStack.pushPose();
        poseStack.mulPose(Axis.XP.rotationDegrees(90));

        poseStack.mulPose(Axis.ZP.rotationDegrees(renderer.sunriseAngle()));

        float sunAngle = Mth.sin(level.getSunAngle(partialTick)) < 0 ? 180 : 0;
        poseStack.mulPose(Axis.ZP.rotationDegrees(sunAngle));
        poseStack.mulPose(Axis.ZP.rotationDegrees(90));

        float r = color[0];
        float g = color[1];
        float b = color[2];

        Matrix4f matrix = poseStack.last().pose();
        bufferBuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
        bufferBuilder.vertex(matrix, 0, 100, 0).color(r, g, b, color[3]).endVertex();

        for (int i = 0; i <= 16; i++) {
            float angle = (float) i * (float) (Math.PI * 2) / 16;
            float x = Mth.sin(angle);
            float y = Mth.cos(angle);
            bufferBuilder.vertex(matrix, x * 120, y * 120, -y * 40 * color[3]).color(r, g, b, 0).endVertex();
        }

        BufferUploader.drawWithShader(bufferBuilder.end());
        poseStack.popPose();
    }

    public void renderSkyRenderable(BufferBuilder bufferBuilder, PoseStack poseStack, Vec3 localRotation, Vec3 globalRotation, float scale, ResourceLocation texture, boolean blend) {
        if (blend) RenderSystem.enableBlend();
        poseStack.pushPose();

        poseStack.mulPose(Axis.XP.rotationDegrees((float) globalRotation.x));
        poseStack.mulPose(Axis.YP.rotationDegrees((float) globalRotation.y));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) globalRotation.z));

        poseStack.translate(0, 100, 0);
        poseStack.mulPose(Axis.XP.rotationDegrees((float) localRotation.x));
        poseStack.mulPose(Axis.YP.rotationDegrees((float) localRotation.y));
        poseStack.mulPose(Axis.ZP.rotationDegrees((float) localRotation.z));
        poseStack.translate(0, -100, 0);

        var matrix = poseStack.last().pose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, texture);
        bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
        bufferBuilder.vertex(matrix, -scale, 100, -scale).uv(1, 0).endVertex();
        bufferBuilder.vertex(matrix, scale, 100, -scale).uv(0, 0).endVertex();
        bufferBuilder.vertex(matrix, scale, 100, scale).uv(0, 1).endVertex();
        bufferBuilder.vertex(matrix, -scale, 100, scale).uv(1, 1).endVertex();
        BufferUploader.drawWithShader(bufferBuilder.end());
        poseStack.popPose();
        RenderSystem.disableBlend();
    }

    public void setSkyRenderableColor(ClientLevel level, float partialTick, int color) {
        float r = FastColor.ARGB32.red(color) / 255f;
        float g = FastColor.ARGB32.green(color) / 255f;
        float b = FastColor.ARGB32.blue(color) / 255f;
        float a = renderer.renderInRain() ? 1 : 1 - level.getRainLevel(partialTick);
        RenderSystem.setShaderColor(r, g, b, a);
    }

    public void renderStars(ClientLevel level, float partialTick, PoseStack poseStack, Matrix4f projectionMatrix, Runnable setupFog) {
        float starLight = renderer.starBrightness().orElseGet(() -> {
            float rainLevel = 1 - level.getRainLevel(partialTick);
            return level.getStarBrightness(partialTick) * rainLevel;
        });
        if (starLight <= 0) return;
        if (starBuffer == null) return;
        RenderSystem.setShaderColor(starLight, starLight, starLight, starLight);
        FogRenderer.setupNoFog();
        starBuffer.bind();
        var shader = GameRenderer.getPositionColorShader();
        if (shader == null) return;
        starBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shader);
        VertexBuffer.unbind();
        setupFog.run();
    }

    public void createStars() {
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        if (starBuffer != null) {
            starBuffer.close();
        }

        starBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
        BufferBuilder.RenderedBuffer renderedBuffer = drawStars(bufferBuilder);
        starBuffer.bind();
        starBuffer.upload(renderedBuffer);
        VertexBuffer.unbind();
    }

    public BufferBuilder.RenderedBuffer drawStars(BufferBuilder builder) {
        var random = RandomSource.create(10842);
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        for (int i = 0; i < renderer.stars(); i++) {
            double x = random.nextFloat() * 2 - 1;
            double y = random.nextFloat() * 2 - 1;
            double z = random.nextFloat() * 2 - 1;
            double scale = 0.15 + random.nextFloat() * 0.1;

            double distance = x * x + y * y + z * z;

            // ensure that the stars are within the sphere and not too close to the center
            if (distance >= 1 || distance <= 0.01) continue;

            distance = 1 / Math.sqrt(distance);
            x *= distance;
            y *= distance;
            z *= distance;

            double xScale = x * 100;
            double yScale = y * 100;
            double zScale = z * 100;

            double theta = Math.atan2(x, z);
            double sinTheta = Math.sin(theta);
            double cosTheta = Math.cos(theta);

            double phi = Math.atan2(Math.sqrt(x * x + z * z), y);
            double sinPhi = Math.sin(phi);
            double cosPhi = Math.cos(phi);

            double rot = random.nextDouble() * Math.PI * 2;
            double sinRot = Math.sin(rot);
            double cosRot = Math.cos(rot);

            int color = renderer.starColors().getRandom(random).map(WeightedEntry.Wrapper::getData).orElse(0xffffffff);

            for (int j = 0; j < 4; j++) {
                double xOffset = ((j & 2) - 1) * scale;
                double yOffset = ((j + 1 & 2) - 1) * scale;

                double rotatedX = xOffset * cosRot - yOffset * sinRot;
                double rotatedY = yOffset * cosRot + xOffset * sinRot;

                double transformedX = rotatedX * sinPhi;
                double transformedY = -rotatedX * cosPhi;

                builder.vertex(
                        xScale + transformedY * sinTheta - rotatedY * cosTheta,
                        yScale + transformedX,
                        zScale + rotatedY * sinTheta + transformedY * cosTheta)
                    .color(color)
                    .endVertex();
            }
        }

        return builder.end();
    }
}
