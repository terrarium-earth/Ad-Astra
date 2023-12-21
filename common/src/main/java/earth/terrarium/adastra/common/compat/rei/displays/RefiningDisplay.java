package earth.terrarium.adastra.common.compat.rei.displays;

import earth.terrarium.adastra.common.compat.rei.categories.RefiningCategory;
import earth.terrarium.adastra.common.recipes.machines.RefiningRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public record RefiningDisplay(RefiningRecipe recipe) implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(EntryIngredients.of(recipe.ingredient().getFluid()));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.resultFluid().getFluid()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return RefiningCategory.ID;
    }
}
