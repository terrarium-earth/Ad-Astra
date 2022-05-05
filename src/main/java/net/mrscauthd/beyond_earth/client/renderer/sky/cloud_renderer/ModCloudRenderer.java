package net.mrscauthd.beyond_earth.client.renderer.sky.cloud_renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.mrscauthd.beyond_earth.mixin.WorldRendererAccessor;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

@Environment(EnvType.CLIENT)
public class ModCloudRenderer implements DimensionRenderingRegistry.CloudRenderer {
        private static final Identifier VENUS_CLOUD_TEXTURE = new ModIdentifier("textures/sky/clouds.png");
        private Identifier texture;

        public ModCloudRenderer withVenus() {
                texture = VENUS_CLOUD_TEXTURE;
                return this;
        }

        @Override
        public void render(WorldRenderContext context) {

                Vec3d cameraPos = context.camera().getPos();
                double cameraPosX = cameraPos.getX();
                double cameraPosY = cameraPos.getY();
                double cameraPosZ = cameraPos.getZ();

                WorldRendererAccessor renderer = (WorldRendererAccessor) context.worldRenderer();
                MatrixStack matrices = context.matrixStack();
                float tickDelta = context.tickDelta();
                MinecraftClient client = MinecraftClient.getInstance();
                float g = context.world().getDimensionEffects().getCloudsHeight();
                if (!Float.isNaN(g)) {
                        RenderSystem.disableCull();
                        RenderSystem.enableBlend();
                        RenderSystem.enableDepthTest();
                        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE,
                                        GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
                        RenderSystem.depthMask(true);
                        double k = ((float) renderer.getTicks() + tickDelta) * 0.03f;
                        double l = (cameraPosX + k) / 12.0;
                        double m = g - (float) cameraPosY + 0.33f;
                        double n = cameraPosZ / 12.0 + 0.33000001311302185;
                        l -= MathHelper.floor(l / 2048.0) * 2048;
                        n -= MathHelper.floor(n / 2048.0) * 2048;
                        float o = (float) (l - (double) MathHelper.floor(l));
                        float p = (float) (m / 4.0 - (double) MathHelper.floor(m / 4.0)) * 4.0f;
                        float q = (float) (n - (double) MathHelper.floor(n));
                        Vec3d vec3d = context.world().getCloudsColor(tickDelta);
                        int r = (int) Math.floor(l);
                        int s = (int) Math.floor(m / 4.0);
                        int t = (int) Math.floor(n);
                        if (r != renderer.getLastCloudsBlockX() || s != renderer.getLastCloudsBlockY() || t != renderer.getLastCloudsBlockZ()
                                        || client.options.getCloudRenderMode() != renderer.getLastCloudsRenderMode() || renderer.getLastCloudsColor().squaredDistanceTo(vec3d) > 2.0E-4) {
                                renderer.setLastCloudsBlockX(r);
                                renderer.setLastCloudsBlockY(s);
                                renderer.setLastCloudsBlockZ(t);
                                renderer.setLastCloudsColor(vec3d);
                                renderer.setLastCloudsRenderMode(client.options.getCloudRenderMode());
                                renderer.setCloudsDirty(true);
                        }

                        if (renderer.getCloudsDirty()) {
                                renderer.setCloudsDirty(false);
                                BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                                if (renderer.getCloudsBuffer() != null) {
                                        renderer.getCloudsBuffer().close();
                                }

                                renderer.setCloudsBuffer(new VertexBuffer());
                                renderClouds(renderer, bufferBuilder, l, m, n, vec3d);
                                bufferBuilder.end();
                                renderer.getCloudsBuffer().upload(bufferBuilder);
                        }

                        RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
                        RenderSystem.setShaderTexture(0, texture);
                        BackgroundRenderer.setFogBlack();
                        matrices.push();
                        matrices.scale(12.0f, 1.0f, 12.0f);
                        matrices.translate(-o, p, -q);
                        if (renderer.getCloudsBuffer() != null) {
                                int u = renderer.getLastCloudsRenderMode() == CloudRenderMode.FANCY ? 0 : 1;

                                for (int v = u; v < 2; ++v) {
                                        if (v == 0) {
                                                RenderSystem.colorMask(false, false, false, false);
                                        } else {
                                                RenderSystem.colorMask(true, true, true, true);
                                        }

                                        Shader shader = RenderSystem.getShader();
                                        renderer.getCloudsBuffer().setShader(matrices.peek().getPositionMatrix(), context.projectionMatrix(), shader);
                                }
                        }

                        matrices.pop();
                        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
                        RenderSystem.enableCull();
                        RenderSystem.disableBlend();
                }
        }

        private void renderClouds(WorldRendererAccessor renderer, BufferBuilder builder, double x, double y, double z, Vec3d color) {
                float k = (float) MathHelper.floor(x) * 0.00390625f;
                float l = (float) MathHelper.floor(z) * 0.00390625f;
                float m = (float) color.x;
                float n = (float) color.y;
                float o = (float) color.z;
                float p = m * 0.9f;
                float q = n * 0.9f;
                float r = o * 0.9f;
                float s = m * 0.7f;
                float t = n * 0.7f;
                float u = o * 0.7f;
                float v = m * 0.8f;
                float w = n * 0.8f;
                float aa = o * 0.8f;
                RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
                builder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_NORMAL);
                float ab = (float) Math.floor(y / 4.0) * 4.0f;
                if (renderer.getLastCloudsRenderMode().equals(CloudRenderMode.FANCY)) {
                        for (int ac = -3; ac <= 4; ++ac) {
                                for (int ad = -3; ad <= 4; ++ad) {
                                        int ag;
                                        float ae = ac * 8;
                                        float af = ad * 8;
                                        if (ab > -5.0f) {
                                                builder.vertex(ae + 0.0f, ab + 0.0f, af + 8.0f).texture((ae + 0.0f) * 0.00390625f + k, (af + 8.0f) * 0.00390625f + l).color(s, t, u, 0.8f)
                                                                .normal(0.0f, -1.0f, 0.0f).next();
                                                builder.vertex(ae + 8.0f, ab + 0.0f, af + 8.0f).texture((ae + 8.0f) * 0.00390625f + k, (af + 8.0f) * 0.00390625f + l).color(s, t, u, 0.8f)
                                                                .normal(0.0f, -1.0f, 0.0f).next();
                                                builder.vertex(ae + 8.0f, ab + 0.0f, af + 0.0f).texture((ae + 8.0f) * 0.00390625f + k, (af + 0.0f) * 0.00390625f + l).color(s, t, u, 0.8f)
                                                                .normal(0.0f, -1.0f, 0.0f).next();
                                                builder.vertex(ae + 0.0f, ab + 0.0f, af + 0.0f).texture((ae + 0.0f) * 0.00390625f + k, (af + 0.0f) * 0.00390625f + l).color(s, t, u, 0.8f)
                                                                .normal(0.0f, -1.0f, 0.0f).next();
                                        }
                                        if (ab <= 5.0f) {
                                                builder.vertex(ae + 0.0f, ab + 4.0f - 9.765625E-4f, af + 8.0f).texture((ae + 0.0f) * 0.00390625f + k, (af + 8.0f) * 0.00390625f + l)
                                                                .color(m, n, o, 0.8f).normal(0.0f, 1.0f, 0.0f).next();
                                                builder.vertex(ae + 8.0f, ab + 4.0f - 9.765625E-4f, af + 8.0f).texture((ae + 8.0f) * 0.00390625f + k, (af + 8.0f) * 0.00390625f + l)
                                                                .color(m, n, o, 0.8f).normal(0.0f, 1.0f, 0.0f).next();
                                                builder.vertex(ae + 8.0f, ab + 4.0f - 9.765625E-4f, af + 0.0f).texture((ae + 8.0f) * 0.00390625f + k, (af + 0.0f) * 0.00390625f + l)
                                                                .color(m, n, o, 0.8f).normal(0.0f, 1.0f, 0.0f).next();
                                                builder.vertex(ae + 0.0f, ab + 4.0f - 9.765625E-4f, af + 0.0f).texture((ae + 0.0f) * 0.00390625f + k, (af + 0.0f) * 0.00390625f + l)
                                                                .color(m, n, o, 0.8f).normal(0.0f, 1.0f, 0.0f).next();
                                        }
                                        if (ac > -1) {
                                                for (ag = 0; ag < 8; ++ag) {
                                                        builder.vertex(ae + (float) ag + 0.0f, ab + 0.0f, af + 8.0f).texture((ae + (float) ag + 0.5f) * 0.00390625f + k, (af + 8.0f) * 0.00390625f + l)
                                                                        .color(p, q, r, 0.8f).normal(-1.0f, 0.0f, 0.0f).next();
                                                        builder.vertex(ae + (float) ag + 0.0f, ab + 4.0f, af + 8.0f).texture((ae + (float) ag + 0.5f) * 0.00390625f + k, (af + 8.0f) * 0.00390625f + l)
                                                                        .color(p, q, r, 0.8f).normal(-1.0f, 0.0f, 0.0f).next();
                                                        builder.vertex(ae + (float) ag + 0.0f, ab + 4.0f, af + 0.0f).texture((ae + (float) ag + 0.5f) * 0.00390625f + k, (af + 0.0f) * 0.00390625f + l)
                                                                        .color(p, q, r, 0.8f).normal(-1.0f, 0.0f, 0.0f).next();
                                                        builder.vertex(ae + (float) ag + 0.0f, ab + 0.0f, af + 0.0f).texture((ae + (float) ag + 0.5f) * 0.00390625f + k, (af + 0.0f) * 0.00390625f + l)
                                                                        .color(p, q, r, 0.8f).normal(-1.0f, 0.0f, 0.0f).next();
                                                }
                                        }
                                        if (ac <= 1) {
                                                for (ag = 0; ag < 8; ++ag) {
                                                        builder.vertex(ae + (float) ag + 1.0f - 9.765625E-4f, ab + 0.0f, af + 8.0f)
                                                                        .texture((ae + (float) ag + 0.5f) * 0.00390625f + k, (af + 8.0f) * 0.00390625f + l).color(p, q, r, 0.8f)
                                                                        .normal(1.0f, 0.0f, 0.0f).next();
                                                        builder.vertex(ae + (float) ag + 1.0f - 9.765625E-4f, ab + 4.0f, af + 8.0f)
                                                                        .texture((ae + (float) ag + 0.5f) * 0.00390625f + k, (af + 8.0f) * 0.00390625f + l).color(p, q, r, 0.8f)
                                                                        .normal(1.0f, 0.0f, 0.0f).next();
                                                        builder.vertex(ae + (float) ag + 1.0f - 9.765625E-4f, ab + 4.0f, af + 0.0f)
                                                                        .texture((ae + (float) ag + 0.5f) * 0.00390625f + k, (af + 0.0f) * 0.00390625f + l).color(p, q, r, 0.8f)
                                                                        .normal(1.0f, 0.0f, 0.0f).next();
                                                        builder.vertex(ae + (float) ag + 1.0f - 9.765625E-4f, ab + 0.0f, af + 0.0f)
                                                                        .texture((ae + (float) ag + 0.5f) * 0.00390625f + k, (af + 0.0f) * 0.00390625f + l).color(p, q, r, 0.8f)
                                                                        .normal(1.0f, 0.0f, 0.0f).next();
                                                }
                                        }
                                        if (ad > -1) {
                                                for (ag = 0; ag < 8; ++ag) {
                                                        builder.vertex(ae + 0.0f, ab + 4.0f, af + (float) ag + 0.0f).texture((ae + 0.0f) * 0.00390625f + k, (af + (float) ag + 0.5f) * 0.00390625f + l)
                                                                        .color(v, w, aa, 0.8f).normal(0.0f, 0.0f, -1.0f).next();
                                                        builder.vertex(ae + 8.0f, ab + 4.0f, af + (float) ag + 0.0f).texture((ae + 8.0f) * 0.00390625f + k, (af + (float) ag + 0.5f) * 0.00390625f + l)
                                                                        .color(v, w, aa, 0.8f).normal(0.0f, 0.0f, -1.0f).next();
                                                        builder.vertex(ae + 8.0f, ab + 0.0f, af + (float) ag + 0.0f).texture((ae + 8.0f) * 0.00390625f + k, (af + (float) ag + 0.5f) * 0.00390625f + l)
                                                                        .color(v, w, aa, 0.8f).normal(0.0f, 0.0f, -1.0f).next();
                                                        builder.vertex(ae + 0.0f, ab + 0.0f, af + (float) ag + 0.0f).texture((ae + 0.0f) * 0.00390625f + k, (af + (float) ag + 0.5f) * 0.00390625f + l)
                                                                        .color(v, w, aa, 0.8f).normal(0.0f, 0.0f, -1.0f).next();
                                                }
                                        }
                                        if (ad > 1)
                                                continue;
                                        for (ag = 0; ag < 8; ++ag) {
                                                builder.vertex(ae + 0.0f, ab + 4.0f, af + (float) ag + 1.0f - 9.765625E-4f)
                                                                .texture((ae + 0.0f) * 0.00390625f + k, (af + (float) ag + 0.5f) * 0.00390625f + l).color(v, w, aa, 0.8f).normal(0.0f, 0.0f, 1.0f)
                                                                .next();
                                                builder.vertex(ae + 8.0f, ab + 4.0f, af + (float) ag + 1.0f - 9.765625E-4f)
                                                                .texture((ae + 8.0f) * 0.00390625f + k, (af + (float) ag + 0.5f) * 0.00390625f + l).color(v, w, aa, 0.8f).normal(0.0f, 0.0f, 1.0f)
                                                                .next();
                                                builder.vertex(ae + 8.0f, ab + 0.0f, af + (float) ag + 1.0f - 9.765625E-4f)
                                                                .texture((ae + 8.0f) * 0.00390625f + k, (af + (float) ag + 0.5f) * 0.00390625f + l).color(v, w, aa, 0.8f).normal(0.0f, 0.0f, 1.0f)
                                                                .next();
                                                builder.vertex(ae + 0.0f, ab + 0.0f, af + (float) ag + 1.0f - 9.765625E-4f)
                                                                .texture((ae + 0.0f) * 0.00390625f + k, (af + (float) ag + 0.5f) * 0.00390625f + l).color(v, w, aa, 0.8f).normal(0.0f, 0.0f, 1.0f)
                                                                .next();
                                        }
                                }
                        }
                } else {
                        for (int ah = -32; ah < 32; ah += 32) {
                                for (int ai = -32; ai < 32; ai += 32) {
                                        builder.vertex(ah, ab, ai + 32).texture((float) (ah) * 0.00390625f + k, (float) (ai + 32) * 0.00390625f + l).color(m, n, o, 0.8f).normal(0.0f, -1.0f, 0.0f)
                                                        .next();
                                        builder.vertex(ah + 32, ab, ai + 32).texture((float) (ah + 32) * 0.00390625f + k, (float) (ai + 32) * 0.00390625f + l).color(m, n, o, 0.8f)
                                                        .normal(0.0f, -1.0f, 0.0f).next();
                                        builder.vertex(ah + 32, ab, ai).texture((float) (ah + 32) * 0.00390625f + k, (float) (ai) * 0.00390625f + l).color(m, n, o, 0.8f).normal(0.0f, -1.0f, 0.0f)
                                                        .next();
                                        builder.vertex(ah, ab, ai).texture((float) (ah) * 0.00390625f + k, (float) (ai) * 0.00390625f + l).color(m, n, o, 0.8f).normal(0.0f, -1.0f, 0.0f).next();
                                }
                        }
                }
        }
}