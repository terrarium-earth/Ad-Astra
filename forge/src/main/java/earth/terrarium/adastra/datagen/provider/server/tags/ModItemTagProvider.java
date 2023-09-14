package earth.terrarium.adastra.datagen.provider.server.tags;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends TagsProvider<Item> {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        add(ModItemTags.RECYCLABLE, Items.ANVIL);
        add(ModItemTags.RECYCLABLE, Items.DISPENSER);
        add(ModItemTags.RECYCLABLE, Items.DROPPER);

        add(ModItemTags.IRON_PLATES, ModItems.IRON_PLATE.get());
        add(ModItemTags.IRON_RODS, ModItems.IRON_ROD.get());

        add(ModItemTags.STEEL_INGOTS, ModItems.STEEL_INGOT.get());
        add(ModItemTags.STEEL_NUGGETS, ModItems.STEEL_NUGGET.get());
        add(ModItemTags.STEEL_PLATES, ModItems.STEEL_PLATE.get());
        add(ModItemTags.STEEL_RODS, ModItems.STEEL_ROD.get());
        add(ModItemTags.STEEL_BLOCKS, ModItems.BLOCK_OF_STEEL.get());

        add(ModItemTags.ETRIUM_INGOTS, ModItems.ETRIUM_INGOT.get());
        add(ModItemTags.ETRIUM_NUGGETS, ModItems.ETRIUM_NUGGET.get());
        add(ModItemTags.ETRIUM_PLATES, ModItems.ETRIUM_PLATE.get());
        add(ModItemTags.ETRIUM_RODS, ModItems.ETRIUM_ROD.get());
        add(ModItemTags.ETRIUM_BLOCKS, ModItems.BLOCK_OF_ETRIUM.get());
    }

    private void add(TagKey<Item> tag, Item item) {
        tag(tag).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.ITEMS.getKey(item))));
    }
}
