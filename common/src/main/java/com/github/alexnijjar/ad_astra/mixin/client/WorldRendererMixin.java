package com.github.alexnijjar.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.registry.ClientModSkies;
import com.github.alexnijjar.ad_astra.registry.ModParticleTypes;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.FluidState;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

	@Shadow
	@Final
	private MinecraftClient client;

	@Shadow
	private int ticks;

	@Shadow
	private int rainSoundTicks;

	// Cancel the portal sound when the player falls out of orbit.
	@Inject(method = "processWorldEvent", at = @At("HEAD"), cancellable = true)
	public void adastra_processWorldEvent(int eventId, BlockPos pos, int data, CallbackInfo ci) {
		if (eventId == WorldEvents.TRAVEL_THROUGH_PORTAL) {
			MinecraftClient client = MinecraftClient.getInstance();
			ClientPlayerEntity player = client.player;
			// Don't player the portal sound if the player teleported to the new world.
			if (((int) player.getPos().getY()) == AdAstra.CONFIG.rocket.atmosphereLeave) {
				ci.cancel();
			}
		}
	}

	// Venus rain.
	@Inject(method = "tickRainSplashing", at = @At("HEAD"), cancellable = true)
	public void adastra_tickRainSplashing(Camera camera, CallbackInfo info) {
		float f = this.client.world.getRainGradient(1.0F) / (MinecraftClient.isFancyGraphicsOrBetter() ? 1.0F : 2.0F);
		if (!(f <= 0.0F)) {
			RandomGenerator randomGenerator = RandomGenerator.createLegacy((long) this.ticks * 312987231L);
			WorldView worldView = this.client.world;
			BlockPos blockPos = new BlockPos(camera.getPos());
			BlockPos blockPos2 = null;
			int i = (int) (100.0F * f * f) / (this.client.options.getParticles().get() == ParticlesMode.DECREASED ? 2 : 1);

			for (int j = 0; j < i; ++j) {
				int k = randomGenerator.nextInt(21) - 10;
				int l = randomGenerator.nextInt(21) - 10;
				BlockPos blockPos3 = worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos.add(k, 0, l));
				Biome biome = worldView.getBiome(blockPos3).value();
				if (blockPos3.getY() > worldView.getBottomY() && blockPos3.getY() <= blockPos.getY() + 10 && blockPos3.getY() >= blockPos.getY() - 10 && biome.getPrecipitation() == Biome.Precipitation.RAIN && biome.doesNotSnow(blockPos3)) {
					blockPos2 = blockPos3.down();
					if (this.client.options.getParticles().get() == ParticlesMode.MINIMAL) {
						break;
					}

					double d = randomGenerator.nextDouble();
					double e = randomGenerator.nextDouble();
					BlockState blockState = worldView.getBlockState(blockPos2);
					FluidState fluidState = worldView.getFluidState(blockPos2);
					VoxelShape voxelShape = blockState.getCollisionShape(worldView, blockPos2);
					double g = voxelShape.getEndingCoord(Direction.Axis.Y, d, e);
					double h = (double) fluidState.getHeight(worldView, blockPos2);
					double m = Math.max(g, h);
					ParticleEffect particleEffect = !fluidState.isIn(FluidTags.LAVA) && !blockState.isOf(Blocks.MAGMA_BLOCK) && !CampfireBlock.isLitCampfire(blockState) ? ParticleTypes.SMOKE : ModParticleTypes.VENUS_RAIN.get();
					this.client.world.addParticle(particleEffect, (double) blockPos2.getX() + d, (double) blockPos2.getY() + m, (double) blockPos2.getZ() + e, 0.0, 0.0, 0.0);
				}
			}

			if (blockPos2 != null && randomGenerator.nextInt(3) < this.rainSoundTicks++) {
				this.rainSoundTicks = 0;
				if (blockPos2.getY() > blockPos.getY() + 1 && worldView.getTopPosition(Heightmap.Type.MOTION_BLOCKING, blockPos).getY() > MathHelper.floor((float) blockPos.getY())) {
					this.client.world.playSound(blockPos2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
				} else {
					this.client.world.playSound(blockPos2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
				}
			}

		}
		return;
	}


	@Inject(at = @At("HEAD"), method = "renderWeather", cancellable = true)
	private void renderWeather(LightmapTextureManager manager, float tickDelta, double x, double y, double z, CallbackInfo info) {
		if (this.client.world != null) {
			ClientModSkies.WeatherRenderer renderer = ClientModSkies.WEATHER_RENDERERS.put(world.getRegistryKey());

			if (renderer != null) {
				renderer.render(context);
				info.cancel();
			}
		}
	}

	@Inject(at = @At("HEAD"), method = "renderClouds(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FDDD)V", cancellable = true)
	private void renderCloud(MatrixStack matrices, Matrix4f matrix4f, float tickDelta, double cameraX, double cameraY, double cameraZ, CallbackInfo info) {
		if (this.client.world != null) {
			ClientModSkies.CloudRenderer renderer = ClientModSkies.CLOUD_RENDERERS.put(world.getRegistryKey());

			if (renderer != null) {
				renderer.render(context);
				info.cancel();
			}
		}
	}

	@Inject(at = @At(value = "INVOKE", target = "Ljava/lang/Runnable;run()V", shift = At.Shift.AFTER, ordinal = 0), method = "renderSky(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/util/math/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", cancellable = true)
	private void renderSky(MatrixStack matrices, Matrix4f matrix4f, float tickDelta, Camera camera, boolean bl, Runnable runnable, CallbackInfo info) {
		if (this.client.world != null) {
			ClientModSkies.SkyRenderer renderer = ClientModSkies.SKY_RENDERERS.put(world.getRegistryKey());

			if (renderer != null) {
				renderer.render(context);
				info.cancel();
			}
		}
	}
}