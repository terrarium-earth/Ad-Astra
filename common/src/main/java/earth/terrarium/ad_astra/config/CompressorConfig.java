package earth.terrarium.ad_astra.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = "compressor", translation = "text.autoconfig.ad_astra.option.compressor")
public final class CompressorConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.autoconfig.ad_astra.option.compressor.maxEnergy"
    )
    public static long maxEnergy = 9000L;

    @ConfigEntry(
        id = "energyPerTick",
        type = EntryType.LONG,
        translation = "text.autoconfig.ad_astra.option.compressor.energyPerTick"
    )
    public static long energyPerTick = 10L;
}