package com.github.alexnijjar.ad_astra.compat.rei.fuel_conversion;

import com.github.alexnijjar.ad_astra.compat.rei.REICategories;
import com.github.alexnijjar.ad_astra.recipes.FuelConversionRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;

@Environment(EnvType.CLIENT)
public record FuelConversionDisplay(FuelConversionRecipe recipe) implements Display {

	@Override
	public List<EntryIngredient> getInputEntries() {
		return List.of(EntryIngredients.of(recipe.getFluidInput()));
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return List.of(EntryIngredients.of(recipe.getFluidOutput()));
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return REICategories.FUEL_CONVERSION_CATEGORY;
	}
}