package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ForgeItemTagProvider extends TagsProvider<Item> {
    public static final TagKey<Item> STEEL_PLATES = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "plates/steel"));
    public static final TagKey<Item> STEEL_RODS = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "rods/steel"));
    public static final TagKey<Item> STEEL_INGOTS = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "ingots/steel"));
    public static final TagKey<Item> STEEL_NUGGETS = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "nuggets/steel"));

    public static final TagKey<Item> DESMIUM_NUGGETS = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "nuggets/desmium"));
    public static final TagKey<Item> DESMIUM_INGOTS = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "nuggets/desmium"));
    public static final TagKey<Item> DESMIUM_PLATES = TagKey.create(ForgeRegistries.ITEMS.getRegistryKey(), new ResourceLocation("forge", "nuggets/desmium"));


    public ForgeItemTagProvider(PackOutput arg, ResourceKey<? extends Registry<Item>> arg2, CompletableFuture<HolderLookup.Provider> completableFuture, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, arg2, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(STEEL_PLATES).add(TagEntry.element(ModItems.STEEL_PLATE.getId()));
        tag(STEEL_RODS).add(TagEntry.element(ModItems.STEEL_ROD.getId()));
        tag(STEEL_INGOTS).add(TagEntry.element(ModItems.STEEL_INGOT.getId()));
        tag(STEEL_NUGGETS).add(TagEntry.element(ModItems.STEEL_NUGGET.getId()));

        tag(DESMIUM_PLATES).add(TagEntry.element(ModItems.DESMIUM_PLATE.getId()));
        tag(DESMIUM_INGOTS).add(TagEntry.element(ModItems.DESMIUM_INGOT.getId()));
        tag(DESMIUM_NUGGETS).add(TagEntry.element(ModItems.DESMIUM_NUGGET.getId()));
    }
}
