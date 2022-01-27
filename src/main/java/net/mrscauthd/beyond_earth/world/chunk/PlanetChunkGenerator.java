package net.mrscauthd.beyond_earth.world.chunk;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.SharedConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryLookupCodec;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.*;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.*;
import net.minecraft.world.level.levelgen.*;
import net.minecraft.world.level.levelgen.blending.Blender;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.mrscauthd.beyond_earth.world.chunk.seedfixer.SeedFixer;

import java.util.function.Supplier;

public class PlanetChunkGenerator extends NoiseBasedChunkGenerator {

    public static final Codec<PlanetChunkGenerator> CODEC = RecordCodecBuilder.create((p_188643_) -> {
        return p_188643_.group(RegistryLookupCodec.create(Registry.NOISE_REGISTRY).forGetter((p_188716_) -> {
            return p_188716_.noises;
        }), BiomeSource.CODEC.fieldOf("biome_source").forGetter((p_188711_) -> {
            return p_188711_.biomeSource;
        }), Codec.LONG.fieldOf("seed").stable().forGetter((p_188690_) -> {
            return p_188690_.seed;
        }), NoiseGeneratorSettings.CODEC.fieldOf("settings").forGetter((p_188652_) -> {
            return p_188652_.settings;
        })).apply(p_188643_, p_188643_.stable(PlanetChunkGenerator::new));
    });

    public PlanetChunkGenerator(Registry<NormalNoise.NoiseParameters> p_188609_, BiomeSource p_188610_, long p_188611_, Supplier<NoiseGeneratorSettings> p_188612_) {
        super(p_188609_, p_188610_, p_188611_ == 0 ? SeedFixer.getSeed() : p_188611_, p_188612_);
    }

    @Override
    public void buildSurface(WorldGenRegion p_188636_, StructureFeatureManager p_188637_, ChunkAccess p_188638_) {
        BlockState bedrock = Blocks.BEDROCK.defaultBlockState();

        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        int x;
        int y;
        int z;

        /**Gen Bedrock Layer*/
        if (defaultBlock != Blocks.AIR.defaultBlockState()) {
            for (x = 0; x < 16; x++) {
                for (z = 0; z < 16; z++) {
                    p_188638_.setBlockState(pos.set(x, getMinY(), z), bedrock, false);
                }
            }
        }

        /**Gen Lava on the Bedrock Layer*/
        if (defaultBlock != Blocks.AIR.defaultBlockState()) {
            for (x = 0; x < 16; x++) {
                for (z = 0; z < 16; z++) {
                    for (y = 1; y < 9; y++) {
                        if (p_188638_.getBlockState(new BlockPos(x, getMinY() + y, z)).isAir()) {
                            p_188638_.setBlockState(pos.set(x, getMinY() + y, z), Blocks.LAVA.defaultBlockState(), false);
                        }
                    }
                }
            }
        }

        if (!SharedConstants.debugVoidTerrain(p_188638_.getPos())) {
            WorldGenerationContext worldgenerationcontext = new WorldGenerationContext(this, p_188636_);
            NoiseGeneratorSettings noisegeneratorsettings = this.settings.get();
            NoiseChunk noisechunk = p_188638_.getOrCreateNoiseChunk(this.sampler, () -> {
                return new Beardifier(p_188637_, p_188638_);
            }, noisegeneratorsettings, this.globalFluidPicker, Blender.of(p_188636_));
            this.surfaceSystem.buildSurface(p_188636_.getBiomeManager(), p_188636_.registryAccess().registryOrThrow(Registry.BIOME_REGISTRY), noisegeneratorsettings.useLegacyRandomSource(), worldgenerationcontext, p_188638_, noisechunk, noisegeneratorsettings.surfaceRule());
        }
    }
}
