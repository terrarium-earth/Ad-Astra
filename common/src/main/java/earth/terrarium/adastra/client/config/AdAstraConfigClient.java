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
    public static boolean jetSuitEnabled = true;

    @ConfigEntry(
        type = EntryType.INTEGER,
        id = "oxygenBarX",
        translation = "Oxygen Bar X"
    )
    public static int oxygenBarX = 5;

    @ConfigEntry(
        type = EntryType.INTEGER,
        id = "oxygenBarY",
        translation = "Oxygen Bar Y"
    )
    public static int oxygenBarY = 25;

    @ConfigEntry(
        type = EntryType.FLOAT,
        id = "oxygenBarScale",
        translation = "Oxygen Bar Scale"
    )
    public static float oxygenBarScale = 1;

    @ConfigEntry(
        type = EntryType.INTEGER,
        id = "energyBarX",
        translation = "Energy Bar X"
    )
    public static int energyBarX = 11;

    @ConfigEntry(
        type = EntryType.INTEGER,
        id = "energyBarY",
        translation = "Energy Bar Y"
    )
    public static int energyBarY = 95;

    @ConfigEntry(
        type = EntryType.FLOAT,
        id = "energyBarScale",
        translation = "Energy Bar Scale"
    )
    public static float energyBarScale = 1;
}
