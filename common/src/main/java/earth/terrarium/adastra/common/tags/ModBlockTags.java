package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModBlockTags {
    public static final TagKey<Block> PASSES_FLOOD_FILL = tag("passes_flood_fill");
    public static final TagKey<Block> BLOCKS_FLOOD_FILL = tag("blocks_flood_fill");

    public static final TagKey<Block> MOON_STONE_REPLACEABLES = tag("moon_stone_replaceables");
    public static final TagKey<Block> MARS_STONE_REPLACEABLES = tag("mars_stone_replaceables");
    public static final TagKey<Block> VENUS_STONE_REPLACEABLES = tag("venus_stone_replaceables");
    public static final TagKey<Block> MERCURY_STONE_REPLACEABLES = tag("mercury_stone_replaceables");
    public static final TagKey<Block> GLACIO_STONE_REPLACEABLES = tag("glacio_stone_replaceables");

    public static final TagKey<Block> CABLE_DUCTS = tag("cable_ducts");
    public static final TagKey<Block> FLUID_PIPE_DUCTS = tag("fluid_pipe_ducts");

    public static final TagKey<Block> LAUNCH_PADS = tag("launch_pads");

    public static final TagKey<Block> GLOBES = tag("globes");
    public static final TagKey<Block> FLAGS = tag("flags");
    public static final TagKey<Block> SLIDING_DOORS = tag("sliding_doors");

    public static final TagKey<Block> STEEL_BLOCKS = tag("steel_blocks");
    public static final TagKey<Block> DESH_BLOCKS = tag("desh_blocks");
    public static final TagKey<Block> RAW_DESH_BLOCKS = tag("raw_desh_blocks");
    public static final TagKey<Block> OSTRUM_BLOCKS = tag("ostrum_blocks");
    public static final TagKey<Block> RAW_OSTRUM_BLOCKS = tag("raw_ostrum_blocks");
    public static final TagKey<Block> CALORITE_BLOCKS = tag("calorite_blocks");
    public static final TagKey<Block> RAW_CALORITE_BLOCKS = tag("raw_calorite_blocks");


    private static TagKey<Block> tag(String name) {
        return TagKey.create(Registries.BLOCK, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
