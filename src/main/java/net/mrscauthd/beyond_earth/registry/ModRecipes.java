package net.mrscauthd.beyond_earth.registry;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.mrscauthd.beyond_earth.recipes.*;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class ModRecipes {

    public static ModRecipeType<ModRecipe> GENERATING_RECIPE;
    public static ModRecipeType<ModRecipe> COMPRESSING_RECIPE;

    public static void register() {

        // Recipe Types.
        GENERATING_RECIPE = register(new ModRecipeType<>(GeneratingRecipe.RECIPE_ID.toString()));
        COMPRESSING_RECIPE = register(new ModRecipeType<>(CompressingRecipe.RECIPE_ID.toString()));

        // Recipe Serializers.
        Registry.register(Registry.RECIPE_SERIALIZER, new ModIdentifier("hammer_recipe"), new HammerShapelessRecipe.Serializer());
        Registry.register(Registry.RECIPE_SERIALIZER,  GeneratingRecipe.RECIPE_ID, new GeneratingRecipe.Serializer());
        Registry.register(Registry.RECIPE_SERIALIZER,  CompressingRecipe.RECIPE_ID, new CompressingRecipe.Serializer());
    }

    private static <T extends ModRecipeType<?>> T register(T value) {
        Registry.register(Registry.RECIPE_TYPE, new Identifier(value.getName()), value);
        return value;
    }
}
