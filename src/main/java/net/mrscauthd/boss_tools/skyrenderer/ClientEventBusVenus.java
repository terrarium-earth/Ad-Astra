package net.mrscauthd.boss_tools.skyrenderer;

import net.minecraftforge.client.IWeatherParticleRenderHandler;
import net.minecraftforge.client.IWeatherRenderHandler;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.client.ISkyRenderHandler;
import net.minecraftforge.client.ICloudRenderHandler;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.platform.GlStateManager;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.particle.VenusRainParticle;

import java.util.Random;

//@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusVenus {
	/*
	private static final float[] rainSizeX = new float[1024];
	private static final float[] rainSizeZ = new float[1024];
	private static int rainSoundTime;
	private static int cloudsCheckX = Integer.MIN_VALUE;
	private static int cloudsCheckY = Integer.MIN_VALUE;
	private static int cloudsCheckZ = Integer.MIN_VALUE;
	private static Vector3d cloudsCheckColor = Vector3d.ZERO;
	private static boolean cloudsNeedUpdate = true;
	private static VertexBuffer cloudsVBO;
	private static CloudOption cloudOption;
	private static final ResourceLocation DIM_RENDER_INFO = new ResourceLocation(BossToolsMod.ModId, "venus");
	private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/sky/clouds.png");
	private static final ResourceLocation RAIN_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/sky/rain.png");
	private static final ResourceLocation SUN_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/sky/sun.png");
	private static final ResourceLocation EARTH_TEXTURE = new ResourceLocation(BossToolsMod.ModId, "textures/sky/earth.png");

	public static int getCombinedLight(IBlockDisplayReader lightReaderIn, BlockPos blockPosIn) {
		return getPackedLightmapCoords(lightReaderIn, lightReaderIn.getBlockState(blockPosIn), blockPosIn);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void clientSetup(FMLClientSetupEvent event) {
		DimensionRenderInfo.field_239208_a_.put(DIM_RENDER_INFO, new DimensionRenderInfo(128, false, FogType.NORMAL, false, false) {
			@Override
			public Vector3d func_230494_a_(Vector3d color, float sunHeight) {
				return new Vector3d(0.647058823529, 0.450980392157, 0.254901960784);
			}

			@Override
			public boolean func_230493_a_(int posX, int posY) {
				return true; // eneable fog
			}


			@Override
			public IWeatherRenderHandler getWeatherRenderHandler() {
				return new IWeatherRenderHandler() {

					@Override
					public void render(int ticks, float partialTicks, ClientWorld world, Minecraft mc, LightTexture lightmapIn, double xIn, double yIn, double zIn) {
						for(int i = 0; i < 32; ++i) {
							for(int j = 0; j < 32; ++j) {
								float f = (float)(j - 16);
								float f1 = (float)(i - 16);
								float f2 = MathHelper.sqrt(f * f + f1 * f1);
								rainSizeX[i << 5 | j] = -f1 / f2;
								rainSizeZ[i << 5 | j] = f / f2;
							}
						}

						float f = Minecraft.getInstance().world.getRainStrength(partialTicks);
						if (!(f <= 0.0F)) {
							lightmapIn.enableLightmap();
							int i = MathHelper.floor(xIn);
							int j = MathHelper.floor(yIn);
							int k = MathHelper.floor(zIn);
							Tessellator tessellator = Tessellator.getInstance();
							BufferBuilder bufferbuilder = tessellator.getBuffer();
							RenderSystem.enableAlphaTest();
							RenderSystem.disableCull();
							RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
							RenderSystem.enableBlend();
							RenderSystem.defaultBlendFunc();
							RenderSystem.defaultAlphaFunc();
							RenderSystem.enableDepthTest();
							int l = 5;
							if (Minecraft.isFancyGraphicsEnabled()) {
								l = 10;
							}

							RenderSystem.depthMask(Minecraft.isFabulousGraphicsEnabled());
							int i1 = -1;
							RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
							BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

							for(int j1 = k - l; j1 <= k + l; ++j1) {
								for(int k1 = i - l; k1 <= i + l; ++k1) {
									int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
									double d0 = (double)rainSizeX[l1] * 0.5D;
									double d1 = (double)rainSizeZ[l1] * 0.5D;
									blockpos$mutable.setPos(k1, 0, j1);
									Biome biome = world.getBiome(blockpos$mutable);
									if (biome.getPrecipitation() != Biome.RainType.NONE) {
										int i2 = world.getHeight(Heightmap.Type.MOTION_BLOCKING, blockpos$mutable).getY();
										int j2 = j - l;
										int k2 = j + l;
										if (j2 < i2) {
											j2 = i2;
										}

										if (k2 < i2) {
											k2 = i2;
										}

										int l2 = i2;
										if (i2 < j) {
											l2 = j;
										}

										if (j2 != k2) {
											Random random = new Random((long)(k1 * k1 * 3121 + k1 * 45238971 ^ j1 * j1 * 418711 + j1 * 13761));
											blockpos$mutable.setPos(k1, j2, j1);
											if (i1 != 0) {
												if (i1 >= 0) {
													tessellator.draw();
												}

												i1 = 0;
												mc.getTextureManager().bindTexture(RAIN_TEXTURE);
												bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
											}

											int i3 = ticks + k1 * k1 * 3121 + k1 * 45238971 + j1 * j1 * 418711 + j1 * 13761 & 31;
											float f3 = -((float)i3 + partialTicks) / 32.0F * (3.0F + random.nextFloat());
											double d2 = (double)((float)k1 + 0.5F) - xIn;
											double d4 = (double)((float)j1 + 0.5F) - zIn;
											float f4 = MathHelper.sqrt(d2 * d2 + d4 * d4) / (float)l;
											float f5 = ((1.0F - f4 * f4) * 0.5F + 0.5F) * f;
											blockpos$mutable.setPos(k1, l2, j1);
											int j3 = getCombinedLight(world, blockpos$mutable);
											bufferbuilder.pos((double)k1 - xIn - d0 + 0.5D, (double)k2 - yIn, (double)j1 - zIn - d1 + 0.5D).tex(0.0F, (float)j2 * 0.25F + f3).color(1.0F, 1.0F, 1.0F, f5).lightmap(j3).endVertex();
											bufferbuilder.pos((double)k1 - xIn + d0 + 0.5D, (double)k2 - yIn, (double)j1 - zIn + d1 + 0.5D).tex(1.0F, (float)j2 * 0.25F + f3).color(1.0F, 1.0F, 1.0F, f5).lightmap(j3).endVertex();
											bufferbuilder.pos((double)k1 - xIn + d0 + 0.5D, (double)j2 - yIn, (double)j1 - zIn + d1 + 0.5D).tex(1.0F, (float)k2 * 0.25F + f3).color(1.0F, 1.0F, 1.0F, f5).lightmap(j3).endVertex();
											bufferbuilder.pos((double)k1 - xIn - d0 + 0.5D, (double)j2 - yIn, (double)j1 - zIn - d1 + 0.5D).tex(0.0F, (float)k2 * 0.25F + f3).color(1.0F, 1.0F, 1.0F, f5).lightmap(j3).endVertex();

										}
									}
								}
							}

							if (i1 >= 0) {
								tessellator.draw();
							}

							RenderSystem.enableCull();
							RenderSystem.disableBlend();
							RenderSystem.defaultAlphaFunc();
							RenderSystem.disableAlphaTest();
							lightmapIn.disableLightmap();
						}
					}
				};
			}

			@Override
			public IWeatherParticleRenderHandler getWeatherParticleRenderHandler() {
				return new IWeatherParticleRenderHandler() {
					@Override
					public void render(int ticks, ClientWorld world, Minecraft mc, ActiveRenderInfo activeRenderInfoIn) {
						float f = mc.world.getRainStrength(1.0F) / (Minecraft.isFancyGraphicsEnabled() ? 1.0F : 2.0F);
						if (!(f <= 0.0F)) {
							Random random = new Random((long)ticks * 312987231L);
							IWorldReader iworldreader = mc.world;
							BlockPos blockpos = new BlockPos(activeRenderInfoIn.getProjectedView());
							BlockPos blockpos1 = null;
							int i = (int)(100.0F * f * f) / (mc.gameSettings.particles == ParticleStatus.DECREASED ? 2 : 1);

							for(int j = 0; j < i; ++j) {
								int k = random.nextInt(21) - 10;
								int l = random.nextInt(21) - 10;
								BlockPos blockpos2 = iworldreader.getHeight(Heightmap.Type.MOTION_BLOCKING, blockpos.add(k, 0, l)).down();
								Biome biome = iworldreader.getBiome(blockpos2);
								if (blockpos2.getY() > 0 && blockpos2.getY() <= blockpos.getY() + 10 && blockpos2.getY() >= blockpos.getY() - 10 && biome.getPrecipitation() == Biome.RainType.RAIN && biome.getTemperature(blockpos2) >= 0.15F) {
									blockpos1 = blockpos2;
									if (mc.gameSettings.particles == ParticleStatus.MINIMAL) {
										break;
									}

									double d0 = random.nextDouble();
									double d1 = random.nextDouble();
									BlockState blockstate = iworldreader.getBlockState(blockpos2);
									FluidState fluidstate = iworldreader.getFluidState(blockpos2);
									VoxelShape voxelshape = blockstate.getCollisionShape(iworldreader, blockpos2);
									double d2 = voxelshape.max(Direction.Axis.Y, d0, d1);
									double d3 = (double)fluidstate.getActualHeight(iworldreader, blockpos2);
									double d4 = Math.max(d2, d3);
									IParticleData iparticledata = !fluidstate.isTagged(FluidTags.LAVA) && !blockstate.isIn(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLit(blockstate) ? (IParticleData) ModInnet.VENUS_RAIN_PARTICLE.get() : ParticleTypes.SMOKE;
									mc.world.addParticle(iparticledata, (double)blockpos2.getX() + d0, (double)blockpos2.getY() + d4, (double)blockpos2.getZ() + d1, 0.0D, 0.0D, 0.0D);
								}
							}

							if (blockpos1 != null && random.nextInt(3) < rainSoundTime++) {
								rainSoundTime = 0;
								if (blockpos1.getY() > blockpos.getY() + 1 && iworldreader.getHeight(Heightmap.Type.MOTION_BLOCKING, blockpos).getY() > MathHelper.floor((float)blockpos.getY())) {
									mc.world.playSound(blockpos1, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
								} else {
									mc.world.playSound(blockpos1, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
								}
							}

						}
					}
				};
			}

			@Override
			public ICloudRenderHandler getCloudRenderHandler() {
				return new ICloudRenderHandler() {
					@Override
					public void render(int ticks, float partialTicks, MatrixStack matrixStack, ClientWorld world, Minecraft mc, double viewEntityX, double viewEntityY, double viewEntityZ) {
						float f = world.func_239132_a_().func_239213_a_();
						if (!Float.isNaN(f)) {
							RenderSystem.disableCull();
							RenderSystem.enableBlend();
							RenderSystem.enableAlphaTest();
							RenderSystem.enableDepthTest();
							RenderSystem.defaultAlphaFunc();
							RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
							RenderSystem.enableFog();
							RenderSystem.depthMask(true);
							float f1 = 12.0F;
							float f2 = 4.0F;
							double d0 = 2.0E-4D;
							double d1 = (double)(((float)ticks + partialTicks) * 0.03F);
							double d2 = (viewEntityX + d1) / 12.0D;
							double d3 = (double)(f - (float)viewEntityY + 0.33F);
							double d4 = viewEntityZ / 12.0D + (double)0.33F;
							d2 = d2 - (double)(MathHelper.floor(d2 / 2048.0D) * 2048);
							d4 = d4 - (double)(MathHelper.floor(d4 / 2048.0D) * 2048);
							float f3 = (float)(d2 - (double)MathHelper.floor(d2));
							float f4 = (float)(d3 / 4.0D - (double)MathHelper.floor(d3 / 4.0D)) * 4.0F;
							float f5 = (float)(d4 - (double)MathHelper.floor(d4));
							Vector3d vector3d = world.getCloudColor(partialTicks);
							int i = (int)Math.floor(d2);
							int j = (int)Math.floor(d3 / 4.0D);
							int k = (int)Math.floor(d4);
							if (i != cloudsCheckX || j != cloudsCheckY || k != cloudsCheckZ || Minecraft.getInstance().gameSettings.getCloudOption() != cloudOption || cloudsCheckColor.squareDistanceTo(vector3d) > 2.0E-4D) {
								cloudsCheckX = i;
								cloudsCheckY = j;
								cloudsCheckZ = k;
								cloudsCheckColor = vector3d;
								cloudOption = Minecraft.getInstance().gameSettings.getCloudOption();
								cloudsNeedUpdate = true;
							}

							if (cloudsNeedUpdate) {
								cloudsNeedUpdate = false;
								BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
								if (cloudsVBO != null) {
									cloudsVBO.close();
								}

								cloudsVBO = new VertexBuffer(DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
								this.drawClouds(bufferbuilder, d2, d3, d4, vector3d);
								bufferbuilder.finishDrawing();
								cloudsVBO.upload(bufferbuilder);
							}

							mc.getTextureManager().bindTexture(CLOUD_TEXTURE);
							matrixStack.push();
							matrixStack.scale(12.0F, 1.0F, 12.0F);
							matrixStack.translate((double)(-f3), (double)f4, (double)(-f5));
							if (cloudsVBO != null) {
								cloudsVBO.bindBuffer();
								DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.setupBufferState(0L);
								int i1 = cloudOption == CloudOption.FANCY ? 0 : 1;

								for(int l = i1; l < 2; ++l) {
									if (l == 0) {
										RenderSystem.colorMask(false, false, false, false);
									} else {
										RenderSystem.colorMask(true, true, true, true);
									}

									cloudsVBO.draw(matrixStack.getLast().getMatrix(), 7);
								}

								VertexBuffer.unbindBuffer();
								DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL.clearBufferState();
							}

							matrixStack.pop();
							RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
							RenderSystem.disableAlphaTest();
							RenderSystem.enableCull();
							RenderSystem.disableBlend();
							RenderSystem.disableFog();
						}
					}

					private void drawClouds(BufferBuilder bufferIn, double cloudsX, double cloudsY, double cloudsZ, Vector3d cloudsColor) {
						float f = 4.0F;
						float f1 = 0.00390625F;
						int i = 8;
						int j = 4;
						float f2 = 9.765625E-4F;
						float f3 = (float)MathHelper.floor(cloudsX) * 0.00390625F;
						float f4 = (float)MathHelper.floor(cloudsZ) * 0.00390625F;
						float f5 = (float)cloudsColor.x;
						float f6 = (float)cloudsColor.y;
						float f7 = (float)cloudsColor.z;
						float f8 = f5 * 0.9F;
						float f9 = f6 * 0.9F;
						float f10 = f7 * 0.9F;
						float f11 = f5 * 0.7F;
						float f12 = f6 * 0.7F;
						float f13 = f7 * 0.7F;
						float f14 = f5 * 0.8F;
						float f15 = f6 * 0.8F;
						float f16 = f7 * 0.8F;
						bufferIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
						float f17 = (float)Math.floor(cloudsY / 4.0D) * 4.0F;
						if (cloudOption == CloudOption.FANCY) {
							for(int k = -3; k <= 4; ++k) {
								for(int l = -3; l <= 4; ++l) {
									float f18 = (float)(k * 8);
									float f19 = (float)(l * 8);
									if (f17 > -5.0F) {
										bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
										bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
										bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
										bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
									}

									if (f17 <= 5.0F) {
										bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
										bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
										bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
										bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
									}

									if (k > -1) {
										for(int i1 = 0; i1 < 8; ++i1) {
											bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
											bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
											bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
											bufferIn.pos((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
										}
									}

									if (k <= 1) {
										for(int j2 = 0; j2 < 8; ++j2) {
											bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
											bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
											bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
											bufferIn.pos((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).tex((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
										}
									}

									if (l > -1) {
										for(int k2 = 0; k2 < 8; ++k2) {
											bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
											bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
											bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
											bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
										}
									}

									if (l <= 1) {
										for(int l2 = 0; l2 < 8; ++l2) {
											bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
											bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
											bufferIn.pos((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
											bufferIn.pos((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).tex((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
										}
									}
								}
							}
						} else {
							int j1 = 1;
							int k1 = 32;

							for(int l1 = -32; l1 < 32; l1 += 32) {
								for(int i2 = -32; i2 < 32; i2 += 32) {
									bufferIn.pos((double)(l1 + 0), (double)f17, (double)(i2 + 32)).tex((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
									bufferIn.pos((double)(l1 + 32), (double)f17, (double)(i2 + 32)).tex((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
									bufferIn.pos((double)(l1 + 32), (double)f17, (double)(i2 + 0)).tex((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
									bufferIn.pos((double)(l1 + 0), (double)f17, (double)(i2 + 0)).tex((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
								}
							}
						}

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
							matrixStack.rotate(Vector3f.ZP.rotationDegrees(f3));// rotation richtig with ^^
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

						matrixStack.rotate(Vector3f.YP.rotationDegrees(-130.0F));
						matrixStack.rotate(Vector3f.ZP.rotationDegrees(210.0F));

						//EARTH
						mc.getTextureManager().bindTexture(EARTH_TEXTURE);
						bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
						bufferbuilder.pos(matrix4f1, -2, -100.0F, 2).tex(0.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, 2, -100.0F, 2).tex(1.0F, 0.0F).endVertex();
						bufferbuilder.pos(matrix4f1, 2, -100.0F, -2).tex(1.0F, 1.0F).endVertex();
						bufferbuilder.pos(matrix4f1, -2, -100.0F, -2).tex(0.0F, 1.0F).endVertex();
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

	 */
}
