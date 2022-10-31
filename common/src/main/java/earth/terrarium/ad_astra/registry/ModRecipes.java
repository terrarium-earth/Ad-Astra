package earth.terrarium.ad_astra.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.recipes.*;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ModRecipes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(AdAstra.MOD_ID, Registry.RECIPE_TYPE_REGISTRY);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(AdAstra.MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY);

    public static Supplier<ModRecipeType<CompressingRecipe>> COMPRESSING_RECIPE;
    public static Supplier<ModRecipeType<SpaceStationRecipe>> SPACE_STATION_RECIPE;
    public static Supplier<ModRecipeType<NasaWorkbenchRecipe>> NASA_WORKBENCH_RECIPE;
    public static Supplier<ModRecipeType<FluidConversionRecipe>> FUEL_CONVERSION_RECIPE;
    public static Supplier<ModRecipeType<OxygenConversionRecipe>> OXYGEN_CONVERSION_RECIPE;
    public static Supplier<ModRecipeType<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_RECIPE;

    public static Supplier<RecipeSerializer<ShapelessRecipe>> HAMMER_SERIALIZER;
    public static Supplier<RecipeSerializer<CompressingRecipe>> COMPRESSING_SERIALIZER;
    public static Supplier<RecipeSerializer<SpaceStationRecipe>> SPACE_STATION_SERIALIZER;
    public static Supplier<RecipeSerializer<NasaWorkbenchRecipe>> NASA_WORKBENCH_SERIALIZER;
    public static Supplier<RecipeSerializer<FluidConversionRecipe>> FUEL_CONVERSION_SERIALIZER;
    public static Supplier<RecipeSerializer<OxygenConversionRecipe>> OXYGEN_CONVERSION_SERIALIZER;
    public static Supplier<RecipeSerializer<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_SERIALIZER;

    public static void register() {

        // Recipe Types
        COMPRESSING_RECIPE = RECIPE_TYPES.register("compressing", () -> new ModRecipeType<>(new ModResourceLocation("compressing")));
        SPACE_STATION_RECIPE = RECIPE_TYPES.register("space_station", () -> new ModRecipeType<>(new ModResourceLocation("space_station")));
        NASA_WORKBENCH_RECIPE = RECIPE_TYPES.register("nasa_workbench", () -> new ModRecipeType<>(new ModResourceLocation("nasa_workbench")));
        FUEL_CONVERSION_RECIPE = RECIPE_TYPES.register("fuel_conversion", () -> new ModRecipeType<>(new ModResourceLocation("nasa_workbench")));
        OXYGEN_CONVERSION_RECIPE = RECIPE_TYPES.register("oxygen_conversion", () -> new ModRecipeType<>(new ModResourceLocation("fuel_conversion")));
        CRYO_FUEL_CONVERSION_RECIPE = RECIPE_TYPES.register("cryo_fuel_conversion", () -> new ModRecipeType<>(new ModResourceLocation("cryo_fuel_conversion")));
        RECIPE_TYPES.register();

        // Recipe Serializers
        HAMMER_SERIALIZER = RECIPE_SERIALIZERS.register("hammering", HammerShapelessRecipe.Serializer::new);
        COMPRESSING_SERIALIZER = RECIPE_SERIALIZERS.register("compressing", () -> new CodecRecipeSerializer<>(COMPRESSING_RECIPE.get(), CompressingRecipe::codec));
        SPACE_STATION_SERIALIZER = RECIPE_SERIALIZERS.register("space_station", () -> new CodecRecipeSerializer<>(SPACE_STATION_RECIPE.get(), SpaceStationRecipe::codec));
        NASA_WORKBENCH_SERIALIZER = RECIPE_SERIALIZERS.register("nasa_workbench", () -> new CodecRecipeSerializer<>(NASA_WORKBENCH_RECIPE.get(), NasaWorkbenchRecipe::codec));
        FUEL_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("fuel_conversion", () -> new CodecRecipeSerializer<>(FUEL_CONVERSION_RECIPE.get(), FluidConversionRecipe::codec));
        OXYGEN_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("oxygen_conversion", () -> new CodecRecipeSerializer<>(OXYGEN_CONVERSION_RECIPE.get(), OxygenConversionRecipe::oxygenCodec));
        CRYO_FUEL_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("cryo_fuel_conversion", () -> new CodecRecipeSerializer<>(CRYO_FUEL_CONVERSION_RECIPE.get(), CryoFuelConversionRecipe::codec));
        RECIPE_SERIALIZERS.register();
    }
}