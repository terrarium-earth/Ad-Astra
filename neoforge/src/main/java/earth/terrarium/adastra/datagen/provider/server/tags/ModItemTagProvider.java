package earth.terrarium.adastra.datagen.provider.server.tags;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.registry.ModItems;
import earth.terrarium.adastra.common.tags.ModItemTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends TagsProvider<Item> {

    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ModItems.GLOBES.stream().map(RegistryEntry::get).forEach(b -> tag(ModItemTags.GLOBES).add(element(b)));
        ModItems.FLAGS.stream().map(RegistryEntry::get).forEach(b -> tag(ModItemTags.FLAGS).add(element(b)));
        ModItems.SLIDING_DOORS.stream().map(RegistryEntry::get).forEach(b -> tag(ModItemTags.SLIDING_DOORS).add(element(b)));
        ModItems.VEHICLES.stream().map(RegistryEntry::get).forEach(b -> tag(ModItemTags.HELD_OVER_HEAD).add(element(b)));

        add(ModItemTags.SPACE_SUITS, ModItems.SPACE_HELMET.get());
        add(ModItemTags.SPACE_SUITS, ModItems.SPACE_SUIT.get());
        add(ModItemTags.SPACE_SUITS, ModItems.SPACE_PANTS.get());
        add(ModItemTags.SPACE_SUITS, ModItems.SPACE_BOOTS.get());
        tag(ModItemTags.SPACE_SUITS).add(TagEntry.tag(ModItemTags.NETHERITE_SPACE_SUITS.location()));
        tag(ModItemTags.SPACE_SUITS).add(TagEntry.tag(ModItemTags.JET_SUITS.location()));

        add(ModItemTags.NETHERITE_SPACE_SUITS, ModItems.NETHERITE_SPACE_HELMET.get());
        add(ModItemTags.NETHERITE_SPACE_SUITS, ModItems.NETHERITE_SPACE_SUIT.get());
        add(ModItemTags.NETHERITE_SPACE_SUITS, ModItems.NETHERITE_SPACE_PANTS.get());
        add(ModItemTags.NETHERITE_SPACE_SUITS, ModItems.NETHERITE_SPACE_BOOTS.get());
        tag(ModItemTags.NETHERITE_SPACE_SUITS).add(TagEntry.tag(ModItemTags.JET_SUITS.location()));

        add(ModItemTags.JET_SUITS, ModItems.JET_SUIT_HELMET.get());
        add(ModItemTags.JET_SUITS, ModItems.JET_SUIT.get());
        add(ModItemTags.JET_SUITS, ModItems.JET_SUIT_PANTS.get());
        add(ModItemTags.JET_SUITS, ModItems.JET_SUIT_BOOTS.get());

        tag(ModItemTags.FREEZE_RESISTANT_ARMOR).add(TagEntry.tag(ModItemTags.SPACE_SUITS.location()));
        tag(ModItemTags.HEAT_RESISTANT_ARMOR).add(TagEntry.tag(ModItemTags.NETHERITE_SPACE_SUITS.location()));

        add(ModItemTags.HELD_OVER_HEAD, ModItems.LAUNCH_PAD.get());

        add(ModItemTags.CABLE_DUCTS, ModItems.CABLE_DUCT.get());
        add(ModItemTags.FLUID_PIPE_DUCTS, ModItems.FLUID_PIPE_DUCT.get());

        add(ModItemTags.IRON_PLATES, ModItems.IRON_PLATE.get(), "iron_plates", "plates/iron");
        add(ModItemTags.IRON_RODS, ModItems.IRON_ROD.get(), "iron_rods", "rods/iron");

        add(ModItemTags.STEEL_INGOTS, ModItems.STEEL_INGOT.get(), "steel_ingots", "ingots/steel");
        add(ModItemTags.STEEL_NUGGETS, ModItems.STEEL_NUGGET.get(), "steel_nuggets", "nuggets/steel");
        add(ModItemTags.STEEL_PLATES, ModItems.STEEL_PLATE.get(), "steel_plates", "plates/steel");
        add(ModItemTags.STEEL_RODS, ModItems.STEEL_ROD.get(), "steel_rods", "rods/steel");
        add(ModItemTags.STEEL_BLOCKS, ModItems.STEEL_BLOCK.get(), "steel_blocks", "storage_blocks/steel");

        add(ModItemTags.DESH_INGOTS, ModItems.DESH_INGOT.get(), "desh_ingots", "ingots/desh");
        add(ModItemTags.DESH_NUGGETS, ModItems.DESH_NUGGET.get(), "desh_nuggets", "nuggets/desh");
        add(ModItemTags.DESH_PLATES, ModItems.DESH_PLATE.get(), "desh_plates", "plates/desh");
        add(ModItemTags.RAW_DESH, ModItems.RAW_DESH.get(), "raw_desh", "raw_materials/desh");
        add(ModItemTags.DESH_BLOCKS, ModItems.DESH_BLOCK.get(), "desh_blocks", "storage_blocks/desh");
        add(ModItemTags.RAW_DESH_BLOCKS, ModItems.RAW_DESH_BLOCK.get(), "raw_desh_blocks", "storage_blocks/raw_desh");

        add(ModItemTags.OSTRUM_INGOTS, ModItems.OSTRUM_INGOT.get(), "ostrum_ingots", "ingots/ostrum");
        add(ModItemTags.OSTRUM_NUGGETS, ModItems.OSTRUM_NUGGET.get(), "ostrum_nuggets", "nuggets/ostrum");
        add(ModItemTags.OSTRUM_PLATES, ModItems.OSTRUM_PLATE.get(), "ostrum_plates", "plates/ostrum");
        add(ModItemTags.RAW_OSTRUM, ModItems.RAW_OSTRUM.get(), "raw_ostrum", "raw_materials/ostrum");
        add(ModItemTags.OSTRUM_BLOCKS, ModItems.OSTRUM_BLOCK.get(), "ostrum_blocks", "storage_blocks/ostrum");
        add(ModItemTags.RAW_OSTRUM_BLOCKS, ModItems.RAW_OSTRUM_BLOCK.get(), "raw_ostrum_blocks", "storage_blocks/raw_ostrum");

        add(ModItemTags.CALORITE_INGOTS, ModItems.CALORITE_INGOT.get(), "calorite_ingots", "ingots/calorite");
        add(ModItemTags.CALORITE_NUGGETS, ModItems.CALORITE_NUGGET.get(), "calorite_nuggets", "nuggets/calorite");
        add(ModItemTags.CALORITE_PLATES, ModItems.CALORITE_PLATE.get(), "calorite_plates", "plates/calorite");
        add(ModItemTags.RAW_CALORITE, ModItems.RAW_CALORITE.get(), "raw_calorite", "raw_materials/calorite");
        add(ModItemTags.CALORITE_BLOCKS, ModItems.CALORITE_BLOCK.get(), "calorite_blocks", "storage_blocks/calorite");
        add(ModItemTags.RAW_CALORITE_BLOCKS, ModItems.RAW_CALORITE_BLOCK.get(), "raw_calorite_blocks", "storage_blocks/raw_calorite");

        add(ModItemTags.GLACIAN_LOGS, ModItems.GLACIAN_LOG.get());
        add(ModItemTags.GLACIAN_LOGS, ModItems.STRIPPED_GLACIAN_LOG.get());

        add(ModItemTags.AERONOS_CAPS, ModItems.AERONOS_CAP.get());
        add(ModItemTags.AERONOS_CAPS, ModItems.AERONOS_STEM.get());

        add(ModItemTags.STROPHAR_CAPS, ModItems.STROPHAR_CAP.get());
        add(ModItemTags.STROPHAR_CAPS, ModItems.STROPHAR_STEM.get());

        tag(ModItemTags.DESTROYED_IN_SPACE)
            .addTag(ItemTags.SAPLINGS)
            .addTag(ItemTags.LEAVES)
            .addTag(ItemTags.FLOWERS)
            .addTag(ItemTags.CANDLES)
            .add(element(Items.TORCH))
            .add(element(Items.LANTERN))
            .add(element(Items.CAMPFIRE))
            .add(element(Items.JACK_O_LANTERN))
            .add(element(Items.COCOA_BEANS))
            .add(element(Items.VINE))
            .add(element(Items.BROWN_MUSHROOM_BLOCK))
            .add(element(Items.RED_MUSHROOM_BLOCK))
            .add(element(Items.BIG_DRIPLEAF))
            .add(element(Items.SMALL_DRIPLEAF))
            .add(element(Items.SHORT_GRASS))
            .add(element(Items.TALL_GRASS))
            .add(element(Items.SWEET_BERRIES))
            .add(element(Items.BAMBOO));

        addVanillaTags();
    }

    private void addVanillaTags() {
        ModBlocks.STAIRS.stream().map(RegistryEntry::get).forEach(b -> tag(ItemTags.STAIRS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.SLABS.stream().map(RegistryEntry::get).forEach(b -> tag(ItemTags.SLABS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.BUTTONS.stream().map(RegistryEntry::get).forEach(b -> tag(ItemTags.BUTTONS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.WALLS.stream().map(RegistryEntry::get).forEach(b -> tag(ItemTags.WALLS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));

        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(element(ModItems.STEEL_INGOT.get()));
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(element(ModItems.ETRIUM_INGOT.get()));
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(element(ModItems.DESH_INGOT.get()));
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(element(ModItems.OSTRUM_INGOT.get()));
        tag(ItemTags.BEACON_PAYMENT_ITEMS).add(element(ModItems.CALORITE_INGOT.get()));

        tag(ItemTags.COAL_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_COAL_ORE.get())));
        tag(ItemTags.COAL_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_COAL_ORE.get())));

        tag(ItemTags.COPPER_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_COPPER_ORE.get())));
        tag(ItemTags.COPPER_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_DIAMOND_ORE.get())));
        tag(ItemTags.COPPER_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_DIAMOND_ORE.get())));

        tag(ItemTags.DOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STEEL_DOOR.get())));
        tag(ItemTags.WOODEN_DOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_DOOR.get())));
        tag(ItemTags.WOODEN_DOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_DOOR.get())));
        tag(ItemTags.WOODEN_DOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_DOOR.get())));

        tag(ItemTags.WOODEN_FENCES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_FENCE.get())));
        tag(ItemTags.WOODEN_FENCES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_FENCE.get())));
        tag(ItemTags.WOODEN_FENCES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_FENCE.get())));

        tag(ItemTags.FENCE_GATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_FENCE_GATE.get())));
        tag(ItemTags.FENCE_GATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_FENCE_GATE.get())));
        tag(ItemTags.FENCE_GATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_FENCE_GATE.get())));

        tag(ItemTags.GOLD_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_GOLD_ORE.get())));

        tag(ItemTags.IRON_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_IRON_ORE.get())));
        tag(ItemTags.IRON_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_IRON_ORE.get())));
        tag(ItemTags.IRON_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MERCURY_IRON_ORE.get())));
        tag(ItemTags.IRON_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_IRON_ORE.get())));

        tag(ItemTags.LAPIS_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_LAPIS_ORE.get())));

        tag(ItemTags.LEAVES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_LEAVES.get())));

        tag(ItemTags.LOGS_THAT_BURN).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_LOG.get())));

        tag(ItemTags.PIGLIN_LOVED).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_GOLD_ORE.get())));

        tag(ItemTags.PLANKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_PLANKS.get())));
        tag(ItemTags.PLANKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_PLANKS.get())));
        tag(ItemTags.PLANKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_PLANKS.get())));

        tag(ItemTags.SAND).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_SAND.get())));
        tag(ItemTags.SAND).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_SAND.get())));
        tag(ItemTags.SAND).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_SAND.get())));

        tag(ItemTags.SMELTS_TO_GLASS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_SAND.get())));
        tag(ItemTags.SMELTS_TO_GLASS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_SAND.get())));
        tag(ItemTags.SMELTS_TO_GLASS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_SAND.get())));

        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_MOON_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_MOON_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_MARS_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_MARS_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_VENUS_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_VENUS_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MERCURY_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_MERCURY_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_GLACIO_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.PERMAFROST_BRICKS.get())));
        tag(ItemTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.PERMAFROST_TILES.get())));

        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_COBBLESTONE.get())));
        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_COBBLESTONE.get())));
        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_COBBLESTONE.get())));
        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MERCURY_COBBLESTONE.get())));
        tag(ItemTags.STONE_CRAFTING_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_COBBLESTONE.get())));

        tag(ItemTags.STONE_TOOL_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_COBBLESTONE.get())));
        tag(ItemTags.STONE_TOOL_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_COBBLESTONE.get())));
        tag(ItemTags.STONE_TOOL_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_COBBLESTONE.get())));
        tag(ItemTags.STONE_TOOL_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MERCURY_COBBLESTONE.get())));
        tag(ItemTags.STONE_TOOL_MATERIALS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_COBBLESTONE.get())));

        tag(ItemTags.TRAPDOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STEEL_TRAPDOOR.get())));
        tag(ItemTags.WOODEN_TRAPDOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_TRAPDOOR.get())));
        tag(ItemTags.WOODEN_TRAPDOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_TRAPDOOR.get())));
        tag(ItemTags.WOODEN_TRAPDOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_TRAPDOOR.get())));

        tag(ItemTags.WOODEN_BUTTONS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_BUTTON.get())));

        tag(ItemTags.WOODEN_PRESSURE_PLATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_PRESSURE_PLATE.get())));

        tag(ItemTags.WOODEN_SLABS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_SLAB.get())));
        tag(ItemTags.WOODEN_SLABS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_SLAB.get())));
        tag(ItemTags.WOODEN_SLABS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_SLAB.get())));

        tag(ItemTags.WOODEN_STAIRS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_STAIRS.get())));
        tag(ItemTags.WOODEN_STAIRS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_STAIRS.get())));
        tag(ItemTags.WOODEN_STAIRS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_STAIRS.get())));

        tag(ItemTags.WOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_FUR.get())));
    }

    private void add(TagKey<Item> tag, Item item) {
        tag(tag).add(element(item));
    }

    private void add(TagKey<Item> tag, Item item, String fabricCommonTag, String forgeCommonTag) {
        add(tag, item);
        addFabricTag(item, tag, fabricCommonTag);
        addForgeTag(item, tag, forgeCommonTag);
    }

    private void addFabricTag(Item item, TagKey<Item> tag, String fabricCommonTag) {
        tag(tag).add(TagEntry.optionalTag(new ResourceLocation("c", fabricCommonTag)));

        var commonTag = TagKey.create(Registries.ITEM, new ResourceLocation("c", fabricCommonTag));
        tag(commonTag).add(element(item));
    }

    private void addForgeTag(Item item, TagKey<Item> tag, String forgeCommonTag) {
        tag(tag).add(TagEntry.optionalTag(new ResourceLocation("forge", forgeCommonTag)));

        var commonTag = TagKey.create(Registries.ITEM, new ResourceLocation("forge", forgeCommonTag));
        tag(commonTag).add(element(item));

        var folderTag = TagKey.create(Registries.ITEM, new ResourceLocation("forge", forgeCommonTag.split("/")[0]));
        tag(folderTag).add(TagEntry.tag(commonTag.location()));
    }

    private static TagEntry element(Item item) {
        return TagEntry.element(loc(item));
    }

    private static ResourceLocation loc(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
