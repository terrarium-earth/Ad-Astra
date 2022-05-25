package com.github.alexnijjar.beyond_earth.client.renderer.sky.cloud_renderer;

import com.github.alexnijjar.beyond_earth.mixin.WorldRendererAccessor;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
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
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

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
                        RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ONE_MINUS_SRC_ALPHA);
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
                        Vec3d colour = context.world().getCloudsColor(tickDelta);
                        int r = (int) Math.floor(l);
                        int s = (int) Math.floor(m / 4.0);
                        int t = (int) Math.floor(n);
                        if (r != renderer.getLastCloudsBlockX() || s != renderer.getLastCloudsBlockY() || t != renderer.getLastCloudsBlockZ() || client.options.getCloudRenderMode() != renderer.getLastCloudsRenderMode()
                                        || renderer.getLastCloudsColor().squaredDistanceTo(colour) > 2.0E-4) {
                                renderer.setLastCloudsBlockX(r);
                                renderer.setLastCloudsBlockY(s);
                                renderer.setLastCloudsBlockZ(t);
                                renderer.setLastCloudsColor(colour);
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
                                renderer.invokeRenderClouds(bufferBuilder, l, m, n, colour);
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
}