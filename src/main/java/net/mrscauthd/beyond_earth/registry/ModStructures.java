package net.mrscauthd.beyond_earth.registry;

import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.world.processor.StructureVoidProcessor;
import net.mrscauthd.beyond_earth.world.structures.*;

public class ModStructures {

    public static StructureFeature<StructurePoolFeatureConfig> ALIEN_VILLAGE = new AlienVillage();
    public static StructureFeature<StructurePoolFeatureConfig> METEOR = new Meteor(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> OIL_WELL = new OilWell(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> PYGRO_TOWER = new PygroTower(StructurePoolFeatureConfig.CODEC);
    public static StructureFeature<StructurePoolFeatureConfig> PYGRO_VILLAGE = new PygroVillage();
    public static StructureFeature<StructurePoolFeatureConfig> VENUS_BULLET = new VenusBullet(StructurePoolFeatureConfig.CODEC);

    public static final StructureProcessorType<StructureVoidProcessor> STRUCTURE_VOID_PROCESSOR = () -> StructureVoidProcessor.CODEC;

    public static void register() {
        register("alien_village", ALIEN_VILLAGE);
        register("meteor", METEOR);
        register("oil_well", OIL_WELL);
        register("pygro_tower", PYGRO_TOWER);
        register("pygro_village", PYGRO_VILLAGE);
        register("venus_bullet", VENUS_BULLET);

        Registry.register(Registry.STRUCTURE_PROCESSOR, new ModIdentifier("structure_void_processor"), STRUCTURE_VOID_PROCESSOR);
    }

    public static void register(String id, StructureFeature<StructurePoolFeatureConfig> feature) {
        Registry.register(Registry.STRUCTURE_FEATURE, new ModIdentifier(id), feature);
    }
}
