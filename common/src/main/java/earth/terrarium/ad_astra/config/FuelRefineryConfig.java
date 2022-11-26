package earth.terrarium.ad_astra.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import earth.terrarium.botarium.api.fluid.FluidHooks;

@Category(id = "fuelRefinery", translation = "text.autoconfig.ad_astra.option.fuelRefinery")
public final class FuelRefineryConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.autoconfig.ad_astra.option.fuelRefinery.maxEnergy"
    )
    public static long maxEnergy = 9000L;

    @ConfigEntry(
        id = "energyPerTick",
        type = EntryType.LONG,
        translation = "text.autoconfig.ad_astra.option.fuelRefinery.energyPerTick"
    )
    public static long energyPerTick = 30L;

    @ConfigEntry(
        id = "tankSize",
        type = EntryType.LONG,
        translation = "text.autoconfig.ad_astra.option.fuelRefinery.tankSize"
    )
    public static long tankSize = FluidHooks.buckets(3);
}