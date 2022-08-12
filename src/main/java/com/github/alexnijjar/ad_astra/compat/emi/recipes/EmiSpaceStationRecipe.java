package com.github.alexnijjar.ad_astra.compat.emi.recipes;

import com.github.alexnijjar.ad_astra.compat.emi.EmiCategories;
import com.github.alexnijjar.ad_astra.recipes.SpaceStationRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class EmiSpaceStationRecipe implements EmiRecipe {
	private final Identifier id;
	SpaceStationRecipe recipe;
	private final List<EmiIngredient> input = new ArrayList<>();

	public EmiSpaceStationRecipe(SpaceStationRecipe recipe) {
		this.id = recipe.getId();
		this.recipe = recipe;

		List<Integer> stackCounts = recipe.getStackCounts();
		List<Ingredient> ingredients = recipe.getIngredients();

		for (int i = 0; i < ingredients.size(); i++) {
			this.input.add(EmiIngredient.of(ingredients.get(i), stackCounts.get(i)));
		}
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return EmiCategories.SPACE_STATION_CATEGORY;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return this.input;
	}

	@Override
	public List<EmiStack> getOutputs() {
		return List.of();
	}

	@Override
	public int getDisplayWidth() {
		return 150;
	}

	@Override
	public int getDisplayHeight() {
		return 51;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		int xOffset = 3;
		int yOffset = 0;

		for (int i = 0; i < 8; i++) {
			if (i < this.input.size()) {
				widgets.addSlot(this.input.get(i), 18 * i + xOffset, 0 + yOffset);
			} else {
				widgets.addSlot(EmiStack.of(ItemStack.EMPTY), 18 * i + xOffset, 0 + yOffset);
			}
		}
	}
}
