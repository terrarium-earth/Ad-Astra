package earth.terrarium.ad_astra.mixin.client;

import earth.terrarium.ad_astra.common.config.VehiclesConfig;
import earth.terrarium.ad_astra.common.registry.ModParticleTypes;
import earth.terrarium.ad_astra.common.util.ModUtils;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.ParticleStatus;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.LevelRenderer;
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
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class LevelRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    private int ticks;

    @Shadow
    private int rainSoundTime;

    // Cancel the portal sound when the player falls out of orbit.
    @Inject(method = "levelEvent", at = @At("HEAD"), cancellable = true)
    public void ad_astra$processWorldEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
        if (eventId == LevelEvent.SOUND_PORTAL_TRAVEL) {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer player = minecraft.player;
            // Don't player the portal sound if the player teleported to the new level.
            if (player != null && ((int) player.position().y()) == VehiclesConfig.RocketConfig.atmosphereLeave) {
                ci.cancel();
            }
        }
    }

    // Venus rain.
    @Inject(method = "tickRain", at = @At("HEAD"))
    public void ad_astra$tickRainSplashing(Camera camera, CallbackInfo info) {
        if (this.minecraft.level != null && ModUtils.isPlanet(this.minecraft.level)) {
            float f = this.minecraft.level.getRainLevel(1.0F) / (Minecraft.useFancyGraphics() ? 1.0F : 2.0F);
            if (!(f <= 0.0F)) {
                RandomSource randomGenerator = RandomSource.create((long) this.ticks * 312987231L);
                LevelReader levelView = this.minecraft.level;
                BlockPos blockPos = new BlockPos(camera.getPosition());
                BlockPos blockPos2 = null;
                int i = (int) (100.0F * f * f) / (this.minecraft.options.particles().get() == ParticleStatus.DECREASED ? 2 : 1);

                for (int j = 0; j < i; ++j) {
                    int k = randomGenerator.nextInt(21) - 10;
                    int l = randomGenerator.nextInt(21) - 10;
                    BlockPos blockPos3 = levelView.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos.offset(k, 0, l));
                    Biome biome = levelView.getBiome(blockPos3).value();
                    if (blockPos3.getY() > levelView.getMinBuildHeight() && blockPos3.getY() <= blockPos.getY() + 10 && blockPos3.getY() >= blockPos.getY() - 10 && biome.getPrecipitation() == Biome.Precipitation.RAIN && biome.warmEnoughToRain(blockPos3)) {
                        blockPos2 = blockPos3.below();
                        if (this.minecraft.options.particles().get() == ParticleStatus.MINIMAL) {
                            break;
                        }

                        double d = randomGenerator.nextDouble();
                        double e = randomGenerator.nextDouble();
                        BlockState blockState = levelView.getBlockState(blockPos2);
                        FluidState fluidState = levelView.getFluidState(blockPos2);
                        VoxelShape voxelShape = blockState.getCollisionShape(levelView, blockPos2);
                        double g = voxelShape.max(Direction.Axis.Y, d, e);
                        double h = fluidState.getHeight(levelView, blockPos2);
                        double m = Math.max(g, h);
                        ParticleOptions particleEffect = !fluidState.is(FluidTags.LAVA) && !blockState.is(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire(blockState) ? ParticleTypes.SMOKE : ModParticleTypes.VENUS_RAIN.get();
                        this.minecraft.level.addParticle(particleEffect, (double) blockPos2.getX() + d, (double) blockPos2.getY() + m, (double) blockPos2.getZ() + e, 0.0, 0.0, 0.0);
                    }
                }

                if (blockPos2 != null && randomGenerator.nextInt(3) < this.rainSoundTime++) {
                    this.rainSoundTime = 0;
                    if (blockPos2.getY() > blockPos.getY() + 1 && levelView.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING, blockPos).getY() > Mth.floor((float) blockPos.getY())) {
                        this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundSource.WEATHER, 0.1F, 0.5F, false);
                    } else {
                        this.minecraft.level.playLocalSound(blockPos2, SoundEvents.WEATHER_RAIN, SoundSource.WEATHER, 0.2F, 1.0F, false);
                    }
                }
            }
        }
    }
}