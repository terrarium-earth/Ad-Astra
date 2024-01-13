package earth.terrarium.adastra.common.config;

import com.teamresourceful.resourcefulconfig.api.annotations.Comment;
import com.teamresourceful.resourcefulconfig.api.annotations.Config;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.api.annotations.ConfigInfo;
import com.teamresourceful.resourcefulconfig.api.types.options.EntryType;
import earth.terrarium.adastra.common.config.info.AdAstraConfigInfo;

@Config(
    value = "ad_astra",
    categories = {
        MachineConfig.class
    }
)
@ConfigInfo.Provider(AdAstraConfigInfo.class)
public final class AdAstraConfig {

    @ConfigEntry(
        id = "allowFlagImages",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.allowFlagImages"
    )
    @Comment("Allow players to set custom flag images for their rockets.")
    public static boolean allowFlagImages = true;

    @ConfigEntry(
        id = "launchAnywhere",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.launchFromAnywhere"
    )
    @Comment("Allow rockets to be launched from any dimension, even if it's not considered a planet.")
    public static boolean launchFromAnywhere;

    @ConfigEntry(
        id = "planetRandomTickSpeed",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.planetRandomTickSpeed"
    )
    @Comment("The random tick speed for breaking plants, torches, freezing water, etc. on planets.")
    public static int planetRandomTickSpeed = 20;

    @ConfigEntry(
        id = "forcePlanetTick",
        type = EntryType.BOOLEAN,
        translation = "config.ad_astra.forcePlanetTick"
    )
    @Comment("Always tick every planet chunk for things like freezing water, breaking plants, etc., regardless of whether the chunk can tick randomly or not. This has a small performance impact.")
    public static boolean forcePlanetTick;

    @ConfigEntry(
        id = "atmosphereLeave",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.atmosphereLeave"
    )
    @Comment("The y level where rockets should leave the dimension and enter space.")
    public static int atmosphereLeave = 600;
}
