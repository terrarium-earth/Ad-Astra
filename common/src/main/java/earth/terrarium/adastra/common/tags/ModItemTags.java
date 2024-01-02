package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
    public static final TagKey<Item> GLOBES = tag("globes");
    public static final TagKey<Item> FLAGS = tag("flags");
    public static final TagKey<Item> SLIDING_DOORS = tag("sliding_doors");

    public static final TagKey<Item> CABLE_DUCTS = tag("cable_ducts");
    public static final TagKey<Item> FLUID_PIPE_DUCTS = tag("fluid_pipe_ducts");

    public static final TagKey<Item> SPACE_SUITS = tag("space_suit_items");
    public static final TagKey<Item> NETHERITE_SPACE_SUITS = tag("netherite_space_suit_items");
    public static final TagKey<Item> JET_SUITS = tag("jet_suit_items");

    public static final TagKey<Item> FREEZE_RESISTANT_ARMOR = tag("freeze_resistant_armor");
    public static final TagKey<Item> HEAT_RESISTANT_ARMOR = tag("heat_resistant_armor");

    public static final TagKey<Item> HELD_OVER_HEAD = tag("held_over_head");

    public static final TagKey<Item> IRON_PLATES = tag("iron_plates");
    public static final TagKey<Item> IRON_RODS = tag("iron_rods");

    public static final TagKey<Item> STEEL_INGOTS = tag("steel_ingots");
    public static final TagKey<Item> STEEL_NUGGETS = tag("steel_nuggets");
    public static final TagKey<Item> STEEL_PLATES = tag("steel_plates");
    public static final TagKey<Item> STEEL_RODS = tag("steel_rods");
    public static final TagKey<Item> STEEL_BLOCKS = tag("steel_blocks");

    public static final TagKey<Item> DESH_INGOTS = tag("desh_ingots");
    public static final TagKey<Item> DESH_NUGGETS = tag("desh_nuggets");
    public static final TagKey<Item> DESH_PLATES = tag("desh_plates");
    public static final TagKey<Item> RAW_DESH = tag("raw_desh");
    public static final TagKey<Item> DESH_BLOCKS = tag("desh_blocks");
    public static final TagKey<Item> RAW_DESH_BLOCKS = tag("raw_desh_blocks");

    public static final TagKey<Item> OSTRUM_INGOTS = tag("ostrum_ingots");
    public static final TagKey<Item> OSTRUM_NUGGETS = tag("ostrum_nuggets");
    public static final TagKey<Item> OSTRUM_PLATES = tag("ostrum_plates");
    public static final TagKey<Item> RAW_OSTRUM = tag("raw_ostrum");
    public static final TagKey<Item> OSTRUM_BLOCKS = tag("ostrum_blocks");
    public static final TagKey<Item> RAW_OSTRUM_BLOCKS = tag("raw_ostrum_blocks");

    public static final TagKey<Item> CALORITE_INGOTS = tag("calorite_ingots");
    public static final TagKey<Item> CALORITE_NUGGETS = tag("calorite_nuggets");
    public static final TagKey<Item> CALORITE_PLATES = tag("calorite_plates");
    public static final TagKey<Item> RAW_CALORITE = tag("raw_calorite");
    public static final TagKey<Item> CALORITE_BLOCKS = tag("calorite_blocks");
    public static final TagKey<Item> RAW_CALORITE_BLOCKS = tag("raw_calorite_blocks");

    public static final TagKey<Item> GLACIAN_LOGS = tag("glacian_logs");
    public static final TagKey<Item> AERONOS_CAPS = tag("aeronos_caps");
    public static final TagKey<Item> STROPHAR_CAPS = tag("strophar_caps");

    public static final TagKey<Item> DESTROYED_IN_SPACE = tag("destroyed_in_space");

    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
