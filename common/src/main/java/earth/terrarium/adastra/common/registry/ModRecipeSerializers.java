package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.recipes.machines.HydraulicPressingRecipe;
import earth.terrarium.adastra.common.recipes.machines.RefiningRecipe;
import earth.terrarium.adastra.common.recipes.machines.SeparatingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
    public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_SERIALIZER, AdAstra.MOD_ID);

    public static final RegistryEntry<CodecRecipeSerializer<SeparatingRecipe>> SEPARATING = RECIPE_SERIALIZERS.register("separating", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.SEPARATING.get(), SeparatingRecipe::codec));

    public static final RegistryEntry<CodecRecipeSerializer<HydraulicPressingRecipe>> HYDRAULIC_PRESSING = RECIPE_SERIALIZERS.register("hydraulic_pressing", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.HYDRAULIC_PRESSING.get(), HydraulicPressingRecipe::codec));

    public static final RegistryEntry<CodecRecipeSerializer<RefiningRecipe>> REFINING = RECIPE_SERIALIZERS.register("refining", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.REFINING.get(), RefiningRecipe::codec));
}
