package net.mrscauthd.beyond_earth.world.structure.configuration;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

public class STConfiguredStructures {

    public static ConfiguredStructureFeature<?, ?> ALIEN_VILLAGE = STStructures.ALIEN_VILLAGE.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> METEOR = STStructures.METEOR.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> VENUS_BULLET = STStructures.VENUS_BULLET.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> VENUS_TOWER = STStructures.VENUS_TOWER.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> CRIMSON = STStructures.CRIMSON.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
    public static ConfiguredStructureFeature<?, ?> OIL = STStructures.OIL.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

    public static void registerConfiguredStructures() {
        Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(BeyondEarthMod.MODID, "alien_village"), ALIEN_VILLAGE);
        Registry.register(registry, new ResourceLocation(BeyondEarthMod.MODID, "meteor"), METEOR);
        Registry.register(registry, new ResourceLocation(BeyondEarthMod.MODID, "venus_bullet"), VENUS_BULLET);
        Registry.register(registry, new ResourceLocation(BeyondEarthMod.MODID, "venus_tower"), VENUS_TOWER);
        Registry.register(registry, new ResourceLocation(BeyondEarthMod.MODID, "crimson_village"), CRIMSON);
        Registry.register(registry, new ResourceLocation(BeyondEarthMod.MODID, "oil"), OIL);
    }
}
