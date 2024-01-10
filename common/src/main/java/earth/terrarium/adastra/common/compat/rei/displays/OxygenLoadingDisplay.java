package earth.terrarium.adastra.common.compat.rei.displays;

import earth.terrarium.adastra.common.compat.rei.categories.OxygenLoadingCategory;
import earth.terrarium.adastra.common.recipes.machines.OxygenLoadingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public record OxygenLoadingDisplay(OxygenLoadingRecipe recipe) implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(EntryIngredients.of(recipe.input().getFluids().get(0).getFluid()));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.result().getFluid()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return OxygenLoadingCategory.ID;
    }
}
