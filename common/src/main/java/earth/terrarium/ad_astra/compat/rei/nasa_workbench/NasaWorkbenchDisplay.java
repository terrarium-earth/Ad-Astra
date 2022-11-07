package earth.terrarium.ad_astra.compat.rei.nasa_workbench;

import earth.terrarium.ad_astra.compat.rei.REICategories;
import earth.terrarium.ad_astra.recipes.NasaWorkbenchRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.List;

@Environment(EnvType.CLIENT)
public record NasaWorkbenchDisplay(NasaWorkbenchRecipe recipe) implements Display {

	@Override
	public List<EntryIngredient> getInputEntries() {
		return EntryIngredients.ofIngredients(recipe.getIngredients());
	}

	@Override
	public List<EntryIngredient> getOutputEntries() {
		return List.of(EntryIngredients.of(recipe.getResultItem()));
	}

	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return REICategories.NASA_WORKBENCH_CATEGORY;
	}
}