package com.github.alexnijjar.ad_astra.world.chunk;

import com.github.alexnijjar.ad_astra.mixin.ChunkGeneratorAccessor;
import com.github.alexnijjar.ad_astra.mixin.NoiseChunkGeneratorAccessor;
import com.github.alexnijjar.ad_astra.world.WorldSeed;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.util.Holder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryOps;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
import net.minecraft.world.gen.structure.StructureSet;

public class PlanetChunkGenerator extends NoiseChunkGenerator {

	public static final Codec<PlanetChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> method_41042(instance)
			.and(instance.group(RegistryOps.getRegistry(Registry.NOISE_KEY).forGetter((generator) -> ((NoiseChunkGeneratorAccessor) generator).getNoiseRegistry()),
					BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> ((ChunkGeneratorAccessor) generator).getPopulationSource()),
					Codec.LONG.fieldOf("seed").stable().forGetter((generator) -> ((NoiseChunkGeneratorAccessor) generator).getSeed()), ChunkGeneratorSettings.REGISTRY_CODEC.fieldOf("settings").forGetter((generator) -> generator.settings)))
			.apply(instance, instance.stable(PlanetChunkGenerator::new)));

	public PlanetChunkGenerator(Registry<StructureSet> noiseRegistry, Registry<DoublePerlinNoiseSampler.NoiseParameters> structuresRegistry, BiomeSource biomeSource, long seed, Holder<ChunkGeneratorSettings> settings) {
		super(noiseRegistry, structuresRegistry, biomeSource, WorldSeed.getSeed() + seed, settings);
	}

	@Override
	public ChunkGenerator withSeed(long seed) {
		NoiseChunkGeneratorAccessor noiseAccessor = (NoiseChunkGeneratorAccessor) this;
		ChunkGeneratorAccessor chunkAccessor = (ChunkGeneratorAccessor) this;
		return new PlanetChunkGenerator(chunkAccessor.getStructureSet(), noiseAccessor.getNoiseRegistry(), this.biomeSource.withSeed(seed), seed, this.settings);
	}

	@Override
	public void buildSurface(ChunkRegion region, StructureAccessor structures, Chunk chunk) {
		BlockPos.Mutable pos = new BlockPos.Mutable();

		int x;
		int y;
		int z;

		// Generate the Bedrock Layer.
		if (!defaultBlock.isAir()) {
			for (x = 0; x < 16; x++) {
				for (z = 0; z < 16; z++) {
					chunk.setBlockState(pos.set(x, this.getMinimumY(), z), Blocks.BEDROCK.getDefaultState(), false);
				}
			}
		}

		// Generate lava on the Bedrock Layer.
		if (!defaultBlock.isAir()) {
			for (x = 0; x < 16; x++) {
				for (z = 0; z < 16; z++) {
					for (y = 1; y < 9; y++) {
						if (chunk.getBlockState(new BlockPos(x, this.getMinimumY() + y, z)).isAir()) {
							chunk.setBlockState(pos.set(x, this.getMinimumY() + y, z), Blocks.LAVA.getDefaultState(), false);
						}
					}
				}
			}
		}

		super.buildSurface(region, structures, chunk);
	}
}