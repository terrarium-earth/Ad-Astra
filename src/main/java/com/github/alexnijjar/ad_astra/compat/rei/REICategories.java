package com.github.alexnijjar.ad_astra.compat.rei;

import com.github.alexnijjar.ad_astra.compat.rei.coal_generator.CoalGeneratorDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.compressor.CompressorDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.fuel_conversion.FuelConversionDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.nasa_workbench.NasaWorkbenchDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.oxygen_conversion.OxygenConversionDisplay;
import com.github.alexnijjar.ad_astra.compat.rei.space_station.SpaceStationDisplay;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;

public class REICategories {
    public static final CategoryIdentifier<CoalGeneratorDisplay> COAL_GENERATOR_CATEGORY = CategoryIdentifier.of(new ModIdentifier("coal_generator"));
    public static final CategoryIdentifier<CompressorDisplay> COMPRESSOR_CATEGORY = CategoryIdentifier.of(new ModIdentifier("compressor"));
    public static final CategoryIdentifier<FuelConversionDisplay> FUEL_CONVERSION_CATEGORY = CategoryIdentifier.of(new ModIdentifier("fuel_conversion"));
    public static final CategoryIdentifier<OxygenConversionDisplay> OXYGEN_CONVERSION_CATEGORY = CategoryIdentifier.of(new ModIdentifier("oxygen_conversion"));
    public static final CategoryIdentifier<NasaWorkbenchDisplay> NASA_WORKBENCH_CATEGORY = CategoryIdentifier.of(new ModIdentifier("nasa_workbench"));
    public static final CategoryIdentifier<SpaceStationDisplay> SPACE_STATION_CATEGORY = CategoryIdentifier.of(new ModIdentifier("space_station"));
}
