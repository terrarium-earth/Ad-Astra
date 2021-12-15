package net.mrscauthd.astrocraft.skyrenderer;

import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
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
import net.mrscauthd.astrocraft.AstroCraftMod;
import net.mrscauthd.astrocraft.ModInnet;

import javax.annotation.Nullable;
import java.util.Random;

@Mod.EventBusSubscriber(modid = AstroCraftMod.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientEventBusVenus {

    private static final float[] rainSizeX = new float[1024];
    private static final float[] rainSizeZ = new float[1024];
    private static int rainSoundTime;

    @Nullable
    private static CloudStatus prevCloudsType;

    @Nullable
    private static VertexBuffer cloudBuffer;
    private static boolean generateClouds = true;
    private static int prevCloudX = Integer.MIN_VALUE;
    private static int prevCloudY = Integer.MIN_VALUE;
    private static int prevCloudZ = Integer.MIN_VALUE;
    private static Vec3 prevCloudColor = Vec3.ZERO;

    private static final ResourceLocation DIM_RENDER_INFO = new ResourceLocation(AstroCraftMod.MODID, "venus");

    private static final ResourceLocation CLOUD_TEXTURE = new ResourceLocation(AstroCraftMod.MODID, "textures/sky/clouds.png");
    private static final ResourceLocation RAIN_TEXTURE = new ResourceLocation(AstroCraftMod.MODID, "textures/sky/rain.png");
    private static final ResourceLocation SUN_TEXTURE = new ResourceLocation(AstroCraftMod.MODID, "textures/sky/sun.png");
    private static final ResourceLocation EARTH_TEXTURE = new ResourceLocation(AstroCraftMod.MODID, "textures/sky/earth.png");

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void clientSetup(FMLClientSetupEvent event) {
        DimensionSpecialEffects.EFFECTS.put(DIM_RENDER_INFO, new DimensionSpecialEffects(192, true, DimensionSpecialEffects.SkyType.NORMAL, false, false) {
            @Override
            public Vec3 getBrightnessDependentFogColor(Vec3 p_108878_, float p_108879_) {
                return new Vec3(0.647058823529, 0.450980392157, 0.254901960784);
            }

            @Override
            public boolean isFoggyAt(int p_108874_, int p_108875_) {
                return true;
            }

            @Override
            public ISkyRenderHandler getSkyRenderHandler() {
                return new ISkyRenderHandler() {
                    @Override
                    public void render(int ticks, float p_181412_, PoseStack p_181410_, ClientLevel level, Minecraft minecraft) {
                        Matrix4f matrix4f = RenderSystem.getProjectionMatrix();
                        Matrix4f starmatrix4f = RenderSystem.getProjectionMatrix();
                        RenderSystem.disableTexture();
                        Vec3 vec3 = level.getSkyColor(minecraft.gameRenderer.getMainCamera().getPosition(), p_181412_);
                        float f = (float) vec3.x;
                        float f1 = (float) vec3.y;
                        float f2 = (float) vec3.z;
                        FogRenderer.levelFogColor();
                        BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                        RenderSystem.depthMask(false);
                        RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                        ShaderInstance shaderinstance = RenderSystem.getShader();
                        minecraft.levelRenderer.skyBuffer.drawWithShader(p_181410_.last().pose(), matrix4f, shaderinstance);
                        RenderSystem.enableBlend();
                        RenderSystem.defaultBlendFunc();
                        float[] afloat = level.effects().getSunriseColor(level.getTimeOfDay(p_181412_), p_181412_);
                        if (afloat != null) {
                            RenderSystem.setShader(GameRenderer::getPositionColorShader);
                            RenderSystem.disableTexture();
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            p_181410_.pushPose();
                            p_181410_.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                            float f3 = Mth.sin(level.getSunAngle(p_181412_)) < 0.0F ? 180.0F : 0.0F;
                            p_181410_.mulPose(Vector3f.ZP.rotationDegrees(f3));
                            p_181410_.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
                            float f4 = afloat[0];
                            float f5 = afloat[1];
                            float f6 = afloat[2];
                            matrix4f = p_181410_.last().pose();
                            bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_COLOR);
                            bufferbuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(f4, f5, f6, afloat[3]).endVertex();

                            for (int j = 0; j <= 16; ++j) {
                                float f7 = (float) j * ((float) Math.PI * 2F) / 16.0F;
                                float f8 = Mth.sin(f7);
                                float f9 = Mth.cos(f7);
                                bufferbuilder.vertex(matrix4f, f8 * 120.0F, f9 * 120.0F, -f9 * 40.0F * afloat[3]).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
                            }

                            bufferbuilder.end();
                            BufferUploader.end(bufferbuilder);
                            p_181410_.popPose();
                        }

                        RenderSystem.enableTexture();
                        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                        p_181410_.pushPose();
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                        //ROT
                        p_181410_.mulPose(Vector3f.XP.rotationDegrees(level.getTimeOfDay(p_181412_) * 360.0F));
                        Matrix4f matrix4f1 = p_181410_.last().pose();

                        RenderSystem.setShader(GameRenderer::getPositionTexShader);

                        float f12 = 20.0F;

                        //SUN
                        RenderSystem.setShaderTexture(0, SUN_TEXTURE);
                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, -f12).uv(0.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, f12, 100.0F, -f12).uv(1.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, f12, 100.0F, f12).uv(1.0F, 1.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, -f12, 100.0F, f12).uv(0.0F, 1.0F).endVertex();
                        bufferbuilder.end();
                        BufferUploader.end(bufferbuilder);

                        p_181410_.mulPose(Vector3f.YP.rotationDegrees(-130.0F));
                        p_181410_.mulPose(Vector3f.ZP.rotationDegrees(210.0F));

                        //EARTH
                        RenderSystem.setShaderTexture(0, EARTH_TEXTURE);
                        bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
                        bufferbuilder.vertex(matrix4f1, -2, -100.0F, 2).uv(0.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, 2, -100.0F, 2).uv(1.0F, 0.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, 2, -100.0F, -2).uv(1.0F, 1.0F).endVertex();
                        bufferbuilder.vertex(matrix4f1, -2, -100.0F, -2).uv(0.0F, 1.0F).endVertex();
                        bufferbuilder.end();
                        BufferUploader.end(bufferbuilder);

                        RenderSystem.disableTexture();

                        //STAR GEN
                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        FogRenderer.setupNoFog();
                        minecraft.levelRenderer.starBuffer.drawWithShader(p_181410_.last().pose(), starmatrix4f, GameRenderer.getPositionShader());

                        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                        RenderSystem.disableBlend();
                        p_181410_.popPose();
                        RenderSystem.disableTexture();
                        RenderSystem.setShaderColor(0.0F, 0.0F, 0.0F, 1.0F);

                        double d0 = minecraft.player.getEyePosition(p_181412_).y - level.getLevelData().getHorizonHeight(level);

                        if (d0 < 0.0D) {
                            p_181410_.pushPose();
                            p_181410_.translate(0.0D, 12.0D, 0.0D);
                            minecraft.levelRenderer.darkBuffer.drawWithShader(p_181410_.last().pose(), matrix4f, shaderinstance);
                            p_181410_.popPose();
                        }

                        if (level.effects().hasGround()) {
                            RenderSystem.setShaderColor(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F, 1.0F);
                        } else {
                            RenderSystem.setShaderColor(f, f1, f2, 1.0F);
                        }

                        RenderSystem.enableTexture();
                        RenderSystem.depthMask(true);
                    }
                };
            }

            @Override
            public ICloudRenderHandler getCloudRenderHandler() {
                return new ICloudRenderHandler() {
                    @Override
                    public void render(int ticks, float p_172957_, PoseStack p_172955_, ClientLevel level, Minecraft minecraft, double p_172958_, double p_172959_, double p_172960_) {
                        float f = level.effects().getCloudHeight();
                        if (!Float.isNaN(f)) {
                            RenderSystem.disableCull();
                            RenderSystem.enableBlend();
                            RenderSystem.enableDepthTest();
                            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
                            RenderSystem.depthMask(true);
                            float f1 = 12.0F;
                            float f2 = 4.0F;
                            double d0 = 2.0E-4D;
                            double d1 = (double)(((float)ticks + p_172957_) * 0.03F);
                            double d2 = (p_172958_ + d1) / 12.0D;
                            double d3 = (double)(f - (float)p_172959_ + 0.33F);
                            double d4 = p_172960_ / 12.0D + (double)0.33F;
                            d2 -= (double)(Mth.floor(d2 / 2048.0D) * 2048);
                            d4 -= (double)(Mth.floor(d4 / 2048.0D) * 2048);
                            float f3 = (float)(d2 - (double)Mth.floor(d2));
                            float f4 = (float)(d3 / 4.0D - (double)Mth.floor(d3 / 4.0D)) * 4.0F;
                            float f5 = (float)(d4 - (double)Mth.floor(d4));
                            Vec3 vec3 = level.getCloudColor(p_172957_);
                            int i = (int)Math.floor(d2);
                            int j = (int)Math.floor(d3 / 4.0D);
                            int k = (int)Math.floor(d4);
                            if (i != prevCloudX || j != prevCloudY || k != prevCloudZ || minecraft.options.getCloudsType() != prevCloudsType || prevCloudColor.distanceToSqr(vec3) > 2.0E-4D) {
                                prevCloudX = i;
                                prevCloudY = j;
                                prevCloudZ = k;
                                prevCloudColor = vec3;
                                prevCloudsType = minecraft.options.getCloudsType();
                                generateClouds = true;
                            }

                            if (generateClouds) {
                                generateClouds = false;
                                BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                                if (cloudBuffer != null) {
                                    cloudBuffer.close();
                                }

                                cloudBuffer = new VertexBuffer();
                                buildClouds(bufferbuilder, d2, d3, d4, vec3);
                                bufferbuilder.end();
                                cloudBuffer.upload(bufferbuilder);
                            }

                            RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
                            RenderSystem.setShaderTexture(0, CLOUD_TEXTURE);
                            FogRenderer.levelFogColor();
                            p_172955_.pushPose();
                            p_172955_.scale(12.0F, 1.0F, 12.0F);
                            p_172955_.translate((double)(-f3), (double)f4, (double)(-f5));
                            if (cloudBuffer != null) {
                                int i1 = prevCloudsType == CloudStatus.FANCY ? 0 : 1;

                                for(int l = i1; l < 2; ++l) {
                                    if (l == 0) {
                                        RenderSystem.colorMask(false, false, false, false);
                                    } else {
                                        RenderSystem.colorMask(true, true, true, true);
                                    }

                                    ShaderInstance shaderinstance = RenderSystem.getShader();
                                    cloudBuffer.drawWithShader(p_172955_.last().pose(), RenderSystem.getProjectionMatrix(), shaderinstance);
                                }
                            }

                            p_172955_.popPose();
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            RenderSystem.enableCull();
                            RenderSystem.disableBlend();
                        }
                    }
                };
            }

            private void buildClouds(BufferBuilder p_109579_, double p_109580_, double p_109581_, double p_109582_, Vec3 p_109583_) {
                float f = 4.0F;
                float f1 = 0.00390625F;
                int i = 8;
                int j = 4;
                float f2 = 9.765625E-4F;
                float f3 = (float)Mth.floor(p_109580_) * 0.00390625F;
                float f4 = (float)Mth.floor(p_109582_) * 0.00390625F;
                float f5 = (float)p_109583_.x;
                float f6 = (float)p_109583_.y;
                float f7 = (float)p_109583_.z;
                float f8 = f5 * 0.9F;
                float f9 = f6 * 0.9F;
                float f10 = f7 * 0.9F;
                float f11 = f5 * 0.7F;
                float f12 = f6 * 0.7F;
                float f13 = f7 * 0.7F;
                float f14 = f5 * 0.8F;
                float f15 = f6 * 0.8F;
                float f16 = f7 * 0.8F;
                RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
                p_109579_.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR_NORMAL);
                float f17 = (float)Math.floor(p_109581_ / 4.0D) * 4.0F;
                if (prevCloudsType == CloudStatus.FANCY) {
                    for(int k = -3; k <= 4; ++k) {
                        for(int l = -3; l <= 4; ++l) {
                            float f18 = (float)(k * 8);
                            float f19 = (float)(l * 8);
                            if (f17 > -5.0F) {
                                p_109579_.vertex((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).uv((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                                p_109579_.vertex((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).uv((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                                p_109579_.vertex((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).uv((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                                p_109579_.vertex((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).uv((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f11, f12, f13, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                            }

                            if (f17 <= 5.0F) {
                                p_109579_.vertex((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).uv((f18 + 0.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                                p_109579_.vertex((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 8.0F)).uv((f18 + 8.0F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                                p_109579_.vertex((double)(f18 + 8.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).uv((f18 + 8.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                                p_109579_.vertex((double)(f18 + 0.0F), (double)(f17 + 4.0F - 9.765625E-4F), (double)(f19 + 0.0F)).uv((f18 + 0.0F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
                            }

                            if (k > -1) {
                                for(int i1 = 0; i1 < 8; ++i1) {
                                    p_109579_.vertex((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).uv((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).uv((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).uv((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + (float)i1 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).uv((f18 + (float)i1 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
                                }
                            }

                            if (k <= 1) {
                                for(int j2 = 0; j2 < 8; ++j2) {
                                    p_109579_.vertex((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 8.0F)).uv((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 8.0F)).uv((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 8.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 4.0F), (double)(f19 + 0.0F)).uv((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + (float)j2 + 1.0F - 9.765625E-4F), (double)(f17 + 0.0F), (double)(f19 + 0.0F)).uv((f18 + (float)j2 + 0.5F) * 0.00390625F + f3, (f19 + 0.0F) * 0.00390625F + f4).color(f8, f9, f10, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
                                }
                            }

                            if (l > -1) {
                                for(int k2 = 0; k2 < 8; ++k2) {
                                    p_109579_.vertex((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).uv((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)k2 + 0.0F)).uv((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).uv((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)k2 + 0.0F)).uv((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)k2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
                                }
                            }

                            if (l <= 1) {
                                for(int l2 = 0; l2 < 8; ++l2) {
                                    p_109579_.vertex((double)(f18 + 0.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).uv((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + 8.0F), (double)(f17 + 4.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).uv((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + 8.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).uv((f18 + 8.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                                    p_109579_.vertex((double)(f18 + 0.0F), (double)(f17 + 0.0F), (double)(f19 + (float)l2 + 1.0F - 9.765625E-4F)).uv((f18 + 0.0F) * 0.00390625F + f3, (f19 + (float)l2 + 0.5F) * 0.00390625F + f4).color(f14, f15, f16, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
                                }
                            }
                        }
                    }
                } else {
                    int j1 = 1;
                    int k1 = 32;

                    for(int l1 = -32; l1 < 32; l1 += 32) {
                        for(int i2 = -32; i2 < 32; i2 += 32) {
                            p_109579_.vertex((double)(l1 + 0), (double)f17, (double)(i2 + 32)).uv((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                            p_109579_.vertex((double)(l1 + 32), (double)f17, (double)(i2 + 32)).uv((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 32) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                            p_109579_.vertex((double)(l1 + 32), (double)f17, (double)(i2 + 0)).uv((float)(l1 + 32) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                            p_109579_.vertex((double)(l1 + 0), (double)f17, (double)(i2 + 0)).uv((float)(l1 + 0) * 0.00390625F + f3, (float)(i2 + 0) * 0.00390625F + f4).color(f5, f6, f7, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
                        }
                    }
                }

            }

            @Override
            public IWeatherRenderHandler getWeatherRenderHandler() {
                return new IWeatherRenderHandler() {
                    @Override
                    public void render(int ticks, float p_109705_, ClientLevel world, Minecraft minecraft, LightTexture p_109704_, double p_109706_, double p_109707_, double p_109708_) {
                        for(int i = 0; i < 32; ++i) {
                            for(int j = 0; j < 32; ++j) {
                                float f = (float)(j - 16);
                                float f1 = (float)(i - 16);
                                float f2 = Mth.sqrt(f * f + f1 * f1);
                                rainSizeX[i << 5 | j] = -f1 / f2;
                                rainSizeZ[i << 5 | j] = f / f2;
                            }
                        }

                        float f = minecraft.level.getRainLevel(p_109705_);
                        if (!(f <= 0.0F)) {
                            p_109704_.turnOnLightLayer();
                            Level level = minecraft.level;
                            int i = Mth.floor(p_109706_);
                            int j = Mth.floor(p_109707_);
                            int k = Mth.floor(p_109708_);
                            Tesselator tesselator = Tesselator.getInstance();
                            BufferBuilder bufferbuilder = tesselator.getBuilder();
                            RenderSystem.disableCull();
                            RenderSystem.enableBlend();
                            RenderSystem.defaultBlendFunc();
                            RenderSystem.enableDepthTest();
                            int l = 5;
                            if (Minecraft.useFancyGraphics()) {
                                l = 10;
                            }

                            RenderSystem.depthMask(Minecraft.useShaderTransparency());
                            int i1 = -1;
                            float f1 = (float)ticks + p_109705_;
                            RenderSystem.setShader(GameRenderer::getParticleShader);
                            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                            for(int j1 = k - l; j1 <= k + l; ++j1) {
                                for(int k1 = i - l; k1 <= i + l; ++k1) {
                                    int l1 = (j1 - k + 16) * 32 + k1 - i + 16;
                                    double d0 = (double)rainSizeX[l1] * 0.5D;
                                    double d1 = (double)rainSizeZ[l1] * 0.5D;
                                    blockpos$mutableblockpos.set((double)k1, p_109707_, (double)j1);
                                    Biome biome = level.getBiome(blockpos$mutableblockpos);
                                    if (biome.getPrecipitation() != Biome.Precipitation.NONE) {
                                        int i2 = level.getHeight(Heightmap.Types.MOTION_BLOCKING, k1, j1);
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
                                            blockpos$mutableblockpos.set(k1, j2, j1);
                                            if (biome.warmEnoughToRain(blockpos$mutableblockpos)) {
                                                if (i1 != 0) {
                                                    if (i1 >= 0) {
                                                        tesselator.end();
                                                    }

                                                    i1 = 0;
                                                    RenderSystem.setShaderTexture(0, RAIN_TEXTURE);
                                                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.PARTICLE);
                                                }

                                                int i3 = ticks + k1 * k1 * 3121 + k1 * 45238971 + j1 * j1 * 418711 + j1 * 13761 & 31;
                                                float f2 = -((float)i3 + p_109705_) / 32.0F * (3.0F + random.nextFloat());
                                                double d2 = (double)k1 + 0.5D - p_109706_;
                                                double d4 = (double)j1 + 0.5D - p_109708_;
                                                float f3 = (float)Math.sqrt(d2 * d2 + d4 * d4) / (float)l;
                                                float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * f;
                                                blockpos$mutableblockpos.set(k1, l2, j1);
                                                int j3 = minecraft.levelRenderer.getLightColor(level, blockpos$mutableblockpos);
                                                bufferbuilder.vertex((double)k1 - p_109706_ - d0 + 0.5D, (double)k2 - p_109707_, (double)j1 - p_109708_ - d1 + 0.5D).uv(0.0F, (float)j2 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                                bufferbuilder.vertex((double)k1 - p_109706_ + d0 + 0.5D, (double)k2 - p_109707_, (double)j1 - p_109708_ + d1 + 0.5D).uv(1.0F, (float)j2 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                                bufferbuilder.vertex((double)k1 - p_109706_ + d0 + 0.5D, (double)j2 - p_109707_, (double)j1 - p_109708_ + d1 + 0.5D).uv(1.0F, (float)k2 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                                bufferbuilder.vertex((double)k1 - p_109706_ - d0 + 0.5D, (double)j2 - p_109707_, (double)j1 - p_109708_ - d1 + 0.5D).uv(0.0F, (float)k2 * 0.25F + f2).color(1.0F, 1.0F, 1.0F, f4).uv2(j3).endVertex();
                                            }
                                        }
                                    }
                                }
                            }

                            if (i1 >= 0) {
                                tesselator.end();
                            }

                            RenderSystem.enableCull();
                            RenderSystem.disableBlend();
                            p_109704_.turnOffLightLayer();
                        }
                    }
                };
            }

            @Override
            public IWeatherParticleRenderHandler getWeatherParticleRenderHandler() {
                return new IWeatherParticleRenderHandler() {
                    @Override
                    public void render(int ticks, ClientLevel world, Minecraft minecraft, Camera p_109694_) {
                        float f = minecraft.level.getRainLevel(1.0F) / (Minecraft.useFancyGraphics() ? 1.0F : 2.0F);
                        if (!(f <= 0.0F)) {
                            Random random = new Random((long)ticks * 312987231L);
                            LevelReader levelreader = minecraft.level;
                            BlockPos blockpos = new BlockPos(p_109694_.getPosition());
                            BlockPos blockpos1 = null;
                            int i = (int)(100.0F * f * f) / (minecraft.options.particles == ParticleStatus.DECREASED ? 2 : 1);

                            for(int j = 0; j < i; ++j) {
                                int k = random.nextInt(21) - 10;
                                int l = random.nextInt(21) - 10;
                                BlockPos blockpos2 = levelreader.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos.offset(k, 0, l));
                                Biome biome = levelreader.getBiome(blockpos2);
                                if (blockpos2.getY() > levelreader.getMinBuildHeight() && blockpos2.getY() <= blockpos.getY() + 10 && blockpos2.getY() >= blockpos.getY() - 10 && biome.getPrecipitation() == Biome.Precipitation.RAIN && biome.warmEnoughToRain(blockpos2)) {
                                    blockpos1 = blockpos2.below();
                                    if (minecraft.options.particles == ParticleStatus.MINIMAL) {
                                        break;
                                    }

                                    double d0 = random.nextDouble();
                                    double d1 = random.nextDouble();
                                    BlockState blockstate = levelreader.getBlockState(blockpos1);
                                    FluidState fluidstate = levelreader.getFluidState(blockpos1);
                                    VoxelShape voxelshape = blockstate.getCollisionShape(levelreader, blockpos1);
                                    double d2 = voxelshape.max(Direction.Axis.Y, d0, d1);
                                    double d3 = (double)fluidstate.getHeight(levelreader, blockpos1);
                                    double d4 = Math.max(d2, d3);
                                    ParticleOptions particleoptions = !fluidstate.is(FluidTags.LAVA) && !blockstate.is(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire(blockstate) ? (ParticleOptions) ModInnet.VENUS_RAIN_PARTICLE.get() : ParticleTypes.SMOKE;
                                    minecraft.level.addParticle(particleoptions, (double)blockpos1.getX() + d0, (double)blockpos1.getY() + d4, (double)blockpos1.getZ() + d1, 0.0D, 0.0D, 0.0D);
                                }
                            }

                            if (blockpos1 != null && random.nextInt(3) < rainSoundTime++) {
                                rainSoundTime = 0;
                                if (blockpos1.getY() > blockpos.getY() + 1 && levelreader.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockpos).getY() > Mth.floor((float)blockpos.getY())) {
                                    minecraft.level.playLocalSound(blockpos1, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
                                } else {
                                    minecraft.level.playLocalSound(blockpos1, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
                                }
                            }
                        }
                    }
                };
            }
        });
    }
}
