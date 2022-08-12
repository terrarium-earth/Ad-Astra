package com.github.alexnijjar.ad_astra.compat.emi;

import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;

public interface EmiCategories {
    public static final EmiRecipeCategory COAL_GENERATOR_CATEGORY = new EmiRecipeCategory(new ModIdentifier("coal_generator"), EmiStack.of(ModItems.COAL_GENERATOR));
    public static final EmiRecipeCategory COMPRESSOR_CATEGORY = new EmiRecipeCategory(new ModIdentifier("compressor"), EmiStack.of(ModItems.COMPRESSOR));
    public static final EmiRecipeCategory FUEL_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModIdentifier("fuel_conversion"), EmiStack.of(ModItems.FUEL_REFINERY));
    public static final EmiRecipeCategory OXYGEN_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModIdentifier("oxygen_conversion"), EmiStack.of(ModItems.OXYGEN_LOADER));
    public static final EmiRecipeCategory CRYO_FREEZER_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModIdentifier("cryo_freezer_conversion"), EmiStack.of(ModItems.CRYO_FREEZER));
    public static final EmiRecipeCategory NASA_WORKBENCH_CATEGORY = new EmiRecipeCategory(new ModIdentifier("nasa_workbench"), EmiStack.of(ModItems.NASA_WORKBENCH));
    public static final EmiRecipeCategory SPACE_STATION_CATEGORY = new EmiRecipeCategory(new ModIdentifier("space_station"), new EmiTexture(new ModIdentifier("textures/gui/space_station_icon.png"), 0, 0, 16, 16, 16, 16, 16, 16));
}
