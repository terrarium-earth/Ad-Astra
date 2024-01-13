package earth.terrarium.adastra.client.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.ResourcefulConfig;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;
import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.config.info.AdAstraConfigInfo;
import net.minecraft.client.Minecraft;

@Config(
    value = "ad_astra-client",
    categories = {
        RadioConfig.class
    }
)
@ConfigInfo.Provider(AdAstraConfigInfo.class)
public final class AdAstraConfigClient {

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "showOxygenDistributorArea",
        translation = "Show Oxygen Distributor Area"
    )
    public static boolean showOxygenDistributorArea;

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "showGravityNormalizerArea",
        translation = "Show Gravity Normalizer Area"
    )
    public static boolean showGravityNormalizerArea;

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

    @ConfigEntry(
        type = EntryType.BOOLEAN,
        id = "spaceMuffler",
        translation = "Reduce volume and increase pitch in space"
    )
    public static boolean spaceMuffler = true;

    public static void open() {
        ResourcefulConfig config = AdAstra.CONFIGURATOR.getConfig(AdAstraConfigClient.class);
        if (config == null) return;
        Minecraft.getInstance().setScreen(new ConfigScreen(Minecraft.getInstance().screen, config));
    }
}
