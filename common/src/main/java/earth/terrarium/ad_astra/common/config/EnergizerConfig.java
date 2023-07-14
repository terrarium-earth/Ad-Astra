package earth.terrarium.ad_astra.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;

@Category(id = "energizer", translation = "text.resourcefulconfig.ad_astra.option.energizer")
@WebInfo(icon = "zap")
public final class EnergizerConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.energizer.maxEnergy"
    )
    public static long maxEnergy = 2000000L;

    @ConfigEntry(
            id = "energyPerTick",
            type = EntryType.LONG,
            translation = "text.resourcefulconfig.ad_astra.option.energizer.energyPerTick"
    )
    public static long energyPerTick = 600L;
}