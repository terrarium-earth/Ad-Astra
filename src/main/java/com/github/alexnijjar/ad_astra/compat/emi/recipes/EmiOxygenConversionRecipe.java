package com.github.alexnijjar.ad_astra.compat.emi.recipes;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.compat.emi.EmiCategories;
import com.github.alexnijjar.ad_astra.compat.emi.EmiTextures;
import com.github.alexnijjar.ad_astra.recipes.OxygenConversionRecipe;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;

import java.util.List;

public class EmiOxygenConversionRecipe implements EmiRecipe {
	private final Identifier id;
	OxygenConversionRecipe recipe;
	private final EmiStack input;
	private final EmiStack output;

	public EmiOxygenConversionRecipe(OxygenConversionRecipe recipe) {
		this.id = recipe.getId();
		this.recipe = recipe;
		this.input = EmiStack.of(recipe.getFluidInput());
		this.output = EmiStack.of(recipe.getFluidOutput());
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return EmiCategories.OXYGEN_CONVERSION_CATEGORY;
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

		widgets.addFillingArrow(26 + xOffset, 1 + yOffset, 20 * 50);

		EmiTextures.createFluidWidget(widgets, -10 + xOffset, -17 + yOffset, FluidVariant.of((Fluid) this.input.getKey()), 150 * 50, false);
		EmiTextures.createFluidWidget(widgets, 60 + xOffset, -17 + yOffset, FluidVariant.of((Fluid) this.output.getKey()), 150 * 50, true);

		EmiTextures.createEnergyWidget(widgets, 100 + xOffset, -20 + yOffset, (int) AdAstra.CONFIG.fuelRefinery.energyPerTick, false);

		OrderedText ratioText = EmiPort.ordered(EmiPort.translatable("rei.text.ad_astra.conversion_ratio", this.recipe.getConversionRatio() * 100.0));
		widgets.addText(ratioText, -17 + xOffset, 50 + yOffset, 0xFF404040, false);
	}
}
