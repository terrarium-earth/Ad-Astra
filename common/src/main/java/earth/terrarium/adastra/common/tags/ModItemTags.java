package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
    public static final TagKey<Item> RECYCLABLE = tag("recyclable");

    public static final TagKey<Item> IRON_PLATES = tag("iron_plates");
    public static final TagKey<Item> IRON_RODS = tag("iron_rods");

    public static final TagKey<Item> STEEL_INGOTS = tag("steel_ingots");
    public static final TagKey<Item> STEEL_NUGGETS = tag("steel_nuggets");
    public static final TagKey<Item> STEEL_PLATES = tag("steel_plates");
    public static final TagKey<Item> STEEL_RODS = tag("steel_rods");
    public static final TagKey<Item> STEEL_BLOCKS = tag("steel_blocks");

    public static final TagKey<Item> ETRIUM_INGOTS = tag("etrium_ingots");
    public static final TagKey<Item> ETRIUM_NUGGETS = tag("etrium_nuggets");
    public static final TagKey<Item> ETRIUM_PLATES = tag("etrium_plates");
    public static final TagKey<Item> ETRIUM_RODS = tag("etrium_rods");
    public static final TagKey<Item> ETRIUM_BLOCKS = tag("etrium_blocks");


    private static TagKey<Item> tag(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
