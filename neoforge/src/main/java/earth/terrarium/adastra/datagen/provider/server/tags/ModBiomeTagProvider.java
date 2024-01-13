package earth.terrarium.adastra.datagen.provider.server.tags;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.tags.ModBiomeTags;
import earth.terrarium.adastra.datagen.provider.server.registry.ModBiomeDataProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends TagsProvider<Biome> {

    public ModBiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BIOME, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModBiomeTags.HAS_ACID_RAIN).addOptional(ModBiomeDataProvider.VENUS_WASTELANDS.location());
        tag(ModBiomeTags.HAS_ACID_RAIN).addOptional(ModBiomeDataProvider.INFERNAL_VENUS_BARRENS.location());

        tag(ModBiomeTags.OIL_WELL).addTag(BiomeTags.HAS_OCEAN_MONUMENT);
        tag(ModBiomeTags.OIL_WELL).addTag(BiomeTags.IS_OCEAN);
        tag(ModBiomeTags.OIL_WELL).addOptionalTag(new ResourceLocation("c:ocean"));

        tag(ModBiomeTags.LUNARIAN_VILLAGE).addOptional(ModBiomeDataProvider.LUNAR_WASTELANDS.location());
        tag(ModBiomeTags.MOON_DUNGEON).addOptional(ModBiomeDataProvider.LUNAR_WASTELANDS.location());
        tag(ModBiomeTags.MARS_TEMPLE).addOptional(ModBiomeDataProvider.MARTIAN_WASTELANDS.location());
        tag(ModBiomeTags.MARS_TEMPLE).addOptional(ModBiomeDataProvider.MARTIAN_CANYON_CREEK.location());
        tag(ModBiomeTags.PYGRO_TOWER).addOptional(ModBiomeDataProvider.VENUS_WASTELANDS.location());
        tag(ModBiomeTags.PYGRO_VILLAGE).addOptional(ModBiomeDataProvider.VENUS_WASTELANDS.location());
        tag(ModBiomeTags.VENUS_BULLET).addOptional(ModBiomeDataProvider.VENUS_WASTELANDS.location());
    }
}
