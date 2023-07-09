package earth.terrarium.adastra.datagen.provider.server.tags;


import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.tags.ModRecipeTypeTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;

public class ModRecipeTypeTagProvider extends TagsProvider<RecipeType<?>> {

    public ModRecipeTypeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.RECIPE_TYPE, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModRecipeTypeTags.RECYCLABLES).add(TagEntry.element(ForgeRegistries.RECIPE_TYPES.getKey(RecipeType.CRAFTING)));
    }
}
