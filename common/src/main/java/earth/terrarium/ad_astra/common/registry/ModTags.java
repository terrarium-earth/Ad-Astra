package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> RECYCLABLE = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "recyclable"));

        public static final TagKey<Item> STEEL_INGOTS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_ingots"));
        public static final TagKey<Item> STEEL_NUGGETS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_nuggets"));
        public static final TagKey<Item> STEEL_PLATES = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_plates"));
        public static final TagKey<Item> STEEL_RODS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_rods"));

        public static final TagKey<Item> IRON_PLATES = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "iron_plates"));
        public static final TagKey<Item> IRON_RODS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "iron_rods"));

        public static final TagKey<Item> DESMIUM_INGOTS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "desmium_ingots"));
        public static final TagKey<Item> DESMIUM_NUGGETS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "desmium_nuggets"));
        public static final TagKey<Item> DESMIUM_PLATES = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "desmium_plates"));

        public static final TagKey<Item> THERMALYTE_INGOTS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "thermalyte_ingots"));
        public static final TagKey<Item> THERMALYTE_NUGGETS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "thermalyte_nuggets"));
        public static final TagKey<Item> THERMALYTE_PLATES = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "thermalyte_plates"));

        public static final TagKey<Item> AEROLYTE_INGOTS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "aerolyte_ingots"));
        public static final TagKey<Item> AEROLYTE_NUGGETS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "aerolyte_nuggets"));
        public static final TagKey<Item> AEROLYTE_PLATES = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "aerolyte_plates"));

        public static final TagKey<Item> ETRIUM_INGOTS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "etrium_ingots"));
        public static final TagKey<Item> ETRIUM_NUGGETS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "etrium_nuggets"));
        public static final TagKey<Item> ETRIUM_PLATES = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "etrium_plates"));

        public static final TagKey<Item> STEEL_BLOCKS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_blocks"));
        public static final TagKey<Item> DESMIUM_BLOCKS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "desmium_blocks"));
        public static final TagKey<Item> THERMALYTE_BLOCKS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "thermalyte_blocks"));
        public static final TagKey<Item> AEROLYTE_BLOCKS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "aerolyte_blocks"));
        public static final TagKey<Item> ETRIUM_BLOCKS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "etrium_blocks"));
        public static final TagKey<Item> VESNIUM_BLOCKS = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "vesnium_blocks"));
    }

    public static class Blocks {
    }

    public static class Fluids {
        public static final TagKey<Fluid> OXYGEN = TagKey.create(Registries.FLUID, new ResourceLocation(AdAstra.MOD_ID, "oxygen"));
        public static final TagKey<Fluid> HYDROGEN = TagKey.create(Registries.FLUID, new ResourceLocation(AdAstra.MOD_ID, "hydrogen"));
    }
}
