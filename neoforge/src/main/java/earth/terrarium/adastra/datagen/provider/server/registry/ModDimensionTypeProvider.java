package earth.terrarium.adastra.datagen.provider.server.registry;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.dimension.ModDimensionRenderers;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class ModDimensionTypeProvider {
    public static final ResourceKey<DimensionType> EARTH_ORBIT = register("earth_orbit");
    public static final ResourceKey<DimensionType> MOON_ORBIT = register("moon_orbit");
    public static final ResourceKey<DimensionType> MARS_ORBIT = register("mars_orbit");
    public static final ResourceKey<DimensionType> VENUS_ORBIT = register("venus_orbit");
    public static final ResourceKey<DimensionType> MERCURY_ORBIT = register("mercury_orbit");
    public static final ResourceKey<DimensionType> GLACIO_ORBIT = register("glacio_orbit");

    public static final ResourceKey<DimensionType> MOON = register("moon");
    public static final ResourceKey<DimensionType> MARS = register("mars");
    public static final ResourceKey<DimensionType> VENUS = register("venus");
    public static final ResourceKey<DimensionType> MERCURY = register("mercury");
    public static final ResourceKey<DimensionType> GLACIO = register("glacio");

    private static ResourceKey<DimensionType> register(String name) {
        return ResourceKey.create(Registries.DIMENSION_TYPE, new ResourceLocation(AdAstra.MOD_ID, name));
    }

    public static void bootstrap(BootstapContext<DimensionType> context) {
        orbit(context, EARTH_ORBIT, ModDimensionRenderers.EARTH_ORBIT_EFFECTS);
        orbit(context, MOON_ORBIT, ModDimensionRenderers.MOON_ORBIT_EFFECTS);
        orbit(context, MARS_ORBIT, ModDimensionRenderers.MARS_ORBIT_EFFECTS);
        orbit(context, VENUS_ORBIT, ModDimensionRenderers.VENUS_ORBIT_EFFECTS);
        orbit(context, MERCURY_ORBIT, ModDimensionRenderers.MERCURY_ORBIT_EFFECTS);
        orbit(context, GLACIO_ORBIT, ModDimensionRenderers.GLACIO_ORBIT_EFFECTS);

        context.register(
            MOON,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ModDimensionRenderers.MOON_EFFECTS,
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            MARS,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ModDimensionRenderers.MARS_EFFECTS,
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            VENUS,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ModDimensionRenderers.VENUS_EFFECTS,
                0.0f,
                createMonsterSettings(
                    true,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            MERCURY,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ModDimensionRenderers.MERCURY_EFFECTS,
                0.0f,
                createMonsterSettings(
                    true,
                    false,
                    UniformInt.of(0, 7),
                    0)));

        context.register(
            GLACIO,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                ModDimensionRenderers.GLACIO_EFFECTS,
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));
    }

    private static void orbit(BootstapContext<DimensionType> context, ResourceKey<DimensionType> key, ResourceLocation dimensionSpecialEffects) {
        context.register(
            key,
            create(
                OptionalLong.empty(),
                true,
                false,
                false,
                true,
                1.0,
                true,
                false,
                -64,
                384,
                384,
                BlockTags.INFINIBURN_OVERWORLD,
                dimensionSpecialEffects,
                0.0f,
                createMonsterSettings(
                    false,
                    false,
                    UniformInt.of(0, 7),
                    0)));
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static DimensionType create(OptionalLong fixedTime, boolean hasSkyLight, boolean hasCeiling, boolean ultraWarm, boolean natural, double coordinateScale, boolean bedWorks, boolean respawnAnchorWorks, int minY, int height, int logicalHeight, TagKey<Block> infiniburn, ResourceLocation effectsLocation, float ambientLight, DimensionType.MonsterSettings monsterSettings) {
        return new DimensionType(fixedTime, hasSkyLight, hasCeiling, ultraWarm, natural, coordinateScale, bedWorks, respawnAnchorWorks, minY, height, logicalHeight, infiniburn, effectsLocation, ambientLight, monsterSettings);
    }

    public static DimensionType.MonsterSettings createMonsterSettings(boolean piglinSafe, boolean hasRaids, IntProvider monsterSpawnLightTest, int monsterSpawnBlockLightLimit) {
        return new DimensionType.MonsterSettings(piglinSafe, hasRaids, monsterSpawnLightTest, monsterSpawnBlockLightLimit);
    }
}
