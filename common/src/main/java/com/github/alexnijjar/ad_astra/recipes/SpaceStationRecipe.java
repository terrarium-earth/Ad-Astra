package com.github.alexnijjar.ad_astra.recipes;

import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SpaceStationRecipe extends ModRecipe {

	public SpaceStationRecipe(Identifier id, List<Ingredient> input, List<Integer> stackCounts) {
		super(id, input, stackCounts);
	}

	public static Codec<SpaceStationRecipe> codec(Identifier id) {
		return RecordCodecBuilder.create(instance -> instance.group(
				RecordCodecBuilder.point(id),
				IngredientHolder.CODEC.listOf().fieldOf("ingredients").forGetter(SpaceStationRecipe::getHolders)
		).apply(instance, SpaceStationRecipe::of));
	}


	private static SpaceStationRecipe of(Identifier id, List<IngredientHolder> ingredients) {
		List<Ingredient> input = new ArrayList<>();
		List<Integer> count = new ArrayList<>();
		for (IngredientHolder ingredient : ingredients) {
			input.add(ingredient.ingredient());
			count.add(ingredient.count());
		}
		return new SpaceStationRecipe(id, input, count);
	}

	public List<IngredientHolder> getHolders() {
		List<IngredientHolder> holders = new LinkedList<>();
		for (int i = 0; i < getIngredients().size(); i++) {
			holders.add(new IngredientHolder(getIngredients().get(i), stackCounts.get(i)));
		}
		return holders;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return ModRecipes.SPACE_STATION_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.SPACE_STATION_RECIPE.get();
	}

}