package earth.terrarium.adastra.common.compat.rei.displays;

import earth.terrarium.adastra.common.compat.rei.categories.NasaWorkbenchCategory;
import earth.terrarium.adastra.common.recipes.machines.NasaWorkbenchRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public record NasaWorkbenchDisplay(NasaWorkbenchRecipe recipe) implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        return EntryIngredients.ofIngredients(recipe.ingredients());
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.result()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return NasaWorkbenchCategory.ID;
    }
}
