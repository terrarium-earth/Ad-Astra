package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeType;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.*;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class ModRecipeTypes {
    public static final RegistryHolder<RecipeType<?>> RECIPE_TYPES = new RegistryHolder<>(Registry.RECIPE_TYPE, AdAstra.MOD_ID);

    public static final Supplier<RecipeType<CompressingRecipe>> COMPRESSING_RECIPE = RECIPE_TYPES.register("compressing", () -> CodecRecipeType.of("compressing"));
    public static final Supplier<RecipeType<SpaceStationRecipe>> SPACE_STATION_RECIPE = RECIPE_TYPES.register("space_station", () -> CodecRecipeType.of("space_station"));
    public static final Supplier<RecipeType<NasaWorkbenchRecipe>> NASA_WORKBENCH_RECIPE = RECIPE_TYPES.register("nasa_workbench", () -> CodecRecipeType.of("nasa_workbench"));
    public static final Supplier<RecipeType<FuelConversionRecipe>> FUEL_CONVERSION_RECIPE = RECIPE_TYPES.register("fuel_conversion", () -> CodecRecipeType.of("fuel_conversion"));
    public static final Supplier<RecipeType<OxygenConversionRecipe>> OXYGEN_CONVERSION_RECIPE = RECIPE_TYPES.register("oxygen_conversion", () -> CodecRecipeType.of("oxygen_conversion"));
    public static final Supplier<RecipeType<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_RECIPE = RECIPE_TYPES.register("cryo_fuel_conversion", () -> CodecRecipeType.of("cryo_fuel_conversion"));
}