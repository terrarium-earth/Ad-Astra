package com.github.alexnijjar.ad_astra.recipes;

import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.teamresourceful.resourcefullib.common.codecs.recipes.ItemStackCodec;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.RecipeType;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class NasaWorkbenchRecipe extends CookingRecipe {

	public NasaWorkbenchRecipe(Identifier id, List<Ingredient> input, List<Integer> stackCounts, ItemStack output) {
		super(id, input, stackCounts, output);
	}

	public static Codec<NasaWorkbenchRecipe> codec(Identifier id) {
		return RecordCodecBuilder.create(instance -> instance.group(
				RecordCodecBuilder.point(id),
				IngredientHolder.CODEC.listOf().fieldOf("ingredients").forGetter(NasaWorkbenchRecipe::getHolders),
				ItemStackCodec.CODEC.fieldOf("output").forGetter(NasaWorkbenchRecipe::getOutput)
		).apply(instance, NasaWorkbenchRecipe::of));
	}

	private static NasaWorkbenchRecipe of(Identifier id, List<IngredientHolder> ingredients, ItemStack output) {
		List<Ingredient> input = new ArrayList<>();
		List<Integer> count = new ArrayList<>();
		for (IngredientHolder ingredient : ingredients) {
			input.add(ingredient.ingredient());
			count.add(ingredient.count());
		}
		return new NasaWorkbenchRecipe(id, input, count, output);
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
		return ModRecipes.NASA_WORKBENCH_SERIALIZER.get();
	}

	@Override
	public RecipeType<?> getType() {
		return ModRecipes.NASA_WORKBENCH_RECIPE.get();
	}
}