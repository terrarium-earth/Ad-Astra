package net.mrscauthd.boss_tools.skyrenderer;

import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.world.DimensionRenderInfo.FogType;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.mrscauthd.boss_tools.BossToolsMod;

import ResourceLocation;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusMars {
	private static final ResourceLocation DIM_RENDER_INFO = new ResourceLocation(BossToolsMod.ModId, "mars");
	private static final ResourceLocation SUN_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/sky/sun.png");
	private static final ResourceLocation PHOBOS_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/sky/phobos.png");
	private static final ResourceLocation DEIMOS_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/sky/deimos.png");
	private static final ResourceLocation EARTH_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/sky/earth.png");
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void clientSetup(FMLClientSetupEvent event) {
		DimensionRenderInfo.field_239208_a_.put(DIM_RENDER_INFO, new DimensionRenderInfo(128, false, FogType.NORMAL, false, false) {
			@Override
			public Vector3d func_230494_a_(Vector3d color, float sunHeight) {
				return new Vector3d(0.647058823529, 0.450980392157, 0.254901960784);
			}

			@Override
			public boolean func_230493_a_(int posX, int posY) {
				return true;
			}

			@Override
			public ICloudRenderHandler getCloudRenderHandler() {
				return new ICloudRenderHandler() {
					@Override
					public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {

					}
				};
			}

			@Override
			public ISkyRenderHandler getSkyRenderHandler() {
				return new ISkyRenderHandler() {
					@SuppressWarnings({"deprecation"})
					@Override
					public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc) {
						RenderSystem.disableTexture();
						Vector3d vector3d = world.getSkyColor(mc.gameRenderer.getActiveRenderInfo().getBlockPos(), partialTicks);
						float f = (float) vector3d.x;
						float f1 = (float) vector3d.y;
						float f2 = (float) vector3d.z;
						FogRenderer.applyFog();
						BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
						RenderSystem.depthMask(false);
						RenderSystem.enableFog();
						RenderSystem.color3f(0.81960785f, 0.54509807f, 0.32156864f);
						mc.worldRenderer.skyVBO.bindBuffer();
						mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
						mc.worldRenderer.skyVBO.draw(matrixStack.getLast().getMatrix(), 7);
						VertexBuffer.unbindBuffer();
						mc.worldRenderer.skyVertexFormat.clearBufferState();
						Matrix4f matrix4f1 = matrixStack.getLast().getMatrix();
						RenderSystem.enableAlphaTest();
						RenderSystem.enableTexture();
						RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
						RenderSystem.color4f(1f, 1f, 1f, 1f);
						RenderSystem.disableTexture();
						RenderSystem.disableFog();
						RenderSystem.disableAlphaTest();
						RenderSystem.enableBlend();
						RenderSystem.defaultBlendFunc();
						float[] afloat = world.func_239132_a_().func_230492_a_(world.func_242415_f(partialTicks), partialTicks);
						if (afloat != null) {
							RenderSystem.disableTexture();
							RenderSystem.shadeModel(7425);
							matrixStack.push();
							matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
							float f3 = MathHelper.sin(world.getCelestialAngleRadians(partialTicks)) < 0.0F ? 180.0F : 0.0F;
							matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3));
							matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));
							float f4 = afloat[0];
							float f5 = afloat[1];
							float f6 = afloat[2];
							Matrix4f matrix4f = matrixStack.getLast().getMatrix();
							bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
							bufferbuilder.pos(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();
							for (int j = 0; j <= 16; ++j) {
								float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
								float f8 = MathHelper.sin(f7);
								float f9 = MathHelper.cos(f7);
								bufferbuilder.pos(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
							}
							bufferbuilder.finishDrawing();
							WorldVertexBufferUploader.draw(bufferbuilder);
							matrixStack.pop();
							RenderSystem.shadeModel(7424);
						}
						RenderSystem.enableTexture();
						RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
						matrixStack.push();
						RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

						matrixStack.rotate(Vector3f.YP.rotationDegrees(-90.0F));
						matrixStack.rotate(Vector3f.XP.rotationDegrees(world.func_242415_f(partialTicks) * 360.0F));
						matrix4f1 = matrixStack.getLast().getMatrix();

						float f12 = 20.0F;

						//SUN
						mc.getTextureManager().bindTexture(SUN_TEXTURE);
						bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
						bufferbuilder.pos(matrix4f1, -f12, 100.0F, -f12).tex(0.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, f12, 100.0F, -f12).tex(1.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, f12, 100.0F, f12).tex(1.0F, 1.0F).endVertex();
						bufferbuilder.pos(matrix4f1, -f12, 100.0F, f12).tex(0.0F, 1.0F).endVertex();
						bufferbuilder.finishDrawing();
						WorldVertexBufferUploader.draw(bufferbuilder);

						//PHOBOS
						matrixStack.rotate(Vector3f.YP.rotationDegrees(-130.0F));
						matrixStack.rotate(Vector3f.ZP.rotationDegrees(100.0F));

						mc.getTextureManager().bindTexture(PHOBOS_TEXTURE);
						bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
						bufferbuilder.pos(matrix4f1, -3, -100.0F, 3).tex(0.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, 3, -100.0F, 3).tex(1.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, 3, -100.0F, -3).tex(1.0F, 1.0F).endVertex();
						bufferbuilder.pos(matrix4f1, -3, -100.0F, -3).tex(0.0F, 1.0F).endVertex();
						bufferbuilder.finishDrawing();
						WorldVertexBufferUploader.draw(bufferbuilder);

						matrixStack.rotate(Vector3f.YP.rotationDegrees(-130.0F));
						matrixStack.rotate(Vector3f.ZP.rotationDegrees(210.0F));

						//EARTH
						mc.getTextureManager().bindTexture(EARTH_TEXTURE);
						bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
						bufferbuilder.pos(matrix4f1, -1, -100.0F, 1).tex(0.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, 1, -100.0F, 1).tex(1.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, 1, -100.0F, -1).tex(1.0F, 1.0F).endVertex();
						bufferbuilder.pos(matrix4f1, -1, -100.0F, -1).tex(0.0F, 1.0F).endVertex();
						bufferbuilder.finishDrawing();
						WorldVertexBufferUploader.draw(bufferbuilder);

						matrixStack.rotate(Vector3f.YP.rotationDegrees(-110.0F));
						matrixStack.rotate(Vector3f.ZP.rotationDegrees(90.0F));

						//DEIMOS
						mc.getTextureManager().bindTexture(DEIMOS_TEXTURE);
						bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
						bufferbuilder.pos(matrix4f1, -4, -100.0F, 4).tex(0.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, 4, -100.0F, 4).tex(1.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, 4, -100.0F, -4).tex(1.0F, 1.0F).endVertex();
						bufferbuilder.pos(matrix4f1, -4, -100.0F, -4).tex(0.0F, 1.0F).endVertex();
						bufferbuilder.finishDrawing();
						WorldVertexBufferUploader.draw(bufferbuilder);

						RenderSystem.disableTexture();

						RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.worldRenderer.starVBO.bindBuffer();
						mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
						mc.worldRenderer.starVBO.draw(matrixStack.getLast().getMatrix(), 7);
						VertexBuffer.unbindBuffer();
						mc.worldRenderer.skyVertexFormat.clearBufferState();

						RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
						RenderSystem.disableBlend();
						RenderSystem.enableAlphaTest();
						RenderSystem.enableFog();
						matrixStack.pop();
						RenderSystem.disableTexture();
						RenderSystem.color3f(0.0F, 0.0F, 0.0F);

						double d0 = mc.player.getEyePosition(partialTicks).y - world.getWorldInfo().getVoidFogHeight();

						if (d0 < 0.0D) {
							matrixStack.push();
							matrixStack.translate(0.0D, 12.0D, 0.0D);
							mc.worldRenderer.sky2VBO.bindBuffer();
							mc.worldRenderer.skyVertexFormat.setupBufferState(0L);
							mc.worldRenderer.sky2VBO.draw(matrixStack.getLast().getMatrix(), 7);
							VertexBuffer.unbindBuffer();
							mc.worldRenderer.skyVertexFormat.clearBufferState();
							matrixStack.pop();
						}
						if (world.func_239132_a_().func_239216_b_()) {
							RenderSystem.color3f(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
						} else {
							RenderSystem.color3f(f, f1, f2);
						}
						RenderSystem.enableTexture();
						RenderSystem.depthMask(true);
						RenderSystem.disableFog();
					}
				};
			}
		});
	}
}
