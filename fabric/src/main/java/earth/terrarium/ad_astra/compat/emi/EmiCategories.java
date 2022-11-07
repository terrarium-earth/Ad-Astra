package earth.terrarium.ad_astra.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.util.ModResourceLocation;

public interface EmiCategories {
	EmiRecipeCategory COAL_GENERATOR_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("coal_generator"), EmiStack.of(ModItems.COAL_GENERATOR.get()));
	EmiRecipeCategory COMPRESSOR_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("compressor"), EmiStack.of(ModItems.COMPRESSOR.get()));
	EmiRecipeCategory FUEL_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("fuel_conversion"), EmiStack.of(ModItems.FUEL_REFINERY.get()));
	EmiRecipeCategory OXYGEN_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("oxygen_conversion"), EmiStack.of(ModItems.OXYGEN_LOADER.get()));
	EmiRecipeCategory CRYO_FREEZER_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("cryo_freezer_conversion"), EmiStack.of(ModItems.CRYO_FREEZER.get()));
	EmiRecipeCategory NASA_WORKBENCH_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("nasa_workbench"), EmiStack.of(ModItems.NASA_WORKBENCH.get()));
	EmiRecipeCategory SPACE_STATION_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("space_station"), new EmiTexture(new ModResourceLocation("textures/gui/space_station_icon.png"), 0, 0, 16, 16, 16, 16, 16, 16));
}
