package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.machine.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final ResourcefulRegistry<RecipeType<?>> RECIPE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<RecipeType<EtrionicGeneratingRecipe>> ETRIONIC_GENERATING = RECIPE_TYPES.register("etrionic_generating", () -> new RecipeType<>() {
    });
    public static final RegistryEntry<RecipeType<CombustionRecipe>> COMBUSTION = RECIPE_TYPES.register("combustion", () -> new RecipeType<>() {
    });
    public static final RegistryEntry<RecipeType<EtrionicBlastingRecipe>> ETRIONIC_BLASTING = RECIPE_TYPES.register("etrionic_blasting", () -> new RecipeType<>() {
    });
    public static final RegistryEntry<RecipeType<HydraulicPressingRecipe>> HYDRAULIC_PRESSING = RECIPE_TYPES.register("hydraulic_pressing", () -> new RecipeType<>() {
    });
    public static final RegistryEntry<RecipeType<ElectrolysisRecipe>> ELECTROLYSIS = RECIPE_TYPES.register("electrolysis", () -> new RecipeType<>() {
    });
    public static final RegistryEntry<RecipeType<GeothermalRecipe>> GEOTHERMAL_GENERATING = RECIPE_TYPES.register("geothermal_generating", () -> new RecipeType<>() {
    });
    public static final RegistryEntry<RecipeType<OilRefiningRecipe>> OIL_REFINING = RECIPE_TYPES.register("oil_refining", () -> new RecipeType<>() {
    });
    public static final RegistryEntry<RecipeType<CryogenicFreezingRecipe>> CRYOGENIC_FREEZING = RECIPE_TYPES.register("cryogenic_freezing", () -> new RecipeType<>() {
    });
}