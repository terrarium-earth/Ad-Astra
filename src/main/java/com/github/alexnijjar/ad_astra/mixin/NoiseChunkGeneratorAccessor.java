package com.github.alexnijjar.ad_astra.mixin;

import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NoiseChunkGenerator.class)
public interface NoiseChunkGeneratorAccessor {

	@Accessor("noiseRegistry")
	Registry<DoublePerlinNoiseSampler.NoiseParameters> getNoiseRegistry();

	@Accessor("seed")
	long getSeed();
}