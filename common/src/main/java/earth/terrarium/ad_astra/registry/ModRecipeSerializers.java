package earth.terrarium.ad_astra.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import earth.terrarium.ad_astra.recipes.*;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

import java.util.function.Supplier;

public class ModRecipeSerializers {
    public static final Supplier<RecipeSerializer<ShapelessRecipe>> HAMMER_SERIALIZER = register("hammering", HammerShapelessRecipe.Serializer::new);
    public static final Supplier<RecipeSerializer<CompressingRecipe>> COMPRESSING_SERIALIZER = register("compressing", () -> new CodecRecipeSerializer<>(ModRecipeTypes.COMPRESSING_RECIPE.get(), CompressingRecipe::codec));
    public static final Supplier<RecipeSerializer<SpaceStationRecipe>> SPACE_STATION_SERIALIZER = register("space_station", () -> new CodecRecipeSerializer<>(ModRecipeTypes.SPACE_STATION_RECIPE.get(), SpaceStationRecipe::codec));
    public static final Supplier<RecipeSerializer<NasaWorkbenchRecipe>> NASA_WORKBENCH_SERIALIZER = register("nasa_workbench", () -> new CodecRecipeSerializer<>(ModRecipeTypes.NASA_WORKBENCH_RECIPE.get(), NasaWorkbenchRecipe::codec));
    public static final Supplier<RecipeSerializer<FluidConversionRecipe>> FUEL_CONVERSION_SERIALIZER = register("fuel_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.FUEL_CONVERSION_RECIPE.get(), FluidConversionRecipe::codec));
    public static final Supplier<RecipeSerializer<OxygenConversionRecipe>> OXYGEN_CONVERSION_SERIALIZER = register("oxygen_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.OXYGEN_CONVERSION_RECIPE.get(), OxygenConversionRecipe::oxygenCodec));
    public static final Supplier<RecipeSerializer<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_SERIALIZER = register("cryo_fuel_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.CRYO_FUEL_CONVERSION_RECIPE.get(), CryoFuelConversionRecipe::codec));

    private static <T extends RecipeSerializer<E>, E extends Recipe<?>> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.RECIPE_SERIALIZER, id, object);
    }

    public static void init() {
    }
}
