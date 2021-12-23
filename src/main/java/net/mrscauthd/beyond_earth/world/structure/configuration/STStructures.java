package net.mrscauthd.beyond_earth.world.structure.configuration;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.world.structure.*;

import java.util.HashMap;
import java.util.Map;

public class STStructures {
    public static final DeferredRegister<StructureFeature<?>> DEFERRED_REGISTRY_STRUCTURE = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, BeyondEarthMod.MODID);

    public static final RegistryObject<StructureFeature<JigsawConfiguration>> ALIEN_VILLAGE = DEFERRED_REGISTRY_STRUCTURE.register("alien_village", () -> (new AlienVillageStructure(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> METEOR = DEFERRED_REGISTRY_STRUCTURE.register("meteor", () -> (new MeteorStructure(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> OIL = DEFERRED_REGISTRY_STRUCTURE.register("oil_well", () -> (new OilStructure(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> VENUS_BULLET = DEFERRED_REGISTRY_STRUCTURE.register("venus_bullet", () -> (new VenusBulletStructure(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> VENUS_TOWER = DEFERRED_REGISTRY_STRUCTURE.register("venus_tower", () -> (new VenusTowerStructure(JigsawConfiguration.CODEC)));
    public static final RegistryObject<StructureFeature<JigsawConfiguration>> CRIMSON = DEFERRED_REGISTRY_STRUCTURE.register("crimson_village", () -> (new CrimsonStructure(JigsawConfiguration.CODEC)));

    public static void setupStructures() {
        setupMapSpacingAndLand(ALIEN_VILLAGE.get(), new StructureFeatureConfiguration(29, 9, 1234567890), false);
        setupMapSpacingAndLand(METEOR.get(), new StructureFeatureConfiguration(22, 5, 1234567890), false);
        setupMapSpacingAndLand(OIL.get(), new StructureFeatureConfiguration(13, 7, 1234567890), false);

        setupMapSpacingAndLand(VENUS_BULLET.get(), new StructureFeatureConfiguration(29, 19, 1234567890), true);
        setupMapSpacingAndLand(VENUS_TOWER.get(), new StructureFeatureConfiguration(24, 17, 1234567890), true);
        setupMapSpacingAndLand(CRIMSON.get(), new StructureFeatureConfiguration(27, 14, 1234567890), true);
    }

    public static <F extends StructureFeature<?>> void setupMapSpacingAndLand(F structure, StructureFeatureConfiguration structureFeatureConfiguration, boolean transformSurroundingLand) {
        StructureFeature.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if(transformSurroundingLand){
            StructureFeature.NOISE_AFFECTING_FEATURES =
                    ImmutableList.<StructureFeature<?>>builder()
                            .addAll(StructureFeature.NOISE_AFFECTING_FEATURES)
                            .add(structure)
                            .build();
        }

        StructureSettings.DEFAULTS =
                ImmutableMap.<StructureFeature<?>, StructureFeatureConfiguration>builder()
                        .putAll(StructureSettings.DEFAULTS)
                        .put(structure, structureFeatureConfiguration)
                        .build();

        BuiltinRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<StructureFeature<?>, StructureFeatureConfiguration> structureMap = settings.getValue().structureSettings().structureConfig();

            if(structureMap instanceof ImmutableMap){
                Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureFeatureConfiguration);
                settings.getValue().structureSettings().structureConfig = tempMap;
            }
            else{
                structureMap.put(structure, structureFeatureConfiguration);
            }
        });
    }
}
