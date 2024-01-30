package earth.terrarium.adastra.datagen.provider.server.tags;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.registry.ModBlocks;
import earth.terrarium.adastra.common.tags.ModBlockTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ModBlockTagProvider extends TagsProvider<Block> {

    private static final List<Supplier<Block>> MINEABLE_WITH_SHOVEL = List.of(
        ModBlocks.MOON_SAND,
        ModBlocks.MARS_SAND,
        ModBlocks.VENUS_SAND
    );

    private static final List<Supplier<Block>> MINEABLE_WITH_AXE = List.of(
        ModBlocks.AERONOS_CAP,
        ModBlocks.AERONOS_STEM,
        ModBlocks.AERONOS_PLANKS,
        ModBlocks.AERONOS_STAIRS,
        ModBlocks.AERONOS_FENCE,
        ModBlocks.AERONOS_FENCE_GATE,
        ModBlocks.AERONOS_SLAB,
        ModBlocks.AERONOS_LADDER,
        ModBlocks.AERONOS_DOOR,
        ModBlocks.AERONOS_TRAPDOOR,
        ModBlocks.STROPHAR_CAP,
        ModBlocks.STROPHAR_STEM,
        ModBlocks.STROPHAR_PLANKS,
        ModBlocks.STROPHAR_STAIRS,
        ModBlocks.STROPHAR_FENCE,
        ModBlocks.STROPHAR_FENCE_GATE,
        ModBlocks.STROPHAR_SLAB,
        ModBlocks.STROPHAR_LADDER,
        ModBlocks.STROPHAR_DOOR,
        ModBlocks.STROPHAR_TRAPDOOR,
        ModBlocks.GLACIAN_LOG,
        ModBlocks.STRIPPED_GLACIAN_LOG,
        ModBlocks.GLACIAN_PLANKS,
        ModBlocks.GLACIAN_STAIRS,
        ModBlocks.GLACIAN_SLAB,
        ModBlocks.GLACIAN_DOOR,
        ModBlocks.GLACIAN_TRAPDOOR,
        ModBlocks.GLACIAN_FENCE,
        ModBlocks.GLACIAN_FENCE_GATE,
        ModBlocks.GLACIAN_BUTTON,
        ModBlocks.GLACIAN_PRESSURE_PLATE
    );

    private static final List<Supplier<Block>> NOT_MINED_WITH_PICKAXE = List.of(
        ModBlocks.CHEESE_BLOCK,
        ModBlocks.GLACIAN_FUR,
        ModBlocks.GLACIAN_LEAVES,
        ModBlocks.OIL,
        ModBlocks.FUEL,
        ModBlocks.CRYO_FUEL,
        ModBlocks.HYDROGEN
    );

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ModBlocks.GLOBES.stream().map(RegistryEntry::get).forEach(b -> tag(ModBlockTags.GLOBES).add(element(b)));
        ModBlocks.FLAGS.stream().map(RegistryEntry::get).forEach(b -> tag(ModBlockTags.FLAGS).add(element(b)));
        ModBlocks.SLIDING_DOORS.stream().map(RegistryEntry::get).forEach(b -> tag(ModBlockTags.SLIDING_DOORS).add(element(b)));
        tag(ModBlockTags.PASSES_FLOOD_FILL).add(TagEntry.tag(BlockTags.FENCES.location()));
        tag(ModBlockTags.PASSES_FLOOD_FILL).add(TagEntry.tag(BlockTags.LEAVES.location()));
        tag(ModBlockTags.PASSES_FLOOD_FILL).add(element(Blocks.LADDER));

        add(ModBlockTags.CABLE_DUCTS, ModBlocks.CABLE_DUCT.get());
        add(ModBlockTags.FLUID_PIPE_DUCTS, ModBlocks.FLUID_PIPE_DUCT.get());

        add(ModBlockTags.LAUNCH_PADS, ModBlocks.LAUNCH_PAD.get());

        add(ModBlockTags.PASSES_FLOOD_FILL, Blocks.IRON_BARS);
        add(ModBlockTags.PASSES_FLOOD_FILL, Blocks.TNT);
        add(ModBlockTags.PASSES_FLOOD_FILL, ModBlocks.VENT.get());
        add(ModBlockTags.PASSES_FLOOD_FILL, ModBlocks.OXYGEN_DISTRIBUTOR.get());
        add(ModBlockTags.PASSES_FLOOD_FILL, ModBlocks.GRAVITY_NORMALIZER.get());

        add(ModBlockTags.MOON_STONE_REPLACEABLES, ModBlocks.MOON_STONE.get());
        add(ModBlockTags.MOON_STONE_REPLACEABLES, ModBlocks.MOON_DEEPSLATE.get());

        add(ModBlockTags.MARS_STONE_REPLACEABLES, ModBlocks.MARS_STONE.get());

        add(ModBlockTags.VENUS_STONE_REPLACEABLES, ModBlocks.VENUS_STONE.get());

        add(ModBlockTags.MERCURY_STONE_REPLACEABLES, ModBlocks.MERCURY_STONE.get());

        add(ModBlockTags.GLACIO_STONE_REPLACEABLES, ModBlocks.GLACIO_STONE.get());
        add(ModBlockTags.GLACIO_STONE_REPLACEABLES, ModBlocks.PERMAFROST.get());

        add(ModBlockTags.STEEL_BLOCKS, ModBlocks.STEEL_BLOCK.get(), "steel_blocks", "storage_blocks/steel");
        add(ModBlockTags.DESH_BLOCKS, ModBlocks.DESH_BLOCK.get(), "desh_blocks", "storage_blocks/desh");
        add(ModBlockTags.RAW_DESH_BLOCKS, ModBlocks.RAW_DESH_BLOCK.get(), "raw_desh_blocks", "storage_blocks/raw_desh");
        add(ModBlockTags.OSTRUM_BLOCKS, ModBlocks.OSTRUM_BLOCK.get(), "ostrum_blocks", "storage_blocks/ostrum");
        add(ModBlockTags.RAW_OSTRUM_BLOCKS, ModBlocks.RAW_OSTRUM_BLOCK.get(), "raw_ostrum_blocks", "storage_blocks/raw_ostrum");
        add(ModBlockTags.CALORITE_BLOCKS, ModBlocks.CALORITE_BLOCK.get(), "calorite_blocks", "storage_blocks/calorite");
        add(ModBlockTags.RAW_CALORITE_BLOCKS, ModBlocks.RAW_CALORITE_BLOCK.get(), "raw_calorite_blocks", "storage_blocks/raw_calorite");

        add(ModBlockTags.GLACIAN_LOGS, ModBlocks.GLACIAN_LOG.get());
        add(ModBlockTags.GLACIAN_LOGS, ModBlocks.STRIPPED_GLACIAN_LOG.get());

        add(ModBlockTags.AERONOS_CAPS, ModBlocks.AERONOS_CAP.get());
        add(ModBlockTags.AERONOS_CAPS, ModBlocks.AERONOS_STEM.get());

        add(ModBlockTags.STROPHAR_CAPS, ModBlocks.STROPHAR_CAP.get());
        add(ModBlockTags.STROPHAR_CAPS, ModBlocks.STROPHAR_STEM.get());

        tag(ModBlockTags.DESTROYED_IN_SPACE)
            .addTag(BlockTags.SAPLINGS)
            .addTag(BlockTags.LEAVES)
            .addTag(BlockTags.FLOWERS)
            .addTag(BlockTags.CORALS)
            .addTag(BlockTags.CROPS)
            .addTag(BlockTags.FIRE)
            .addTag(BlockTags.CAVE_VINES)
            .add(element(Blocks.TORCH))
            .add(element(Blocks.WALL_TORCH))
            .add(element(Blocks.LANTERN))
            .add(element(Blocks.JACK_O_LANTERN))
            .add(element(Blocks.COCOA))
            .add(element(Blocks.VINE))
            .add(element(Blocks.BROWN_MUSHROOM_BLOCK))
            .add(element(Blocks.RED_MUSHROOM_BLOCK))
            .add(element(Blocks.BIG_DRIPLEAF))
            .add(element(Blocks.BIG_DRIPLEAF_STEM))
            .add(element(Blocks.SMALL_DRIPLEAF))
            .add(element(Blocks.SHORT_GRASS))
            .add(element(Blocks.TALL_GRASS))
            .add(element(Blocks.TALL_GRASS))
            .add(element(Blocks.SWEET_BERRY_BUSH))
            .add(element(Blocks.BAMBOO));

        addVanillaTags();
    }

    private void addVanillaTags() {
        ModBlocks.STAIRS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.STAIRS).add(element(b)));
        ModBlocks.SLABS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.SLABS).add(element(b)));
        ModBlocks.PRESSURE_PLATES.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.PRESSURE_PLATES).add(element(b)));
        ModBlocks.BUTTONS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.BUTTONS).add(element(b)));
        ModBlocks.WALLS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.WALLS).add(element(b)));
        ModBlocks.MACHINES.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.NEEDS_IRON_TOOL).add(element(b)));
        ModBlocks.SLIDING_DOORS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.NEEDS_IRON_TOOL).add(element(b)));

        tag(BlockTags.COAL_ORES).add(element(ModBlocks.VENUS_COAL_ORE.get()));
        tag(BlockTags.COAL_ORES).add(element(ModBlocks.GLACIO_COAL_ORE.get()));

        tag(BlockTags.COPPER_ORES).add(element(ModBlocks.GLACIO_COPPER_ORE.get()));
        tag(BlockTags.DIAMOND_ORES).add(element(ModBlocks.MARS_DIAMOND_ORE.get()));
        tag(BlockTags.DIAMOND_ORES).add(element(ModBlocks.VENUS_DIAMOND_ORE.get()));

        tag(BlockTags.DOORS).add(element(ModBlocks.STEEL_DOOR.get()));
        tag(BlockTags.WOODEN_DOORS).add(element(ModBlocks.AERONOS_DOOR.get()));
        tag(BlockTags.WOODEN_DOORS).add(element(ModBlocks.STROPHAR_DOOR.get()));
        tag(BlockTags.WOODEN_DOORS).add(element(ModBlocks.GLACIAN_DOOR.get()));

        tag(BlockTags.WOODEN_FENCES).add(element(ModBlocks.AERONOS_FENCE.get()));
        tag(BlockTags.WOODEN_FENCES).add(element(ModBlocks.STROPHAR_FENCE.get()));
        tag(BlockTags.WOODEN_FENCES).add(element(ModBlocks.GLACIAN_FENCE.get()));

        tag(BlockTags.FENCE_GATES).add(element(ModBlocks.AERONOS_FENCE_GATE.get()));
        tag(BlockTags.FENCE_GATES).add(element(ModBlocks.STROPHAR_FENCE_GATE.get()));
        tag(BlockTags.FENCE_GATES).add(element(ModBlocks.GLACIAN_FENCE_GATE.get()));

        tag(BlockTags.GOLD_ORES).add(element(ModBlocks.VENUS_GOLD_ORE.get()));

        tag(BlockTags.IRON_ORES).add(element(ModBlocks.MOON_IRON_ORE.get()));
        tag(BlockTags.IRON_ORES).add(element(ModBlocks.MARS_IRON_ORE.get()));
        tag(BlockTags.IRON_ORES).add(element(ModBlocks.MERCURY_IRON_ORE.get()));
        tag(BlockTags.IRON_ORES).add(element(ModBlocks.GLACIO_IRON_ORE.get()));

        tag(BlockTags.LAPIS_ORES).add(element(ModBlocks.GLACIO_LAPIS_ORE.get()));

        tag(BlockTags.LEAVES).add(element(ModBlocks.GLACIAN_LEAVES.get()));

        tag(BlockTags.LOGS_THAT_BURN).add(element(ModBlocks.GLACIAN_LOG.get()));

        tag(BlockTags.PLANKS).add(element(ModBlocks.AERONOS_PLANKS.get()));
        tag(BlockTags.PLANKS).add(element(ModBlocks.STROPHAR_PLANKS.get()));
        tag(BlockTags.PLANKS).add(element(ModBlocks.GLACIAN_PLANKS.get()));

        tag(BlockTags.SAND).add(element(ModBlocks.MOON_SAND.get()));
        tag(BlockTags.SAND).add(element(ModBlocks.MARS_SAND.get()));
        tag(BlockTags.SAND).add(element(ModBlocks.VENUS_SAND.get()));

        tag(BlockTags.SMELTS_TO_GLASS).add(element(ModBlocks.MOON_SAND.get()));
        tag(BlockTags.SMELTS_TO_GLASS).add(element(ModBlocks.MARS_SAND.get()));
        tag(BlockTags.SMELTS_TO_GLASS).add(element(ModBlocks.VENUS_SAND.get()));

        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.MOON_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CRACKED_MOON_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CHISELED_MOON_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.MARS_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CRACKED_MARS_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CHISELED_MARS_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.VENUS_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CRACKED_VENUS_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CHISELED_VENUS_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.MERCURY_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CRACKED_MERCURY_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.GLACIO_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CRACKED_GLACIO_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.PERMAFROST_BRICKS.get()));
        tag(BlockTags.STONE_BRICKS).add(element(ModBlocks.PERMAFROST_TILES.get()));

        tag(BlockTags.TRAPDOORS).add(element(ModBlocks.STEEL_TRAPDOOR.get()));
        tag(BlockTags.WOODEN_TRAPDOORS).add(element(ModBlocks.AERONOS_TRAPDOOR.get()));
        tag(BlockTags.WOODEN_TRAPDOORS).add(element(ModBlocks.STROPHAR_TRAPDOOR.get()));
        tag(BlockTags.WOODEN_TRAPDOORS).add(element(ModBlocks.GLACIAN_TRAPDOOR.get()));

        tag(BlockTags.WOODEN_BUTTONS).add(element(ModBlocks.GLACIAN_BUTTON.get()));

        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(element(ModBlocks.GLACIAN_PRESSURE_PLATE.get()));

        tag(BlockTags.WOODEN_SLABS).add(element(ModBlocks.AERONOS_SLAB.get()));
        tag(BlockTags.WOODEN_SLABS).add(element(ModBlocks.STROPHAR_SLAB.get()));
        tag(BlockTags.WOODEN_SLABS).add(element(ModBlocks.GLACIAN_SLAB.get()));

        tag(BlockTags.WOODEN_STAIRS).add(element(ModBlocks.AERONOS_STAIRS.get()));
        tag(BlockTags.WOODEN_STAIRS).add(element(ModBlocks.STROPHAR_STAIRS.get()));
        tag(BlockTags.WOODEN_STAIRS).add(element(ModBlocks.GLACIAN_STAIRS.get()));

        tag(BlockTags.WOOL).add(element(ModBlocks.GLACIAN_FUR.get()));

        tag(BlockTags.BEACON_BASE_BLOCKS).add(element(ModBlocks.STEEL_BLOCK.get()));
        tag(BlockTags.BEACON_BASE_BLOCKS).add(element(ModBlocks.ETRIUM_BLOCK.get()));
        tag(BlockTags.BEACON_BASE_BLOCKS).add(element(ModBlocks.DESH_BLOCK.get()));
        tag(BlockTags.BEACON_BASE_BLOCKS).add(element(ModBlocks.OSTRUM_BLOCK.get()));
        tag(BlockTags.BEACON_BASE_BLOCKS).add(element(ModBlocks.CALORITE_BLOCK.get()));

        tag(BlockTags.CLIMBABLE).add(element(ModBlocks.AERONOS_LADDER.get()));
        tag(BlockTags.CLIMBABLE).add(element(ModBlocks.STROPHAR_LADDER.get()));

        tag(BlockTags.GUARDED_BY_PIGLINS).add(element(ModBlocks.VENUS_GOLD_ORE.get()));

        tag(BlockTags.NEEDS_IRON_TOOL).add(element(ModBlocks.MARS_DIAMOND_ORE.get()));
        tag(BlockTags.NEEDS_IRON_TOOL).add(element(ModBlocks.MARS_DIAMOND_ORE.get()));
        tag(BlockTags.NEEDS_IRON_TOOL).add(element(ModBlocks.VENUS_GOLD_ORE.get()));
        tag(BlockTags.NEEDS_IRON_TOOL).add(element(ModBlocks.GLACIO_LAPIS_ORE.get()));
        tag(BlockTags.NEEDS_IRON_TOOL).add(element(ModBlocks.STEEL_DOOR.get()));
        tag(BlockTags.NEEDS_IRON_TOOL).add(element(ModBlocks.STEEL_TRAPDOOR.get()));

        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(element(ModBlocks.MOON_STONE.get()));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(element(ModBlocks.MARS_STONE.get()));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(element(ModBlocks.VENUS_STONE.get()));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(element(ModBlocks.MERCURY_STONE.get()));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(element(ModBlocks.GLACIO_STONE.get()));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(element(ModBlocks.PERMAFROST.get()));

        MINEABLE_WITH_SHOVEL.stream().map(Supplier::get).forEach(b -> tag(BlockTags.MINEABLE_WITH_SHOVEL).add(element(b)));
        MINEABLE_WITH_AXE.stream().map(Supplier::get).forEach(b -> tag(BlockTags.MINEABLE_WITH_AXE).add(element(b)));

        ModBlocks.BLOCKS.stream()
            .filter(b -> !MINEABLE_WITH_SHOVEL.contains(b) && !MINEABLE_WITH_AXE.contains(b) && !NOT_MINED_WITH_PICKAXE.contains(b))
            .map(RegistryEntry::get)
            .forEach(b -> tag(BlockTags.MINEABLE_WITH_PICKAXE).add(element(b)));
    }

    private void add(TagKey<Block> tag, Block block) {
        tag(tag).add(element(block));
    }

    private void add(TagKey<Block> tag, Block item, String fabricCommonTag, String forgeCommonTag) {
        add(tag, item);
        addFabricTag(item, tag, fabricCommonTag);
        addForgeTag(item, tag, forgeCommonTag);
    }

    private void addFabricTag(Block item, TagKey<Block> tag, String fabricCommonTag) {
        tag(tag).add(TagEntry.optionalTag(new ResourceLocation("c", fabricCommonTag)));

        var commonTag = TagKey.create(Registries.BLOCK, new ResourceLocation("c", fabricCommonTag));
        tag(commonTag).add(element(item));
    }

    private void addForgeTag(Block item, TagKey<Block> tag, String forgeCommonTag) {
        tag(tag).add(TagEntry.optionalTag(new ResourceLocation("forge", forgeCommonTag)));

        var commonTag = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", forgeCommonTag));
        tag(commonTag).add(element(item));

        var folderTag = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", forgeCommonTag.split("/")[0]));
        tag(folderTag).add(TagEntry.tag(commonTag.location()));
    }

    private static TagEntry element(Block block) {
        return TagEntry.element(loc(block));
    }

    private static ResourceLocation loc(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }
}
