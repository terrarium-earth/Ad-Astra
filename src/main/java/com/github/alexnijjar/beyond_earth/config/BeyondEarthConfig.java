package com.github.alexnijjar.beyond_earth.config;

import com.github.alexnijjar.beyond_earth.config.BeyondEarthConfig.VehiclesConfig.LanderConfig;
import com.github.alexnijjar.beyond_earth.config.BeyondEarthConfig.VehiclesConfig.RocketConfig;
import com.github.alexnijjar.beyond_earth.config.BeyondEarthConfig.VehiclesConfig.RoverConfig;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

@Config(name = "beyond_earth")
@Config.Gui.Background("beyond_earth:textures/block/machine_down.png")
public class BeyondEarthConfig implements ConfigData {
    @ConfigEntry.Gui.CollapsibleObject
    public GeneralConfig general = new GeneralConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public SpaceSuitConfig spaceSuit = new SpaceSuitConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public VehiclesConfig vehicles = new VehiclesConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public RocketConfig rocket = new RocketConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public RoverConfig rover = new RoverConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public LanderConfig lander = new LanderConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public CoalGeneratorConfig coalGenerator = new CoalGeneratorConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public CompressorConfig compressor = new CompressorConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public FuelRefineryConfig fuelRefinery = new FuelRefineryConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public OxygenLoaderConfig oxygenLoader = new OxygenLoaderConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public OxygenDistributorConfig oxygenDistributor = new OxygenDistributorConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public SolarPanelConfig solarPanel = new SolarPanelConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public WaterPumpConfig waterPump = new WaterPumpConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public EnergizerConfig energizer = new EnergizerConfig();
    @ConfigEntry.Gui.CollapsibleObject
    public CryoFreezerConfig cryoFreezer = new CryoFreezerConfig();

    public static class GeneralConfig {
        public boolean spawnCorruptedLunarians = true;
        public boolean spawnStarCrawlers = true;
        public boolean spawnMartianRaptors = true;
        public boolean spawnMoglers = true;
        public boolean spawnSulfurCreepers = true;
        public boolean spawnLunarianWanderingTrader = true;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean doEntityGravity = true;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public boolean doLivingEntityGravity = true;
        public boolean acidRainBurns = true;
        public boolean doOxygen = true;
        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean doSpaceMuffler = true;
        public long oxygenTankSize = FluidConstants.BUCKET / 2;
        @ConfigEntry.Gui.RequiresRestart
        public int hammerDurability = 64;
        public float orbitGravity = 3.26f;
        public boolean giveAstroduxAtSpawn = false;
        public int oxygenBarXOffset = 0;
        public int oxygenBarYOffset = 0;
        public int energyBarXOffset = 0;
        public int energyBarYOffset = 0;
    }

    public static class SpaceSuitConfig {
        public long spaceSuitTankSize = FluidConstants.BUCKET;
        public long netheriteSpaceSuitTankSize = FluidConstants.BUCKET * 2;
        public boolean netheriteSpaceSuitHasFireResistance = true;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public double jetSuitSpeed = 0.8;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public double jetSuitUpwardsSpeed = 0.5;
        public long jetSuitEnergyPerTick = 80;
        public long jetSuitTankSize = FluidConstants.BUCKET * 4;
        public long jetSuitMaxEnergy = 1000000L;
        public boolean spawnJetSuitParticles = true;
        public boolean enableJetSuitFlight = true;
        public int jetSuitProtectionMultiplier = 1;
        public int jetSuitArmorToughness = 5;
    }

    public static class VehiclesConfig {
        @ConfigEntry.Gui.Tooltip(count = 1)
        public double fallingExplosionThreshold = -1.2;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public float fallingExplosionMultiplier = 0.7f;
        public double gravity = -2.0;
        @ConfigEntry.Gui.Tooltip(count = 2)
        public boolean moveCameraInVehicle = true;

        public static class RocketConfig {
            @ConfigEntry.Gui.Tooltip(count = 1)
            public int atmosphereLeave = 600;
            @ConfigEntry.Gui.Tooltip(count = 1)
            @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
            public int countDownTicks = 200;
            public double acceleration = 0.005;
            public double maxSpeed = 0.5;
            @ConfigEntry.Gui.Tooltip(count = 1)
            public boolean entitiesBurnUnderRocket = true;
            public int tankBuckets = 3;
        }

        public static class RoverConfig {
            public boolean explodeRoverInLava = true;
            public long fuelPerTick = FluidConstants.BUCKET / 3000;
            public float turnSpeed = 3.0f;
            public float maxTurnSpeed = 6.0f;
            public float deceleration = 0.9f;
            public float minSpeed = -0.2f;
            public float maxSpeed = 0.4f;
            public int tankBuckets = 3;
        }

        public static class LanderConfig {
            public double boosterSpeed = 0.1;
            @ConfigEntry.Gui.Tooltip(count = 2)
            public double boosterThreshold = -0.1;
        }
    }

    public static class CoalGeneratorConfig {
        public long maxEnergy = 9000L;
        public long energyPerTick = 10L;
    }

    public static class CompressorConfig {
        public long maxEnergy = 9000L;
        public long energyPerTick = 10L;
    }

    public static class FuelRefineryConfig {
        public long maxEnergy = 9000L;
        public long energyPerTick = 30L;
        public int tankBuckets = 3;
    }

    public static class OxygenLoaderConfig {
        public long maxEnergy = 9000L;
        public long energyPerTick = 10L;
        public int tankBuckets = 3;
    }

    public static class OxygenDistributorConfig {
        public long maxEnergy = 20000L;
        public long fluidConversionEnergyPerTick = 5L;
        public int tankBuckets = 3;
        @ConfigEntry.Gui.Tooltip(count = 5)
        @ConfigEntry.BoundedDiscrete(min = 1, max = 50000)
        public int maxBlockChecks = 2000;
        @ConfigEntry.Gui.Tooltip(count = 2)
        @ConfigEntry.BoundedDiscrete(min = 0, max = 500)
        public int refreshTicks = 60;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public double oxygenMultiplier = 0.8;
        @ConfigEntry.Gui.Tooltip(count = 1)
        public double energyMultiplier = 3.0;
    }

    public static class SolarPanelConfig {
        public long maxEnergy = 18000L;
        public long energyPerTick = 15L;
    }

    public static class WaterPumpConfig {
        public long maxEnergy = 9000L;
        public long energyPerTick = 10L;
        public int tankBuckets = 6;
        public long transferPerTick = FluidConstants.BLOCK / 100;
        public boolean deleteWaterBelowWaterPump = true;
    }

    public static class EnergizerConfig {
        public long maxEnergy = 2000000L;
        public long energyPerTick = 64L;
    }

    public static class CryoFreezerConfig {
        public long maxEnergy = 30000L;
        public long energyPerTick = 24L;
        public int tankBuckets = 3;
    }
}