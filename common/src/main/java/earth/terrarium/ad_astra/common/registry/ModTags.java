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
    }

    public static class Blocks {
    }

    public static class FLUIDS {
        public static final TagKey<Fluid> OXYGEN = TagKey.create(Registries.FLUID, new ResourceLocation(AdAstra.MOD_ID, "oxygen"));
        public static final TagKey<Fluid> HYDROGEN = TagKey.create(Registries.FLUID, new ResourceLocation(AdAstra.MOD_ID, "hydrogen"));
    }
}
