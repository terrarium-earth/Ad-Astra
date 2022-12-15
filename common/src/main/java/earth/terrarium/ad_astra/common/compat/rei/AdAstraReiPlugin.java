package earth.terrarium.ad_astra.common.compat.rei;

import earth.terrarium.ad_astra.client.screen.*;
import earth.terrarium.ad_astra.common.compat.rei.compressor.CompressorCategory;
import earth.terrarium.ad_astra.common.compat.rei.compressor.CompressorDisplay;
import earth.terrarium.ad_astra.common.compat.rei.compressor.CompressorScreenClickArea;
import earth.terrarium.ad_astra.common.compat.rei.cryo_freezer.CryoFreezerConversionCategory;
import earth.terrarium.ad_astra.common.compat.rei.cryo_freezer.CryoFreezerConversionDisplay;
import earth.terrarium.ad_astra.common.compat.rei.cryo_freezer.CryoFreezerConversionScreenClickArea;
import earth.terrarium.ad_astra.common.compat.rei.fuel_conversion.FuelConversionCategory;
import earth.terrarium.ad_astra.common.compat.rei.fuel_conversion.FuelConversionDisplay;
import earth.terrarium.ad_astra.common.compat.rei.fuel_conversion.FuelConversionScreenClickArea;
import earth.terrarium.ad_astra.common.compat.rei.nasa_workbench.NasaWorkbenchCategory;
import earth.terrarium.ad_astra.common.compat.rei.nasa_workbench.NasaWorkbenchDisplay;
import earth.terrarium.ad_astra.common.compat.rei.nasa_workbench.NasaWorkbenchScreenClickArea;
import earth.terrarium.ad_astra.common.compat.rei.oxygen_conversion.OxygenConversionCategory;
import earth.terrarium.ad_astra.common.compat.rei.oxygen_conversion.OxygenConversionDisplay;
import earth.terrarium.ad_astra.common.compat.rei.oxygen_conversion.OxygenConversionScreenClickArea;
import earth.terrarium.ad_astra.common.compat.rei.oxygen_conversion.OxygenDistributorScreenClickArea;
import earth.terrarium.ad_astra.common.compat.rei.space_station.SpaceStationCategory;
import earth.terrarium.ad_astra.common.compat.rei.space_station.SpaceStationDisplay;
import earth.terrarium.ad_astra.common.recipe.*;
import earth.terrarium.ad_astra.common.registry.ModBlocks;
import earth.terrarium.ad_astra.common.registry.ModItems;
import earth.terrarium.ad_astra.common.registry.ModRecipeTypes;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.plugin.common.displays.DefaultInformationDisplay;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

@Environment(EnvType.CLIENT)
public class AdAstraReiPlugin implements REIClientPlugin {

	@SuppressWarnings("removal")
	@Override
	public void registerCategories(CategoryRegistry registry) {

		registry.add(new CompressorCategory());
		registry.add(new FuelConversionCategory());
		registry.add(new OxygenConversionCategory());
		registry.add(new CryoFreezerConversionCategory());
		registry.add(new NasaWorkbenchCategory());
		registry.add(new SpaceStationCategory());

		registry.addWorkstations(REICategories.COMPRESSOR_CATEGORY, EntryStacks.of(ModBlocks.COMPRESSOR.get()));
		registry.addWorkstations(REICategories.FUEL_CONVERSION_CATEGORY, EntryStacks.of(ModBlocks.FUEL_REFINERY.get()));
		registry.addWorkstations(REICategories.OXYGEN_CONVERSION_CATEGORY, EntryStacks.of(ModBlocks.OXYGEN_LOADER.get()), EntryStacks.of(ModBlocks.OXYGEN_DISTRIBUTOR.get()));
		registry.addWorkstations(REICategories.CRYO_FREEZER_CONVERSION_CATEGORY, EntryStacks.of(ModBlocks.CRYO_FREEZER.get()));
		registry.addWorkstations(REICategories.NASA_WORKBENCH_CATEGORY, EntryStacks.of(ModBlocks.NASA_WORKBENCH.get()));
		registry.addWorkstations(REICategories.SPACE_STATION_CATEGORY, EntryStacks.of(ItemStack.EMPTY));

		registry.removePlusButton(REICategories.SPACE_STATION_CATEGORY);
	}

	@Override
	public void registerDisplays(DisplayRegistry registry) {

		registry.registerRecipeFiller(CompressingRecipe.class, ModRecipeTypes.COMPRESSING_RECIPE.get(), CompressorDisplay::new);
		registry.registerRecipeFiller(FuelConversionRecipe.class, ModRecipeTypes.FUEL_CONVERSION_RECIPE.get(), FuelConversionDisplay::new);
		registry.registerRecipeFiller(OxygenConversionRecipe.class, ModRecipeTypes.OXYGEN_CONVERSION_RECIPE.get(), OxygenConversionDisplay::new);
		registry.registerRecipeFiller(CryoFuelConversionRecipe.class, ModRecipeTypes.CRYO_FUEL_CONVERSION_RECIPE.get(), CryoFreezerConversionDisplay::new);
		registry.registerRecipeFiller(NasaWorkbenchRecipe.class, ModRecipeTypes.NASA_WORKBENCH_RECIPE.get(), NasaWorkbenchDisplay::new);
		registry.registerRecipeFiller(SpaceStationRecipe.class, ModRecipeTypes.SPACE_STATION_RECIPE.get(), SpaceStationDisplay::new);

		DefaultInformationDisplay info = DefaultInformationDisplay.createFromEntry(EntryStacks.of(ModItems.OIL_BUCKET.get()), Component.translatable("rei.text.ad_astra.oil.title"));
		info.lines(Component.translatable("rei.text.ad_astra.oil.body"));
		registry.add(info);
	}

	@Override
	public void registerScreens(ScreenRegistry registry) {

		registry.registerClickArea(CompressorScreen.class, new CompressorScreenClickArea());
		registry.registerClickArea(ConversionScreen.class, new FuelConversionScreenClickArea());
		registry.registerClickArea(ConversionScreen.class, new OxygenConversionScreenClickArea());
		registry.registerClickArea(OxygenDistributorScreen.class, new OxygenDistributorScreenClickArea());
		registry.registerClickArea(CryoFreezerScreen.class, new CryoFreezerConversionScreenClickArea());
		registry.registerClickArea(NasaWorkbenchScreen.class, new NasaWorkbenchScreenClickArea());
	}
}
