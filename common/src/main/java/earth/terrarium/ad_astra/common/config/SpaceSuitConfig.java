package earth.terrarium.ad_astra.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;

@Category(id = "spaceSuit", translation = "text.resourcefulconfig.ad_astra.option.spaceSuit")
public final class SpaceSuitConfig {

    @ConfigEntry(
        id = "spaceSuitTankSize",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.spaceSuitTankSize"
    )
    public static long spaceSuitTankSize = FluidHooks.buckets(1f);

    @ConfigEntry(
        id = "netheriteSpaceSuitTankSize",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.netheriteSpaceSuitTankSize"
    )
    public static long netheriteSpaceSuitTankSize = FluidHooks.buckets(2f);

    @ConfigEntry(
        id = "netheriteSpaceSuitHasFireResistance",
        type = EntryType.BOOLEAN,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.netheriteSpaceSuitHasFireResistance"
    )
    public static boolean netheriteSpaceSuitHasFireResistance = true;

    @ConfigEntry(
        id = "jetSuitSpeed",
        type = EntryType.DOUBLE,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitSpeed"
    )
    @Comment(value = "The speed when flying forward.", translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitSpeed.tooltip")
    public static double jetSuitSpeed = 0.8;

    @ConfigEntry(
        id = "jetSuitUpwardsSpeed",
        type = EntryType.DOUBLE,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitUpwardsSpeed"
    )
    @Comment(value = "The speed when idle flying up.", translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitUpwardsSpeed.tooltip")
    public static double jetSuitUpwardsSpeed = 0.5;

    @ConfigEntry(
        id = "jetSuitEnergyPerTick",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitEnergyPerTick"
    )
    public static long jetSuitEnergyPerTick = 60;

    @ConfigEntry(
        id = "jetSuitTankSize",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitTankSize"
    )
    public static long jetSuitTankSize = FluidHooks.buckets(4f);

    @ConfigEntry(
        id = "jetSuitMaxEnergy",
        type = EntryType.LONG,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitMaxEnergy"
    )
    public static long jetSuitMaxEnergy = 1000000L;

    @ConfigEntry(
        id = "enableJetSuitFlight",
        type = EntryType.BOOLEAN,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.enableJetSuitFlight"
    )
    public static boolean enableJetSuitFlight = true;

    @ConfigEntry(
        id = "jetSuitProtectionMultiplier",
        type = EntryType.INTEGER,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitProtectionMultiplier"
    )
    public static int jetSuitProtectionMultiplier = 1;

    @ConfigEntry(
        id = "jetSuitArmorToughness",
        type = EntryType.INTEGER,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.jetSuitArmorToughness"
    )
    public static int jetSuitArmorToughness = 5;

    @ConfigEntry(
        id = "spawnJetSuitParticles",
        type = EntryType.BOOLEAN,
        translation = "text.resourcefulconfig.ad_astra.option.spaceSuit.spawnJetSuitParticles"
    )
    public static boolean spawnJetSuitParticles = true;
}