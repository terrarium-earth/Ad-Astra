package earth.terrarium.adastra.common.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.adastra.common.registry.ModStructures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.List;
import java.util.Optional;

/*
 * This is a modified version of the JigsawStructure which allows for a depth of up to 50 instead of 20.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class LargeJigsawStructure extends Structure {

    public static final int MAX_DEPTH = 50;
    public static final Codec<LargeJigsawStructure> CODEC = ExtraCodecs.validate(
        RecordCodecBuilder.mapCodec(instance -> instance.group(
                settingsCodec(instance),
                StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(jigsawStructure -> jigsawStructure.startPool),
                ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(jigsawStructure -> jigsawStructure.startJigsawName),
                Codec.intRange(0, MAX_DEPTH).fieldOf("size").forGetter(jigsawStructure -> jigsawStructure.maxDepth),
                HeightProvider.CODEC.fieldOf("start_height").forGetter(jigsawStructure -> jigsawStructure.startHeight),
                Codec.BOOL.fieldOf("use_expansion_hack").forGetter(jigsawStructure -> jigsawStructure.useExpansionHack),
                Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(jigsawStructure -> jigsawStructure.projectStartToHeightmap),
                Codec.intRange(1, JigsawStructure.MAX_TOTAL_STRUCTURE_RANGE).fieldOf("max_distance_from_center").forGetter(jigsawStructure -> jigsawStructure.maxDistanceFromCenter),
                Codec.list(PoolAliasBinding.CODEC).optionalFieldOf("pool_aliases", List.of()).forGetter(jigsawStructure -> jigsawStructure.poolAliases)
            ).apply(instance, LargeJigsawStructure::new)
        ), LargeJigsawStructure::verifyRange
    ).codec();

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int maxDepth;
    private final HeightProvider startHeight;
    private final boolean useExpansionHack;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;
    private final List<PoolAliasBinding> poolAliases;

    private static DataResult<LargeJigsawStructure> verifyRange(LargeJigsawStructure structure) {
        int i = switch (structure.terrainAdaptation()) {
            case NONE -> 0;
            case BURY, BEARD_THIN, BEARD_BOX -> 12;
        };
        return structure.maxDistanceFromCenter + i > 128
            ? DataResult.error(() -> "Structure size including terrain adaptation must not exceed 128")
            : DataResult.success(structure);
    }

    public LargeJigsawStructure(
        Structure.StructureSettings settings,
        Holder<StructureTemplatePool> startPool,
        Optional<ResourceLocation> startJigsawName,
        int maxDepth,
        HeightProvider startHeight,
        boolean useExpansionHack,
        Optional<Heightmap.Types> projectStartToHeightmap,
        int maxDistanceFromCenter,
        List<PoolAliasBinding> poolAliases
    ) {
        super(settings);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.maxDepth = maxDepth;
        this.startHeight = startHeight;
        this.useExpansionHack = useExpansionHack;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
        this.poolAliases = poolAliases;
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int i = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), i, chunkPos.getMinBlockZ());
        return JigsawPlacement.addPieces(
            context,
            this.startPool,
            this.startJigsawName,
            this.maxDepth,
            blockPos,
            this.useExpansionHack,
            this.projectStartToHeightmap,
            this.maxDistanceFromCenter,
            PoolAliasLookup.create(this.poolAliases, blockPos, context.seed())
        );
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.LARGE_JIGSAW_STRUCTURE.get();
    }
}
