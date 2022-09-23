package com.github.alexnijjar.ad_astra.recipes;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.minecraft.inventory.Inventory;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModRecipeType<T extends Recipe<Inventory>> implements RecipeType<T> {
	private final Identifier id;

	public ModRecipeType(Identifier id) {
		this.id = id;
	}

	public Identifier getId() {
		return this.id;
	}

	public Stream<T> filter(World world, Predicate<T> filter) {
		return this.getRecipes(world).stream().filter(filter);
	}

	public T findFirst(World world, Predicate<T> filter) {
		return this.filter(world, filter).findFirst().orElse(null);
	}

	public List<T> getRecipes(World world) {
		RecipeManager recipeManager = world.getRecipeManager();
		return recipeManager.listAllOfType(this);
	}
}