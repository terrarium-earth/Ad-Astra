package com.github.alexnijjar.ad_astra.compat.emi.recipes;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.compat.emi.EmiCategories;
import com.github.alexnijjar.ad_astra.compat.emi.EmiTextures;
import com.github.alexnijjar.ad_astra.recipes.CompressingRecipe;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.util.Identifier;

import java.util.List;

public class EmiCompressingRecipe implements EmiRecipe {
	private final Identifier id;
	CompressingRecipe recipe;
	private final EmiIngredient input;
	private final EmiStack output;

	public EmiCompressingRecipe(CompressingRecipe recipe) {
		this.id = recipe.getId();
		this.recipe = recipe;
		this.input = EmiIngredient.of(recipe.getIngredients().get(0));
		this.output = EmiStack.of(recipe.getOutput());
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return EmiCategories.COMPRESSOR_CATEGORY;
	}

	@Override
	public Identifier getId() {
		return this.id;
	}

	@Override
	public List<EmiIngredient> getInputs() {
		return List.of(this.input);
	}

	@Override
	public List<EmiStack> getOutputs() {
		return List.of(this.output);
	}

	@Override
	public int getDisplayWidth() {
		return 144;
	}

	@Override
	public int getDisplayHeight() {
		return 90;
	}

	@Override
	public void addWidgets(WidgetHolder widgets) {
		int xOffset = 20;
		int yOffset = 30;

		widgets.addFillingArrow(26 + xOffset, 1 + yOffset, 50 * recipe.getCookTime()).tooltip((mx, my) -> {
			return List.of(TooltipComponent.of(EmiPort.ordered(EmiPort.translatable("emi.cooking.time", recipe.getCookTime() / 20f))));
		});

		widgets.addSlot(this.input, 0 + xOffset, 0 + yOffset);
		widgets.addSlot(this.output, 58 + xOffset, 0 + yOffset).recipeContext(this);

		EmiTextures.createEnergyWidget(widgets, 100 + xOffset, -20 + yOffset, (int) AdAstra.CONFIG.compressor.energyPerTick, false);
	}
}
