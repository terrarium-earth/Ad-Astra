package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.recipe.CodecRecipeSerializer;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.machine.CombustionRecipe;
import earth.terrarium.ad_astra.common.recipe.machine.EtrionicBlastingRecipe;
import earth.terrarium.ad_astra.common.recipe.machine.EtrionicGeneratingRecipe;
import earth.terrarium.ad_astra.common.recipe.machine.HydraulicPressingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;

public class ModRecipeSerializers {
    public static final ResourcefulRegistry<RecipeSerializer<?>> RECIPE_SERIALIZERS = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_SERIALIZER, AdAstra.MOD_ID);

    public static final RegistryEntry<CodecRecipeSerializer<EtrionicGeneratingRecipe>> ETRIONIC_GENERATING_SERIALIZER = RECIPE_SERIALIZERS.register("etrionic_generating", () -> new CodecRecipeSerializer<>(ModRecipeTypes.ETRIONIC_GENERATING.get(), EtrionicGeneratingRecipe::codec, EtrionicGeneratingRecipe::networkingCodec));
    public static final RegistryEntry<CodecRecipeSerializer<CombustionRecipe>> COMBUSTION_SERIALIZER = RECIPE_SERIALIZERS.register("combustion", () -> new CodecRecipeSerializer<>(ModRecipeTypes.COMBUSTION.get(), CombustionRecipe::codec));
    public static final RegistryEntry<SimpleCookingSerializer<EtrionicBlastingRecipe>> ETRIONIC_BLASTING_SERIALIZER = RECIPE_SERIALIZERS.register("etrionic_blasting", () -> new SimpleCookingSerializer<>(EtrionicBlastingRecipe::new, 100));
    public static final RegistryEntry<SimpleCookingSerializer<HydraulicPressingRecipe>> HYDRAULIC_PRESSING_SERIALIZER = RECIPE_SERIALIZERS.register("hydraulic_pressing", () -> new SimpleCookingSerializer<>(HydraulicPressingRecipe::new, 100));
}
