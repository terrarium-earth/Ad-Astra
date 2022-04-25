package net.mrscauthd.beyond_earth.registry;

import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.recipes.CompressingRecipe;
import net.mrscauthd.beyond_earth.recipes.GeneratingRecipe;
import net.mrscauthd.beyond_earth.recipes.HammerShapelessRecipe;
import net.mrscauthd.beyond_earth.recipes.ModRecipe;
import net.mrscauthd.beyond_earth.recipes.ModRecipeType;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class ModRecipes {

    public static ModRecipeType<ModRecipe> GENERATING_RECIPE;
    public static ModRecipeType<ModRecipe> COMPRESSING_RECIPE;

    public static void register() {

        // TODO: Fix server crash with recipes.

        // Recipe Types.
        GENERATING_RECIPE = register(new ModRecipeType<>(new ModIdentifier("generating")));
        COMPRESSING_RECIPE = register(new ModRecipeType<>(new ModIdentifier("compressing")));

        // Recipe Serializers.
        Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("hammering"), new HammerShapelessRecipe.Serializer());
        Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("generating"), new GeneratingRecipe.Serializer());
        Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("compressing"), new CompressingRecipe.Serializer());
    }

    private static <T extends ModRecipeType<?>> T register(T recipe) {
        Registry.register(Registry.RECIPE_TYPE, recipe.getId(), recipe);
        return recipe;
    }
}