package earth.terrarium.ad_astra.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.annotations.InlineCategory;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;

@Config("ad_astra")
public final class AdAstraConfig {

    @ConfigEntry(
        id = "doEntityGravity",
        type = EntryType.BOOLEAN,
        translation = "text.resourcefulconfig.ad_astra.option.general.doEntityGravity"
    )
    @Comment(value = "If true, entities will be affected by gravity.", translation = "text.resourcefulconfig.ad_astra.option.general.doEntityGravity.tooltip")
    public static boolean doEntityGravity = true;

    @ConfigEntry(
            id = "doLivingEntityGravity",
            type = EntryType.BOOLEAN,
            translation = "text.resourcefulconfig.ad_astra.option.general.doLivingEntityGravity"
    )
    @Comment(value = "Do gravity for mobs, players etc.", translation = "text.resourcefulconfig.ad_astra.option.general.doLivingEntityGravity.tooltip")
    public static boolean doLivingEntityGravity = true;

    @ConfigEntry(
            id = "acidRainBurns",
            type = EntryType.BOOLEAN,
            translation = "text.resourcefulconfig.ad_astra.option.general.acidRainBurns"
    )
    public static boolean acidRainBurns = true;

    @ConfigEntry(
            id = "doOxygen",
            type = EntryType.BOOLEAN,
            translation = "text.resourcefulconfig.ad_astra.option.general.doOxygen"
    )
    public static boolean doOxygen = true;

    @ConfigEntry(
            id = "doSpaceMuffler",
            type = EntryType.BOOLEAN,
            translation = "text.resourcefulconfig.ad_astra.option.general.doSpaceMuffler"
    )
    @Comment(value = "Do sound suppression in orbit dimensions. If it gets annoying, you can turn it off.", translation = "text.resourcefulconfig.ad_astra.option.general.doSpaceMuffler.tooltip")
    public static boolean doSpaceMuffler = true;

    @ConfigEntry(
            id = "oxygenTankSize",
            type = EntryType.LONG,
            translation = "text.resourcefulconfig.ad_astra.option.general.oxygenTankSize"
    )
    public static long oxygenTankSize = FluidHooks.buckets(1f) / 2;

    @ConfigEntry(
            id = "hammerDurability",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.hammerDurability"
    )
    public static int hammerDurability = 64;

    @ConfigEntry(
            id = "giveAstroduxAtSpawn",
            type = EntryType.BOOLEAN,
            translation = "text.resourcefulconfig.ad_astra.option.general.giveAstroduxAtSpawn"
    )
    public static boolean giveAstroduxAtSpawn = false;

    @ConfigEntry(
            id = "oxygenBarXOffset",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.oxygenBarXOffset"
    )
    public static int oxygenBarXOffset = 0;

    @ConfigEntry(
            id = "oxygenBarYOffset",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.oxygenBarYOffset"
    )
    public static int oxygenBarYOffset = 0;

    @ConfigEntry(
            id = "energyBarXOffset",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.energyBarXOffset"
    )
    public static int energyBarXOffset = 0;

    @ConfigEntry(
            id = "energyBarYOffset",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.energyBarYOffset"
    )
    public static int energyBarYOffset = 0;

    @ConfigEntry(
            id = "orbitGravity",
            type = EntryType.FLOAT,
            translation = "text.resourcefulconfig.ad_astra.option.general.orbitGravity"
    )
    public static float orbitGravity = 3.26f;

    @ConfigEntry(
            id = "oxygenDamage",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.oxygenDamage"
    )
    public static int oxygenDamage = 1;

    @ConfigEntry(
            id = "freezeDamage",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.freezeDamage"
    )
    public static int freezeDamage = 1;

    @ConfigEntry(
            id = "heatDamage",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.heatDamage"
    )
    public static int heatDamage = 2;

    @ConfigEntry(
            id = "acidRainDamage",
            type = EntryType.INTEGER,
            translation = "text.resourcefulconfig.ad_astra.option.general.acidRainDamage"
    )
    public static int acidRainDamage = 3;

    @ConfigEntry(
            id = "allowFlagImages",
            type = EntryType.BOOLEAN,
            translation = "text.resourcefulconfig.ad_astra.option.general.allowFlagImages"
    )
    public static boolean allowFlagImages = true;

    @ConfigEntry(
            id = "disabledPlanets",
            type = EntryType.STRING,
            translation = "text.resourcefulconfig.ad_astra.option.general.disabledPlanets"
    )
    @Comment("A list of planets that should be disabled. This is a comma-separated list of planet ids.")
    public static String disabledPlanets = "";

    @ConfigEntry(
            id = "avoidOverworldChecks",
            type = EntryType.BOOLEAN,
            translation = "text.resourcefulconfig.ad_astra.option.general.avoidOverworldChecks"
    )
    @Comment("Prevents stuff like gravity and oxygen checks in the overworld as that's normally not used in Ad Astra. enable if you're making an addon or something that transforms the overworld into some sort of planet.")
    public static boolean avoidOverworldChecks = true;

    @InlineCategory
    public static SpawnConfig spawnConfig;

    @InlineCategory
    public static SpaceSuitConfig spaceSuitConfig;

    @InlineCategory
    public static VehiclesConfig vehiclesConfig;

    @InlineCategory
    public static CoalGeneratorConfig coalGeneratorConfig;

    @InlineCategory
    public static CompressorConfig compressorConfig;

    @InlineCategory
    public static FuelRefineryConfig fuelRefineryConfig;

    @InlineCategory
    public static OxygenLoaderConfig oxygenLoaderConfig;

    @InlineCategory
    public static OxygenDistributorConfig oxygenDistributorConfig;

    @InlineCategory
    public static SolarPanelConfig solarPanelConfig;

    @InlineCategory
    public static WaterPumpConfig waterPumpConfig;

    @InlineCategory
    public static EnergizerConfig energizerConfig;

    @InlineCategory
    public static CryoFreezerConfig cryoFreezerConfig;

}