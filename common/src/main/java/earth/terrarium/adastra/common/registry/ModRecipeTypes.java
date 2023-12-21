package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.recipes.SpaceStationRecipe;
import earth.terrarium.adastra.common.recipes.machines.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final ResourcefulRegistry<RecipeType<?>> RECIPE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<RecipeType<CompressingRecipe>> COMPRESSING = register("compressing");
    public static final RegistryEntry<RecipeType<OxygenLoadingRecipe>> OXYGEN_LOADING = register("oxygen_loading");
    public static final RegistryEntry<RecipeType<RefiningRecipe>> REFINING = register("refining");
    public static final RegistryEntry<RecipeType<CryoFreezingRecipe>> CRYO_FREEZING = register("cryo_freezing");
    public static final RegistryEntry<RecipeType<NasaWorkbenchRecipe>> NASA_WORKBENCH = register("nasa_workbench");

    public static final RegistryEntry<RecipeType<SpaceStationRecipe>> SPACE_STATION_RECIPE = register("space_station_recipe");

    private static <T extends Recipe<?>> RegistryEntry<RecipeType<T>> register(String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            public String toString() {
                return id;
            }
        });
    }
}