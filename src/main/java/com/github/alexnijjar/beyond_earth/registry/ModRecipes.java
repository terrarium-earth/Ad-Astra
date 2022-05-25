package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.recipes.CompressingRecipe;
import com.github.alexnijjar.beyond_earth.recipes.GeneratingRecipe;
import com.github.alexnijjar.beyond_earth.recipes.HammerShapelessRecipe;
import com.github.alexnijjar.beyond_earth.recipes.ModRecipeType;
import com.github.alexnijjar.beyond_earth.recipes.SpaceStationRecipe;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.registry.Registry;

public class ModRecipes {

    public static ModRecipeType<GeneratingRecipe> GENERATING_RECIPE;
    public static ModRecipeType<CompressingRecipe> COMPRESSING_RECIPE;
    public static ModRecipeType<SpaceStationRecipe> SPACE_STATION_RECIPE;

    public static RecipeSerializer<ShapelessRecipe> HAMMER_SERIALIZER;
    public static RecipeSerializer<GeneratingRecipe> GENERATING_SERIALIZER;
    public static RecipeSerializer<CompressingRecipe> COMPRESSING_SERIALIZER;
    public static RecipeSerializer<SpaceStationRecipe> SPACE_STATION_SERIALIZER;

    public static void register() {

        // Recipe Types.
        GENERATING_RECIPE = register(new ModRecipeType<>(new ModIdentifier("generating")));
        COMPRESSING_RECIPE = register(new ModRecipeType<>(new ModIdentifier("compressing")));
        SPACE_STATION_RECIPE = register(new ModRecipeType<>(new ModIdentifier("space_station")));

        // Recipe Serializers.
        HAMMER_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("hammering"), new HammerShapelessRecipe.Serializer());
        GENERATING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("generating"), new GeneratingRecipe.Serializer());
        COMPRESSING_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("compressing"), new CompressingRecipe.Serializer());
        SPACE_STATION_SERIALIZER = Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("space_station"), new SpaceStationRecipe.Serializer());
    }

    private static <T extends ModRecipeType<?>> T register(T recipe) {
        Registry.register(Registry.RECIPE_TYPE, recipe.getId(), recipe);
        return recipe;
    }
}