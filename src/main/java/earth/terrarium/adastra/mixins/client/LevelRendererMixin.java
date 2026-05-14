package earth.terrarium.adastra.mixins.client;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexBuffer;
import earth.terrarium.adastra.client.utils.DimensionRenderingUtils;
import earth.terrarium.adastra.common.registry.ModParticleTypes;
import earth.terrarium.adastra.common.tags.ModBiomeTags;
import net.minecraft.client.Camera;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(value = LevelRenderer.class, priority = 2000)
public abstract class LevelRendererMixin {

    @Shadow
    private ClientLevel level;

    @Shadow
    private int ticks;

    @Shadow
    private int prevCloudX;

    @Shadow
    private int prevCloudY;

    @Shadow
    private int prevCloudZ;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private Vec3 prevCloudColor;

    @Shadow
    private CloudStatus prevCloudsType;

    @Shadow
    private boolean generateClouds;

    @Shadow
    private VertexBuffer cloudBuffer;

    @Shadow
    protected abstract BufferBuilder.RenderedBuffer buildClouds(BufferBuilder builder, double x, double y, double z, Vec3 cloudColor);

    @Shadow
    private int rainSoundTime;

    @Inject(method = "renderSnowAndRain", at = @At(
        value = "INVOKE",
        target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderTexture(ILnet/minecraft/resources/ResourceLocation;)V",
        shift = At.Shift.AFTER
    ))
    private void adastra$renderSnowAndRain(LightTexture lightTexture, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        if (adastra$hasAcidRain()) {
            RenderSystem.setShaderTexture(0, DimensionRenderingUtils.ACID_RAIN);
        }
    }

    // A copy of vanilla's cloud renderer that uses the venus cloud texture. I have to copy the entire
    // thing because sodium is stupid and overwrites the entire method.
    @Inject(
        method = "renderClouds",
        at = @At(
            value = "HEAD"
        ),
        cancellable = true)
    private void adastra$renderClouds(PoseStack poseStack, Matrix4f projectionMatrix, float partialTick, double camX, double camY, double camZ, CallbackInfo ci) {
        if (adastra$hasAcidRain()) {
            ci.cancel();
            float f = this.level.effects().getCloudHeight();
            if (!Float.isNaN(f)) {
                RenderSystem.disableCull();
                RenderSystem.enableBlend();
                RenderSystem.enableDepthTest();
                RenderSystem.blendFuncSeparate(
                    GlStateManager.SourceFactor.SRC_ALPHA,
                    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA,
                    GlStateManager.SourceFactor.ONE,
                    GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA
                );
                RenderSystem.depthMask(true);
                double d1 = ((float) this.ticks + partialTick) * 0.03F;
                double d2 = (camX + d1) / 12.0;
                double d3 = f - (float) camY + 0.33F;
                double d4 = camZ / 12.0 + 0.33F;
                d2 -= Mth.floor(d2 / 2048.0) * 2048;
                d4 -= Mth.floor(d4 / 2048.0) * 2048;
                float f3 = (float) (d2 - (double) Mth.floor(d2));
                float f4 = (float) (d3 / 4.0 - (double) Mth.floor(d3 / 4.0)) * 4.0F;
                float f5 = (float) (d4 - (double) Mth.floor(d4));
                Vec3 vec3 = this.level.getCloudColor(partialTick);
                int i = (int) Math.floor(d2);
                int j = (int) Math.floor(d3 / 4.0);
                int k = (int) Math.floor(d4);
                if (i != this.prevCloudX
                    || j != this.prevCloudY
                    || k != this.prevCloudZ
                    || this.minecraft.options.getCloudsType() != this.prevCloudsType
                    || this.prevCloudColor.distanceToSqr(vec3) > 2.0E-4) {
                    this.prevCloudX = i;
                    this.prevCloudY = j;
                    this.prevCloudZ = k;
                    this.prevCloudColor = vec3;
                    this.prevCloudsType = this.minecraft.options.getCloudsType();
                    this.generateClouds = true;
                }

                if (this.generateClouds) {
                    this.generateClouds = false;
                    BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
                    if (this.cloudBuffer != null) {
                        this.cloudBuffer.close();
                    }

                    this.cloudBuffer = new VertexBuffer(VertexBuffer.Usage.STATIC);
                    BufferBuilder.RenderedBuffer bufferbuilder$renderedbuffer = this.buildClouds(bufferbuilder, d2, d3, d4, vec3);
                    this.cloudBuffer.bind();
                    this.cloudBuffer.upload(bufferbuilder$renderedbuffer);
                    VertexBuffer.unbind();
                }

                RenderSystem.setShader(GameRenderer::getPositionTexColorNormalShader);
                RenderSystem.setShaderTexture(0, DimensionRenderingUtils.VENUS_CLOUDS);
                FogRenderer.levelFogColor();
                poseStack.pushPose();
                poseStack.scale(12.0F, 1.0F, 12.0F);
                poseStack.translate(-f3, f4, -f5);
                if (this.cloudBuffer != null) {
                    this.cloudBuffer.bind();
                    int l = this.prevCloudsType == CloudStatus.FANCY ? 0 : 1;

                    for (int i1 = l; i1 < 2; ++i1) {
                        if (i1 == 0) {
                            RenderSystem.colorMask(false, false, false, false);
                        } else {
                            RenderSystem.colorMask(true, true, true, true);
                        }

                        ShaderInstance shaderinstance = RenderSystem.getShader();
                        assert shaderinstance != null;
                        this.cloudBuffer.drawWithShader(poseStack.last().pose(), projectionMatrix, shaderinstance);
                    }

                    VertexBuffer.unbind();
                }

                poseStack.popPose();
                RenderSystem.enableCull();
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            }
        }
    }

    // A copy of vanilla's rain rendering with custom particles because there's some mod that overwrites this, so it
    // has to be copied in full.
    @Inject(
        method = "tickRain",
        at = @At(
            value = "HEAD"
        ),
        cancellable = true)
    public void adastra$tickRain(Camera camera, CallbackInfo ci) {
        if (adastra$hasAcidRain()) {
            ci.cancel();
            float f = Objects.requireNonNull(this.minecraft.level).getRainLevel(1.0F) / (Minecraft.useFancyGraphics() ? 1.0F : 2.0F);
            if (!(f <= 0.0F)) {
                RandomSource randomSource = RandomSource.create((long) this.ticks * 312987231L);
                LevelReader levelReader = this.minecraft.level;
                BlockPos blockPos = BlockPos.containing(camera.getPosition());
                BlockPos blockPos2 = null;
                int i = (int) (100.0F * f * f) / (this.minecraft.options.particles().get() == ParticleStatus.DECREASED ? 2 : 1);

                for (int j = 0; j < i; ++j) {
                    int k = randomSource.nextInt(21) - 10;
                    int l = randomSource.nextInt(21) - 10;
                    BlockPos blockPos3 = levelReader.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos.offset(k, 0, l));
                    if (blockPos3.getY() > levelReader.getMinBuildHeight() && blockPos3.getY() <= blockPos.getY() + 10 && blockPos3.getY() >= blockPos.getY() - 10) {
                        Biome biome = levelReader.getBiome(blockPos3).value();
                        if (biome.getPrecipitationAt(blockPos3) == Biome.Precipitation.RAIN) {
                            blockPos2 = blockPos3.below();
                            if (this.minecraft.options.particles().get() == ParticleStatus.MINIMAL) {
                                break;
                            }

                            double d = randomSource.nextDouble();
                            double e = randomSource.nextDouble();
                            BlockState blockState = levelReader.getBlockState(blockPos2);
                            FluidState fluidState = levelReader.getFluidState(blockPos2);
                            VoxelShape voxelShape = blockState.getCollisionShape(levelReader, blockPos2);
                            double g = voxelShape.max(Direction.Axis.Y, d, e);
                            double h = fluidState.getHeight(levelReader, blockPos2);
                            double m = Math.max(g, h);
                            ParticleOptions particleOptions = !fluidState.is(FluidTags.LAVA) && !blockState.is(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire(blockState)
                                ? ModParticleTypes.ACID_RAIN.get()
                                : ParticleTypes.SMOKE;
                            this.minecraft
                                .level
                                .addParticle(particleOptions, (double) blockPos2.getX() + d, (double) blockPos2.getY() + m, (double) blockPos2.getZ() + e, 0.0, 0.0, 0.0);
                        }
                    }
                }

                if (blockPos2 != null && randomSource.nextInt(3) < rainSoundTime++) {
                    rainSoundTime = 0;
                    if (blockPos2.getY() > blockPos.getY() + 1
                        && levelReader.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos).getY() > Mth.floor((float) blockPos.getY())) {
                        this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
                    } else {
                        this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
                    }
                }
            }
        }
    }

    @Unique
    private boolean adastra$hasAcidRain() {
        var player = Minecraft.getInstance().player;
        if (player == null) return false;
        return player.level().getBiome(player.blockPosition()).is(ModBiomeTags.HAS_ACID_RAIN);
    }
}
