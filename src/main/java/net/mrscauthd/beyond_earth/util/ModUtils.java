package net.mrscauthd.beyond_earth.util;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.BeyondEarth;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModUtils {

    public static final RegistryKey<World> EARTH_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("earth_orbit"));
    public static final RegistryKey<World> MOON_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("moon"));
    public static final RegistryKey<World> MOON_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("moon_orbit"));
    public static final RegistryKey<World> MARS_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("mars"));
    public static final RegistryKey<World> MARS_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("mars_orbit"));
    public static final RegistryKey<World> VENUS_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("venus"));
    public static final RegistryKey<World> VENUS_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("venus_orbit"));
    public static final RegistryKey<World> MERCURY_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("mercury"));
    public static final RegistryKey<World> MERCURY_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("mercury_orbit"));
    public static final RegistryKey<World> GLACIO_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("glacio"));
    public static final RegistryKey<World> GLACIO_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, new ModIdentifier("glacio_orbit"));

    private static final List<RegistryKey<World>> dimensionsWithoutOxygen = Arrays.asList(
            EARTH_ORBIT_KEY,
            MOON_KEY,
            MOON_ORBIT_KEY,
            MARS_KEY,
            MARS_ORBIT_KEY,
            VENUS_KEY,
            VENUS_ORBIT_KEY,
            MERCURY_KEY,
            MERCURY_ORBIT_KEY,
            GLACIO_ORBIT_KEY
    );


    public static void teleportToPlanet(RegistryKey<World> dimension, ServerPlayerEntity player) {
        if (player.canUsePortals()) {
            ServerWorld targetDimension = Objects.requireNonNull(player.getServer()).getWorld(getCorrespondingOrbitDimension(dimension));
            player.teleport(targetDimension, player.getX(), 450.0, player.getZ(), player.getYaw(), player.getPitch());
        }
    }

    public static boolean isOrbitDimension(RegistryKey<World> dimension) {
        return dimension.equals(EARTH_ORBIT_KEY) || dimension.equals(MOON_ORBIT_KEY) || dimension.equals(MARS_ORBIT_KEY) || dimension.equals(VENUS_ORBIT_KEY) || dimension.equals(MERCURY_ORBIT_KEY) || dimension.equals(GLACIO_ORBIT_KEY);
    }

    public static RegistryKey<World> getCorrespondingOrbitDimension(RegistryKey<World> dimension) {
        if (dimension.equals(EARTH_ORBIT_KEY)) {
            return World.OVERWORLD;
        } else if (dimension.equals(MOON_ORBIT_KEY)) {
            return MOON_KEY;
        } else if (dimension.equals(MARS_ORBIT_KEY)) {
            return MARS_KEY;
        } else if (dimension.equals(VENUS_ORBIT_KEY)) {
            return VENUS_KEY;
        } else if (dimension.equals(MERCURY_ORBIT_KEY)) {
            return MERCURY_KEY;
        } else if (dimension.equals(GLACIO_ORBIT_KEY)) {
            return GLACIO_KEY;
        } else {
            return World.OVERWORLD;
        }
    }

    public static boolean dimensionHasOxygen(World world) {
        return dimensionsWithoutOxygen.stream().noneMatch(world.getRegistryKey()::equals);
    }
}
