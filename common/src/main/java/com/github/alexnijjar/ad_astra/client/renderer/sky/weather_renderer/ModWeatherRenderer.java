package com.github.alexnijjar.ad_astra.client.renderer.sky.weather_renderer;

import java.util.Random;

import com.github.alexnijjar.ad_astra.mixin.client.WorldRendererAccessor;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

@Environment(EnvType.CLIENT)
public class ModWeatherRenderer implements DimensionRenderingRegistry.WeatherRenderer {

	private static final Identifier VENUS_RAIN_TEXTURE = new ModIdentifier("textures/sky/venus/rain.png");

	private Identifier texture;

	public ModWeatherRenderer withVenus() {
		texture = VENUS_RAIN_TEXTURE;
		return this;
	}

	@Override
	public void render(WorldRenderContext context) {

		Vec3d cameraPos = context.camera().getPos();
		double cameraPosX = cameraPos.getX();
		double cameraPosY = cameraPos.getY();
		double cameraPosZ = cameraPos.getZ();

		WorldRendererAccessor renderer = (WorldRendererAccessor) context.worldRenderer();
		float tickDelta = context.tickDelta();
		MinecraftClient client = MinecraftClient.getInstance();

		float h = client.world.getRainGradient(tickDelta);
		if (!(h <= 0.0f)) {
			context.lightmapTextureManager().enable();
			World world = client.world;
			int i = MathHelper.floor(cameraPosX);
			int j = MathHelper.floor(cameraPosY);
			int k = MathHelper.floor(cameraPosZ);
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder bufferBuilder = tessellator.getBuffer();
			RenderSystem.disableCull();
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			int l = 5;
			if (MinecraftClient.isFancyGraphicsOrBetter()) {
				l = 10;
			}

			RenderSystem.depthMask(MinecraftClient.isFabulousGraphicsOrBetter());
			int m = -1;
			RenderSystem.setShader(GameRenderer::getParticleShader);
			RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
			BlockPos.Mutable mutable = new BlockPos.Mutable();

			for (int o = k - l; o <= k + l; ++o) {
				for (int p = i - l; p <= i + l; ++p) {
					int q = (o - k + 16) * 32 + p - i + 16;
					double r = (double) renderer.getRainSizeX()[q] * 0.5;
					double s = (double) renderer.getRainSizeZ()[q] * 0.5;
					mutable.set(p, cameraPosY, o);
					Biome biome = world.getBiome(mutable).value();
					if (biome.getPrecipitation() != Biome.Precipitation.NONE) {
						int t = world.getTopY(Heightmap.Type.MOTION_BLOCKING, p, o);
						int u = j - l;
						int v = j + l;
						if (u < t) {
							u = t;
						}

						if (v < t) {
							v = t;
						}

						int w = Math.max(t, j);

						if (u != v) {
							Random random = new Random((long) p * p * 3121 + p * 45238971L ^ (long) o * o * 418711 + o * 13761L);
							mutable.set(p, u, o);
							float y;
							float ac;
							if (biome.doesNotSnow(mutable)) {
								if (m != 0) {

									m = 0;
									RenderSystem.setShaderTexture(0, texture);
									bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR_LIGHT);
								}

								int x = renderer.getTicks() + p * p * 3121 + p * 45238971 + o * o * 418711 + o * 13761 & 31;
								y = -((float) x + tickDelta) / 32.0f * (3.0f + random.nextFloat());
								double z = (double) p + 0.5 - cameraPosX;
								double aa = (double) o + 0.5 - cameraPosZ;
								float ab = (float) Math.sqrt(z * z + aa * aa) / (float) l;
								ac = ((1.0f - ab * ab) * 0.5f + 0.5f) * h;
								mutable.set(p, w, o);
								int ad = WorldRenderer.getLightmapCoordinates(world, mutable);
								bufferBuilder.vertex((double) p - cameraPosX - r + 0.5, (double) v - cameraPosY, (double) o - cameraPosZ - s + 0.5).texture(0.0f, (float) u * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).light(ad).next();
								bufferBuilder.vertex((double) p - cameraPosX + r + 0.5, (double) v - cameraPosY, (double) o - cameraPosZ + s + 0.5).texture(1.0f, (float) u * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).light(ad).next();
								bufferBuilder.vertex((double) p - cameraPosX + r + 0.5, (double) u - cameraPosY, (double) o - cameraPosZ + s + 0.5).texture(1.0f, (float) v * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).light(ad).next();
								bufferBuilder.vertex((double) p - cameraPosX - r + 0.5, (double) u - cameraPosY, (double) o - cameraPosZ - s + 0.5).texture(0.0f, (float) v * 0.25f + y).color(1.0f, 1.0f, 1.0f, ac).light(ad).next();
							}
						}
					}
				}
			}

			if (m >= 0) {
				tessellator.draw();
			}

			RenderSystem.enableCull();
			RenderSystem.disableBlend();
			context.lightmapTextureManager().disable();
		}
	}
}