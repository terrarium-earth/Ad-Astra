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

        public static final TagKey<Item> STEEL_INGOT = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_ingot"));
        public static final TagKey<Item> STEEL_NUGGET = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_nugget"));
        public static final TagKey<Item> STEEL_PLATE = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_plate"));
        public static final TagKey<Item> STEEL_ROD = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "steel_rod"));

        //desmium
        public static final TagKey<Item> DESMIUM_INGOT = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "desmium_ingot"));
        public static final TagKey<Item> DESMIUM_NUGGET = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "desmium_nugget"));
        public static final TagKey<Item> DESMIUM_PLATE = TagKey.create(Registries.ITEM, new ResourceLocation(AdAstra.MOD_ID, "desmium_plate"));
    }

    public static class Blocks {
    }

    public static class Fluids {
        public static final TagKey<Fluid> OXYGEN = TagKey.create(Registries.FLUID, new ResourceLocation(AdAstra.MOD_ID, "oxygen"));
        public static final TagKey<Fluid> HYDROGEN = TagKey.create(Registries.FLUID, new ResourceLocation(AdAstra.MOD_ID, "hydrogen"));
    }
}
