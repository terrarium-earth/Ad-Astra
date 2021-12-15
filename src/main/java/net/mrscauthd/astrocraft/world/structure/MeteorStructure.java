package net.mrscauthd.astrocraft.world.structure;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.mrscauthd.astrocraft.AstroCraftMod;

import java.util.Optional;

public class MeteorStructure extends StructureFeature<JigsawConfiguration> {

    public MeteorStructure(Codec<JigsawConfiguration> codec) {
        super(codec, (context) -> {
            if (!MeteorStructure.isFeatureChunk(context)) {
                return Optional.empty();
            } else {
                return MeteorStructure.createPiecesGenerator(context);
            }
        }, PostPlacementProcessor.NONE);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos blockPos = context.chunkPos().getWorldPosition();

        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor());

        BlockState topBlock = columnOfBlocks.getBlock(landHeight);

        return topBlock.getFluidState().isEmpty();
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos chunkpos = context.chunkPos().getMiddleBlockPosition(0);

        BlockPos blockpos = new BlockPos(chunkpos.getX(), chunkpos.getY() - 18, chunkpos.getZ());

        JigsawConfiguration newConfig = new JigsawConfiguration(() -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                .get(new ResourceLocation(AstroCraftMod.MODID, "run_meteor/side_meteor_start")), 25);

        PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
                context.chunkGenerator(),
                context.biomeSource(),
                context.seed(),
                context.chunkPos(),
                newConfig,
                context.heightAccessor(),
                context.validBiome(),
                context.structureManager(),
                context.registryAccess()
        );

        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(newContext, PoolElementStructurePiece::new, blockpos, false, true);

        return structurePiecesGenerator;
    }
}