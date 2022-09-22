package com.github.alexnijjar.ad_astra.mixin;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.github.alexnijjar.ad_astra.entities.mobs.PygroBruteEntity;
import com.github.alexnijjar.ad_astra.entities.mobs.PygroEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.PiglinSpecificSensor;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

@Mixin(PiglinSpecificSensor.class)
public class PiglinSpecificSensorMixin {

	// Disable Pygros running away from soul blocks.
	@Inject(method = "findPiglinRepellent", at = @At("HEAD"), cancellable = true)
	private static void adastra_findPiglinRepellent(ServerWorld world, LivingEntity entity, CallbackInfoReturnable<Optional<BlockPos>> ci) {
		if (entity instanceof PygroEntity || entity instanceof PygroBruteEntity) {
			ci.setReturnValue(BlockPos.findClosest(entity.getBlockPos(), 8, 4, pos -> false));
		}
	}

	// Disable Pygros attacking players without gold.
	@Inject(method = "sense", at = @At("TAIL"))
	public void adastra_sense(ServerWorld world, LivingEntity entity, CallbackInfo ci) {
		if (entity instanceof PygroEntity || entity instanceof PygroBruteEntity) {
			entity.getBrain().forget(MemoryModuleType.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GOLD);
		}
	}
}
