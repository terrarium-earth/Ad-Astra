package earth.terrarium.ad_astra.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import earth.terrarium.botarium.api.fluid.FluidHooks;

@Category(id = "cryoFreezer", translation = "text.resourcefulconfig.ad_astra.option.cryoFreezer")
public final class CryoFreezerConfig {

    @ConfigEntry(
        id = "maxEnergy",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.cryoFreezer.energyPerTick"
    )
    public static long maxEnergy = 30000L;

    @ConfigEntry(
        id = "energyPerTick",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.cryoFreezer.energyPerTick"
    )
    public static long energyPerTick = 24L;

    @ConfigEntry(
        id = "tankSize",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.cryoFreezer.tankSize"
    )
    public static long tankSize = FluidHooks.buckets(3);
}