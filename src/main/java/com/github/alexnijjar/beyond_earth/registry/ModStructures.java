package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.github.alexnijjar.beyond_earth.world.processor.StructureVoidProcessor;
import com.github.alexnijjar.beyond_earth.world.structures.LunarianVillage;
import com.github.alexnijjar.beyond_earth.world.structures.MarsTemple;
import com.github.alexnijjar.beyond_earth.world.structures.Meteor;
import com.github.alexnijjar.beyond_earth.world.structures.MoonDungeon;
import com.github.alexnijjar.beyond_earth.world.structures.OilWell;
import com.github.alexnijjar.beyond_earth.world.structures.PygroTower;
import com.github.alexnijjar.beyond_earth.world.structures.PygroVillage;
import com.github.alexnijjar.beyond_earth.world.structures.VenusBullet;

import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

public class ModStructures {

    public static StructureFeature<StructurePoolFeatureConfig> LUNARIAN_VILLAGE = new LunarianVillage();
    public static StructureFeature<StructurePoolFeatureConfig> MOON_DUNGEON = new MoonDungeon();
    public static StructureFeature<StructurePoolFeatureConfig> MARS_TEMPLE = new MarsTemple(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> METEOR = new Meteor(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> OIL_WELL = new OilWell(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> PYGRO_TOWER = new PygroTower(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> PYGRO_VILLAGE = new PygroVillage();
    public static StructureFeature<StructurePoolFeatureConfig> VENUS_BULLET = new VenusBullet(StructurePoolFeatureConfig.CODEC);

    public static final StructureProcessorType<StructureVoidProcessor> STRUCTURE_VOID_PROCESSOR = () -> StructureVoidProcessor.CODEC;

    public static void register() {
        register("lunarian_village", LUNARIAN_VILLAGE);
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
}