package earth.terrarium.ad_astra.config;

import earth.terrarium.ad_astra.config.AdAstraConfig.VehiclesConfig.LanderConfig;
import earth.terrarium.ad_astra.config.AdAstraConfig.VehiclesConfig.RocketConfig;
import earth.terrarium.ad_astra.config.AdAstraConfig.VehiclesConfig.RoverConfig;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "ad_astra")
@Config.Gui.Background("ad_astra:textures/block/machine_down.png")
public class AdAstraConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public final GeneralConfig general = new GeneralConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final SpaceSuitConfig spaceSuit = new SpaceSuitConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final VehiclesConfig vehicles = new VehiclesConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final RocketConfig rocket = new RocketConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final RoverConfig rover = new RoverConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final LanderConfig lander = new LanderConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final CoalGeneratorConfig coalGenerator = new CoalGeneratorConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final CompressorConfig compressor = new CompressorConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final FuelRefineryConfig fuelRefinery = new FuelRefineryConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final OxygenLoaderConfig oxygenLoader = new OxygenLoaderConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final OxygenDistributorConfig oxygenDistributor = new OxygenDistributorConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final SolarPanelConfig solarPanel = new SolarPanelConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final WaterPumpConfig waterPump = new WaterPumpConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final EnergizerConfig energizer = new EnergizerConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public final CryoFreezerConfig cryoFreezer = new CryoFreezerConfig();

    public static class GeneralConfig {
        public final boolean spawnCorruptedLunarians = true;
        public final boolean spawnStarCrawlers = true;
        public final boolean spawnMartianRaptors = true;
        public final boolean spawnMoglers = true;
        public final boolean spawnSulfurCreepers = true;
        @ConfigEntry.Gui.Tooltip()
        public final boolean doEntityGravity = true;
        @ConfigEntry.Gui.Tooltip()
        public final boolean doLivingEntityGravity = true;
        public final boolean acidRainBurns = true;
        public final boolean doOxygen = true;
        @ConfigEntry.Gui.Tooltip(count = 2)
        public final boolean doSpaceMuffler = true;
        public final long oxygenTankSize = FluidHooks.buckets(1) / 2;
        public final int hammerDurability = 64;
        public final boolean giveAstroduxAtSpawn = false;
        public final int oxygenBarXOffset = 0;
        public final int oxygenBarYOffset = 0;
        public final int energyBarXOffset = 0;
        public final int energyBarYOffset = 0;
        public boolean spawnLunarianWanderingTrader = true;
        public float orbitGravity = 3.26f;
        public int oxygenDamage = 1;
        public int freezeDamage = 1;
        public int heatDamage = 2;
        public int acidRainDamage = 3;
    }

    public static class SpaceSuitConfig {
        public final long spaceSuitTankSize = FluidHooks.buckets(1);
        public final long netheriteSpaceSuitTankSize = FluidHooks.buckets(2);
        public final boolean netheriteSpaceSuitHasFireResistance = true;
        @ConfigEntry.Gui.Tooltip()
        public final double jetSuitSpeed = 0.8;
        @ConfigEntry.Gui.Tooltip()
        public final double jetSuitUpwardsSpeed = 0.5;
        public final long jetSuitEnergyPerTick = 60;
        public final long jetSuitTankSize = FluidHooks.buckets(4);
        public final long jetSuitMaxEnergy = 1000000L;
        public final boolean enableJetSuitFlight = true;
        public final int jetSuitProtectionMultiplier = 1;
        public final int jetSuitArmorToughness = 5;
        public boolean spawnJetSuitParticles = true;
    }

    public static class VehiclesConfig {
        @ConfigEntry.Gui.Tooltip()
        public final double fallingExplosionThreshold = -1.2;
        @ConfigEntry.Gui.Tooltip()
        public final float fallingExplosionMultiplier = 0.7f;
        public final double gravity = -2.0;
        @ConfigEntry.Gui.Tooltip(count = 2)
        public final boolean moveCameraInVehicle = true;

        public static class RocketConfig {
            @ConfigEntry.Gui.Tooltip()
            public final int atmosphereLeave = 600;
            @ConfigEntry.Gui.Tooltip()
            @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
            public final int countDownTicks = 200;
            public final double acceleration = 0.005;
            public final double maxSpeed = 0.5;
            @ConfigEntry.Gui.Tooltip()
            public final boolean entitiesBurnUnderRocket = true;
            public final long tankSize = FluidHooks.buckets(3);
            public final long fuelLaunchCost = FluidHooks.buckets(3);
            public final long efficientFuelLaunchCost = FluidHooks.buckets(1);
        }

        public static class RoverConfig {
            public final boolean explodeRoverInLava = true;
            public final long fuelPerSecond = FluidHooks.buckets(1) / 100;
            public final float turnSpeed = 3.0f;
            public final float maxTurnSpeed = 6.0f;
            public final float deceleration = 0.9f;
            public final float minSpeed = -0.2f;
            public final float maxSpeed = 0.3f;
            public final long tankSize = FluidHooks.buckets(3);
        }

        public static class LanderConfig {
            @ConfigEntry.Gui.Tooltip(count = 2)
            public final double boosterThreshold = -0.1;
            public double boosterSpeed = 0.1;
        }
    }

    public static class CoalGeneratorConfig {
        public final long maxEnergy = 9000L;
        public final long energyPerTick = 10L;
    }

    public static class CompressorConfig {
        public final long maxEnergy = 9000L;
        public final long energyPerTick = 10L;
    }

    public static class FuelRefineryConfig {
        public final long maxEnergy = 9000L;
        public final long energyPerTick = 30L;
        public final long tankSize = FluidHooks.buckets(3);
    }

    public static class OxygenLoaderConfig {
        public final long maxEnergy = 9000L;
        public final long energyPerTick = 10L;
        public final long tankSize = FluidHooks.buckets(3);
    }

    public static class OxygenDistributorConfig {
        public final long maxEnergy = 20000L;
        public final long fluidConversionEnergyPerTick = 5L;
        public final long tankSize = FluidHooks.buckets(6);
        @ConfigEntry.Gui.Tooltip(count = 5)
        @ConfigEntry.BoundedDiscrete(min = 1, max = 50000)
        public final int maxBlockChecks = 2000;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public final int refreshTicks = 60;
        @ConfigEntry.Gui.Tooltip()
        public final double oxygenMultiplier = 0.8;
        @ConfigEntry.Gui.Tooltip()
        public final double energyMultiplier = 3.0;
    }

    public static class SolarPanelConfig {
        public final long maxEnergy = 18000L;
        public final double energyMultiplier = 1.0;
    }

    public static class WaterPumpConfig {
        public final long maxEnergy = 9000L;
        public final long energyPerTick = 10L;
        public final long tankSize = FluidHooks.buckets(6);
        public final long transferPerTick = FluidHooks.buckets(1) / 10;
        public final boolean deleteWaterBelowWaterPump = true;
    }

    public static class EnergizerConfig {
        public final long maxEnergy = 2000000L;
        public final long energyPerTick = 170L;
    }

    public static class CryoFreezerConfig {
        public final long maxEnergy = 30000L;
        public final long energyPerTick = 24L;
        public final long tankSize = FluidHooks.buckets(3);
    }
}