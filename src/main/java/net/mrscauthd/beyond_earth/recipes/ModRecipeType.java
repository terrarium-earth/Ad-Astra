package net.mrscauthd.beyond_earth.recipes;

import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.world.World;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ModRecipeType<T extends Recipe<Inventory>> implements RecipeType<T> {
    private final String name;
    private List<T> cached;

    public ModRecipeType(String name) {
        this.name = name;
        this.cached = null;
    }

    public String getName() {
        return this.name;
    }

    public Stream<T> filter(World world, Predicate<T> filter) {
        return this.getRecipes(world).stream().filter(filter);
    }

    public T findFirst(World world, Predicate<T> filter) {
        return this.filter(world, filter).findFirst().orElse(null);
    }

    public List<T> getRecipes(World world) {
        this.cached = null;
        RecipeManager recipeManager = world.getRecipeManager();
        this.cached = recipeManager.listAllOfType(this);

        return this.cached;
    }
}
