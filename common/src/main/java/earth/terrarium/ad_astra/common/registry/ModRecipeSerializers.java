package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.machine.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class ModRecipeSerializers {
    public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_SERIALIZER, AdAstra.MOD_ID);

    public static final RegistryEntry<CodecRecipeSerializer<EtrionicGeneratingRecipe>> ETRIONIC_GENERATING = RECIPE_SERIALIZERS.register("etrionic_generating", () -> new CodecRecipeSerializer<>(ModRecipeTypes.ETRIONIC_GENERATING.get(), EtrionicGeneratingRecipe::codec, EtrionicGeneratingRecipe::networkingCodec));
    public static final RegistryEntry<CodecRecipeSerializer<CombustionRecipe>> COMBUSTION = RECIPE_SERIALIZERS.register("combustion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.COMBUSTION.get(), CombustionRecipe::codec));
    public static final RegistryEntry<SimpleCookingSerializer<EtrionicBlastingRecipe>> ETRIONIC_BLASTING = RECIPE_SERIALIZERS.register("etrionic_blasting", () -> new SimpleCookingSerializer<>(EtrionicBlastingRecipe::new, 100));
    public static final RegistryEntry<SimpleCookingSerializer<HydraulicPressingRecipe>> HYDRAULIC_PRESSING = RECIPE_SERIALIZERS.register("hydraulic_pressing", () -> new SimpleCookingSerializer<>(HydraulicPressingRecipe::new, 100));
    public static final RegistryEntry<CodecRecipeSerializer<ElectrolysisRecipe>> ELECTROLYSIS = RECIPE_SERIALIZERS.register("electrolysis", () -> new CodecRecipeSerializer<>(ModRecipeTypes.ELECTROLYSIS.get(), ElectrolysisRecipe::codec));
    public static final RegistryEntry<CodecRecipeSerializer<GeothermalRecipe>> GEOTHERMAL_GENERATING = RECIPE_SERIALIZERS.register("geothermal_generating", () -> new CodecRecipeSerializer<>(ModRecipeTypes.GEOTHERMAL_GENERATING.get(), GeothermalRecipe::codec));
}
