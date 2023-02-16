package earth.terrarium.ad_astra.common.recipe.machine;

import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.registry.ModRecipeSerializers;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class HydraulicPressingRecipe extends AbstractCookingRecipe {

    public HydraulicPressingRecipe(ResourceLocation resourceLocation, String group, CookingBookCategory cookingBookCategory, Ingredient ingredient, ItemStack itemStack, float f, int i) {
        super(ModRecipeTypes.HYDRAULIC_PRESSING.get(), resourceLocation, group, cookingBookCategory, ingredient, itemStack, f, i);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.HYDRAULIC_PRESSING.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return ModItems.ETRIONIC_PRESS.get().getDefaultInstance();
    }
}
