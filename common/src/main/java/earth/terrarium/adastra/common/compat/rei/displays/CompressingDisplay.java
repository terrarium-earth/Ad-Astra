package earth.terrarium.adastra.common.compat.rei.displays;

import earth.terrarium.adastra.common.compat.rei.categories.CompressingCategory;
import earth.terrarium.adastra.common.recipes.machines.CompressingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import java.util.List;

public record CompressingDisplay(CompressingRecipe recipe) implements Display {
    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(EntryIngredients.ofIngredient(recipe.ingredient()));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.result()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return CompressingCategory.ID;
    }
}
