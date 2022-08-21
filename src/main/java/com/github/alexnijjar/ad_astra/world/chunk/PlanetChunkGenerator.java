// package com.github.alexnijjar.ad_astra.world.chunk;

// import com.github.alexnijjar.ad_astra.mixin.NoiseChunkGeneratorAccessor;
// import com.mojang.serialization.Codec;
// import com.mojang.serialization.codecs.RecordCodecBuilder;

// import net.minecraft.block.BlockState;
// import net.minecraft.block.Blocks;
// import net.minecraft.structure.StructureSet;
// import net.minecraft.util.dynamic.RegistryOps;
// import net.minecraft.util.math.BlockPos;
// import net.minecraft.util.math.noise.DoublePerlinNoiseSampler;
// import net.minecraft.util.registry.Registry;
// import net.minecraft.util.registry.RegistryEntry;
// import net.minecraft.world.ChunkRegion;
// import net.minecraft.world.biome.source.BiomeSource;
// import net.minecraft.world.chunk.Chunk;
// import net.minecraft.world.gen.StructureAccessor;
// import net.minecraft.world.gen.chunk.ChunkGeneratorSettings;
// import net.minecraft.world.gen.chunk.NoiseChunkGenerator;
// import net.minecraft.world.gen.noise.NoiseConfig;

// public class PlanetChunkGenerator extends NoiseChunkGenerator {

//     public static final Codec<PlanetChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
//         return createStructureSetRegistryGetter(instance).and(instance.group(RegistryOps.createRegistryCodec(Registry.NOISE_KEY).forGetter((generator) -> {
//             return ((NoiseChunkGeneratorAccessor)generator).getNoiseRegistry();
//         }), BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> {
//             return ((NoiseChunkGeneratorAccessor)generator).getBiomeSource();
//         }), ChunkGeneratorSettings.CODEC.fieldOf("settings").forGetter((generator) -> {
//             return generator.getSettings().value();
//         }))).apply(instance, instance.stable(PlanetChunkGenerator::new));
//     });


    
//     public PlanetChunkGenerator(Registry<StructureSet> structureSetRegistry, Registry<DoublePerlinNoiseSampler.NoiseParameters> noiseRegistry, BiomeSource populationSource, RegistryEntry<ChunkGeneratorSettings> registryEntry) {
//         super(structureSetRegistry, noiseRegistry, populationSource, registryEntry);
//     }

//     @Override
//     public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
//         BlockState bedrock = Blocks.BEDROCK.getDefaultState();
//         BlockPos.Mutable pos = new BlockPos.Mutable();

//         int x;
//         int y;
//         int z;

//         // Generate the Bedrock Layer.
//         if (!defaultBlock.isAir()) {
//             for (x = 0; x < 16; x++) {
//                 for (z = 0; z < 16; z++) {
//                     chunk.setBlockState(pos.set(x, this.getMinimumY(), z), bedrock, false);
//                 }
//             }
//         }

//         // Generate lava on the Bedrock Layer.
//         if (!defaultBlock.isAir()) {
//             for (x = 0; x < 16; x++) {
//                 for (z = 0; z < 16; z++) {
//                     for (y = 1; y < 9; y++) {
//                         if (chunk.getBlockState(new BlockPos(x, this.getMinimumY() + y, z)).isAir()) {
//                             chunk.setBlockState(pos.set(x, this.getMinimumY() + y, z), Blocks.LAVA.getDefaultState(), false);
//                         }
//                     }
//                 }
//             }
//         }

//         super.buildSurface(region, structures, noiseConfig, chunk);
//     }
// }