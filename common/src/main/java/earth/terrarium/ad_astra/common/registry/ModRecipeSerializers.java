package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.*;
import earth.terrarium.ad_astra.common.recipe.lunarian.*;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapelessRecipe;

public class ModRecipeSerializers {
    public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(Registry.RECIPE_SERIALIZER, AdAstra.MOD_ID);

    public static final RegistryEntry<RecipeSerializer<ShapelessRecipe>> HAMMER_SERIALIZER = RECIPE_SERIALIZERS.register("hammering", HammerShapelessRecipe.Serializer::new);
    public static final RegistryEntry<SpaceSuitShapedRecipe.Serializer> SPACE_SUIT_CRAFTING_SERIALIZER = RECIPE_SERIALIZERS.register("crafting_shaped_space_suit", SpaceSuitShapedRecipe.Serializer::new);
    public static final RegistryEntry<RecipeSerializer<CompressingRecipe>> COMPRESSING_SERIALIZER = RECIPE_SERIALIZERS.register("compressing", () -> new CodecRecipeSerializer<>(ModRecipeTypes.COMPRESSING_RECIPE.get(), CompressingRecipe::codec, CompressingRecipe::networkCodec));
    public static final RegistryEntry<RecipeSerializer<SpaceStationRecipe>> SPACE_STATION_SERIALIZER = RECIPE_SERIALIZERS.register("space_station", () -> new CodecRecipeSerializer<>(ModRecipeTypes.SPACE_STATION_RECIPE.get(), SpaceStationRecipe::codec, SpaceStationRecipe::networkCodec));
    public static final RegistryEntry<RecipeSerializer<NasaWorkbenchRecipe>> NASA_WORKBENCH_SERIALIZER = RECIPE_SERIALIZERS.register("nasa_workbench", () -> new CodecRecipeSerializer<>(ModRecipeTypes.NASA_WORKBENCH_RECIPE.get(), NasaWorkbenchRecipe::codec, NasaWorkbenchRecipe::networkCodec));
    public static final RegistryEntry<RecipeSerializer<FuelConversionRecipe>> FUEL_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("fuel_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.FUEL_CONVERSION_RECIPE.get(), FuelConversionRecipe::codec));
    public static final RegistryEntry<RecipeSerializer<OxygenConversionRecipe>> OXYGEN_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("oxygen_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.OXYGEN_CONVERSION_RECIPE.get(), OxygenConversionRecipe::codec));
    public static final RegistryEntry<RecipeSerializer<CryoFuelConversionRecipe>> CRYO_FUEL_CONVERSION_SERIALIZER = RECIPE_SERIALIZERS.register("cryo_fuel_conversion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.CRYO_FUEL_CONVERSION_RECIPE.get(), CryoFuelConversionRecipe::codec, CryoFuelConversionRecipe::networkCodec));

    public static final RegistryEntry<RecipeSerializer<LunarianTradeSimpleRecipe>> LUNARIAN_TRADE_SIMPLE_SERIALIZER = RECIPE_SERIALIZERS.register("lunarian_trade_simple", () -> new LunarianTradeRecipe.Serializer<>(LunarianTradeSimpleRecipe::new));
    public static final RegistryEntry<RecipeSerializer<LunarianTradeEnchantedBookRecipe>> LUNARIAN_TRADE_ENCHANTED_BOOK_SERIALIZER = RECIPE_SERIALIZERS.register("lunarian_trade_enchanted_book", () -> new LunarianTradeRecipe.Serializer<>(LunarianTradeEnchantedBookRecipe::new));
    public static final RegistryEntry<RecipeSerializer<LunarianTradeEnchantedItemRecipe>> LUNARIAN_TRADE_ENCHANTED_ITEM_SERIALIZER = RECIPE_SERIALIZERS.register("lunarian_trade_enchanted_item", () -> new LunarianTradeRecipe.Serializer<>(LunarianTradeEnchantedItemRecipe::new));
}
