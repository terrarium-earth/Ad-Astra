package earth.terrarium.ad_astra.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import earth.terrarium.ad_astra.registry.ModItems;
import earth.terrarium.ad_astra.util.ModResourceLocation;

public class EmiCategories {
	public static final EmiRecipeCategory COMPRESSOR_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("compressor"), EmiStack.of(ModItems.COMPRESSOR.get()));
	public static final EmiRecipeCategory FUEL_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("fuel_conversion"), EmiStack.of(ModItems.FUEL_REFINERY.get()));
	public static final EmiRecipeCategory OXYGEN_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("oxygen_conversion"), EmiStack.of(ModItems.OXYGEN_LOADER.get()));
	public static final EmiRecipeCategory CRYO_FREEZER_CONVERSION_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("cryo_freezer_conversion"), EmiStack.of(ModItems.CRYO_FREEZER.get()));
	public static final EmiRecipeCategory NASA_WORKBENCH_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("nasa_workbench"), EmiStack.of(ModItems.NASA_WORKBENCH.get()));
	public static final EmiRecipeCategory SPACE_STATION_CATEGORY = new EmiRecipeCategory(new ModResourceLocation("space_station"), new EmiTexture(new ModResourceLocation("textures/gui/space_station_icon.png"), 0, 0, 16, 16, 16, 16, 16, 16));
}
