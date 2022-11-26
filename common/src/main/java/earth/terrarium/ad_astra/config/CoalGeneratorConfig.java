package earth.terrarium.ad_astra.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = "coalGenerator", translation = "text.autoconfig.ad_astra.option.coalGenerator")
public final class CoalGeneratorConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.autoconfig.ad_astra.option.coalGenerator.maxEnergy"
    )
    public static long maxEnergy = 9000L;

    @ConfigEntry(
        id = "energyPerTick",
        type = EntryType.LONG,
        translation = "text.autoconfig.ad_astra.option.coalGenerator.energyPerTick"
    )
    public static long energyPerTick = 10L;
}