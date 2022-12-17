package earth.terrarium.ad_astra.common.compat.rei.fuel_conversion;

import earth.terrarium.ad_astra.common.compat.rei.REICategories;
import earth.terrarium.ad_astra.common.recipe.FuelConversionRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.core.Holder;

import java.util.List;
import java.util.stream.Collectors;

public record FuelConversionDisplay(FuelConversionRecipe recipe) implements Display {

	@Override
	public List<EntryIngredient> getInputEntries() {
		return recipe.getFluidInput().stream().map(Holder::value).map(EntryIngredients::of).collect(Collectors.toList());
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