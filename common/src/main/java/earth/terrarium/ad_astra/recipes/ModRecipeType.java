package earth.terrarium.ad_astra.recipes;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ModRecipeType<T extends Recipe<Container>> implements RecipeType<T> {
    private final ResourceLocation id;

    public ModRecipeType(ResourceLocation id) {
        this.id = id;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public Stream<T> filter(Level level, Predicate<T> filter) {
        return this.getRecipes(level).stream().filter(filter);
    }

    public T findFirst(Level level, Predicate<T> filter) {
        return this.filter(level, filter).findFirst().orElse(null);
    }

    public List<T> getRecipes(Level level) {
        RecipeManager recipeManager = level.getRecipeManager();
        return recipeManager.getAllRecipesFor(this);
    }
}