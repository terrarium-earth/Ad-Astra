package earth.terrarium.ad_astra.compat.rei;

import earth.terrarium.ad_astra.compat.rei.compressor.CompressorDisplay;
import earth.terrarium.ad_astra.compat.rei.cryo_freezer.CryoFreezerConversionDisplay;
import earth.terrarium.ad_astra.compat.rei.fuel_conversion.FuelConversionDisplay;
import earth.terrarium.ad_astra.compat.rei.nasa_workbench.NasaWorkbenchDisplay;
import earth.terrarium.ad_astra.compat.rei.oxygen_conversion.OxygenConversionDisplay;
import earth.terrarium.ad_astra.compat.rei.space_station.SpaceStationDisplay;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

public class REICategories {
	public static final CategoryIdentifier<CompressorDisplay> COMPRESSOR_CATEGORY = CategoryIdentifier.of(new ModResourceLocation("compressor"));
	public static final CategoryIdentifier<FuelConversionDisplay> FUEL_CONVERSION_CATEGORY = CategoryIdentifier.of(new ModResourceLocation("fuel_conversion"));
	public static final CategoryIdentifier<OxygenConversionDisplay> OXYGEN_CONVERSION_CATEGORY = CategoryIdentifier.of(new ModResourceLocation("oxygen_conversion"));
	public static final CategoryIdentifier<CryoFreezerConversionDisplay> CRYO_FREEZER_CONVERSION_CATEGORY = CategoryIdentifier.of(new ModResourceLocation("cryo_freezer_conversion"));
	public static final CategoryIdentifier<NasaWorkbenchDisplay> NASA_WORKBENCH_CATEGORY = CategoryIdentifier.of(new ModResourceLocation("nasa_workbench"));
	public static final CategoryIdentifier<SpaceStationDisplay> SPACE_STATION_CATEGORY = CategoryIdentifier.of(new ModResourceLocation("space_station"));
}
