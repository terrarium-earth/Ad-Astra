package net.mrscauthd.boss_tools.world.oregen;

//@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class OreGeneration {
    /*
    //MOON ORES:
    public static IRuleTestType<RuleTests.MoonRuleTest> MOON_MATCH = null;
    private static Feature<OreFeatureConfig> MOON_GEN_FEATURE = null;

    private static ConfiguredFeature<?, ?> moonChesseOre = null;
    private static ConfiguredFeature<?, ?> moonGlowstoneOre = null;
    private static ConfiguredFeature<?, ?> moonIronOre = null;
    private static ConfiguredFeature<?, ?> moonDeshOre = null;

    //MARS ORES:
    public static IRuleTestType<RuleTests.MarsRuleTest> MARS_MATCH = null;
    private static Feature<OreFeatureConfig> MARS_GEN_FEATURE = null;

    private static ConfiguredFeature<?, ?> marsIceShardOre = null;
    private static ConfiguredFeature<?, ?> marsIronOre = null;
    private static ConfiguredFeature<?, ?> marsDiamondOre = null;
    private static ConfiguredFeature<?, ?> marsSiliconOre = null;

    //MERCURY ORES:
    public static IRuleTestType<RuleTests.MercuryRuleTest> MERCURY_MATCH = null;
    private static Feature<OreFeatureConfig> MERCURY_GEN_FEATURE = null;

    private static ConfiguredFeature<?, ?> mercuryIronOre = null;

    //MERCURY ORES:
    public static IRuleTestType<RuleTests.VenusRuleTest> VENUS_MATCH = null;
    private static Feature<OreFeatureConfig> VENUS_GEN_FEATURE = null;

    private static ConfiguredFeature<?, ?> venusCoalOre = null;
    private static ConfiguredFeature<?, ?> venusGoldOre = null;
    private static ConfiguredFeature<?, ?> venusDiamondOre = null;

    @SubscribeEvent
    public static void registerFeature(RegistryEvent.Register<Feature<?>> event) {
        MOON_MATCH = Registry.register(Registry.RULE_TEST, new ResourceLocation(BossToolsMod.ModId,"moon_ore_match"), () -> RuleTests.MoonRuleTest.codec);
        MARS_MATCH = Registry.register(Registry.RULE_TEST, new ResourceLocation(BossToolsMod.ModId,"mars_ore_match"), () -> RuleTests.MarsRuleTest.codec);
        MERCURY_MATCH = Registry.register(Registry.RULE_TEST, new ResourceLocation(BossToolsMod.ModId,"mercury_ore_match"), () -> RuleTests.MercuryRuleTest.codec);
        VENUS_MATCH = Registry.register(Registry.RULE_TEST, new ResourceLocation(BossToolsMod.ModId,"venus_ore_match"), () -> RuleTests.VenusRuleTest.codec);

        //MOON
        MOON_GEN_FEATURE = featureDimCheck(MOON_GEN_FEATURE, new ResourceLocation(BossToolsMod.ModId,"moon"));
        event.getRegistry().register(MOON_GEN_FEATURE.setRegistryName("moon_ore"));

        moonChesseOre = MOON_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MoonRuleTest.INSTANCE, ModInnet.MOON_CHESSE_ORE.get().getDefaultState(), 10)).range(75).square().func_242731_b(4);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"moon_cheese_ore"), moonChesseOre);

        moonGlowstoneOre = MOON_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MoonRuleTest.INSTANCE, ModInnet.MOON_GLOWSTONE_ORE.get().getDefaultState(), 11)).range(50).square().func_242731_b(4);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"moon_glowstone_ore"), moonGlowstoneOre);

        moonIronOre = MOON_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MoonRuleTest.INSTANCE, ModInnet.MOON_IRON_ORE.get().getDefaultState(), 11)).range(64).square().func_242731_b(5);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"moon_iron_ore"), moonIronOre);

        moonDeshOre = MOON_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MoonRuleTest.INSTANCE, ModInnet.MOON_DESH_ORE.get().getDefaultState(), 9)).range(25).square().func_242731_b(3);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"moon_desh_ore"), moonDeshOre);



        //MARS
        MARS_GEN_FEATURE = featureDimCheck(MARS_GEN_FEATURE, new ResourceLocation(BossToolsMod.ModId,"mars"));
        event.getRegistry().register(MARS_GEN_FEATURE.setRegistryName("mars_ore"));

        marsIceShardOre = MARS_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MarsRuleTest.INSTANCE, ModInnet.MARS_ICE_SHARD_ORE.get().getDefaultState(), 11)).range(64).square().func_242731_b(7);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"mars_ice_shard_ore"), marsIceShardOre);

        marsIronOre = MARS_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MarsRuleTest.INSTANCE, ModInnet.MARS_IRON_ORE.get().getDefaultState(), 11)).range(64).square().func_242731_b(5);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"mars_iron_ore"), marsIronOre);

        marsDiamondOre = MARS_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MarsRuleTest.INSTANCE, ModInnet.MARS_DIAMOND_ORE.get().getDefaultState(), 7)).range(16).square().func_242731_b(3);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"mars_diamond_ore"), marsDiamondOre);

        marsSiliconOre = MARS_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MarsRuleTest.INSTANCE, ModInnet.MARS_SILICON_ORE.get().getDefaultState(), 8)).range(20).square().func_242731_b(3);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"mars_silicon_ore"), marsSiliconOre);



        //MERCURY
        MERCURY_GEN_FEATURE = featureDimCheck(MERCURY_GEN_FEATURE, new ResourceLocation(BossToolsMod.ModId,"mercury"));
        event.getRegistry().register(MERCURY_GEN_FEATURE.setRegistryName("mercury_ore"));

        mercuryIronOre = MERCURY_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.MercuryRuleTest.INSTANCE, ModInnet.MERCURY_IRON_ORE.get().getDefaultState(), 12)).range(64).square().func_242731_b(5);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"mercury_iron_ore"), mercuryIronOre);



        //Venus
        VENUS_GEN_FEATURE = featureDimCheck(VENUS_GEN_FEATURE, new ResourceLocation(BossToolsMod.ModId,"venus"));
        event.getRegistry().register(VENUS_GEN_FEATURE.setRegistryName("venus_ore"));

        venusCoalOre = VENUS_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.VenusRuleTest.INSTANCE, ModInnet.VENUS_COAL_ORE.get().getDefaultState(), 17)).range(125).square().func_242731_b(20);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"venus_coal_ore"), venusCoalOre);

        venusGoldOre = VENUS_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.VenusRuleTest.INSTANCE, ModInnet.VENUS_GOLD_ORE.get().getDefaultState(), 9)).range(32).square().func_242731_b(2);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"venus_gold_ore"), venusGoldOre);

        venusDiamondOre = VENUS_GEN_FEATURE.withConfiguration(new OreFeatureConfig(RuleTests.VenusRuleTest.INSTANCE, ModInnet.VENUS_DIAMOND_ORE.get().getDefaultState(), 7)).range(16).square().func_242731_b(3);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(BossToolsMod.ModId,"venus_diamond_ore"), venusDiamondOre);
    }

    public static void biomesLoading(BiomeLoadingEvent event) {
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> moonIronOre);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> moonDeshOre);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> moonChesseOre);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> moonGlowstoneOre);

        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> marsIceShardOre);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> marsIronOre);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> marsDiamondOre);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> marsSiliconOre);

        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> mercuryIronOre);

        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> venusCoalOre);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> venusGoldOre);
        event.getGeneration().getFeatures(GenerationStage.Decoration.UNDERGROUND_ORES).add(() -> venusDiamondOre);
    }

    /**DIM CHECK FOR FEATURE*/
/*    public static Feature featureDimCheck(Feature feature, ResourceLocation worldcheck) {
        feature = new OreFeature(OreFeatureConfig.CODEC) {
            @Override
            public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, OreFeatureConfig config) {
                if (Methodes.isWorld(world.getWorld(), worldcheck)) {
                    return super.generate(world, generator, rand, pos, config);
                }
                return false;
            }
        };
        return feature;
    }
*/
}