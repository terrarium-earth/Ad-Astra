package earth.terrarium.ad_astra.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = "solarPanel", translation = "text.resourcefulconfig.ad_astra.option.solarPanel")
public final class SolarPanelConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.solarPanel.maxEnergy"
    )
    public static long maxEnergy = 18000L;

    @ConfigEntry(
        id = "energyMultiplier",
        type = EntryType.DOUBLE,
        translation = "text.resourcefulconfig.ad_astra.option.solarPanel.energyMultiplier"
    )
    public static double energyMultiplier = 1.0;
}