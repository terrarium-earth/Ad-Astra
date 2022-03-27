package net.mrscauthd.beyond_earth.registry;

import net.minecraft.recipe.RecipeType;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.recipes.GeneratingRecipe;

public class ModRecipeTypes {

    public static RecipeType<GeneratingRecipe> GENERATING_RECIPE;

    public static void register() {
        GENERATING_RECIPE = RecipeType.register(BeyondEarth.MOD_ID + ":generating");
    }
}
