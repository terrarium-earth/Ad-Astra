package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeType;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final ResourcefulRegistry<RecipeType<?>> RECIPE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<RecipeType<CompressingRecipe>> COMPRESSING_RECIPE = RECIPE_TYPES.register("compressing", () -> CodecRecipeType.of("compressing"));
    public static final RegistryEntry<RecipeType<SpaceStationRecipe>> SPACE_STATION_RECIPE = RECIPE_TYPES.register("space_station", () -> CodecRecipeType.of("space_station"));
    public static final RegistryEntry<RecipeType<NasaWorkbenchRecipe>> NASA_WORKBENCH_RECIPE = RECIPE_TYPES.register("nasa_workbench", () -> CodecRecipeType.of("nasa_workbench"));
    public static final RegistryEntry<RecipeType<FuelConversionRecipe>> FUEL_CONVERSION_RECIPE = RECIPE_TYPES.register("fuel_conversion", () -> CodecRecipeType.of("fuel_conversion"));
    public static final RegistryEntry<RecipeType<OxygenConversionRecipe>> OXYGEN_CONVERSION_RECIPE = RECIPE_TYPES.register("oxygen_conversion", () -> CodecRecipeType.of("oxygen_conversion"));
    public static final RegistryEntry<RecipeType<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_RECIPE = RECIPE_TYPES.register("cryo_fuel_conversion", () -> CodecRecipeType.of("cryo_fuel_conversion"));
}