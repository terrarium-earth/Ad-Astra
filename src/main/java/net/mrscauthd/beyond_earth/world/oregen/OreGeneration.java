package net.mrscauthd.beyond_earth.world.oregen;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.OreFeature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.world.biomes.BiomeRegistry;

import java.util.List;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreGeneration {

    /** MOON ORES: */
    private static RuleTest MOON_MATCH = new BlockMatchTest(Blocks.NETHERRACK);

    private static Feature<OreConfiguration> MOON_GEN_FEATURE;

    private static Holder<PlacedFeature> moonCheeseOre;
    private static Holder<PlacedFeature> soulSoil;
    private static Holder<PlacedFeature> moonIceShardOre;
    private static Holder<PlacedFeature> moonIronOre;
    private static Holder<PlacedFeature> moonDeshOre;

    /** MARS ORES: */
   // public static RuleTestType<RuleTests.MarsRuleTest> MARS_MATCH;
    private static Feature<OreConfiguration> MARS_GEN_FEATURE;

    private static Holder<PlacedFeature> marsIceShardOre;
    private static Holder<PlacedFeature> marsIronOre;
    private static Holder<PlacedFeature> marsDiamondOre;
    private static Holder<PlacedFeature> marsOstrumOre;

    /** MERCURY ORES: */
   // public static RuleTestType<RuleTests.MercuryRuleTest> MERCURY_MATCH;
    private static Feature<OreConfiguration> MERCURY_GEN_FEATURE;

    private static Holder<PlacedFeature> mercuryIronOre;

    /** VENUS ORES: */
    //public static RuleTestType<RuleTests.VenusRuleTest> VENUS_MATCH;
    private static Feature<OreConfiguration> VENUS_GEN_FEATURE;

    private static Holder<PlacedFeature> venusCoalOre;
    private static Holder<PlacedFeature> venusGoldOre;
    private static Holder<PlacedFeature> venusDiamondOre;
    private static Holder<PlacedFeature> venusCaloriteOre;

    /** GLACIO ORES: */
    //public static RuleTestType<RuleTests.GlacioRuleTest> GLACIO_MATCH;
    //public static RuleTestType<RuleTests.GlacioDeepslateRuleTest> GLACIO_DEEPSLATE_MATCH;
    private static Feature<OreConfiguration> GLACIO_GEN_FEATURE;

    private static Holder<PlacedFeature> glacioIceShardOre;
    private static Holder<PlacedFeature> glacioCoalOre;
    private static Holder<PlacedFeature> glacioCopperOre;
    private static Holder<PlacedFeature> glacioIronOre;
    private static Holder<PlacedFeature> glacioLapisOre;

    private static Holder<PlacedFeature> deepslateCoalOre;
    private static Holder<PlacedFeature> deepslateCopperOre;
    private static Holder<PlacedFeature> deepslateIronOre;
    private static Holder<PlacedFeature> deepslateLapisOre;

    @SubscribeEvent
    public static void registerFeature(RegistryEvent.Register<Feature<?>> event) {

        /** MOON */
        MOON_GEN_FEATURE = new OreFeature(OreConfiguration.CODEC);
        event.getRegistry().register(MOON_GEN_FEATURE.setRegistryName("moon_ore"));

        moonCheeseOre = PlacementUtils.register("moon_cheese_ore", FeatureUtils.register("moon_cheese_ore", Feature.ORE, new OreConfiguration(MOON_MATCH, ModInit.MOON_CHEESE_ORE.get().defaultBlockState(), 10)), commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "moon_cheese_ore"), moonCheeseOre.value());

        soulSoil = PlacementUtils.register("soul_soil", FeatureUtils.register("soul_soil", Feature.ORE, new OreConfiguration(MOON_MATCH, Blocks.SOUL_SOIL.defaultBlockState(), 60)), commonOrePlacement(22, HeightRangePlacement.triangle(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "soul_soil"), soulSoil.value());

        moonIceShardOre  = PlacementUtils.register("moon_ice_shard_ore", FeatureUtils.register("moon_ice_shard_ore", Feature.ORE, new OreConfiguration(MOON_MATCH, ModInit.MOON_ICE_SHARD_ORE.get().defaultBlockState(), 10)), commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "moon_ice_shard_ore"), moonIceShardOre.value());

        moonIronOre  = PlacementUtils.register("moon_iron_ore", FeatureUtils.register("moon_iron_ore", Feature.ORE, new OreConfiguration(MOON_MATCH, ModInit.MOON_IRON_ORE.get().defaultBlockState(), 11)), commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "moon_iron_ore"), moonIronOre.value());

        moonDeshOre  = PlacementUtils.register("moon_desh_ore", FeatureUtils.register("moon_desh_ore", Feature.ORE, new OreConfiguration(MOON_MATCH, ModInit.MOON_DESH_ORE.get().defaultBlockState(), 9)), commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID, "moon_desh_ore"), moonDeshOre.value());

        /** MARS */
    /*    MARS_GEN_FEATURE = new OreFeature(OreConfiguration.CODEC);
        event.getRegistry().register(MARS_GEN_FEATURE.setRegistryName("mars_ore"));

        marsIceShardOre = MARS_GEN_FEATURE.configured(new OreConfiguration(RuleTests.MarsRuleTest.INSTANCE, ModInit.MARS_ICE_SHARD_ORE.get().defaultBlockState(), 10)).placed(commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"mars_ice_shard_ore"), marsIceShardOre);

        marsIronOre = MARS_GEN_FEATURE.configured(new OreConfiguration(RuleTests.MarsRuleTest.INSTANCE, ModInit.MARS_IRON_ORE.get().defaultBlockState(), 11)).placed(commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"mars_iron_ore"), marsIronOre);

        marsDiamondOre = MARS_GEN_FEATURE.configured(new OreConfiguration(RuleTests.MarsRuleTest.INSTANCE, ModInit.MARS_DIAMOND_ORE.get().defaultBlockState(), 7)).placed(commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"mars_diamond_ore"), marsDiamondOre);

        marsOstrumOre = MARS_GEN_FEATURE.configured(new OreConfiguration(RuleTests.MarsRuleTest.INSTANCE, ModInit.MARS_OSTRUM_ORE.get().defaultBlockState(), 8)).placed(commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"mars_ostrum_ore"), marsOstrumOre);

        /** MERCURY */
    /*    MERCURY_GEN_FEATURE = new OreFeature(OreConfiguration.CODEC);
        event.getRegistry().register(MERCURY_GEN_FEATURE.setRegistryName("mercury_ore"));

        mercuryIronOre = MERCURY_GEN_FEATURE.configured(new OreConfiguration(RuleTests.MercuryRuleTest.INSTANCE, ModInit.MERCURY_IRON_ORE.get().defaultBlockState(), 12)).placed(commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"mercury_iron_ore"), mercuryIronOre);

        /** Venus */
    /*    VENUS_GEN_FEATURE = new OreFeature(OreConfiguration.CODEC);
        event.getRegistry().register(VENUS_GEN_FEATURE.setRegistryName("venus_ore"));

        venusCoalOre = VENUS_GEN_FEATURE.configured(new OreConfiguration(RuleTests.VenusRuleTest.INSTANCE, ModInit.VENUS_COAL_ORE.get().defaultBlockState(), 17)).placed(commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"venus_coal_ore"), venusCoalOre);

        venusGoldOre = VENUS_GEN_FEATURE.configured(new OreConfiguration(RuleTests.VenusRuleTest.INSTANCE, ModInit.VENUS_GOLD_ORE.get().defaultBlockState(), 10)).placed(commonOrePlacement(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"venus_gold_ore"), venusGoldOre);

        venusDiamondOre = VENUS_GEN_FEATURE.configured(new OreConfiguration(RuleTests.VenusRuleTest.INSTANCE, ModInit.VENUS_DIAMOND_ORE.get().defaultBlockState(), 9)).placed(commonOrePlacement(7, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"venus_diamond_ore"), venusDiamondOre);

        venusCaloriteOre = VENUS_GEN_FEATURE.configured(new OreConfiguration(RuleTests.VenusRuleTest.INSTANCE, ModInit.VENUS_CALORITE_ORE.get().defaultBlockState(), 8)).placed(commonOrePlacement(6, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-80), VerticalAnchor.aboveBottom(80))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"venus_calorite_ore"), venusCaloriteOre);

        /** Glacio */
    /*    GLACIO_GEN_FEATURE = new OreFeature(OreConfiguration.CODEC);
        event.getRegistry().register(GLACIO_GEN_FEATURE.setRegistryName("glacio_ore"));

        glacioIceShardOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioRuleTest.INSTANCE, ModInit.GLACIO_ICE_SHARD_ORE.get().defaultBlockState(), 10)).placed(commonOrePlacement(8, HeightRangePlacement.triangle(VerticalAnchor.aboveBottom(-32), VerticalAnchor.aboveBottom(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"glacio_ice_shard_ore"), glacioIceShardOre);

        glacioCoalOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioRuleTest.INSTANCE, ModInit.GLACIO_COAL_ORE.get().defaultBlockState(), 17)).placed(commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"glacio_coal_ore"), glacioCoalOre);

        glacioCopperOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioRuleTest.INSTANCE, ModInit.GLACIO_COPPER_ORE.get().defaultBlockState(), 17)).placed(commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-16), VerticalAnchor.absolute(112))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"glacio_copper_ore"), glacioCopperOre);

        glacioIronOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioRuleTest.INSTANCE, ModInit.GLACIO_IRON_ORE.get().defaultBlockState(), 12)).placed(commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-24), VerticalAnchor.absolute(56))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"glacio_iron_ore"), glacioIronOre);

        glacioLapisOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioRuleTest.INSTANCE, ModInit.GLACIO_LAPIS_ORE.get().defaultBlockState(), 9)).placed(commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(32))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"glacio_lapis_ore"), glacioLapisOre);

        //DEEPSLATE
        deepslateCoalOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioDeepslateRuleTest.INSTANCE, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState(), 17)).placed(commonOrePlacement(20, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(192))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"deepslate_coal_ore"), deepslateCoalOre);

        deepslateCopperOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioDeepslateRuleTest.INSTANCE, Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState(), 17)).placed(commonOrePlacement(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(112))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"deepslate_copper_ore"), deepslateCopperOre);

        deepslateIronOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioDeepslateRuleTest.INSTANCE, Blocks.DEEPSLATE_IRON_ORE.defaultBlockState(), 12)).placed(commonOrePlacement(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(20))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"deepslate_iron_ore"), deepslateIronOre);

        deepslateLapisOre = GLACIO_GEN_FEATURE.configured(new OreConfiguration(RuleTests.GlacioDeepslateRuleTest.INSTANCE, Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState(), 9)).placed(commonOrePlacement(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-80), VerticalAnchor.absolute(10))));
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation(BeyondEarthMod.MODID,"deepslate_lapis_ore"), deepslateLapisOre);
        */
    }

    /** RULE TEST */
    /*@SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MOON_MATCH = new BlockMatchTest(ModInit.MOON_STONE.get());
        });
    }*/

    /** BIOME ORE GEN */
    public static void biomesLoading(BiomeLoadingEvent event) {
        if (event.getName().getPath().equals(BiomeRegistry.moon.getRegistryName().getPath())) {
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(moonIceShardOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(moonIronOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(moonDeshOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(moonCheeseOre);
            event.getGeneration().getFeatures(GenerationStep.Decoration.UNDERGROUND_ORES).add(soulSoil);
        }
/*
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
        }*/
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
