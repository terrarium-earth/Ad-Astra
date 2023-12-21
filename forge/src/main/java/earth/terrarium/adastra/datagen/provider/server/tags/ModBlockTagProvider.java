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
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class ModBlockTagProvider extends TagsProvider<Block> {

    private static final Set<Supplier<Block>> MINEABLE_WITH_SHOVEL = Set.of(
        ModBlocks.MOON_SAND,
        ModBlocks.MARS_SAND,
        ModBlocks.VENUS_SAND
    );

    private static final Set<Supplier<Block>> MINEABLE_WITH_AXE = Set.of(
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

    private static final Set<Supplier<Block>> NOT_MINED_WITH_PICKAXE = Set.of(
        ModBlocks.CHEESE_BLOCK,
        ModBlocks.GLACIAN_FUR,
        ModBlocks.GLACIAN_LEAVES
    );

    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> completableFuture, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, completableFuture, AdAstra.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        ModBlocks.GLOBES.stream().map(RegistryEntry::get).forEach(b -> tag(ModBlockTags.GLOBES).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(b)))));
        ModBlocks.FLAGS.stream().map(RegistryEntry::get).forEach(b -> tag(ModBlockTags.FLAGS).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(b)))));
        ModBlocks.SLIDING_DOORS.stream().map(RegistryEntry::get).forEach(b -> tag(ModBlockTags.SLIDING_DOORS).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(b)))));
        tag(ModBlockTags.PASSES_FLOOD_FILL).add(TagEntry.tag(BlockTags.FENCES.location()));

        add(ModBlockTags.CABLE_DUCTS, ModBlocks.CABLE_DUCT.get());
        add(ModBlockTags.FLUID_PIPE_DUCTS, ModBlocks.FLUID_PIPE_DUCT.get());

        add(ModBlockTags.LAUNCH_PADS, ModBlocks.LAUNCH_PAD.get());

        add(ModBlockTags.PASSES_FLOOD_FILL, Blocks.IRON_BARS);
        add(ModBlockTags.PASSES_FLOOD_FILL, Blocks.TNT);
        add(ModBlockTags.PASSES_FLOOD_FILL, ModBlocks.VENT.get());
        add(ModBlockTags.PASSES_FLOOD_FILL, ModBlocks.OXYGEN_DISTRIBUTOR.get());
//        add(ModBlockTags.PASSES_FLOOD_FILL, ModBlocks.GRAVITY_NORMALIZER.get()); // TODO

        add(ModBlockTags.MOON_STONE_REPLACEABLES, ModBlocks.MOON_STONE.get());
        add(ModBlockTags.MOON_STONE_REPLACEABLES, ModBlocks.MOON_DEEPSLATE.get());

        add(ModBlockTags.MARS_STONE_REPLACEABLES, ModBlocks.MARS_STONE.get());

        add(ModBlockTags.VENUS_STONE_REPLACEABLES, ModBlocks.VENUS_STONE.get());

        add(ModBlockTags.MERCURY_STONE_REPLACEABLES, ModBlocks.MERCURY_STONE.get());

        add(ModBlockTags.GLACIO_STONE_REPLACEABLES, ModBlocks.GLACIO_STONE.get());
        add(ModBlockTags.GLACIO_STONE_REPLACEABLES, ModBlocks.PERMAFROST.get());

        add(ModBlockTags.STEEL_BLOCKS, ModBlocks.BLOCK_OF_STEEL.get(), "steel_blocks", "storage_blocks/steel");
        add(ModBlockTags.DESH_BLOCKS, ModBlocks.BLOCK_OF_DESH.get(), "desh_blocks", "storage_blocks/desh");
        add(ModBlockTags.RAW_DESH_BLOCKS, ModBlocks.RAW_DESH_BLOCK.get(), "raw_desh_blocks", "storage_blocks/raw_desh");
        add(ModBlockTags.OSTRUM_BLOCKS, ModBlocks.BLOCK_OF_OSTRUM.get(), "ostrum_blocks", "storage_blocks/ostrum");
        add(ModBlockTags.RAW_OSTRUM_BLOCKS, ModBlocks.RAW_OSTRUM_BLOCK.get(), "raw_ostrum_blocks", "storage_blocks/raw_ostrum");
        add(ModBlockTags.CALORITE_BLOCKS, ModBlocks.BLOCK_OF_CALORITE.get(), "calorite_blocks", "storage_blocks/calorite");
        add(ModBlockTags.RAW_CALORITE_BLOCKS, ModBlocks.RAW_CALORITE_BLOCK.get(), "raw_calorite_blocks", "storage_blocks/raw_calorite");
        addVanillaTags();
    }

    private void addVanillaTags() {
        ModBlocks.STAIRS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.STAIRS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.SLABS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.SLABS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.PRESSURE_PLATES.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.PRESSURE_PLATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.BUTTONS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.BUTTONS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.WALLS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.WALLS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.MACHINES.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.NEEDS_IRON_TOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        ModBlocks.SLIDING_DOORS.stream().map(RegistryEntry::get).forEach(b -> tag(BlockTags.NEEDS_IRON_TOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));

        tag(BlockTags.COAL_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_COAL_ORE.get())));
        tag(BlockTags.COAL_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_COAL_ORE.get())));

        tag(BlockTags.COPPER_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_COPPER_ORE.get())));
        tag(BlockTags.COPPER_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_DIAMOND_ORE.get())));
        tag(BlockTags.COPPER_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_DIAMOND_ORE.get())));

        tag(BlockTags.DOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STEEL_DOOR.get())));
        tag(BlockTags.WOODEN_DOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_DOOR.get())));
        tag(BlockTags.WOODEN_DOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_DOOR.get())));
        tag(BlockTags.WOODEN_DOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_DOOR.get())));

        tag(BlockTags.WOODEN_FENCES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_FENCE.get())));
        tag(BlockTags.WOODEN_FENCES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_FENCE.get())));
        tag(BlockTags.WOODEN_FENCES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_FENCE.get())));

        tag(BlockTags.FENCE_GATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_FENCE_GATE.get())));
        tag(BlockTags.FENCE_GATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_FENCE_GATE.get())));
        tag(BlockTags.FENCE_GATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_FENCE_GATE.get())));

        tag(BlockTags.GOLD_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_GOLD_ORE.get())));

        tag(BlockTags.IRON_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_IRON_ORE.get())));
        tag(BlockTags.IRON_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_IRON_ORE.get())));
        tag(BlockTags.IRON_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MERCURY_IRON_ORE.get())));
        tag(BlockTags.IRON_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_IRON_ORE.get())));

        tag(BlockTags.LAPIS_ORES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_LAPIS_ORE.get())));

        tag(BlockTags.LEAVES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_LEAVES.get())));

        tag(BlockTags.LOGS_THAT_BURN).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_LOG.get())));

        tag(BlockTags.PLANKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_PLANKS.get())));
        tag(BlockTags.PLANKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_PLANKS.get())));
        tag(BlockTags.PLANKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_PLANKS.get())));

        tag(BlockTags.SAND).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_SAND.get())));
        tag(BlockTags.SAND).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_SAND.get())));
        tag(BlockTags.SAND).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_SAND.get())));

        tag(BlockTags.SMELTS_TO_GLASS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_SAND.get())));
        tag(BlockTags.SMELTS_TO_GLASS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_SAND.get())));
        tag(BlockTags.SMELTS_TO_GLASS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_SAND.get())));

        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_MOON_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_MOON_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_MARS_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_MARS_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_VENUS_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_VENUS_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MERCURY_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_MERCURY_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_MERCURY_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CRACKED_GLACIO_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.CHISELED_GLACIO_STONE_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.PERMAFROST_BRICKS.get())));
        tag(BlockTags.STONE_BRICKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.PERMAFROST_TILES.get())));

        tag(BlockTags.TRAPDOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STEEL_TRAPDOOR.get())));
        tag(BlockTags.WOODEN_TRAPDOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_TRAPDOOR.get())));
        tag(BlockTags.WOODEN_TRAPDOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_TRAPDOOR.get())));
        tag(BlockTags.WOODEN_TRAPDOORS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_TRAPDOOR.get())));

        tag(BlockTags.WOODEN_BUTTONS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_BUTTON.get())));

        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_PRESSURE_PLATE.get())));

        tag(BlockTags.WOODEN_SLABS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_SLAB.get())));
        tag(BlockTags.WOODEN_SLABS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_SLAB.get())));
        tag(BlockTags.WOODEN_SLABS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_SLAB.get())));

        tag(BlockTags.WOODEN_STAIRS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_STAIRS.get())));
        tag(BlockTags.WOODEN_STAIRS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_STAIRS.get())));
        tag(BlockTags.WOODEN_STAIRS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_STAIRS.get())));

        tag(BlockTags.WOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIAN_FUR.get())));

        tag(BlockTags.BEACON_BASE_BLOCKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.BLOCK_OF_STEEL.get())));
        tag(BlockTags.BEACON_BASE_BLOCKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.BLOCK_OF_ETRIUM.get())));
        tag(BlockTags.BEACON_BASE_BLOCKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.BLOCK_OF_DESH.get())));
        tag(BlockTags.BEACON_BASE_BLOCKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.BLOCK_OF_OSTRUM.get())));
        tag(BlockTags.BEACON_BASE_BLOCKS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.BLOCK_OF_CALORITE.get())));

        tag(BlockTags.CLIMBABLE).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.AERONOS_LADDER.get())));
        tag(BlockTags.CLIMBABLE).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STROPHAR_LADDER.get())));

        tag(BlockTags.GUARDED_BY_PIGLINS).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_GOLD_ORE.get())));

        tag(BlockTags.NEEDS_IRON_TOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_DIAMOND_ORE.get())));
        tag(BlockTags.NEEDS_IRON_TOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_DIAMOND_ORE.get())));
        tag(BlockTags.NEEDS_IRON_TOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_GOLD_ORE.get())));
        tag(BlockTags.NEEDS_IRON_TOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_LAPIS_ORE.get())));
        tag(BlockTags.NEEDS_IRON_TOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STEEL_DOOR.get())));
        tag(BlockTags.NEEDS_IRON_TOOL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.STEEL_TRAPDOOR.get())));

        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MOON_STONE.get())));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MARS_STONE.get())));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.VENUS_STONE.get())));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.MERCURY_STONE.get())));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.GLACIO_STONE.get())));
        tag(BlockTags.OVERWORLD_CARVER_REPLACEABLES).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(ModBlocks.PERMAFROST.get())));

        MINEABLE_WITH_SHOVEL.stream().map(Supplier::get).forEach(b -> tag(BlockTags.MINEABLE_WITH_SHOVEL).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
        MINEABLE_WITH_AXE.stream().map(Supplier::get).forEach(b -> tag(BlockTags.MINEABLE_WITH_AXE).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));

        ModBlocks.BLOCKS.stream()
            .filter(b -> !MINEABLE_WITH_SHOVEL.contains(b) && !MINEABLE_WITH_AXE.contains(b) && !NOT_MINED_WITH_PICKAXE.contains(b))
            .map(RegistryEntry::get)
            .forEach(b -> tag(BlockTags.MINEABLE_WITH_PICKAXE).add(TagEntry.element(BuiltInRegistries.BLOCK.getKey(b))));
    }

    private void add(TagKey<Block> tag, Block block) {
        tag(tag).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(block))));
    }

    private void add(TagKey<Block> tag, Block item, String fabricCommonTag, String forgeCommonTag) {
        add(tag, item);
        addFabricTag(item, tag, fabricCommonTag);
        addForgeTag(item, tag, forgeCommonTag);
    }

    private void addFabricTag(Block item, TagKey<Block> tag, String fabricCommonTag) {
        tag(tag).add(TagEntry.optionalTag(new ResourceLocation("c", fabricCommonTag)));

        var commonTag = TagKey.create(Registries.BLOCK, new ResourceLocation("c", fabricCommonTag));
        tag(commonTag).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(item))));
    }

    private void addForgeTag(Block item, TagKey<Block> tag, String forgeCommonTag) {
        tag(tag).add(TagEntry.optionalTag(new ResourceLocation("forge", forgeCommonTag)));

        var commonTag = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", forgeCommonTag));
        tag(commonTag).add(TagEntry.element(Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(item))));

        var folderTag = TagKey.create(Registries.BLOCK, new ResourceLocation("forge", forgeCommonTag.split("/")[0]));
        tag(folderTag).add(TagEntry.tag(commonTag.location()));
    }
}
