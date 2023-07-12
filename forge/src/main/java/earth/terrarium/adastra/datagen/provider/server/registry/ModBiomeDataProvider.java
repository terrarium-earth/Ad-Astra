package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.Music;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import javax.annotation.Nullable;

@SuppressWarnings("SameParameterValue")
public class ModBiomeDataProvider {
    public static final ResourceKey<Biome> SPACE = register("space");
    public static final ResourceKey<Biome> MOON_WASTES = register("moon_wastes");

    private static ResourceKey<Biome> register(String name) {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<Biome> context) {
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<ConfiguredWorldCarver<?>> configuredCarvers = context.lookup(Registries.CONFIGURED_CARVER);
        context.register(
            SPACE,
            space(placedFeatures, configuredCarvers));

        context.register(
            MOON_WASTES,
            moonWastes(placedFeatures, configuredCarvers));
    }

    public static Biome space(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return biome(
            false,
            0.5F,
            0.5F,
            new MobSpawnSettings.Builder(), builder,
            null);
    }

    public static Biome moonWastes(HolderGetter<PlacedFeature> placedFeatures, HolderGetter<ConfiguredWorldCarver<?>> worldCarvers) {
        BiomeGenerationSettings.Builder builder = new BiomeGenerationSettings.Builder(placedFeatures, worldCarvers);
        return biome(
            false,
            0.5F,
            0.5F,
            new MobSpawnSettings.Builder(),
            builder,
            null);
    }

    public static Biome biome(boolean hasPercipitation, float temperature, float downfall, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        return biome(hasPercipitation, temperature, downfall, 0x3f76e4, 0x50533, null, null, mobSpawnSettings, generationSettings, backgroundMusic);
    }

    public static Biome biome(boolean hasPrecipitation, float temperature, float downfall, int waterColor, int waterFogColor, @Nullable Integer grassColorOverride, @Nullable Integer foliageColorOverride, MobSpawnSettings.Builder mobSpawnSettings, BiomeGenerationSettings.Builder generationSettings, @Nullable Music backgroundMusic) {
        BiomeSpecialEffects.Builder biomespecialeffects$builder = (new BiomeSpecialEffects.Builder()).waterColor(waterColor).waterFogColor(waterFogColor).fogColor(0xc0d8ff).skyColor(calculateSkyColor(temperature)).ambientMoodSound(AmbientMoodSettings.LEGACY_CAVE_SETTINGS).backgroundMusic(backgroundMusic);
        if (grassColorOverride != null) {
            biomespecialeffects$builder.grassColorOverride(grassColorOverride);
        }

        if (foliageColorOverride != null) {
            biomespecialeffects$builder.foliageColorOverride(foliageColorOverride);
        }

        return (new Biome.BiomeBuilder()).hasPrecipitation(hasPrecipitation).temperature(temperature).downfall(downfall).specialEffects(biomespecialeffects$builder.build()).mobSpawnSettings(mobSpawnSettings.build()).generationSettings(generationSettings.build()).build();
    }

    public static int calculateSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = Mth.clamp(f, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
