package com.github.alexnijjar.beyond_earth.world.structures;

import java.util.Optional;

import com.mojang.serialization.Codec;

import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class MarsTemple extends StructureFeature<StructurePoolFeatureConfig> {

    public MarsTemple(Codec<StructurePoolFeatureConfig> codec) {
        super(codec, MarsTemple::generate, PostPlacementProcessor.EMPTY);
    }

    @Override
    public GenerationStep.Feature getGenerationStep() {
        return GenerationStep.Feature.SURFACE_STRUCTURES;
    }

    private static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        ChunkPos chunkpos = context.chunkPos();

        return !context.chunkGenerator().method_41053(StructureSetKeys.OCEAN_MONUMENTS, context.seed(), chunkpos.x, chunkpos.z, 10);
    }

    private static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> generate(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {

        if (!MarsTemple.isFeatureChunk(context)) {
            return Optional.empty();
        }

        return StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, context.chunkPos().getCenterAtY(0), false, true);
    }
    
}
