package com.github.alexnijjar.ad_astra.compat.emi.recipes;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.compat.emi.EmiCategories;
import com.github.alexnijjar.ad_astra.compat.emi.EmiTextures;
import com.github.alexnijjar.ad_astra.recipes.CryoFuelConversionRecipe;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.FluidEmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.text.OrderedText;
import net.minecraft.util.Identifier;

import java.util.List;

public class EmiCryoFreezerConversionRecipe implements EmiRecipe {
	private final Identifier id;
	CryoFuelConversionRecipe recipe;
	private final EmiIngredient input;
	private final FluidEmiStack output;

	public EmiCryoFreezerConversionRecipe(CryoFuelConversionRecipe recipe) {
		this.id = recipe.getId();
		this.recipe = recipe;
		this.input = EmiIngredient.of(recipe.getIngredients().get(0));
		this.output = new FluidEmiStack(FluidVariant.of(recipe.getFluidOutput()), FluidConstants.BUCKET);
	}

	@Override
	public EmiRecipeCategory getCategory() {
		return EmiCategories.CRYO_FREEZER_CONVERSION_CATEGORY;
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
		return List.of();
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

		widgets.addSlot(this.input, 0 + xOffset, 0 + yOffset);
		EmiTextures.createFluidWidget(widgets, 60 + xOffset, -17 + yOffset, FluidVariant.of((Fluid) this.output.getKey()), 150 * 50, true);

		EmiTextures.createEnergyWidget(widgets, 100 + xOffset, -20 + yOffset, (int) AdAstra.CONFIG.fuelRefinery.energyPerTick, false);

		OrderedText ratioText = EmiPort.ordered(EmiPort.translatable("rei.text.ad_astra.amount", 1000 * this.recipe.getConversionRatio()));
		widgets.addText(ratioText, -17 + xOffset, 50 + yOffset, 0xFF404040, false);
	}
}
