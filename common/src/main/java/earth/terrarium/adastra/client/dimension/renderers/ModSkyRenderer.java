package earth.terrarium.adastra.client.dimension.renderers;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Axis;
import earth.terrarium.adastra.client.dimension.MovementType;
import earth.terrarium.adastra.client.dimension.SkyRenderable;
import earth.terrarium.adastra.client.utils.DimensionUtils;
import earth.terrarium.adastra.mixins.client.LevelRendererAccessor;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.material.FogType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

import java.util.List;
import java.util.function.BiFunction;

public class ModSkyRenderer {
    private final int stars;
    private final BiFunction<ClientLevel, Float, Float> starBrightness;
    private final SimpleWeightedRandomList<Integer> starColors;
    private final List<SkyRenderable> skyRenderables;

    @Nullable
    private VertexBuffer starBuffer;

    public ModSkyRenderer(
        int stars,
        BiFunction<ClientLevel, Float, Float> starBrightness,
        SimpleWeightedRandomList<Integer> starColors,
        List<SkyRenderable> skyRenderables
    ) {
        this.stars = stars;
        this.starBrightness = starBrightness;
        this.starColors = starColors;
        this.skyRenderables = skyRenderables;
    }

    public void render(ClientLevel level, float partialTick, PoseStack poseStack, Camera camera, Matrix4f projectionMatrix, boolean isFoggy, Runnable setupFog) {
        setupFog.run();
        if (isFoggy || inFog(camera)) return;
        if (starBuffer == null) createStars();

        setSkyColor(level, camera, partialTick);

        RenderSystem.depthMask(false);
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        poseStack.pushPose();

        renderStars(level, partialTick, poseStack, projectionMatrix, setupFog);

        RenderSystem.disableBlend();

        BufferBuilder bufferBuilder = Tesselator.getInstance().getBuilder();
        skyRenderables.forEach(renderable -> {
            var globalRotation = renderable.movementType() == MovementType.STATIC ?
                renderable.globalRotation() :
                renderable.globalRotation().add(0, 0, level.getTimeOfDay(partialTick) * 360 - 90);

            renderSkyRenderable(bufferBuilder, poseStack, renderable.localRotation(), globalRotation, renderable.scale(), renderable.texture());
            if (renderable.backLightScale() > 0) {
                setSkyRenderableColor(level, partialTick, renderable.backLightColor());
                RenderSystem.enableBlend();
                renderSkyRenderable(bufferBuilder, poseStack, renderable.localRotation(), globalRotation, renderable.backLightScale(), DimensionUtils.BACKLIGHT);
                RenderSystem.setShaderColor(1, 1, 1, 1);
                RenderSystem.disableBlend();
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

    public void renderSkyRenderable(BufferBuilder bufferBuilder, PoseStack poseStack, Vec3 localRotation, Vec3 globalRotation, float scale, ResourceLocation texture) {
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
//        float a = 1 - level.getRainLevel(partialTick); // TODO
        RenderSystem.setShaderColor(r, g, b, 1);
    }

    public void renderStars(ClientLevel level, float partialTick, PoseStack poseStack, Matrix4f projectionMatrix, Runnable setupFog) {
        float starLight = starBrightness.apply(level, partialTick);
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

        for (int i = 0; i < stars; i++) {
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

            int color = starColors.getRandomValue(random).orElse(0xffffffff);

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
