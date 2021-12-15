package net.mrscauthd.astrocraft.crafting;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class AstroCraftRecipeType<T extends AstroCraftRecipe> implements RecipeType<T> {
	private final String name;
	private List<T> cached;

	public AstroCraftRecipeType(String name) {
		this.name = name;
		this.cached = null;
	}

	public String getName() {
		return this.name;
	}

	public Stream<T> filter(Level level, Predicate<T> filter) {
		return this.getRecipes(level).stream().filter(filter);
	}

	public T findFirst(Level level, Predicate<T> filter) {
		return this.filter(level, filter).findFirst().orElse(null);
	}

	public List<T> getRecipes(Level level) {
		this.cached = null;
		if (this.cached == null) {
			RecipeManager recipeManager = level.getRecipeManager();
			this.cached = recipeManager.getAllRecipesFor(this);
		}

		return this.cached;
	}

}
