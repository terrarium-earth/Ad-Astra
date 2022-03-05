package net.mrscauthd.beyond_earth.world.oregen;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.world.biomes.BiomeRegistry;

import java.util.List;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreGeneration {

    /** MOON ORES: */
    public static final TagKey<Block> MOON_ORE_REPLACEABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BeyondEarthMod.MODID, "moon_ore_replaceables"));
    public static final RuleTest MOON_MATCH = new TagMatchTest(MOON_ORE_REPLACEABLES);

    public static Holder<PlacedFeature> moonCheeseOre;
    public static Holder<PlacedFeature> soulSoil;
    public static Holder<PlacedFeature> moonIceShardOre;
    public static Holder<PlacedFeature> moonIronOre;
    public static Holder<PlacedFeature> moonDeshOre;

    /** MARS ORES: */
    public static final TagKey<Block> MARS_ORE_REPLACEABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BeyondEarthMod.MODID, "mars_ore_replaceables"));
    public static final RuleTest MARS_MATCH = new TagMatchTest(MARS_ORE_REPLACEABLES);

    public static Holder<PlacedFeature> marsIceShardOre;
    public static Holder<PlacedFeature> marsIronOre;
    public static Holder<PlacedFeature> marsDiamondOre;
    public static Holder<PlacedFeature> marsOstrumOre;

    /** MERCURY ORES: */
    public static final TagKey<Block> MERCURY_ORE_REPLACEABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BeyondEarthMod.MODID, "mercury_ore_replaceables"));
    public static final RuleTest MERCURY_MATCH = new TagMatchTest(MERCURY_ORE_REPLACEABLES);

    public static Holder<PlacedFeature> mercuryIronOre;

    /** VENUS ORES: */
    public static final TagKey<Block> VENUS_ORE_REPLACEABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BeyondEarthMod.MODID, "venus_ore_replaceables"));
    public static final RuleTest VENUS_MATCH = new TagMatchTest(VENUS_ORE_REPLACEABLES);

    public static Holder<PlacedFeature> venusCoalOre;
    public static Holder<PlacedFeature> venusGoldOre;
    public static Holder<PlacedFeature> venusDiamondOre;
    public static Holder<PlacedFeature> venusCaloriteOre;

    /** GLACIO ORES: */
    public static final TagKey<Block> GLACIO_ORE_REPLACEABLES = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(BeyondEarthMod.MODID, "glacio_ore_replaceables"));
    public static final RuleTest GLACIO_MATCH = new TagMatchTest(GLACIO_ORE_REPLACEABLES);

    public static Holder<PlacedFeature> glacioIceShardOre;
    public static Holder<PlacedFeature> glacioCoalOre;
    public static Holder<PlacedFeature> glacioCopperOre;
    public static Holder<PlacedFeature> glacioIronOre;
    public static Holder<PlacedFeature> glacioLapisOre;

    public static Holder<PlacedFeature> deepslateCoalOre;
    public static Holder<PlacedFeature> deepslateCopperOre;
    public static Holder<PlacedFeature> deepslateIronOre;
    public static Holder<PlacedFeature> deepslateLapisOre;

    @SubscribeEvent
    public static void registerFeature(RegistryEvent.Register<Feature<?>> event) {

        /** MOON */
        moonCheeseOre = PlacementUtils.register("moon_cheese_ore", FeatureUtils.register("moon_cheese_ore", Feature.ORE, new OreConfiguration(MOON_MATCH, ModInit.MOON_CHEESE_ORE.get().defaultBlockState(), 10)), commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "moon_cheese_ore"), moonCheeseOre.value());

        soulSoil = PlacementUtils.register("soul_soil", FeatureUtils.register("soul_soil", Feature.ORE, new OreConfiguration(MOON_MATCH, Blocks.SOUL_SOIL.defaultBlockState(), 60)), commonOrePlacement(22, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "soul_soil"), soulSoil.value());

        moonIceShardOre = PlacementUtils.register("moon_ice_shard_ore", FeatureUtils.register("moon_ice_shard_ore", Feature.ORE, new OreConfiguration(MOON_MATCH, ModInit.MOON_ICE_SHARD_ORE.get().defaultBlockState(), 10)), commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "moon_ice_shard_ore"), moonIceShardOre.value());

        moonIronOre = PlacementUtils.register("moon_iron_ore", FeatureUtils.register("moon_iron_ore", Feature.ORE, new OreConfiguration(MOON_MATCH, ModInit.MOON_IRON_ORE.get().defaultBlockState(), 11)), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "moon_iron_ore"), moonIronOre.value());

        moonDeshOre = PlacementUtils.register("moon_desh_ore", FeatureUtils.register("moon_desh_ore", Feature.ORE, new OreConfiguration(MOON_MATCH, ModInit.MOON_DESH_ORE.get().defaultBlockState(), 9)), commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "moon_desh_ore"), moonDeshOre.value());

        /** MARS */
        marsIceShardOre = PlacementUtils.register("mars_ice_shard_ore", FeatureUtils.register("mars_ice_shard_ore", Feature.ORE, new OreConfiguration(MARS_MATCH, ModInit.MARS_ICE_SHARD_ORE.get().defaultBlockState(), 10)), commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "mars_ice_shard_ore"), marsIceShardOre.value());

        marsIronOre = PlacementUtils.register("mars_iron_ore", FeatureUtils.register("mars_iron_ore", Feature.ORE, new OreConfiguration(MARS_MATCH, ModInit.MARS_IRON_ORE.get().defaultBlockState(), 11)), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "mars_iron_ore"), marsIronOre.value());

        marsDiamondOre = PlacementUtils.register("mars_diamond_ore", FeatureUtils.register("mars_diamond_ore", Feature.ORE, new OreConfiguration(MARS_MATCH, ModInit.MARS_DIAMOND_ORE.get().defaultBlockState(), 7)), commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "mars_diamond_ore"), marsDiamondOre.value());

        marsOstrumOre = PlacementUtils.register("mars_ostrum_ore", FeatureUtils.register("mars_ostrum_ore", Feature.ORE, new OreConfiguration(MARS_MATCH, ModInit.MARS_OSTRUM_ORE.get().defaultBlockState(), 8)), commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "mars_ostrum_ore"), marsOstrumOre.value());

        /** MERCURY */
        mercuryIronOre = PlacementUtils.register("mercury_iron_ore", FeatureUtils.register("mercury_iron_ore", Feature.ORE, new OreConfiguration(MERCURY_MATCH, ModInit.MERCURY_IRON_ORE.get().defaultBlockState(), 8)), commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "mercury_iron_ore"), mercuryIronOre.value());

        /** Venus */
        venusCoalOre = PlacementUtils.register("venus_coal_ore", FeatureUtils.register("venus_coal_ore", Feature.ORE, new OreConfiguration(VENUS_MATCH, ModInit.VENUS_COAL_ORE.get().defaultBlockState(), 17)), commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "venus_coal_ore"), venusCoalOre.value());

        venusGoldOre = PlacementUtils.register("venus_gold_ore", FeatureUtils.register("venus_gold_ore", Feature.ORE, new OreConfiguration(VENUS_MATCH, ModInit.VENUS_GOLD_ORE.get().defaultBlockState(), 10)), commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "venus_gold_ore"), venusGoldOre.value());

        venusDiamondOre = PlacementUtils.register("venus_diamond_ore", FeatureUtils.register("venus_diamond_ore", Feature.ORE, new OreConfiguration(VENUS_MATCH, ModInit.VENUS_DIAMOND_ORE.get().defaultBlockState(), 9)), commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "venus_diamond_ore"), venusDiamondOre.value());

        venusCaloriteOre = PlacementUtils.register("venus_calorite_ore", FeatureUtils.register("venus_calorite_ore", Feature.ORE, new OreConfiguration(VENUS_MATCH, ModInit.VENUS_CALORITE_ORE.get().defaultBlockState(), 8)), commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "venus_calorite_ore"), venusCaloriteOre.value());

        /** Glacio */
        glacioIceShardOre = PlacementUtils.register("glacio_ice_shard_ore", FeatureUtils.register("glacio_ice_shard_ore", Feature.ORE, new OreConfiguration(GLACIO_MATCH, ModInit.GLACIO_ICE_SHARD_ORE.get().defaultBlockState(), 10)), commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "glacio_ice_shard_ore"), glacioIceShardOre.value());

        glacioCoalOre = PlacementUtils.register("glacio_coal_ore", FeatureUtils.register("glacio_coal_ore", Feature.ORE, new OreConfiguration(GLACIO_MATCH, ModInit.GLACIO_COAL_ORE.get().defaultBlockState(), 17)), commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "glacio_coal_ore"), glacioCoalOre.value());

        glacioCopperOre = PlacementUtils.register("glacio_copper_ore", FeatureUtils.register("glacio_copper_ore", Feature.ORE, new OreConfiguration(GLACIO_MATCH, ModInit.GLACIO_COPPER_ORE.get().defaultBlockState(), 17)), commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "glacio_copper_ore"), glacioCopperOre.value());

        glacioIronOre = PlacementUtils.register("glacio_iron_ore", FeatureUtils.register("glacio_iron_ore", Feature.ORE, new OreConfiguration(GLACIO_MATCH, ModInit.GLACIO_IRON_ORE.get().defaultBlockState(), 12)), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "glacio_iron_ore"), glacioIronOre.value());

        glacioLapisOre  = PlacementUtils.register("glacio_lapis_ore", FeatureUtils.register("glacio_lapis_ore", Feature.ORE, new OreConfiguration(GLACIO_MATCH, ModInit.GLACIO_LAPIS_ORE.get().defaultBlockState(), 9)), commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "glacio_lapis_ore"), glacioLapisOre.value());

        /** Glacio Deepslate */
        deepslateCoalOre = PlacementUtils.register("deepslate_coal_ore", FeatureUtils.register("deepslate_coal_ore", Feature.ORE, new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(), 17)), commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "deepslate_coal_ore"), deepslateCoalOre.value());

        deepslateCopperOre = PlacementUtils.register("deepslate_copper_ore", FeatureUtils.register("deepslate_copper_ore", Feature.ORE, new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState(), 17)), commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(112))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "deepslate_copper_ore"), deepslateCopperOre.value());

        deepslateIronOre = PlacementUtils.register("deepslate_iron_ore", FeatureUtils.register("deepslate_iron_ore", Feature.ORE, new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(), 12)), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(20))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "deepslate_iron_ore"), deepslateIronOre.value());

        deepslateLapisOre = PlacementUtils.register("deepslate_lapis_ore", FeatureUtils.register("deepslate_lapis_ore", Feature.ORE, new OreConfiguration(OreFeatures.DEEPSLATE_ORE_REPLACEABLES, Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(), 9)), commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(10))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "deepslate_lapis_ore"), deepslateLapisOre.value());
    }

    /** BIOME ORE GEN */
    public static void biomesLoading(BiomeLoadingEvent event) {
        if (event.getName().getPath().equals(BiomeRegistry.moon.getRegistryName().getPath())) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(moonIceShardOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(moonIronOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(moonDeshOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(moonCheeseOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(soulSoil);
        }

        if (event.getName().getPath().equals(BiomeRegistry.mars.getRegistryName().getPath()) || event.getName().getPath().equals(BiomeRegistry.mars_ice_spikes.getRegistryName().getPath())) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(marsIceShardOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(marsIronOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(marsDiamondOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(marsOstrumOre);
        }

        if (event.getName().getPath().equals(BiomeRegistry.mercury.getRegistryName().getPath())) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(mercuryIronOre);
        }

        if (event.getName().getPath().equals(BiomeRegistry.venus.getRegistryName().getPath()) || event.getName().getPath().equals(BiomeRegistry.infernal_venus_barrens.getRegistryName().getPath())) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(venusCoalOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(venusGoldOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(venusDiamondOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(venusCaloriteOre);
        }

        if (event.getName().getPath().equals(BiomeRegistry.glacio.getRegistryName().getPath()) || event.getName().getPath().equals(BiomeRegistry.glacio_ice_spikes.getRegistryName().getPath())) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(glacioIceShardOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(glacioCoalOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(glacioIronOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(glacioCopperOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(glacioLapisOre);

            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(deepslateCoalOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(deepslateIronOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(deepslateCopperOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(deepslateLapisOre);
        }
    }

    /** ORE PLACEMENT */
    private static List<PlacementModifier> orePlacement(PlacementModifier p_195347_, PlacementModifier p_195348_) {
        return List.of(p_195347_, InSquarePlacement.spread(), p_195348_, BiomeFilter.biome());
    }

    private static List<PlacementModifier> commonOrePlacement(int p_195344_, PlacementModifier p_195345_) {
        return orePlacement(CountPlacement.of(p_195344_), p_195345_);
    }

    private static List<PlacementModifier> rareOrePlacement(int p_195350_, PlacementModifier p_195351_) {
        return orePlacement(RarityFilter.onAverageOnceEvery(p_195350_), p_195351_);
    }
}
