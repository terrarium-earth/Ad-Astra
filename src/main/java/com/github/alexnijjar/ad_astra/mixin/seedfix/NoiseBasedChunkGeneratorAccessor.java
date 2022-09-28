package com.github.alexnijjar.ad_astra.mixin.seedfix;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.util.Holder;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;

@Mixin(NoiseChunkGenerator.class)
public interface NoiseBasedChunkGeneratorAccessor {
    @Accessor("noiseRegistry")
	Registry<DoublePerlinNoiseSampler.NoiseParameters> getNoiseRegistry();

    @Accessor("seed")
    long getSeed();

    @Accessor("settings")
    Holder<ChunkGeneratorSettings> getSettings();
}
