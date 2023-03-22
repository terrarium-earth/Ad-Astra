package earth.terrarium.ad_astra.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;
import earth.terrarium.botarium.api.fluid.FluidHooks;

@Category(id = "waterPump", translation = "text.resourcefulconfig.ad_astra.option.waterPump")
@WebInfo(icon = "shower-head")
public final class WaterPumpConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.waterPump.maxEnergy"
    )
    public static long maxEnergy = 9000L;

    @ConfigEntry(
        id = "energyPerTick",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.waterPump.energyPerTick"
    )
    public static long energyPerTick = 10L;

    @ConfigEntry(
        id = "tankSize",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.waterPump.tankSize"
    )
    public static long tankSize = FluidHooks.buckets(6);

    @ConfigEntry(
        id = "transferPerTick",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.waterPump.transferPerTick"
    )
    public static long transferPerTick = FluidHooks.buckets(1) / 10;

    @ConfigEntry(
        id = "deleteWaterBelowWaterPump",
        type = EntryType.BOOLEAN,
        translation = "text.resourcefulconfig.ad_astra.option.waterPump.deleteWaterBelowWaterPump"
    )
    public static boolean deleteWaterBelowWaterPump = true;
}