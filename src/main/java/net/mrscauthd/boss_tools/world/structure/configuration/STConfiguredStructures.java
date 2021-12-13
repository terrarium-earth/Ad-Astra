package net.mrscauthd.boss_tools.world.structure.configuration;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.mrscauthd.boss_tools.BossToolsMod;

public class STConfiguredStructures {

    public static ConfiguredStructureFeature<?, ?> ALIEN_VILLAGE = STStructures.ALIEN_VILLAGE.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
   // public static ConfiguredStructureFeature<?, ?> METEOR = STStructures.METEOR.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
   // public static ConfiguredStructureFeature<?, ?> VENUS_BULLET = STStructures2.VENUS_BULLET.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
   // public static ConfiguredStructureFeature<?, ?> VENUS_TOWER = STStructures2.VENUS_TOWER.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
   // public static ConfiguredStructureFeature<?, ?> CRIMSON = STStructures2.CRIMSON.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);
   // public static ConfiguredStructureFeature<?, ?> OIL = STStructures.OIL.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "alien_village"), ALIEN_VILLAGE);
       // Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "meteor"), METEOR);
       // Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "venus_bullet"), VENUS_BULLET);
       // Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "venus_tower"), VENUS_TOWER);
       // Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "crimson_village"), CRIMSON);
       // Registry.register(registry, new ResourceLocation(BossToolsMod.ModId, "oil"), OIL);
    }
}
