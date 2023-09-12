package earth.terrarium.adastra.common.compat.rei.displays;

import earth.terrarium.adastra.common.compat.rei.categories.SeparatorCategory;
import earth.terrarium.adastra.common.recipes.machines.SeparatingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public record SeparatorDisplay(SeparatingRecipe recipe) implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(EntryIngredients.of(recipe.ingredient().getFluid()));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(
            EntryIngredients.of(recipe.resultFluid1().getFluid()),
            EntryIngredients.of(recipe.resultFluid2().getFluid()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return SeparatorCategory.ID;
    }
}
