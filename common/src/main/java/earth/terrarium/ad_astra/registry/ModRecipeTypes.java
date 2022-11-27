package earth.terrarium.ad_astra.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeType;
import earth.terrarium.ad_astra.recipes.*;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class ModRecipeTypes {

    public static final Supplier<RecipeType<CompressingRecipe>> COMPRESSING_RECIPE = register("compressing", () -> CodecRecipeType.of("compressing"));
    public static final Supplier<RecipeType<SpaceStationRecipe>> SPACE_STATION_RECIPE = register("space_station", () -> CodecRecipeType.of("space_station"));
    public static final Supplier<RecipeType<NasaWorkbenchRecipe>> NASA_WORKBENCH_RECIPE = register("nasa_workbench", () -> CodecRecipeType.of("nasa_workbench"));
    public static final Supplier<RecipeType<FuelConversionRecipe>> FUEL_CONVERSION_RECIPE = register("fuel_conversion", () -> CodecRecipeType.of("fuel_conversion"));
    public static final Supplier<RecipeType<OxygenConversionRecipe>> OXYGEN_CONVERSION_RECIPE = register("oxygen_conversion", () -> CodecRecipeType.of("oxygen_conversion"));
    public static final Supplier<RecipeType<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_RECIPE = register("cryo_fuel_conversion", () -> CodecRecipeType.of("cryo_fuel_conversion"));

    private static <T extends RecipeType<E>, E extends Recipe<?>> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.RECIPE_TYPE, id, object);
    }

    public static void init() {
    }
}