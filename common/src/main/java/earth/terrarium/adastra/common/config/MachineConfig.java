package earth.terrarium.adastra.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;

@Category(id = "cryoFreezer", translation = "config.ad_astra.machines")
@WebInfo(icon = "zap")
public final class MachineConfig {

    @ConfigEntry(
        id = "ironTierMaxEnergyInOut",
        type = EntryType.LONG,
        translation = "config.ad_astra.ironTierMaxEnergyInOut"
    )
    public static long ironTierMaxEnergyInOut = 100;

    @ConfigEntry(
        id = "steelTierMaxEnergyInOut",
        type = EntryType.LONG,
        translation = "config.ad_astra.steelTierMaxEnergyInOut"
    )
    public static long steelTierMaxEnergyInOut = 150;

    @ConfigEntry(
        id = "deshTierMaxEnergyInOut",
        type = EntryType.LONG,
        translation = "config.ad_astra.deshTierMaxEnergyInOut"
    )
    public static long deshTierMaxEnergyInOut = 250;

    @ConfigEntry(
        id = "ostrumTierMaxEnergyInOut",
        type = EntryType.LONG,
        translation = "config.ad_astra.ostrumTierMaxEnergyInOut"
    )
    public static long ostrumTierMaxEnergyInOut = 500;


    @ConfigEntry(
        id = "ironTierEnergyCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.ironTierEnergyCapacity"
    )
    public static long ironTierEnergyCapacity = 10_000;

    @ConfigEntry(
        id = "steelTierEnergyCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.steelTierEnergyCapacity"
    )
    public static long steelTierEnergyCapacity = 20_000;

    @ConfigEntry(
        id = "deshTierEnergyCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.deshTierEnergyCapacity"
    )
    public static long deshTierEnergyCapacity = 50_000;

    @ConfigEntry(
        id = "ostrumTierEnergyCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.ostrumTierEnergyCapacity"
    )
    public static long ostrumTierEnergyCapacity = 100_000;


    @ConfigEntry(
        id = "steelTierFluidCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.steelTierFluidCapacity"
    )
    public static long steelTierFluidCapacity = 3;

    @ConfigEntry(
        id = "deshTierFluidCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.deshTierFluidCapacity"
    )
    public static long deshTierFluidCapacity = 5;

    @ConfigEntry(
        id = "ostrumTierFluidCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.ostrumTierFluidCapacity"
    )
    public static long ostrumTierFluidCapacity = 10;


    @ConfigEntry(
        id = "coalGeneratorEnergyGenerationPerTick",
        type = EntryType.LONG,
        translation = "config.ad_astra.coalGeneratorEnergyGenerationPerTick"
    )
    public static long coalGeneratorEnergyGenerationPerTick = 20;

    @ConfigEntry(
        id = "etrionicBlastFurnaceBlastingEnergyPerItem",
        type = EntryType.LONG,
        translation = "config.ad_astra.etrionicBlastFurnaceBlastingEnergyPerItem"
    )
    public static long etrionicBlastFurnaceBlastingEnergyPerItem = 10;

    @ConfigEntry(
        id = "waterPumpEnergyPerTick",
        type = EntryType.LONG,
        translation = "config.ad_astra.waterPumpEnergyPerTick"
    )
    public static long waterPumpEnergyPerTick = 20;

    @ConfigEntry(
        id = "waterPumpFluidGenerationPerTick",
        type = EntryType.DOUBLE,
        translation = "config.ad_astra.waterPumpFluidGenerationPerTick"
    )
    public static double waterPumpFluidGenerationPerTick = 0.05;

    @ConfigEntry(
        id = "energizerEnergyCapacity",
        type = EntryType.LONG,
        translation = "config.ad_astra.energizerEnergyCapacity"
    )
    public static long energizerEnergyCapacity = 2_000_000;

    @ConfigEntry(
        id = "maxDistributionBlocks",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.maxDistributionBlocks"
    )
    @Comment("The maximum number of blocks that an oxygen distributor and gravity normalizer can distribute to.")
    public static int maxDistributionBlocks = 6_000;

    @ConfigEntry(
        id = "distributionRefreshRate",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.distributionRefreshRate"
    )
    @Comment("The tick rate (20 ticks = 1 second) at which the oxygen distributor and gravity normalizer will recalculate the distribution area.")
    public static int distributionRefreshRate = 100;

    @ConfigEntry(
        id = "pipeRefreshRate",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.pipeRefreshRate"
    )
    @Comment("The tick rate (20 ticks = 1 second) at which cables and fluid pipes will recalculate their connections.")
    public static int pipeRefreshRate = 50;
}
