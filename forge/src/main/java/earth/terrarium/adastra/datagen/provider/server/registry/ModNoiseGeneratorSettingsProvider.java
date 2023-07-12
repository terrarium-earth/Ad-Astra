package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.datagen.dimensions.ModSurfaceRuleData;
import earth.terrarium.adastra.datagen.dimensions.MoonNoiseRouterData;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.SurfaceRuleData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.*;

import java.util.List;

public class ModNoiseGeneratorSettingsProvider {
    public static final ResourceKey<NoiseGeneratorSettings> SPACE = register("space");
    public static final ResourceKey<NoiseGeneratorSettings> MOON = register("moon");

    protected static final NoiseSettings SIMPLE_NOISE_SETTINGS = NoiseSettings.create(-64, 384, 2, 1);

    private static ResourceKey<NoiseGeneratorSettings> register(String name) {
        return ResourceKey.create(Registries.NOISE_SETTINGS, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<NoiseGeneratorSettings> context) {
        context.register(SPACE, space());
        context.register(MOON, moon(context));
    }

    public static NoiseGeneratorSettings space() {
        return new NoiseGeneratorSettings(
            SIMPLE_NOISE_SETTINGS,
            Blocks.AIR.defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            none(),
            SurfaceRuleData.air(),
            List.of(),
            0,
            true,
            false,
            false,
            false
        );
    }

    public static NoiseGeneratorSettings moon(BootstapContext<NoiseGeneratorSettings> context) {
        return create(
            SIMPLE_NOISE_SETTINGS,
            ModBlocks.MOON_STONE.get().defaultBlockState(),
            Blocks.AIR.defaultBlockState(),
            MoonNoiseRouterData.moon(context.lookup(Registries.DENSITY_FUNCTION), context.lookup(Registries.NOISE)),
            ModSurfaceRuleData.moon(),
            List.of(),
            63,
            true,
            false,
            false,
            false
        );
    }

    public static NoiseRouter none() {
        return new NoiseRouter(
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero(),
            DensityFunctions.zero()
        );
    }

    // named parameters
    public static NoiseGeneratorSettings create(NoiseSettings noiseSettings, BlockState defaultBlock, BlockState defaultFluid, NoiseRouter noiseRouter, SurfaceRules.RuleSource surfaceRule, List<Climate.ParameterPoint> spawnTarget, int seaLevel, boolean disableMobGeneration, boolean aquifersEnabled, boolean oreVeinsEnabled, boolean useLegacyRandomSource) {
        return new NoiseGeneratorSettings(noiseSettings, defaultBlock, defaultFluid, noiseRouter, surfaceRule, spawnTarget, seaLevel, disableMobGeneration, aquifersEnabled, oreVeinsEnabled, useLegacyRandomSource);
    }
}
