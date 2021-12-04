package net.mrscauthd.boss_tools.world.structure.configuration;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.world.structure.AlienVillageStructure;
import net.mrscauthd.boss_tools.world.structure.CrimsonStructure;
import net.mrscauthd.boss_tools.world.structure.MeteorStructure;
import net.mrscauthd.boss_tools.world.structure.OilStructure;

import java.util.HashMap;
import java.util.Map;

public class STStructures {

    public static final DeferredRegister<Structure<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, BossToolsMod.ModId);

    public static final RegistryObject<Structure<NoFeatureConfig>> ALIEN_VILLAGE = DEFERRED_REGISTRY_STRUCTURE.register("alien_village", () -> (new AlienVillageStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> METEOR = DEFERRED_REGISTRY_STRUCTURE.register("meteor", () -> (new MeteorStructure(NoFeatureConfig.field_236558_a_)));
    public static final RegistryObject<Structure<NoFeatureConfig>> OIL = DEFERRED_REGISTRY_STRUCTURE.register("oil_well", () -> (new OilStructure(NoFeatureConfig.field_236558_a_)));

    public static void setupStructures() {
        setupMapSpacingAndLand(ALIEN_VILLAGE.get(), new StructureSeparationSettings(24, 9, 1234567890), true);

        setupMapSpacingAndLand(METEOR.get(), new StructureSeparationSettings(22, 5, 1234567890), true);

        setupMapSpacingAndLand(OIL.get(), new StructureSeparationSettings(13, 7, 1234567890), true);
    }

    public static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.NAME_STRUCTURE_BIMAP.put(structure.getRegistryName().toString(), structure);

        if (transformSurroundingLand) {
            DimensionStructuresSettings.field_236191_b_ =
                    ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                            .putAll(DimensionStructuresSettings.field_236191_b_)
                            .put(structure, structureSeparationSettings)
                            .build();

            WorldGenRegistries.NOISE_SETTINGS.getEntries().forEach(settings -> {
                Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().getStructures().func_236195_a_();

                if (structureMap instanceof ImmutableMap) {
                    Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                    tempMap.put(structure, structureSeparationSettings);
                } else {
                    structureMap.put(structure, structureSeparationSettings);
                }
            });
        }
    }
}
