package com.github.alexnijjar.ad_astra.compat.emi.recipes;

import com.github.alexnijjar.ad_astra.compat.emi.EmiCategories;
import com.github.alexnijjar.ad_astra.recipes.NasaWorkbenchRecipe;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public class EmiNasaWorkbenchRecipe implements EmiRecipe {
	private final Identifier id;
	NasaWorkbenchRecipe recipe;
	private final List<EmiIngredient> input;
	private final EmiStack output;

	public EmiNasaWorkbenchRecipe(NasaWorkbenchRecipe recipe) {
		this.id = recipe.getId();
		this.recipe = recipe;
		this.input = recipe.getIngredients().stream().map(EmiIngredient::of).collect(Collectors.toList());
		this.output = EmiStack.of(recipe.getOutput());
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return EmiCategories.NASA_WORKBENCH_CATEGORY;
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
		return List.of(this.output);
	}

	@Override
	public int getDisplayWidth() {
		return 176;
	}

	@Override
	public int getDisplayHeight() {
		return 130;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		int xOffset = 40;
		int yOffset = 10;

		widgets.addSlot(this.input.get(0), 0 + xOffset, 0 + yOffset);

		widgets.addSlot(this.input.get(1), -9 + xOffset, 18 + yOffset);
		widgets.addSlot(this.input.get(2), 9 + xOffset, 18 + yOffset);

		widgets.addSlot(this.input.get(3), -9 + xOffset, 18 * 2 + yOffset);
		widgets.addSlot(this.input.get(4), 9 + xOffset, 18 * 2 + yOffset);

		widgets.addSlot(this.input.get(5), -9 + xOffset, 18 * 3 + yOffset);
		widgets.addSlot(this.input.get(6), 9 + xOffset, 18 * 3 + yOffset);

		widgets.addSlot(this.input.get(7), -27 + xOffset, 18 * 4 + yOffset);
		widgets.addSlot(this.input.get(8), -9 + xOffset, 18 * 4 + yOffset);
		widgets.addSlot(this.input.get(9), 9 + xOffset, 18 * 4 + yOffset);
		widgets.addSlot(this.input.get(10), 27 + xOffset, 18 * 4 + yOffset);

		widgets.addSlot(this.input.get(11), -27 + xOffset, 18 * 5 + yOffset);
		widgets.addSlot(this.input.get(12), 0 + xOffset, 18 * 5 + yOffset);
		widgets.addSlot(this.input.get(13), 27 + xOffset, 18 * 5 + yOffset);

		widgets.addTexture(EmiTexture.EMPTY_ARROW, 60 + xOffset, 70 + yOffset);

		widgets.addSlot(this.output, 90 + xOffset, 70 + yOffset).recipeContext(this);
	}
}
