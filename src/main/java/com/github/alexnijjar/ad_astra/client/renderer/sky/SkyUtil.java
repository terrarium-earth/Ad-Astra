package com.github.alexnijjar.ad_astra.client.renderer.sky;

import com.github.alexnijjar.ad_astra.client.resourcepack.SkyRenderer;
import com.github.alexnijjar.ad_astra.client.resourcepack.SkyRenderer.StarsRenderer;
import com.github.alexnijjar.ad_astra.mixin.client.WorldRendererAccessor;
import com.github.alexnijjar.ad_astra.util.ColourHolder;
import com.github.alexnijjar.ad_astra.world.WorldSeed;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
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

import java.util.Random;

@Environment(EnvType.CLIENT)
public class SkyUtil {

	public static float getScale() {
		MinecraftClient client = MinecraftClient.getInstance();
		float distance = (float) (-3000.0f + client.player.getY() * 4.5f);
		float scale = 100 * (0.2f - distance / 10000.0f);
		return Math.max(scale, 0.5f);
	}

	public static void preRender(WorldRenderContext context, BufferBuilder bufferBuilder, SkyRenderer.SunsetColour colourType, int sunsetAngle, MatrixStack matrices, ClientWorld world, float tickDelta) {
		RenderSystem.disableTexture();
        Vec3d vec3d = world.getSkyColor(context.gameRenderer().getCamera().getPos(), tickDelta);
        float f = (float) vec3d.getX();
        float g = (float) vec3d.getY();
        float h = (float) vec3d.getZ();
        BackgroundRenderer.setFogBlack();
        RenderSystem.depthMask(false);

        RenderSystem.setShaderColor(f, g, h, 1.0f);
        VertexBuffer skyBuffer = ((WorldRendererAccessor) context.worldRenderer()).getLightSkyBuffer();
        skyBuffer.bind();
		((WorldRendererAccessor) context.worldRenderer()).getLightSkyBuffer().setShader(context.matrixStack().peek().getModel(), context.projectionMatrix(), RenderSystem.getShader());
        VertexBuffer.unbind();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

		renderColouring(colourType, bufferBuilder, matrices, world, tickDelta, world.getSkyAngle(tickDelta), sunsetAngle);
        RenderSystem.enableTexture();
        RenderSystem.blendFuncSeparate(GlStateManager.class_4535.SRC_ALPHA, GlStateManager.class_4534.ONE, GlStateManager.class_4535.ONE, GlStateManager.class_4534.ZERO);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}

	public static void postRender(WorldRenderContext context, MatrixStack matrices, ClientWorld world, float tickDelta) {

		Vec3d vec3d = world.getSkyColor(context.gameRenderer().getCamera().getPos(), tickDelta);
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

	private static void startRendering(MatrixStack matrices, Vec3f rotation) {

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
	public static void render(WorldRenderContext context, BufferBuilder bufferBuilder, Identifier texture, ColourHolder colour, Vec3f rotation, float scale, boolean blending) {

		startRendering(context.matrixStack(), rotation);
		RenderSystem.setShader(GameRenderer::getPositionTexColorShader);

		RenderSystem.setShaderColor((int) colour.r() / 255f, (int) colour.g() / 255f, (int) colour.b() / 255f, 1f);

		if (blending) {
			RenderSystem.enableBlend();
		} else {
			RenderSystem.disableBlend();
		}

		Matrix4f positionMatrix = context.matrixStack().peek().getModel();
		RenderSystem.setShaderTexture(0, texture);
		bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);

		bufferBuilder.vertex(positionMatrix, -scale, 100.0f, -scale).texture(1.0f, 0.0f).color((int) colour.r(), (int) colour.g(), (int) colour.b(), 255).next();
        bufferBuilder.vertex(positionMatrix, scale, 100.0f, -scale).texture(0.0f, 0.0f).color((int) colour.r(), (int) colour.g(), (int) colour.b(), 255).next();
        bufferBuilder.vertex(positionMatrix, scale, 100.0f, scale).texture(0.0f, 1.0f).color((int) colour.r(), (int) colour.g(), (int) colour.b(), 255).next();
        bufferBuilder.vertex(positionMatrix, -scale, 100.0f, scale).texture(1.0f, 1.0f).color((int) colour.r(), (int) colour.g(), (int) colour.b(), 255).next();

		bufferBuilder.end();
		BufferRenderer.draw(bufferBuilder);

		endRendering(context.matrixStack());
	}

	public static VertexBuffer renderStars(WorldRenderContext context, BufferBuilder bufferBuilder, VertexBuffer starsBuffer, int stars, StarsRenderer starsRenderer) {

		startRendering(context.matrixStack(), new Vec3f(-30.0f, 0.0f, context.world().getSkyAngle(context.tickDelta()) * 360.0f));
		RenderSystem.setShader(GameRenderer::getPositionColorShader);

		if (starsBuffer != null) {
			starsBuffer.close();
		}

		starsBuffer = new VertexBuffer();
		SkyUtil.renderStars(bufferBuilder, stars, starsRenderer.colouredStars());
		bufferBuilder.end();
		starsBuffer.upload(bufferBuilder);

		if (!starsRenderer.daylightVisible()) {
			float rot = context.world().getStarBrightness(context.tickDelta());
			RenderSystem.setShaderColor(rot, rot, rot, rot);
		} else {
			RenderSystem.setShaderColor(0.8f, 0.8f, 0.8f, 0.8f);
		}

		BackgroundRenderer.setFogBlack();
		starsBuffer.setShader(context.matrixStack().peek().getModel(), RenderSystem.getProjectionMatrix(), GameRenderer.getPositionColorShader());
		context.matrixStack().pop();
		return starsBuffer;
	}

	private static void renderStars(BufferBuilder buffer, int stars, boolean colouredStars) {
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		Random random = new Random(WorldSeed.getSeed());
		buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
		for (int i = 0; i < stars; ++i) {
			double d = random.nextFloat() * 2.0f - 1.0f;
			double e = random.nextFloat() * 2.0f - 1.0f;
			double f = random.nextFloat() * 2.0f - 1.0f;
			double g = 0.15f + random.nextFloat() * 0.01;
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

			int starR = 255;
			int starG = 255;
			int starB = 255;
			if (colouredStars) {
				int colourChannel = random.nextInt(5);
				if (colourChannel == 0) { // Blue
					starR = 204;
					starG = 238;
					starB = 255;
				} else if (colourChannel == 1) { // Purple
					starR = 204;
					starG = 153;
					starB = 255;
				} else if (colourChannel == 2) { // Yellow
					starR = 255;
					starG = 255;
					starB = 153;
				} else if (colourChannel == 3) { // Orange
					starR = 255;
					starG = 204;
					starB = 102;
				} else { // White.
					starR = 255;
					starG = 255;
					starB = 255;
				}
			}

			for (int v = 0; v < 4; ++v) {
				double x = (double) ((v & 2) - 1) * g;
				double y = (double) ((v + 1 & 2) - 1) * g;
				double aa = x * u - y * t;
				double ac = y * u + x * t;
				double ad = aa * q + 0.0 * r;
				double ae = 0.0 * q - aa * r;
				double af = ae * n - ac * o;
				double ah = ac * n + ae * o;

				buffer.vertex(j + af, k + ad, l + ah).color(starR, starG, starB, 255).next();
			}
		}
	}

	public static void renderColouring(SkyRenderer.SunsetColour type, BufferBuilder bufferBuilder, MatrixStack matrices, ClientWorld world, float tickDelta, float skyAngle, int sunsetAngle) {

		float[] fogColours = switch (type) {
			case VANILLA -> world.getSkyProperties().getFogColorOverride(world.getSkyAngle(tickDelta), tickDelta);
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

			Matrix4f matrix4f = matrices.peek().getModel();
			bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
			bufferBuilder.vertex(matrix4f, 0.0f, 100.0f, 0.0f).color(fogColours[0], fogColours[1], fogColours[2], fogColours[3]).next();

			for (int i = 0; i <= 16; ++i) {
				float o = (float) i * MathHelper.TAU / 16.0f;
				float cosine = MathHelper.cos(o);
				bufferBuilder.vertex(matrix4f, MathHelper.sin(o) * 120.0f, cosine * 120.0f, -cosine * 40.0f * fogColours[3]).color(fogColours[0], fogColours[1], fogColours[2], 0.0f).next();
			}
			bufferBuilder.end();
			BufferRenderer.draw(bufferBuilder);
			matrices.pop();
		}
	}
}