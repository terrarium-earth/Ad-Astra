package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.FixedBiomeSource;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

public class ModDimensionProvider {
    public static final ResourceKey<LevelStem> SPACE = register("space");
    public static final ResourceKey<LevelStem> MOON = register("moon");

    private static ResourceKey<LevelStem> register(String name) {
        return ResourceKey.create(Registries.LEVEL_STEM, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<LevelStem> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<DimensionType> dimensionTypes = context.lookup(Registries.DIMENSION_TYPE);
        HolderGetter<NoiseGeneratorSettings> noiseSettings = context.lookup(Registries.NOISE_SETTINGS);

        context.register(SPACE, new LevelStem(
            dimensionTypes.getOrThrow(ModDimensionTypeProvider.SPACE),
            new NoiseBasedChunkGenerator(
                new FixedBiomeSource(
                    biomes.getOrThrow(ModBiomeDataProvider.SPACE)),
                noiseSettings.getOrThrow(ModNoiseGeneratorSettingsProvider.SPACE))));

        context.register(MOON, new LevelStem(
            dimensionTypes.getOrThrow(ModDimensionTypeProvider.MOON),
            new NoiseBasedChunkGenerator(
                new FixedBiomeSource(
                    biomes.getOrThrow(ModBiomeDataProvider.MOON_WASTES)),
                noiseSettings.getOrThrow(ModNoiseGeneratorSettingsProvider.MOON))));
    }
}
