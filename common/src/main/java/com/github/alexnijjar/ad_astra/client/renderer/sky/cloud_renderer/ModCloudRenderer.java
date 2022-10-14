package com.github.alexnijjar.ad_astra.client.renderer.sky.cloud_renderer;

import com.github.alexnijjar.ad_astra.client.registry.ClientModSkies;
import com.github.alexnijjar.ad_astra.client.renderer.sky.WorldRenderContext;
import com.github.alexnijjar.ad_astra.mixin.client.WorldRendererAccessor;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tessellator;
import com.mojang.blaze3d.vertex.VertexBuffer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.ShaderProgram;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ModCloudRenderer implements ClientModSkies.CloudRenderer {
	private static final Identifier VENUS_CLOUD_TEXTURE = new ModIdentifier("textures/sky/venus/clouds.png");
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

		float f = context.world().getSkyProperties().getCloudsHeight();
		if (!Float.isNaN(f)) {
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.enableDepthTest();
			RenderSystem.blendFuncSeparate(GlStateManager.class_4535.SRC_ALPHA, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA, GlStateManager.class_4535.ONE, GlStateManager.class_4534.ONE_MINUS_SRC_ALPHA);
			RenderSystem.depthMask(true);
			double e = ((float) renderer.getTicks() + tickDelta) * 0.03f;
			double i = (cameraPosX + e) / 12.0;
			double j = f - (float) cameraPosY + 0.33f;
			double k = cameraPosZ / 12.0 + 0.33f;
			i -= MathHelper.floor(i / 2048.0) * 2048;
			k -= MathHelper.floor(k / 2048.0) * 2048;
			float l = (float) (i - (double) MathHelper.floor(i));
			float m = (float) (j / 4.0 - (double) MathHelper.floor(j / 4.0)) * 4.0f;
			float n = (float) (k - (double) MathHelper.floor(k));
			Vec3d colour = context.world().getCloudsColor(tickDelta);
			int o = (int) Math.floor(i);
			int p = (int) Math.floor(j / 4.0);
			int q = (int) Math.floor(k);
			if (o != renderer.getLastCloudsBlockX() || p != renderer.getLastCloudsBlockY() || q != renderer.getLastCloudsBlockZ() || client.options.getCloudRenderMode() != renderer.getLastCloudsRenderMode() || renderer.getLastCloudsColor().squaredDistanceTo(colour) > 2.0E-4) {
				renderer.setLastCloudsBlockX(o);
				renderer.setLastCloudsBlockY(p);
				renderer.setLastCloudsBlockZ(q);
				renderer.setLastCloudsColor(colour);
				renderer.setLastCloudsRenderMode(client.options.getCloudRenderMode());
				renderer.setCloudsDirty(true);
			}

			if (renderer.getCloudsDirty()) {
				renderer.setCloudsDirty(false);
				BufferBuilder bufferBuilder = Tessellator.getInstance().getBufferBuilder();
				if (renderer.getCloudsBuffer() != null) {
					renderer.getCloudsBuffer().close();
				}

				renderer.setCloudsBuffer(new VertexBuffer());
				BufferBuilder.RenderedBuffer renderedBuffer = renderer.invokeRenderClouds(bufferBuilder, i, j, k, colour);
				renderer.getCloudsBuffer().bind();
				renderer.getCloudsBuffer().upload(renderedBuffer);
				VertexBuffer.unbind();
			}

			RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
			RenderSystem.setShaderTexture(0, texture);
			BackgroundRenderer.setShaderFogColor();
			matrices.push();
			matrices.scale(12.0f, 1.0f, 12.0f);
			matrices.translate(-l, m, -n);
			if (renderer.getCloudsBuffer() != null) {
				renderer.getCloudsBuffer().bind();
				int r = renderer.getLastCloudsRenderMode().equals(CloudRenderMode.FANCY) ? 0 : 1;

				for (int s = r; s < 2; ++s) {
					if (s == 0) {
						RenderSystem.colorMask(false, false, false, false);
					} else {
						RenderSystem.colorMask(true, true, true, true);
					}

					ShaderProgram shaderProgram = RenderSystem.getShader();
					renderer.getCloudsBuffer().setShader(matrices.peek().getModel(), context.projectionMatrix(), shaderProgram);
				}

				VertexBuffer.unbind();
			}

			matrices.pop();
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			RenderSystem.enableCull();
			RenderSystem.disableBlend();
		}
	}
}