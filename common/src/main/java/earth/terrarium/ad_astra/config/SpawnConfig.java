package earth.terrarium.ad_astra.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;

@Category(id = "spawning", translation = "text.autoconfig.ad_astra.option.spawning")
public final class SpawnConfig {
    @ConfigEntry(
            id = "spawnCorruptedLunarians",
            type = EntryType.BOOLEAN,
            translation = "text.autoconfig.ad_astra.option.general.spawnCorruptedLunarians"
    )
    public static boolean spawnCorruptedLunarians = true;

    @ConfigEntry(
            id = "spawnStarCrawlers",
            type = EntryType.BOOLEAN,
            translation = "text.autoconfig.ad_astra.option.general.spawnStarCrawlers"
    )
    public static boolean spawnStarCrawlers = true;

    @ConfigEntry(
            id = "spawnMartianRaptors",
            type = EntryType.BOOLEAN,
            translation = "text.autoconfig.ad_astra.option.general.spawnMartianRaptors"
    )
    public static boolean spawnMartianRaptors = true;

    @ConfigEntry(
            id = "spawnMoglers",
            type = EntryType.BOOLEAN,
            translation = "text.autoconfig.ad_astra.option.general.spawnMoglers"
    )
    public static boolean spawnMoglers = true;

    @ConfigEntry(
            id = "spawnSulfurCreepers",
            type = EntryType.BOOLEAN,
            translation = "text.autoconfig.ad_astra.option.general.spawnSulfurCreepers"
    )
    public static boolean spawnSulfurCreepers = true;

    @ConfigEntry(
            id = "spawnLunarianWanderingTrader",
            type = EntryType.BOOLEAN,
            translation = "text.autoconfig.ad_astra.option.general.spawnLunarianWanderingTrader"
    )
    public static boolean spawnLunarianWanderingTrader = true;
}