package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.world.processor.StructureVoidProcessor;
import com.github.alexnijjar.ad_astra.world.structures.SimpleStructure;

import net.minecraft.structure.StructurePiecesGeneratorFactory;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModStructures {

    public static StructureFeature<StructurePoolFeatureConfig> LUNARIAN_VILLAGE = new SimpleStructure(-20);
    public static StructureFeature<StructurePoolFeatureConfig> MOON_DUNGEON = new SimpleStructure(-40, ModStructures::placeAwayFromLunarianVillage);
    public static StructureFeature<StructurePoolFeatureConfig> MARS_TEMPLE = new SimpleStructure(0);
    public static StructureFeature<StructurePoolFeatureConfig> LUNAR_TOWER = new SimpleStructure(0, ModStructures::placeAwayFromLunarianVillage);
    public static StructureFeature<StructurePoolFeatureConfig> METEOR = new SimpleStructure(-20);
    public static StructureFeature<StructurePoolFeatureConfig> OIL_WELL = new SimpleStructure(-10);
    public static StructureFeature<StructurePoolFeatureConfig> PYGRO_TOWER = new SimpleStructure(0);
    public static StructureFeature<StructurePoolFeatureConfig> PYGRO_VILLAGE = new SimpleStructure(0);
    public static StructureFeature<StructurePoolFeatureConfig> VENUS_BULLET = new SimpleStructure(0);

    public static final StructureProcessorType<StructureVoidProcessor> STRUCTURE_VOID_PROCESSOR = () -> StructureVoidProcessor.CODEC;

    public static void register() {
        register("lunarian_village", LUNARIAN_VILLAGE);
        register("lunar_tower", LUNAR_TOWER);
        register("meteor", METEOR);
        register("oil_well", OIL_WELL);
        register("pygro_tower", PYGRO_TOWER);
        register("pygro_village", PYGRO_VILLAGE);
        register("venus_bullet", VENUS_BULLET);

        register("moon_dungeon", MOON_DUNGEON);
        register("mars_temple", MARS_TEMPLE);

        Registry.register(Registry.STRUCTURE_PROCESSOR, new ModIdentifier("structure_void_processor"), STRUCTURE_VOID_PROCESSOR);
    }

    public static void register(String id, StructureFeature<StructurePoolFeatureConfig> feature) {
        Registry.register(Registry.STRUCTURE_FEATURE, new ModIdentifier(id), feature);
    }

    private static boolean placeAwayFromLunarianVillage(StructurePiecesGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
        ChunkPos chunkpos = context.chunkPos();
        return !context.chunkGenerator().method_41053(RegistryKey.of(Registry.STRUCTURE_SET_WORLDGEN, new ModIdentifier("lunarian_village")), context.seed(), chunkpos.x, chunkpos.z, 10);
    }
}