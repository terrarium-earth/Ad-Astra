package earth.terrarium.ad_astra.datagen.provider.server;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.CompletableFuture;


public class ModItemTagProvider extends TagsProvider<Item> {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, ForgeRegistries.ITEMS.getRegistryKey(), completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        addVanillaTags();
        ModItems.FLAGS.stream().forEach(i -> tag(ModTags.Items.FLAGS).add(TagEntry.element(i.getId())));
        ModItems.GLOBES.stream().forEach(i -> tag(ModTags.Items.GLOBES).add(TagEntry.element(i.getId())));

        platform(ModTags.Items.ETRIUM_BLOCKS, ItemType.STORAGE_BLOCKS, "etrium");
        platform(ModTags.Items.STEEL_BLOCKS, ItemType.STORAGE_BLOCKS, "steel");
        platform(ModTags.Items.DESMIUM_BLOCKS, ItemType.STORAGE_BLOCKS, "desmium");
        platform(ModTags.Items.THERMALYTE_BLOCKS, ItemType.STORAGE_BLOCKS, "thermalyte");
        platform(ModTags.Items.VESNIUM_BLOCKS, ItemType.STORAGE_BLOCKS, "vesnium");
        platform(ModTags.Items.AEROLYTE_BLOCKS, ItemType.STORAGE_BLOCKS, "aerolyte");

        platform(ModTags.Items.IRON_RODS, ItemType.RODS, "iron");
        platform(ModTags.Items.IRON_PLATES, ItemType.PLATES, "iron");

        platform(ModTags.Items.STEEL_RODS, ItemType.RODS, "steel");
        platform(ModTags.Items.STEEL_PLATES, ItemType.PLATES, "steel");
        platform(ModTags.Items.STEEL_INGOTS, ItemType.INGOTS, "steel");
        platform(ModTags.Items.STEEL_NUGGETS, ItemType.NUGGETS, "steel");

        platform(ModTags.Items.DESMIUM_PLATES, ItemType.PLATES, "desmium");
        platform(ModTags.Items.DESMIUM_INGOTS, ItemType.INGOTS, "desmium");
        platform(ModTags.Items.DESMIUM_NUGGETS, ItemType.NUGGETS, "desmium");

        platform(ModTags.Items.THERMALYTE_PLATES, ItemType.PLATES, "thermalyte");
        platform(ModTags.Items.THERMALYTE_INGOTS, ItemType.INGOTS, "thermalyte");
        platform(ModTags.Items.THERMALYTE_NUGGETS, ItemType.NUGGETS, "thermalyte");

        platform(ModTags.Items.AEROLYTE_PLATES, ItemType.PLATES, "aerolyte");
        platform(ModTags.Items.AEROLYTE_INGOTS, ItemType.INGOTS, "aerolyte");
        platform(ModTags.Items.AEROLYTE_NUGGETS, ItemType.NUGGETS, "aerolyte");

        platform(ModTags.Items.ETRIUM_PLATES, ItemType.PLATES, "etrium");
        platform(ModTags.Items.ETRIUM_INGOTS, ItemType.INGOTS, "etrium");
        platform(ModTags.Items.ETRIUM_NUGGETS, ItemType.NUGGETS, "etrium");
    }

    private void addVanillaTags() {
        ModItems.STAIRS.stream().forEach(b -> tag(ItemTags.STAIRS).add(TagEntry.element(b.getId())));
        ModItems.SLABS.stream().forEach(b -> tag(ItemTags.SLABS).add(TagEntry.element(b.getId())));
        ModItems.WALLS.stream().forEach(b -> tag(ItemTags.WALLS).add(TagEntry.element(b.getId())));
        ModItems.BUTTONS.stream().forEach(b -> tag(ItemTags.BUTTONS).add(TagEntry.element(b.getId())));
        ModItems.SANDS.stream().forEach(b -> tag(ItemTags.SAND).add(TagEntry.element(b.getId())));
        ModItems.STONE_BRICKS.stream().forEach(b -> tag(ItemTags.STONE_BRICKS).add(TagEntry.element(b.getId())));
    }

    private void platform(TagKey<Item> tag, ItemType type, String name) {
        // separate the path of the resource location of the tag by underscores and join every element into a string except the last element.

        tag(tag).addOptionalTag(new ResourceLocation("c", type.fabric(name))).addOptionalTag(new ResourceLocation("forge", type.forge(name)));
    }

    public enum ItemType {
        STORAGE_BLOCKS("storage_blocks", "blocks"),
        PLATES,
        RODS,
        NUGGETS,
        INGOTS;

        private final String forge;
        private final String fabric;

        ItemType(String forge, String fabric) {
            this.forge = forge;
            this.fabric = fabric;
        }

        ItemType() {
            forge = name().toLowerCase();
            fabric = name().toLowerCase();
        }

        public String forge(String material) {
            return forge + "/" + material;
        }

        public String fabric(String material) {
            return material + "_" + fabric;
        }
    }
}
