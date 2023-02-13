package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.recipe.machine.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final ResourcefulRegistry<RecipeType<?>> RECIPE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<RecipeType<EtrionicGeneratingRecipe>> ETRIONIC_GENERATING = register("etrionic_generating");
    public static final RegistryEntry<RecipeType<CombustionRecipe>> COMBUSTION = register("combustion");
    public static final RegistryEntry<RecipeType<EtrionicBlastingRecipe>> ETRIONIC_BLASTING = register("etrionic_blasting");
    public static final RegistryEntry<RecipeType<HydraulicPressingRecipe>> HYDRAULIC_PRESSING = register("hydraulic_pressing");
    public static final RegistryEntry<RecipeType<ElectrolysisRecipe>> ELECTROLYSIS = register("electrolysis");
    public static final RegistryEntry<RecipeType<GeothermalRecipe>> GEOTHERMAL_GENERATING = register("geothermal_generating");
    public static final RegistryEntry<RecipeType<OilRefiningRecipe>> OIL_REFINING = register("oil_refining");
    public static final RegistryEntry<RecipeType<CryogenicFreezingRecipe>> CRYOGENIC_FREEZING = register("cryogenic_freezing");

    private static <T extends Recipe<?>> RegistryEntry<RecipeType<T>> register(String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            public String toString() {
                return id;
            }
        });
    }
}