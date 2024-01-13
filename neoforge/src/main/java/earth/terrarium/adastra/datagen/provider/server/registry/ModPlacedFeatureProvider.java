package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

@SuppressWarnings("SameParameterValue")
public class ModPlacedFeatureProvider {
    public static final ResourceKey<PlacedFeature> MOON_CHEESE_ORE = register("moon_cheese_ore");
    public static final ResourceKey<PlacedFeature> MOON_DESH_ORE = register("moon_desh_ore");
    public static final ResourceKey<PlacedFeature> MOON_ICE_SHARD_ORE = register("moon_ice_shard_ore");
    public static final ResourceKey<PlacedFeature> MOON_IRON_ORE = register("moon_iron_ore");
    public static final ResourceKey<PlacedFeature> MOON_SOUL_SOIL = register("moon_soul_soil");

    public static final ResourceKey<PlacedFeature> MARS_DIAMOND_ORE = register("mars_diamond_ore");
    public static final ResourceKey<PlacedFeature> MARS_ICE_SHARD_ORE = register("mars_ice_shard_ore");
    public static final ResourceKey<PlacedFeature> MARS_IRON_ORE = register("mars_iron_ore");
    public static final ResourceKey<PlacedFeature> MARS_OSTRUM_ORE = register("mars_ostrum_ore");

    public static final ResourceKey<PlacedFeature> VENUS_CALORITE_ORE = register("venus_calorite_ore");
    public static final ResourceKey<PlacedFeature> VENUS_COAL_ORE = register("venus_coal_ore");
    public static final ResourceKey<PlacedFeature> VENUS_DIAMOND_ORE = register("venus_diamond_ore");
    public static final ResourceKey<PlacedFeature> VENUS_GOLD_ORE = register("venus_gold_ore");

    public static final ResourceKey<PlacedFeature> MERCURY_IRON_ORE = register("mercury_iron_ore");

    public static final ResourceKey<PlacedFeature> GLACIO_COAL_ORE = register("glacio_coal_ore");
    public static final ResourceKey<PlacedFeature> GLACIO_COPPER_ORE = register("glacio_copper_ore");
    public static final ResourceKey<PlacedFeature> GLACIO_ICE_SHARD_ORE = register("glacio_ice_shard_ore");
    public static final ResourceKey<PlacedFeature> GLACIO_IRON_ORE = register("glacio_iron_ore");
    public static final ResourceKey<PlacedFeature> GLACIO_LAPIS_ORE = register("glacio_lapis_ore");
    public static final ResourceKey<PlacedFeature> GLACIO_DEEPSLATE_COAL_ORE = register("glacio_deepslate_coal_ore");
    public static final ResourceKey<PlacedFeature> GLACIO_DEEPSLATE_COPPER_ORE = register("glacio_deepslate_copper_ore");
    public static final ResourceKey<PlacedFeature> GLACIO_DEEPSLATE_IRON_ORE = register("glacio_deepslate_iron_ore");
    public static final ResourceKey<PlacedFeature> GLACIO_DEEPSLATE_LAPIS_ORE = register("glacio_deepslate_lapis_ore");

    public static final ResourceKey<PlacedFeature> MARS_ROCK = register("mars_rock");
    public static final ResourceKey<PlacedFeature> LARGE_INFERNAL_SPIRE_COLUMN = register("large_infernal_spire_column");
    public static final ResourceKey<PlacedFeature> SMALL_INFERNAL_SPIRE_COLUMN = register("small_infernal_spire_column");

    private static ResourceKey<PlacedFeature> register(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> holderGetter = context.lookup(Registries.CONFIGURED_FEATURE);

        var moonCheeseHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MOON_CHEESE_ORE);
        var moonDeshHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MOON_DESH_ORE);
        var moonIceShardHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MOON_ICE_SHARD_ORE);
        var moonIronHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MOON_IRON_ORE);
        var moonSoulSoilHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MOON_SOUL_SOIL);

        var marsDiamondHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MARS_DIAMOND_ORE);
        var marsIceShardHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MARS_ICE_SHARD_ORE);
        var marsIronHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MARS_IRON_ORE);
        var marsOstrumHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MARS_OSTRUM_ORE);

        var venusCaloriteHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.VENUS_CALORITE_ORE);
        var venusCoalHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.VENUS_COAL_ORE);
        var venusDiamondHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.VENUS_DIAMOND_ORE);
        var venusGoldHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.VENUS_GOLD_ORE);

        var mercuryIronHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MERCURY_IRON_ORE);

        var glacioCoalHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_COAL_ORE);
        var glacioCopperHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_COPPER_ORE);
        var glacioIceShardHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_ICE_SHARD_ORE);
        var glacioIronHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_IRON_ORE);
        var glacioLapisHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_LAPIS_ORE);
        var glacioDeepslateCoalHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_DEEPSLATE_COAL_ORE);
        var glacioDeepslateCopperHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_DEEPSLATE_COPPER_ORE);
        var glacioDeepslateIronHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_DEEPSLATE_IRON_ORE);
        var glacioDeepslateLapisHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.GLACIO_DEEPSLATE_LAPIS_ORE);

        var marsRockHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.MARS_ROCK);
        var largeInfernalSpireColumnHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.LARGE_INFERNAL_SPIRE_COLUMN);
        var smallInfernalSpireColumnHolder = holderGetter.getOrThrow(ModConfiguredFeatureProvider.SMALL_INFERNAL_SPIRE_COLUMN);

        PlacementUtils.register(context, MOON_CHEESE_ORE, moonCheeseHolder, commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(6), VerticalAnchor.absolute(192))));
        PlacementUtils.register(context, MOON_DESH_ORE, moonDeshHolder, commonOrePlacement(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        PlacementUtils.register(context, MOON_ICE_SHARD_ORE, moonIceShardHolder, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        PlacementUtils.register(context, MOON_IRON_ORE, moonIronHolder, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        PlacementUtils.register(context, MOON_SOUL_SOIL, moonSoulSoilHolder, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));

        PlacementUtils.register(context, MARS_DIAMOND_ORE, marsDiamondHolder, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        PlacementUtils.register(context, MARS_ICE_SHARD_ORE, marsIceShardHolder, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        PlacementUtils.register(context, MARS_IRON_ORE, marsIronHolder, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        PlacementUtils.register(context, MARS_OSTRUM_ORE, marsOstrumHolder, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));

        PlacementUtils.register(context, VENUS_CALORITE_ORE, venusCaloriteHolder, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        PlacementUtils.register(context, VENUS_COAL_ORE, venusCoalHolder, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        PlacementUtils.register(context, VENUS_DIAMOND_ORE, venusDiamondHolder, commonOrePlacement(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        PlacementUtils.register(context, VENUS_GOLD_ORE, venusGoldHolder, commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));

        PlacementUtils.register(context, MERCURY_IRON_ORE, mercuryIronHolder, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));

        PlacementUtils.register(context, GLACIO_COAL_ORE, glacioCoalHolder, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        PlacementUtils.register(context, GLACIO_COPPER_ORE, glacioCopperHolder, commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
        PlacementUtils.register(context, GLACIO_ICE_SHARD_ORE, glacioIceShardHolder, commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        PlacementUtils.register(context, GLACIO_IRON_ORE, glacioIronHolder, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        PlacementUtils.register(context, GLACIO_LAPIS_ORE, glacioLapisHolder, commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));

        PlacementUtils.register(context, GLACIO_DEEPSLATE_COAL_ORE, glacioDeepslateCoalHolder, commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        PlacementUtils.register(context, GLACIO_DEEPSLATE_COPPER_ORE, glacioDeepslateCopperHolder, commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(112))));
        PlacementUtils.register(context, GLACIO_DEEPSLATE_IRON_ORE, glacioDeepslateIronHolder, commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(20))));
        PlacementUtils.register(context, GLACIO_DEEPSLATE_LAPIS_ORE, glacioDeepslateLapisHolder, commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(10))));

        PlacementUtils.register(context, MARS_ROCK, marsRockHolder, CountPlacement.of(2), InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome());
        PlacementUtils.register(context, SMALL_INFERNAL_SPIRE_COLUMN, smallInfernalSpireColumnHolder, CountOnEveryLayerPlacement.of(4), BiomeFilter.biome());
        PlacementUtils.register(context, LARGE_INFERNAL_SPIRE_COLUMN, largeInfernalSpireColumnHolder, CountOnEveryLayerPlacement.of(2), BiomeFilter.biome());
    }

    private static List<PlacementModifier> orePlacement(PlacementModifier countPlacement, PlacementModifier heightRange) {
        return List.of(countPlacement, InSquarePlacement.spread(), heightRange, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int count, PlacementModifier heightRange) {
        return orePlacement(CountPlacement.of(count), heightRange);
    }
}
