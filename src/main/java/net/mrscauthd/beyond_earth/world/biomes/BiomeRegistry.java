package net.mrscauthd.beyond_earth.world.biomes;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.Carvers;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.*;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BiomeRegistry {
    public static Biome moon;

    public static Biome mars;
    public static Biome mars_ice_spikes;

    public static Biome venus;
    public static Biome infernal_venus_barrens;

    public static Biome mercury;

    public static Biome glacio;
    public static Biome glacio_ice_spikes;

    public static Biome orbit;

    @SubscribeEvent
    public static void registerBiomes(RegistryEvent.Register<Biome> event) {

        if (moon == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-16777216).waterColor(4159204).waterFogColor(329011).skyColor(-16777216).foliageColorOverride(7842607).grassColorOverride(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            BiomeDefaultFeatures.addDripstone(biomeGenerationSettings);
            addDefaultCarvers(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModInit.ALIEN_ZOMBIE.get(), 20, 5, 5)).addMobCharge(ModInit.ALIEN_ZOMBIE.get(), 0.7D, 0.15D).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModInit.STAR_CRAWLER.get(), 20, 5, 5)).addMobCharge(ModInit.STAR_CRAWLER.get(), 0.7D, 0.15D).build();

            moon = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(moon.setRegistryName(BeyondEarthMod.MODID,"moon"));
        }

        if (mars == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            addLushCavesVegetationFeatures(biomeGenerationSettings);
            BiomeDefaultFeatures.addFossilDecoration(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = (new MobSpawnSettings.Builder()).addSpawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(ModInit.MARTIAN_RAPTOR.get(), 20, 5, 5)).addMobCharge(ModInit.MARTIAN_RAPTOR.get(), 0.7D, 0.15D).build();

            mars = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mars.setRegistryName(BeyondEarthMod.MODID,"mars"));
        }

        if (mars_ice_spikes == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.014f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            addLushCavesVegetationFeatures(biomeGenerationSettings);
            BiomeDefaultFeatures.addFossilDecoration(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            mars_ice_spikes = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.ICY).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(mars_ice_spikes.setRegistryName(BeyondEarthMod.MODID,"mars_ice_spikes"));
        }

        if (venus == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            venus = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.RAIN).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.5f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(venus.setRegistryName(BeyondEarthMod.MODID,"venus"));
        }

        if (infernal_venus_barrens == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-3044526).waterColor(4159204).waterFogColor(329011).skyColor(-3044526).foliageColorOverride(7842607).grassColorOverride(9551193).ambientParticle(new AmbientParticleSettings(ParticleTypes.CRIMSON_SPORE, 0.02f)).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
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
            event.getRegistry().register(infernal_venus_barrens.setRegistryName(BeyondEarthMod.MODID,"infernal_venus_barrens"));
        }

        if (mercury == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-16777216).waterColor(4159204).waterFogColor(329011).skyColor(-16777216).foliageColorOverride(7842607).grassColorOverride(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
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
            event.getRegistry().register(mercury.setRegistryName(BeyondEarthMod.MODID,"mercury"));
        }

        if (glacio == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(12638463).waterColor(4159204).waterFogColor(329011).skyColor(calculateSkyColor(-0.7f)).foliageColorOverride(0xFFFFFF).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            BiomeDefaultFeatures.addSurfaceFreezing(biomeGenerationSettings);
            BiomeDefaultFeatures.addDefaultCrystalFormations(biomeGenerationSettings);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            glacio = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(glacio.setRegistryName(BeyondEarthMod.MODID,"glacio"));
        }

        if (glacio_ice_spikes == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(12638463).waterColor(4159204).waterFogColor(329011).skyColor(calculateSkyColor(-0.7f)).foliageColorOverride(0xFFFFFF).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            addDefaultCarvers(biomeGenerationSettings);
            BiomeDefaultFeatures.addSurfaceFreezing(biomeGenerationSettings);
            BiomeDefaultFeatures.addDefaultCrystalFormations(biomeGenerationSettings);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);

            biomeGenerationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_SPIKE);
            biomeGenerationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, MiscOverworldPlacements.ICE_PATCH);

            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            glacio_ice_spikes = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.SNOW).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(-0.7f).downfall(1f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(glacio_ice_spikes.setRegistryName(BeyondEarthMod.MODID,"glacio_ice_spikes"));
        }

        if (orbit == null) {
            BiomeSpecialEffects effects = new BiomeSpecialEffects.Builder().fogColor(-16777216).waterColor(4159204).waterFogColor(329011).skyColor(-16777216).foliageColorOverride(7842607).grassColorOverride(9551193).build();
            BiomeGenerationSettings.Builder biomeGenerationSettings = new BiomeGenerationSettings.Builder();
            MobSpawnSettings mobSpawnInfo = MobSpawnSettings.EMPTY;

            orbit = new Biome.BiomeBuilder().precipitation(Biome.Precipitation.NONE).biomeCategory(Biome.BiomeCategory.NONE).mobSpawnSettings(mobSpawnInfo).temperature(1.6f).downfall(0f).specialEffects(effects).generationSettings(biomeGenerationSettings.build()).build();
            event.getRegistry().register(orbit.setRegistryName(BeyondEarthMod.MODID,"orbit"));
        }
    }

    public static void addDefaultCarvers(BiomeGenerationSettings.Builder p_194721_) {
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE);
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CAVE_EXTRA_UNDERGROUND);
        p_194721_.addCarver(GenerationStep.Carving.AIR, Carvers.CANYON);
    }

    protected static int calculateSkyColor(float p_194844_) {
        float $$1 = p_194844_ / 3.0F;
        $$1 = Mth.clamp($$1, -1.0F, 1.0F);
        return Mth.hsvToRgb(0.62222224F - $$1 * 0.05F, 0.5F + $$1 * 0.1F, 1.0F);
    }

    public static void addLushCavesVegetationFeatures(BiomeGenerationSettings.Builder p_176851_) {
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_CEILING_VEGETATION);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.CAVE_VINES);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_CLAY);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.LUSH_CAVES_VEGETATION);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.SPORE_BLOSSOM);
        p_176851_.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.CLASSIC_VINES);
    }
}
