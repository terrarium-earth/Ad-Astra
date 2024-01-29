package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.recipes.SpaceStationRecipe;
import earth.terrarium.adastra.common.recipes.machines.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModRecipeSerializers {
    public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_SERIALIZER, AdAstra.MOD_ID);

    public static final RegistryEntry<CodecRecipeSerializer<CompressingRecipe>> COMPRESSING = RECIPE_SERIALIZERS.register("compressing", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.COMPRESSING.get(), CompressingRecipe::codec, CompressingRecipe::netCodec));

    public static final RegistryEntry<CodecRecipeSerializer<AlloyingRecipe>> ALLOYING = RECIPE_SERIALIZERS.register("alloying", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.ALLOYING.get(), AlloyingRecipe::codec, AlloyingRecipe::netCodec));

    public static final RegistryEntry<CodecRecipeSerializer<OxygenLoadingRecipe>> OXYGEN_LOADING = RECIPE_SERIALIZERS.register("oxygen_loading", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.OXYGEN_LOADING.get(), OxygenLoadingRecipe::codec));

    public static final RegistryEntry<CodecRecipeSerializer<RefiningRecipe>> REFINING = RECIPE_SERIALIZERS.register("refining", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.REFINING.get(), RefiningRecipe::codec));

    public static final RegistryEntry<CodecRecipeSerializer<CryoFreezingRecipe>> CRYO_FREEZING = RECIPE_SERIALIZERS.register("cryo_freezing", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.CRYO_FREEZING.get(), CryoFreezingRecipe::codec, CryoFreezingRecipe::netCodec));

    public static final RegistryEntry<CodecRecipeSerializer<NasaWorkbenchRecipe>> NASA_WORKBENCH_SERIALIZER = RECIPE_SERIALIZERS.register("nasa_workbench", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.NASA_WORKBENCH.get(), NasaWorkbenchRecipe::codec, NasaWorkbenchRecipe::netCodec));

    public static final RegistryEntry<CodecRecipeSerializer<SpaceStationRecipe>> SPACE_STATION_SERIALIZER = RECIPE_SERIALIZERS.register("space_station_recipe", () ->
        new CodecRecipeSerializer<>(ModRecipeTypes.SPACE_STATION_RECIPE.get(), SpaceStationRecipe::codec, SpaceStationRecipe::netCodec));
}
