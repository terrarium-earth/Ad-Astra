package earth.terrarium.adastra.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import com.teamresourceful.resourcefulconfig.web.annotations.Gradient;
import com.teamresourceful.resourcefulconfig.web.annotations.Link;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;

@Config("ad_astra")
@WebInfo(
    title = "Ad Astra",
    description = "Live long and prosper, and may the force be with you.",

    icon = "planet",
    gradient = @Gradient(value = "45deg", first = "#7F4DEE", second = "#E7797A"),

    links = {
        @Link(value = "https://discord.gg/terrarium", icon = "gamepad-2", title = "Discord"),
        @Link(value = "https://github.com/terrarium-earth/Ad-Astra", icon = "github", title = "GitHub"),

        @Link(value = "https://www.curseforge.com/minecraft/mc-mods/ad-astra", icon = "curseforge", title = "CurseForge"),
        @Link(value = "https://modrinth.com/mod/ad-astra", icon = "modrinth", title = "Modrinth"),
    }
)
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
    @Comment("The random tick for breaking plants, torches, freezing water, etc. on planets.")
    public static int planetRandomTickSpeed = 20;

    @ConfigEntry(
        id = "atmosphereLeave",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.atmosphereLeave"
    )
    @Comment("The y level where rockets should leave the dimension and enter space.")
    public static int atmosphereLeave = 600;

    @ConfigEntry(
        id = "maxDistributionBlocks",
        type = EntryType.INTEGER,
        translation = "config.ad_astra.maxDistributionBlocks"
    )
    @Comment("The maximum number of blocks that an oxygen distributor and gravity normalizer can distribute to.")
    public static int maxDistributionBlocks = 6_000;
}
