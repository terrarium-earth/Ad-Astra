package net.mrscauthd.boss_tools.world.structure.configuration;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.mrscauthd.boss_tools.BossToolsMod;

public class STConfiguredStructures {
    public static StructureFeature<?, ?> ALIEN_VILLAGE = STStructures.ALIEN_VILLAGE.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> METEOR = STStructures.METEOR.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> VENUS_BULLET = STStructures2.VENUS_BULLET.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> VENUS_TOWER = STStructures2.VENUS_TOWER.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> CRIMSON = STStructures2.CRIMSON.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
    public static StructureFeature<?, ?> OIL = STStructures.OIL.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "alien_village"), ALIEN_VILLAGE);
        Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "meteor"), METEOR);
        Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "venus_bullet"), VENUS_BULLET);
        Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "venus_tower"), VENUS_TOWER);
        Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "crimson_village"), CRIMSON);
        Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "oil"), OIL);

        FlatGenerationSettings.STRUCTURES.put(STStructures.ALIEN_VILLAGE.get(), ALIEN_VILLAGE);
        FlatGenerationSettings.STRUCTURES.put(STStructures.METEOR.get(), METEOR);
        FlatGenerationSettings.STRUCTURES.put(STStructures2.VENUS_BULLET.get(), VENUS_BULLET);
        FlatGenerationSettings.STRUCTURES.put(STStructures2.VENUS_TOWER.get(), VENUS_TOWER);
        FlatGenerationSettings.STRUCTURES.put(STStructures2.CRIMSON.get(), CRIMSON);
        FlatGenerationSettings.STRUCTURES.put(STStructures.OIL.get(), OIL);
    }
}
