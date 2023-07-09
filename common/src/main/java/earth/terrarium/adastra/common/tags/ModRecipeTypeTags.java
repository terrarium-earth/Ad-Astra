package earth.terrarium.adastra.common.tags;

import earth.terrarium.adastra.AdAstra;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.RecipeType;

public final class ModRecipeTypeTags {
    public static final TagKey<RecipeType<?>> RECYCLABLES = tag("recyclables");

    private static TagKey<RecipeType<?>> tag(String name) {
        return TagKey.create(Registries.RECIPE_TYPE, new ResourceLocation(AdAstra.MOD_ID, name));
    }
}
