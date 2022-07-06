package com.github.alexnijjar.beyond_earth.compat.rei.cryo_freezer;

import java.util.List;

import com.github.alexnijjar.beyond_earth.compat.rei.REICategories;
import com.github.alexnijjar.beyond_earth.recipes.CryoFuelConversionRecipe;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public record CryoFreezerConversionDisplay(CryoFuelConversionRecipe recipe) implements Display {

    @Override
    public List<EntryIngredient> getInputEntries() {
        return List.of(EntryIngredients.of(recipe.getItemInput()));
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return List.of(EntryIngredients.of(recipe.getFluidOutput()));
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REICategories.CRYO_FREEZER_CONVERSION_CATEGORY;
    }
}