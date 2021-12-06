package net.mrscauthd.boss_tools.world.structure;

import com.google.common.collect.ImmutableList;
import net.mrscauthd.boss_tools.BossToolsMod;


public class VenusBulletStructure/* extends Structure<NoFeatureConfig>*/ {
    /*
    public VenusBulletStructure(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public  IStartFactory<NoFeatureConfig> getStartFactory() {
        return VenusBulletStructure.Start::new;
    }

    @Override
    public GenerationStage.Decoration getDecorationStage() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }

    private static final List<MobSpawnInfo.Spawners> STRUCTURE_MONSTERS = ImmutableList.of(

    );

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
        return STRUCTURE_MONSTERS;
    }

    private static final List<MobSpawnInfo.Spawners> STRUCTURE_CREATURES = ImmutableList.of(

    );

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultCreatureSpawnList() {
        return STRUCTURE_CREATURES;
    }

    public static class Start extends StructureStart<NoFeatureConfig>  {
        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void func_230364_a_(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoFeatureConfig config) {

            int x = (chunkX << 4) + 7;
            int z = (chunkZ << 4) + 7;

            BlockPos blockpos = new BlockPos(x, 0, z);

            JigsawManager.func_242837_a(dynamicRegistryManager, new VillageConfig(() -> dynamicRegistryManager.getRegistry(Registry.JIGSAW_POOL_KEY)
                    .getOrDefault(new ResourceLocation(BossToolsMod.ModId, "run_venus_bullet/side_venus_bullet_start")),
                    25),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    blockpos,
                    this.components,
                    this.rand,
                    true,
                    true);

            this.components.forEach(piece -> piece.offset(0, 1, 0));
            this.components.forEach(piece -> piece.getBoundingBox().minY -= 1);

            this.recalculateStructureSize();
        }
    }
     */
}