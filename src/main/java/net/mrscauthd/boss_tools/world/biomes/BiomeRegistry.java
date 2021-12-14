package net.mrscauthd.boss_tools.world.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.BossToolsMod;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeRegistry {
    public static Biome moon;

    public static Biome mars;
    public static Biome mars_ice_spikes;

    public static Biome venus;
    public static Biome infernal_venus_barrens;

    public static Biome mercury;

    public static Biome orbit;

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {

        if (moon == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-16777216).waterColor(4159204).waterFogColor(329011).skyColor(-16777216).foliageColorOverride(7842607).grassColorOverride(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            BiomeDefaultFeatures.addDripstone(biomeGenerationSettings);
            addDefaultCarversAndLakes(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            moon = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.6f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(moon.setRegistryName(BossToolsMod.ModId,"moon"));
        }

        if (mars == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarversAndLakes(biomeGenerationSettings);
            BiomeDefaultFeatures.addLushCavesVegetationFeatures(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            mars = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.6f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mars.setRegistryName(BossToolsMod.ModId,"mars"));
        }

        if (mars_ice_spikes == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarversAndLakes(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            mars_ice_spikes = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.ICY).mobSpawnSettings(mobSpawnInfo).temperature(1.6f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mars_ice_spikes.setRegistryName(BossToolsMod.ModId,"mars_ice_spikes"));
        }

        if (venus == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarversAndLakes(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            venus = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.5f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(venus.setRegistryName(BossToolsMod.ModId,"venus"));
        }

        if (infernal_venus_barrens == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarversAndLakes(biomeGenerationSettings);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_DELTA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED_DOUBLE);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            infernal_venus_barrens = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.5f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(infernal_venus_barrens.setRegistryName(BossToolsMod.ModId,"infernal_venus_barrens"));
        }

        if (mercury == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-16777216).waterColor(4159204).waterFogColor(329011).skyColor(-16777216).foliageColorOverride(7842607).grassColorOverride(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarversAndLakes(biomeGenerationSettings);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_PILLAR);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BASALT_BLOBS);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.BLACKSTONE_BLOBS);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_DELTA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_FIRE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.PATCH_SOUL_FIRE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, OrePlacements.ORE_MAGMA);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION, NetherPlacements.SPRING_CLOSED_DOUBLE);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            mercury = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.6f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mercury.setRegistryName(BossToolsMod.ModId,"mercury"));
        }

        if (orbit == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-16777216).waterColor(4159204).waterFogColor(329011).skyColor(-16777216).foliageColorOverride(7842607).grassColorOverride(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            orbit = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.6f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(orbit.setRegistryName(BossToolsMod.ModId,"orbit"));
        }
    }

    public static void addDefaultCarversAndLakes(BiomeGenerationSettings.Builder p_194721_) {
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
    }
}
