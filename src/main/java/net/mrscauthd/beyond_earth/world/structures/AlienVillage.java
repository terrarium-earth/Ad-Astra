package net.mrscauthd.beyond_earth.world.structures;

import java.util.Optional;

import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.mrscauthd.beyond_earth.world.configurator.JigsawConfigurator;

public class AlienVillage extends StructureFeature<StructurePoolFeatureConfig> {

    public AlienVillage() {
        super(JigsawConfigurator.CODEC, AlienVillage::generate, PostPlacementProcessor.EMPTY);
    }

    @Override
    public GenerationStep.Feature getGenerationStep() {
        return GenerationStep.Feature.SURFACE_STRUCTURES;
    }

    private static boolean isFeatureChunk(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        ChunkPos chunkpos = context.chunkPos();

        return !context.chunkGenerator().method_41053(StructureSetKeys.OCEAN_MONUMENTS, context.seed(), chunkpos.x, chunkpos.z, 10);
    }

    public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> generate(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {

        if (!AlienVillage.isFeatureChunk(context)) {
            return Optional.empty();
        }

        BlockPos blockpos = context.chunkPos().getCenterAtY(0);

        int topLandY = context.chunkGenerator().getHeightOnGround(blockpos.getX(), blockpos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world());
        blockpos = blockpos.up(topLandY - 20);

        return StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, blockpos, false, false);
    }
}