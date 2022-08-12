package com.github.alexnijjar.ad_astra.compat.rei.oxygen_conversion;

import java.util.List;

import com.github.alexnijjar.ad_astra.compat.rei.REICategories;
import com.github.alexnijjar.ad_astra.recipes.OxygenConversionRecipe;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public record OxygenConversionDisplay(OxygenConversionRecipe recipe) implements Display {

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
        return REICategories.OXYGEN_CONVERSION_CATEGORY;
    }
}