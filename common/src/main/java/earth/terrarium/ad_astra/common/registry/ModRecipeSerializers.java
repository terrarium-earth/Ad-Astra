package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.*;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.function.Supplier;

public class ModRecipeSerializers {
    public static final RegistryHolder<RecipeSerializer<?>> RECIPE_SERIALIZERS = new RegistryHolder<>(Registry.RECIPE_SERIALIZER, AdAstra.MOD_ID);

    public static final Supplier<RecipeSerializer<ShapelessRecipe>> HAMMER_SERIALIZER = RECIPE_SERIALIZERS.register("hammering", HammerShapelessRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<CompressingRecipe>> COMPRESSING_SERIALIZER = RECIPE_SERIALIZERS.register("compressing", () -> new CodecRecipeSerializer<>(ModRecipeTypes.COMPRESSING_RECIPE.get(), CompressingRecipe::codec));
    public static final Supplier<RecipeSerializer<SpaceStationRecipe>> SPACE_STATION_SERIALIZER = RECIPE_SERIALIZERS.register("space_station", () -> new CodecRecipeSerializer<>(ModRecipeTypes.SPACE_STATION_RECIPE.get(), SpaceStationRecipe::codec));
    public static final Supplier<RecipeSerializer<NasaWorkbenchRecipe>> NASA_WORKBENCH_SERIALIZER = RECIPE_SERIALIZERS.register("nasa_workbench", () -> new CodecRecipeSerializer<>(ModRecipeTypes.NASA_WORKBENCH_RECIPE.get(), NasaWorkbenchRecipe::codec));
    public static final Supplier<RecipeSerializer<FuelConversionRecipe>> FUEL_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("fuel_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.FUEL_CONVERSION_RECIPE.get(), FuelConversionRecipe::codec));
    public static final Supplier<RecipeSerializer<OxygenConversionRecipe>> OXYGEN_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("oxygen_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.OXYGEN_CONVERSION_RECIPE.get(), OxygenConversionRecipe::oxygenCodec));
    public static final Supplier<RecipeSerializer<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("cryo_fuel_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.CRYO_FUEL_CONVERSION_RECIPE.get(), CryoFuelConversionRecipe::codec));
    public static final Supplier<SpaceSuitShapedRecipe.Serializer> SPACE_SUIT_CRAFTING_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_shaped_space_suit", SpaceSuitShapedRecipe.Serializer::new);
}
