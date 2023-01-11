package earth.terrarium.ad_astra.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.IntRange;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;

@Category(id = "oxygenDistributor", translation = "text.resourcefulconfig.ad_astra.option.oxygenDistributor")
public final class OxygenDistributorConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.oxygenDistributor.maxEnergy"
    )
    public static long maxEnergy = 20000L;

    @ConfigEntry(
        id = "fluidConversionEnergyPerTick",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.oxygenDistributor.fluidConversionEnergyPerTick"
    )
    public static long fluidConversionEnergyPerTick = 5L;

    @ConfigEntry(
            id = "tankSize",
            type = EntryType.LONG,
            translation = "text.resourcefulconfig.ad_astra.option.oxygenDistributor.tankSize"
    )
    public static long tankSize = FluidHooks.buckets(6f);

    @ConfigEntry(
        id = "maxBlockChecks",
        type = EntryType.INTEGER,
        translation = "text.resourcefulconfig.ad_astra.option.oxygenDistributor.maxBlockChecks"
    )
    @IntRange(min = 1, max = 50000)
    @Comment("How many blocks the distributor can distribute oxygen in. Be careful increasing this number, as it can reduce server performance significantly. If you are adamant about increasing this value, consider increasing the Oxygen Loader refresh ticks so the server is not constantly checking thousands of blocks.")
    public static int maxBlockChecks = 2000;

    @ConfigEntry(
        id = "refreshTicks",
        type = EntryType.INTEGER,
        translation = "text.resourcefulconfig.ad_astra.option.oxygenDistributor.refreshTicks"
    )
    @IntRange(min = 0, max = 500)
    @Comment("How often the oxygen loader checks if the structure is sealed. Performing this operation too often can kill server TPS, so be wary.")
    public static int refreshTicks = 60;

    @ConfigEntry(
            id = "oxygenMultiplier",
            type = EntryType.DOUBLE,
            translation = "text.resourcefulconfig.ad_astra.option.oxygenDistributor.oxygenMultiplier"
    )
    @Comment("Increases the oxygen requirements as the distributor covers more blocks.")
    public static double oxygenMultiplier = 1.0;

    @ConfigEntry(
            id = "energyMultiplier",
            type = EntryType.DOUBLE,
            translation = "text.resourcefulconfig.ad_astra.option.oxygenDistributor.energyMultiplier"
    )
    @Comment("Increases the energy requirements as the distributor covers more blocks.")
    public static double energyMultiplier = 3.0;
}