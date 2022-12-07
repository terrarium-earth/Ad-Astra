package earth.terrarium.ad_astra.compat.rei;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.compat.rei.compressor.CompressorDisplay;
import earth.terrarium.ad_astra.compat.rei.cryo_freezer.CryoFreezerConversionDisplay;
import earth.terrarium.ad_astra.compat.rei.fuel_conversion.FuelConversionDisplay;
import earth.terrarium.ad_astra.compat.rei.nasa_workbench.NasaWorkbenchDisplay;
import earth.terrarium.ad_astra.compat.rei.oxygen_conversion.OxygenConversionDisplay;
import earth.terrarium.ad_astra.compat.rei.space_station.SpaceStationDisplay;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;

public class REICategories {
	public static final CategoryIdentifier<CompressorDisplay> COMPRESSOR_CATEGORY = CategoryIdentifier.of(new ResourceLocation(AdAstra.MOD_ID, "compressor"));
	public static final CategoryIdentifier<FuelConversionDisplay> FUEL_CONVERSION_CATEGORY = CategoryIdentifier.of(new ResourceLocation(AdAstra.MOD_ID, "fuel_conversion"));
	public static final CategoryIdentifier<OxygenConversionDisplay> OXYGEN_CONVERSION_CATEGORY = CategoryIdentifier.of(new ResourceLocation(AdAstra.MOD_ID, "oxygen_conversion"));
	public static final CategoryIdentifier<CryoFreezerConversionDisplay> CRYO_FREEZER_CONVERSION_CATEGORY = CategoryIdentifier.of(new ResourceLocation(AdAstra.MOD_ID, "cryo_freezer_conversion"));
	public static final CategoryIdentifier<NasaWorkbenchDisplay> NASA_WORKBENCH_CATEGORY = CategoryIdentifier.of(new ResourceLocation(AdAstra.MOD_ID, "nasa_workbench"));
	public static final CategoryIdentifier<SpaceStationDisplay> SPACE_STATION_CATEGORY = CategoryIdentifier.of(new ResourceLocation(AdAstra.MOD_ID, "space_station"));
}
