package com.github.alexnijjar.beyond_earth.world.structures;

import java.util.Optional;
import java.util.function.Predicate;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class SimpleStructure extends StructureFeature<StructurePoolFeatureConfig> {

    public static final Codec<StructurePoolFeatureConfig> CODEC = RecordCodecBuilder
            .create((instance) -> instance.group(StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(StructurePoolFeatureConfig::getStartPool), Codec.intRange(0, 50).fieldOf("size").forGetter(StructurePoolFeatureConfig::getSize)).apply(instance,
                    StructurePoolFeatureConfig::new));

    public SimpleStructure() {
        super(CODEC, context -> SimpleStructure.generate(context, 0, context1 -> true), PostPlacementProcessor.EMPTY);
    }

    public SimpleStructure(int heightOffset) {
        super(CODEC, context -> SimpleStructure.generate(context, heightOffset, context1 -> true), PostPlacementProcessor.EMPTY);
    }

    public SimpleStructure(int heightOffset, Predicate<StructureGeneratorFactory.Context<StructurePoolFeatureConfig>> canPlace) {
        super(CODEC, context -> SimpleStructure.generate(context, heightOffset, canPlace), PostPlacementProcessor.EMPTY);
    }

    @Override
    public GenerationStep.Feature getGenerationStep() {
        return GenerationStep.Feature.SURFACE_STRUCTURES;
    }

    private static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> generate(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context, int heightOffset,
            Predicate<StructureGeneratorFactory.Context<StructurePoolFeatureConfig>> canPlace) {

        if (!canPlace.test(context)) {
            return Optional.empty();
        }

        BlockPos blockpos = context.chunkPos().getCenterAtY(0);

        int topLandY = context.chunkGenerator().getHeightOnGround(blockpos.getX(), blockpos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world());
        blockpos = blockpos.up(topLandY + heightOffset);

        return StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, blockpos, false, false);
    }

}
