package net.mrscauthd.beyond_earth.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.recipes.CompressingRecipe;
import net.mrscauthd.beyond_earth.recipes.GeneratingRecipe;
import net.mrscauthd.beyond_earth.recipes.ModRecipe;
import net.mrscauthd.beyond_earth.recipes.ModRecipeType;

public class ModRecipeTypes {

    public static ModRecipeType<ModRecipe> GENERATING_RECIPE;
    public static ModRecipeType<ModRecipe> COMPRESSING_RECIPE;

    public static void register() {
        GENERATING_RECIPE = register(new ModRecipeType<>(GeneratingRecipe.RECIPE_ID.toString()));
        COMPRESSING_RECIPE = register(new ModRecipeType<>(CompressingRecipe.RECIPE_ID.toString()));
    }

    private static <T extends ModRecipeType<?>> T register(T value) {
        Registry.register(Registry.RECIPE_TYPE, new Identifier(value.getName()), value);
        return value;
    }
}
