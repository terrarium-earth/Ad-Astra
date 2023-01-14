package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends TagsProvider<Item> {

    public ModItemTagProvider(PackOutput arg, ResourceKey<? extends Registry<Item>> arg2, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, arg2, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModTags.Items.STEEL_PLATE).add(TagEntry.element(ModItems.STEEL_PLATE.getId()));
        tag(ModTags.Items.STEEL_ROD).add(TagEntry.element(ModItems.STEEL_ROD.getId()));
        tag(ModTags.Items.STEEL_INGOT).add(TagEntry.element(ModItems.STEEL_INGOT.getId()));
        tag(ModTags.Items.STEEL_NUGGET).add(TagEntry.element(ModItems.STEEL_NUGGET.getId()));

        tag(ModTags.Items.DESMIUM_PLATE).add(TagEntry.element(ModItems.DESMIUM_PLATE.getId()));
        tag(ModTags.Items.DESMIUM_INGOT).add(TagEntry.element(ModItems.DESMIUM_INGOT.getId()));
        tag(ModTags.Items.DESMIUM_NUGGET).add(TagEntry.element(ModItems.DESMIUM_NUGGET.getId()));
    }
}
