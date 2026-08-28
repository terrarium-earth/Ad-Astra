package earth.terrarium.adastra.datagen.provider.server.tags;


import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModPaintingVariants;
import earth.terrarium.adastra.common.tags.ModPaintingVariantTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModPaintingVariantTagProvider extends TagsProvider<PaintingVariant> {

    public ModPaintingVariantTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.PAINTING_VARIANT, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ModPaintingVariants.PAINTING_VARIANTS.forEach(p -> tag(ModPaintingVariantTags.SPACE_PAINTINGS).add(p));
    }
}
