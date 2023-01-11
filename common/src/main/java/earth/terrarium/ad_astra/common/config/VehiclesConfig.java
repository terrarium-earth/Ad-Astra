package earth.terrarium.ad_astra.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Category;
import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import earth.terrarium.botarium.common.fluid.utils.FluidHooks;

@Category(id = "vehicles", translation = "text.resourcefulconfig.ad_astra.option.vehicles")
public final class VehiclesConfig {

    @ConfigEntry(
            id = "fallingExplosionThreshold",
            type = EntryType.DOUBLE,
            translation = "text.resourcefulconfig.ad_astra.option.vehicles.fallingExplosionThreshold"
    )
    @Comment(value = "The velocity to trigger a vehicle explosion while falling.", translation = "text.resourcefulconfig.ad_astra.option.vehicles.fallingExplosionThreshold.tooltip")
    public static double fallingExplosionThreshold = -1.2;

    @ConfigEntry(
            id = "fallingExplosionMultiplier",
            type = EntryType.FLOAT,
            translation = "text.resourcefulconfig.ad_astra.option.vehicles.fallingExplosionMultiplier"
    )
    @Comment(value = "How much the explosion should be multiplied by when the vehicle has fallen.", translation = "text.resourcefulconfig.ad_astra.option.vehicles.fallingExplosionMultiplier.tooltip")
    public static float fallingExplosionMultiplier = 0.7f;

    @ConfigEntry(
            id = "gravity",
            type = EntryType.DOUBLE,
            translation = "text.resourcefulconfig.ad_astra.option.vehicles.gravity"
    )
    public static double gravity = -2.0;

    @ConfigEntry(
            id = "moveCameraInVehicle",
            type = EntryType.BOOLEAN,
            translation = "text.resourcefulconfig.ad_astra.option.vehicles.moveCameraInVehicle"
    )
    @Comment(value = "Should the camera move in 3rd person in the rocket and lander for a better view?", translation = "text.resourcefulconfig.ad_astra.option.vehicles.moveCameraInVehicle.tooltip")
    public static boolean moveCameraInVehicle = true;

    @Category(id = "rocket", translation = "text.resourcefulconfig.ad_astra.option.rocket")
    public static final class RocketConfig {

        @ConfigEntry(
                id = "atmosphereLeave",
                type = EntryType.INTEGER,
                translation = "text.resourcefulconfig.ad_astra.option.rocket.atmosphereLeave"
        )
        public static int atmosphereLeave = 600;

        @ConfigEntry(
                id = "countDownTicks",
                type = EntryType.INTEGER,
                translation = "text.resourcefulconfig.ad_astra.option.rocket.countDownTicks"
        )
        public static int countDownTicks = 200;

        @ConfigEntry(
                id = "acceleration",
                type = EntryType.DOUBLE,
                translation = "text.resourcefulconfig.ad_astra.option.rocket.acceleration"
        )
        public static double acceleration = 0.005;

        @ConfigEntry(
                id = "maxSpeed",
                type = EntryType.DOUBLE,
                translation = "text.resourcefulconfig.ad_astra.option.rocket.maxSpeed"
        )
        public static double maxSpeed = 0.5;

        @ConfigEntry(
                id = "entitiesBurnUnderRocket",
                type = EntryType.BOOLEAN,
                translation = "text.resourcefulconfig.ad_astra.option.rocket.entitiesBurnUnderRocket"
        )
        public static boolean entitiesBurnUnderRocket = true;

        @ConfigEntry(
                id = "tankSize",
                type = EntryType.LONG,
                translation = "text.resourcefulconfig.ad_astra.option.rocket.tankSize"
        )
        public static long tankSize = FluidHooks.buckets(3f);

        @ConfigEntry(
                id = "fuelLaunchCost",
                type = EntryType.LONG,
                translation = "text.resourcefulconfig.ad_astra.option.rocket.fuelLaunchCost"
        )
        public static long fuelLaunchCost = FluidHooks.buckets(3f);

        @ConfigEntry(
                id = "efficientFuelLaunchCost",
                type = EntryType.LONG,
                translation = "text.resourcefulconfig.ad_astra.option.rocket.efficientFuelLaunchCost"
        )
        public static long efficientFuelLaunchCost = FluidHooks.buckets(1f);
    }

    @Category(id = "rover", translation = "text.resourcefulconfig.ad_astra.option.rover")
    public static final class RoverConfig {

        @ConfigEntry(
                id = "explodeRoverInLava",
                type = EntryType.BOOLEAN,
                translation = "text.resourcefulconfig.ad_astra.option.rover.explodeRoverInLava"
        )
        public static boolean explodeRoverInLava = true;

        @ConfigEntry(
                id = "fuelPerSecond",
                type = EntryType.LONG,
                translation = "text.resourcefulconfig.ad_astra.option.rover.fuelPerSecond"
        )
        public static long fuelPerSecond = FluidHooks.buckets(1f) / 100;

        @ConfigEntry(
                id = "turnSpeed",
                type = EntryType.FLOAT,
                translation = "text.resourcefulconfig.ad_astra.option.rover.turnSpeed"
        )
        public static float turnSpeed = 3.0f;

        @ConfigEntry(
                id = "maxTurnSpeed",
                type = EntryType.FLOAT,
                translation = "text.resourcefulconfig.ad_astra.option.rover.maxTurnSpeed"
        )
        public static float maxTurnSpeed = 6.0f;

        @ConfigEntry(
                id = "deceleration",
                type = EntryType.FLOAT,
                translation = "text.resourcefulconfig.ad_astra.option.rover.deceleration"
        )
        public static float deceleration = 0.9f;

        @ConfigEntry(
                id = "minSpeed",
                type = EntryType.FLOAT,
                translation = "text.resourcefulconfig.ad_astra.option.rover.minSpeed"
        )
        public static float minSpeed = -0.2f;

        @ConfigEntry(
                id = "maxSpeed",
                type = EntryType.FLOAT,
                translation = "text.resourcefulconfig.ad_astra.option.rover.maxSpeed"
        )
        public static float maxSpeed = 0.3f;

        @ConfigEntry(
                id = "tankSize",
                type = EntryType.LONG,
                translation = "text.resourcefulconfig.ad_astra.option.rover.tankSize"
        )
        public static long tankSize = FluidHooks.buckets(3);
    }

    @Category(id = "lander", translation = "text.resourcefulconfig.ad_astra.option.lander")
    public static final class LanderConfig {

        @ConfigEntry(
                id = "boosterThreshold",
                type = EntryType.DOUBLE,
                translation = "text.resourcefulconfig.ad_astra.option.lander.boosterThreshold"
        )
        public static double boosterThreshold = -0.1;

        @ConfigEntry(
                id = "boosterSpeed",
                type = EntryType.DOUBLE,
                translation = "text.resourcefulconfig.ad_astra.option.lander.boosterSpeed"
        )
        public static double boosterSpeed = 0.1;
    }
}