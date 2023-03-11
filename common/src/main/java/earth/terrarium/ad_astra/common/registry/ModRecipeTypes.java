package earth.terrarium.ad_astra.common.registry;

import java.util.HashSet;
import java.util.Set;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeType;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.*;
import earth.terrarium.ad_astra.common.recipe.lunarian.*;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final ResourcefulRegistry<RecipeType<?>> RECIPE_TYPES = ResourcefulRegistries.create(Registry.RECIPE_TYPE, AdAstra.MOD_ID);
    private static final Set<RegistryEntry<RecipeType<LunarianTradeRecipe>>> LUNARIAN_TRADE_RECIPE_TYPES = new HashSet<>();

    public static final RegistryEntry<RecipeType<CompressingRecipe>> COMPRESSING_RECIPE = RECIPE_TYPES.register("compressing", () -> CodecRecipeType.of("compressing"));
    public static final RegistryEntry<RecipeType<SpaceStationRecipe>> SPACE_STATION_RECIPE = RECIPE_TYPES.register("space_station", () -> CodecRecipeType.of("space_station"));
    public static final RegistryEntry<RecipeType<NasaWorkbenchRecipe>> NASA_WORKBENCH_RECIPE = RECIPE_TYPES.register("nasa_workbench", () -> CodecRecipeType.of("nasa_workbench"));
    public static final RegistryEntry<RecipeType<FuelConversionRecipe>> FUEL_CONVERSION_RECIPE = RECIPE_TYPES.register("fuel_conversion", () -> CodecRecipeType.of("fuel_conversion"));
    public static final RegistryEntry<RecipeType<OxygenConversionRecipe>> OXYGEN_CONVERSION_RECIPE = RECIPE_TYPES.register("oxygen_conversion", () -> CodecRecipeType.of("oxygen_conversion"));
    public static final RegistryEntry<RecipeType<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_RECIPE = RECIPE_TYPES.register("cryo_fuel_conversion", () -> CodecRecipeType.of("cryo_fuel_conversion"));

    public static final RegistryEntry<RecipeType<LunarianTradeSimpleRecipe>> LUNARIAN_TRADE_SIMPLE_RECIPE = registerLunarianTradeRecipe(RECIPE_TYPES.register("lunarian_trade_simple", () -> CodecRecipeType.of("lunarian_trade_simple")));
    public static final RegistryEntry<RecipeType<LunarianTradeEnchantedBookRecipe>> LUNARIAN_TRADE_ENCHANTED_BOOK_RECIPE = registerLunarianTradeRecipe(RECIPE_TYPES.register("lunarian_trade_enchanted_book", () -> CodecRecipeType.of("lunarian_trade_enchanted_book")));

    @SuppressWarnings("unchecked")
    public static RegistryEntry<RecipeType<LunarianTradeRecipe>>[] getLunarianTradeRecipeTypes() {
        return LUNARIAN_TRADE_RECIPE_TYPES.toArray(RegistryEntry[]::new);
    }

    @SuppressWarnings("unchecked")
    public static <T extends RecipeType<? extends LunarianTradeRecipe>> RegistryEntry<T> registerLunarianTradeRecipe(RegistryEntry<T> registryEntry) {
        LUNARIAN_TRADE_RECIPE_TYPES.add((RegistryEntry<RecipeType<LunarianTradeRecipe>>) registryEntry);
        return registryEntry;
    }
}