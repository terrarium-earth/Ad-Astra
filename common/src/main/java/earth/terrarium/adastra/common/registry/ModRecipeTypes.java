package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.recipes.SeparatingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

public class ModRecipeTypes {
    public static final ResourcefulRegistry<RecipeType<?>> RECIPE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.RECIPE_TYPE, AdAstra.MOD_ID);

    public static final RegistryEntry<RecipeType<SeparatingRecipe>> SEPARATING = register("separating");

    private static <T extends Recipe<?>> RegistryEntry<RecipeType<T>> register(String id) {
        return RECIPE_TYPES.register(id, () -> new RecipeType<>() {
            public String toString() {
                return id;
            }
        });
    }
}