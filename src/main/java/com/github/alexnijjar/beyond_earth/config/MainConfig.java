package com.github.alexnijjar.beyond_earth.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;

@Config(name = "main")
public class MainConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip(count = 1)
    public double jetSuitSpeed = 0.8;
    @ConfigEntry.Gui.Tooltip(count = 1)
    public double jetSuitUpwardsSpeed = 0.3;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean doSpaceMuffler = true;

    public long oxygenTankSize = FluidConstants.BUCKET / 2;

    public long spaceSuitTankSize = FluidConstants.BUCKET;

    public long netheriteSpaceSuitTankSize = FluidConstants.BUCKET * 2;

    public long jetSuitTankSize = FluidConstants.BUCKET * 4;
    public long jetSuitMaxEnergy = 200000L;
    public boolean spawnJetSuitParticles = true;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public double vehicleFallingExplosionThreshold = -0.4;
    @ConfigEntry.Gui.Tooltip(count = 1)
    public float vehicleFallingExplosionMultiplier = 0.55f;
    public double vehicleGravity = -0.04;
    public float vehicleMinSpeed = -0.1f;
    public float vehicleMaxSpeed = 0.2f;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public int rocketAtmosphereLeave = 600;
    @ConfigEntry.Gui.Tooltip(count = 1)
    @ConfigEntry.BoundedDiscrete(min = 0, max = 200)
    public int rocketCountDownTicks = 200;
    public double rocketAcceleration = 0.005;
    public double rocketMaxSpeed = 0.5;
    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean entitiesBurnUnderRocket = true;
    public int rocketTankBuckets = 3;

    public boolean explodeRoverInLava = true;
    public float roverForwardSpeed = 0.1f;
    public float roverBackwardSpeed = 0.05f;
    public float roverTurnSpeed = 15.0f;
    public float roverMaxTurnSpeed = 3.0f;
    public float roverDeceleration = 0.9f;
    public int roverTankBuckets = 3;

    public double landerBoosterSpeed = 0.025;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public double landerBoosterThreshold = -0.1;

    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean doEntityGravity = true;
    @ConfigEntry.Gui.Tooltip(count = 1)
    public boolean doLivingEntityGravity = true;
    public boolean acidRainBurns = true;
    public boolean doOxygen = true;
    @ConfigEntry.Gui.Tooltip(count = 4)
    public int oxygenGracePeriodTicks = 140;
    public boolean netheriteSpaceSuitHasFireResistance = true;
    public boolean enableJetSuitFlight = true;

    public long coalGeneratorMaxEnergy = 9000L;
    public long coalGeneratorEnergyPerTick = 2L;

    public long compressorGeneratorMaxEnergy = 9000L;
    public long compressorGeneratorEnergyPerTick = 5L;

    public long fuelRefineryGeneratorMaxEnergy = 9000L;
    public long fuelRefineryGeneratorEnergyPerTick = 10L;
    public int fuelRefineryTankBuckets = 3;

    public long oxygenDistributorGeneratorMaxEnergy = 20000L;
    public long oxygenDistributorGeneratorEnergyPerTick = 5L;
    public int oxygenDistributorTankBuckets = 3;
    @ConfigEntry.Gui.Tooltip(count = 5)
    @ConfigEntry.BoundedDiscrete(min = 1, max = 4000)
    @ConfigEntry.Gui.RequiresRestart
    public int oxygenDistributorMaxBlockChecks = 2000;
    @ConfigEntry.Gui.Tooltip(count = 2)
    @ConfigEntry.BoundedDiscrete(min = 50, max = 2000)
    @ConfigEntry.Gui.RequiresRestart
    public int oxygenDistributorRefreshTicks = 100;
    @ConfigEntry.Gui.Tooltip(count = 1)
    public double oxygenDistributorOxygenMultiplier = 0.8;
    @ConfigEntry.Gui.Tooltip(count = 1)
    public double oxygenDistributorEnergyMultiplier = 0.5;

    public long oxygenLoaderGeneratorMaxEnergy = 9000L;
    public long oxygenLoaderGeneratorEnergyPerTick = 10L;
    public int oxygenLoaderTankBuckets = 3;

    public long solarPanelGeneratorMaxEnergy = 9000L;
    public long solarPanelGeneratorEnergyPerTick = 5L;

    public long waterPumpGeneratorMaxEnergy = 9000L;
    public long waterPumpGeneratorEnergyPerTick = 5L;
    public int waterPumpTankBuckets = 6;
    public long waterPumpTransferPerTick = FluidConstants.BLOCK / 100;
    public boolean deleteWaterBelowWaterPump = true;

    public int hammerDurability = 9;

    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean moveCameraInVehicle = true;

    public boolean spawnAlienZombies = true;
    public boolean spawnStarCrawlers = true;
    public boolean spawnMartianRaptors = true;
}