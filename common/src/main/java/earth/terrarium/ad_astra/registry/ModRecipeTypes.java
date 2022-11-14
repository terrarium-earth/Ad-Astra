package earth.terrarium.ad_astra.registry;

import earth.terrarium.ad_astra.recipes.*;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class ModRecipeTypes {

    public static final Supplier<ModRecipeType<CompressingRecipe>> COMPRESSING_RECIPE = register("compressing", () -> new ModRecipeType<>(new ModResourceLocation("compressing")));
    public static final Supplier<ModRecipeType<SpaceStationRecipe>> SPACE_STATION_RECIPE = register("space_station", () -> new ModRecipeType<>(new ModResourceLocation("space_station")));
    public static final Supplier<ModRecipeType<NasaWorkbenchRecipe>> NASA_WORKBENCH_RECIPE = register("nasa_workbench", () -> new ModRecipeType<>(new ModResourceLocation("nasa_workbench")));
    public static final Supplier<ModRecipeType<FluidConversionRecipe>> FUEL_CONVERSION_RECIPE = register("fuel_conversion", () -> new ModRecipeType<>(new ModResourceLocation("fuel_conversion")));
    public static final Supplier<ModRecipeType<OxygenConversionRecipe>> OXYGEN_CONVERSION_RECIPE = register("oxygen_conversion", () -> new ModRecipeType<>(new ModResourceLocation("oxygen_conversion")));
    public static final Supplier<ModRecipeType<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_RECIPE = register("cryo_fuel_conversion", () -> new ModRecipeType<>(new ModResourceLocation("cryo_fuel_conversion")));

    private static <T extends RecipeType<E>, E extends Recipe<?>> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.RECIPE_TYPE, id, object);
    }

    public static void init() {
    }
}