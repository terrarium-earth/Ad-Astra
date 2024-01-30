package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModFeatures;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.ColumnFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;

import java.util.List;

@SuppressWarnings("SameParameterValue")
public class ModConfiguredFeatureProvider {
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_CHEESE_ORE = register("moon_cheese_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_DESH_ORE = register("moon_desh_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_ICE_SHARD_ORE = register("moon_ice_shard_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_IRON_ORE = register("moon_iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOON_SOUL_SOIL = register("moon_soul_soil");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_DIAMOND_ORE = register("mars_diamond_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_ICE_SHARD_ORE = register("mars_ice_shard_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_IRON_ORE = register("mars_iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_OSTRUM_ORE = register("mars_ostrum_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> VENUS_CALORITE_ORE = register("venus_calorite_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENUS_COAL_ORE = register("venus_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENUS_DIAMOND_ORE = register("venus_diamond_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VENUS_GOLD_ORE = register("venus_gold_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MERCURY_IRON_ORE = register("mercury_iron_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_COAL_ORE = register("glacio_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_COPPER_ORE = register("glacio_copper_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_ICE_SHARD_ORE = register("glacio_ice_shard_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_IRON_ORE = register("glacio_iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_LAPIS_ORE = register("glacio_lapis_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_DEEPSLATE_COAL_ORE = register("glacio_deepslate_coal_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_DEEPSLATE_COPPER_ORE = register("glacio_deepslate_copper_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_DEEPSLATE_IRON_ORE = register("glacio_deepslate_iron_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIO_DEEPSLATE_LAPIS_ORE = register("glacio_deepslate_lapis_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MARS_ROCK = register("mars_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_INFERNAL_SPIRE_COLUMN = register("large_infernal_spire_column");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_INFERNAL_SPIRE_COLUMN = register("small_infernal_spire_column");

    private static ResourceKey<ConfiguredFeature<?, ?>> register(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        RuleTest moonRuleTest = new TagMatchTest(ModBlockTags.MOON_STONE_REPLACEABLES);
        RuleTest marsRuleTest = new TagMatchTest(ModBlockTags.MARS_STONE_REPLACEABLES);
        RuleTest venusRuleTest = new TagMatchTest(ModBlockTags.VENUS_STONE_REPLACEABLES);
        RuleTest mercuryRuleTest = new TagMatchTest(ModBlockTags.MERCURY_STONE_REPLACEABLES);
        RuleTest glacioRuleTest = new TagMatchTest(ModBlockTags.GLACIO_STONE_REPLACEABLES);
        RuleTest deepslateRuleTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);

        var moonCheeseTarget = List.of(OreConfiguration.target(moonRuleTest, ModBlocks.MOON_CHEESE_ORE.get().defaultBlockState()));
        var moonDeshTarget = List.of(OreConfiguration.target(moonRuleTest, ModBlocks.MOON_DESH_ORE.get().defaultBlockState()));
        var moonIceShardTarget = List.of(OreConfiguration.target(moonRuleTest, ModBlocks.MOON_ICE_SHARD_ORE.get().defaultBlockState()));
        var moonIronTarget = List.of(OreConfiguration.target(moonRuleTest, ModBlocks.MOON_IRON_ORE.get().defaultBlockState()));
        var moonSoulSoilTarget = List.of(OreConfiguration.target(moonRuleTest, Blocks.SOUL_SOIL.defaultBlockState()));

        var marsDiamondTarget = List.of(OreConfiguration.target(marsRuleTest, ModBlocks.MARS_DIAMOND_ORE.get().defaultBlockState()));
        var marsIceShardTarget = List.of(OreConfiguration.target(marsRuleTest, ModBlocks.MARS_ICE_SHARD_ORE.get().defaultBlockState()));
        var marsIronTarget = List.of(OreConfiguration.target(marsRuleTest, ModBlocks.MARS_IRON_ORE.get().defaultBlockState()));
        var marsOstrumTarget = List.of(OreConfiguration.target(marsRuleTest, ModBlocks.MARS_OSTRUM_ORE.get().defaultBlockState()));

        var venusCaloriteTarget = List.of(OreConfiguration.target(venusRuleTest, ModBlocks.VENUS_CALORITE_ORE.get().defaultBlockState()));
        var venusCoalTarget = List.of(OreConfiguration.target(venusRuleTest, ModBlocks.VENUS_COAL_ORE.get().defaultBlockState()));
        var venusDiamondTarget = List.of(OreConfiguration.target(venusRuleTest, ModBlocks.VENUS_DIAMOND_ORE.get().defaultBlockState()));
        var venusGoldTarget = List.of(OreConfiguration.target(venusRuleTest, ModBlocks.VENUS_GOLD_ORE.get().defaultBlockState()));

        var mercuryIronTarget = List.of(OreConfiguration.target(mercuryRuleTest, ModBlocks.MERCURY_IRON_ORE.get().defaultBlockState()));

        var glacioCoalTarget = List.of(OreConfiguration.target(glacioRuleTest, ModBlocks.GLACIO_COAL_ORE.get().defaultBlockState()));
        var glacioCopperTarget = List.of(OreConfiguration.target(glacioRuleTest, ModBlocks.GLACIO_COPPER_ORE.get().defaultBlockState()));
        var glacioIceShardTarget = List.of(OreConfiguration.target(glacioRuleTest, ModBlocks.GLACIO_ICE_SHARD_ORE.get().defaultBlockState()));
        var glacioIronTarget = List.of(OreConfiguration.target(glacioRuleTest, ModBlocks.GLACIO_IRON_ORE.get().defaultBlockState()));
        var glacioLapisTarget = List.of(OreConfiguration.target(glacioRuleTest, ModBlocks.GLACIO_LAPIS_ORE.get().defaultBlockState()));
        var glacioDeepslateCoalTarget = List.of(OreConfiguration.target(deepslateRuleTest, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState()));
        var glacioDeepslateCopperTarget = List.of(OreConfiguration.target(deepslateRuleTest, Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState()));
        var glacioDeepslateIronTarget = List.of(OreConfiguration.target(deepslateRuleTest, Blocks.DEEPSLATE_IRON_ORE.defaultBlockState()));
        var glacioDeepslateLapisTarget = List.of(OreConfiguration.target(deepslateRuleTest, Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState()));

        FeatureUtils.register(context, MOON_CHEESE_ORE, Feature.ORE, new OreConfiguration(moonCheeseTarget, 8, 0.2f));
        FeatureUtils.register(context, MOON_DESH_ORE, Feature.ORE, new OreConfiguration(moonDeshTarget, 9, 0.5f));
        FeatureUtils.register(context, MOON_ICE_SHARD_ORE, Feature.ORE, new OreConfiguration(moonIceShardTarget, 10, 0.0f));
        FeatureUtils.register(context, MOON_IRON_ORE, Feature.ORE, new OreConfiguration(moonIronTarget, 11, 0.0f));
        FeatureUtils.register(context, MOON_SOUL_SOIL, Feature.ORE, new OreConfiguration(moonSoulSoilTarget, 60, 0.0f));

        FeatureUtils.register(context, MARS_DIAMOND_ORE, Feature.ORE, new OreConfiguration(marsDiamondTarget, 7, 0.5f));
        FeatureUtils.register(context, MARS_ICE_SHARD_ORE, Feature.ORE, new OreConfiguration(marsIceShardTarget, 10, 0.0f));
        FeatureUtils.register(context, MARS_IRON_ORE, Feature.ORE, new OreConfiguration(marsIronTarget, 11, 0.0f));
        FeatureUtils.register(context, MARS_OSTRUM_ORE, Feature.ORE, new OreConfiguration(marsOstrumTarget, 8, 0.5f));

        FeatureUtils.register(context, VENUS_CALORITE_ORE, Feature.ORE, new OreConfiguration(venusCaloriteTarget, 8, 0.5f));
        FeatureUtils.register(context, VENUS_COAL_ORE, Feature.ORE, new OreConfiguration(venusCoalTarget, 17, 0.0f));
        FeatureUtils.register(context, VENUS_DIAMOND_ORE, Feature.ORE, new OreConfiguration(venusDiamondTarget, 9, 0.5f));
        FeatureUtils.register(context, VENUS_GOLD_ORE, Feature.ORE, new OreConfiguration(venusGoldTarget, 10, 0.0f));

        FeatureUtils.register(context, MERCURY_IRON_ORE, Feature.ORE, new OreConfiguration(mercuryIronTarget, 8, 0.0f));

        FeatureUtils.register(context, GLACIO_COAL_ORE, Feature.ORE, new OreConfiguration(glacioCoalTarget, 17, 0.0f));
        FeatureUtils.register(context, GLACIO_COPPER_ORE, Feature.ORE, new OreConfiguration(glacioCopperTarget, 17, 0.0f));
        FeatureUtils.register(context, GLACIO_ICE_SHARD_ORE, Feature.ORE, new OreConfiguration(glacioIceShardTarget, 17, 0.0f));
        FeatureUtils.register(context, GLACIO_IRON_ORE, Feature.ORE, new OreConfiguration(glacioIronTarget, 11, 0.0f));
        FeatureUtils.register(context, GLACIO_LAPIS_ORE, Feature.ORE, new OreConfiguration(glacioLapisTarget, 9, 0.0f));
        FeatureUtils.register(context, GLACIO_DEEPSLATE_COAL_ORE, Feature.ORE, new OreConfiguration(glacioDeepslateCoalTarget, 17, 0.0f));
        FeatureUtils.register(context, GLACIO_DEEPSLATE_COPPER_ORE, Feature.ORE, new OreConfiguration(glacioDeepslateCopperTarget, 17, 0.0f));
        FeatureUtils.register(context, GLACIO_DEEPSLATE_IRON_ORE, Feature.ORE, new OreConfiguration(glacioDeepslateIronTarget, 12, 0.0f));
        FeatureUtils.register(context, GLACIO_DEEPSLATE_LAPIS_ORE, Feature.ORE, new OreConfiguration(glacioDeepslateLapisTarget, 9, 0.0f));

        FeatureUtils.register(context, MARS_ROCK, ModFeatures.MARS_BLOCK_BLOB_FEATURE.get(), new BlockStateConfiguration(ModBlocks.CONGLOMERATE.get().defaultBlockState()));
        FeatureUtils.register(context, SMALL_INFERNAL_SPIRE_COLUMN, ModFeatures.INFERNAL_SPIRE_COLUMN.get(), new ColumnFeatureConfiguration(ConstantInt.of(1), UniformInt.of(1, 4)));
        FeatureUtils.register(context, LARGE_INFERNAL_SPIRE_COLUMN, ModFeatures.INFERNAL_SPIRE_COLUMN.get(), new ColumnFeatureConfiguration(UniformInt.of(2, 3), UniformInt.of(5, 10)));
    }
}
