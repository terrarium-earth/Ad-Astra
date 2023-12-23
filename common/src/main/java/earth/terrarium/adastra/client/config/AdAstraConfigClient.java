package earth.terrarium.adastra.client.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Config("ad_astra-client")
public final class AdAstraConfigClient {

    @InlineCategory
    public static RadioConfig radio;

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "showOxygenDistributorArea",
        translation = "Show Oxygen Distributor Area"
    )
    public static boolean showOxygenDistributorArea;

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "jetSuitEnabled",
        translation = "Jet Suit Enabled"
    )
    public static boolean jetSuitEnabled;
}
