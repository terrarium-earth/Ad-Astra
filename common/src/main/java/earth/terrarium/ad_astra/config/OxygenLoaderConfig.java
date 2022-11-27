package earth.terrarium.ad_astra.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import earth.terrarium.botarium.api.fluid.FluidHooks;

@Category(id = "oxygenLoader", translation = "text.resourcefulconfig.ad_astra.option.oxygenLoader")
public final class OxygenLoaderConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.oxygenLoader.maxEnergy"
    )
    public static long maxEnergy = 9000L;

    @ConfigEntry(
        id = "energyPerTick",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.oxygenLoader.energyPerTick"
    )
    public static long energyPerTick = 10L;

    @ConfigEntry(
        id = "tankSize",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.oxygenLoader.tankSize"
    )
    public static long tankSize = FluidHooks.buckets(3);
}