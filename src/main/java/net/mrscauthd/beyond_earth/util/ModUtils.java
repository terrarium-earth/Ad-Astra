package net.mrscauthd.beyond_earth.util;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.world.SoundUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ModUtils {
    public static final int SPAWN_START = 450;
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

    public static final Identifier PORTAL_SOUND_PACKET_ID = new ModIdentifier("portal_sound_packet");

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

    private static final List<RegistryKey<World>> planets = Arrays.asList(
            World.OVERWORLD,
            MOON_KEY,
            MARS_KEY,
            VENUS_KEY,
            MERCURY_KEY,
            GLACIO_KEY
    );

    private static final List<RegistryKey<World>> orbitDimensions = Arrays.asList(
            EARTH_ORBIT_KEY,
            MOON_ORBIT_KEY,
            MARS_ORBIT_KEY,
            VENUS_ORBIT_KEY,
            MERCURY_ORBIT_KEY,
            GLACIO_ORBIT_KEY
    );

    public static void teleportToPlanet(RegistryKey<World> dimension, Entity entity) {
        if (!entity.getWorld().isClient && entity.canUsePortals()) {
            ServerWorld destination = Objects.requireNonNull(entity.getServer()).getWorld(getCorrespondingOrbitDimension(dimension));

            Vec3d newPos = new Vec3d(entity.getX(), ModUtils.SPAWN_START, entity.getZ());
            TeleportTarget target = new TeleportTarget(newPos, entity.getVelocity(), entity.getYaw(), entity.getPitch());

            entity = FabricDimensions.teleport(entity, destination, target);
            if (entity instanceof PlayerEntity player) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(true);
                ServerPlayNetworking.send((ServerPlayerEntity) player, PORTAL_SOUND_PACKET_ID, buf);
            }

            // Cook food.
            if (entity instanceof ItemEntity item) {
                ItemStack stack = item.getStack();
                if (stack.getItem().equals(Items.CHICKEN)) {
                    item.setStack(new ItemStack(Items.COOKED_CHICKEN, stack.getCount()));
                } else if (stack.getItem().equals(Items.BEEF)) {
                    item.setStack(new ItemStack(Items.COOKED_BEEF, stack.getCount()));
                } else if (stack.getItem().equals(Items.PORKCHOP)) {
                    item.setStack(new ItemStack(Items.COOKED_PORKCHOP, stack.getCount()));
                } else if (stack.getItem().equals(Items.MUTTON)) {
                    item.setStack(new ItemStack(Items.COOKED_MUTTON, stack.getCount()));
                } else if (stack.getItem().equals(Items.RABBIT)) {
                    item.setStack(new ItemStack(Items.COOKED_RABBIT, stack.getCount()));
                } else if (stack.getItem().equals(Items.COD)) {
                    item.setStack(new ItemStack(Items.COOKED_COD, stack.getCount()));
                } else if (stack.getItem().equals(Items.SALMON)) {
                    item.setStack(new ItemStack(Items.COOKED_SALMON, stack.getCount()));
                }
            }
        }

        if (entity.getWorld().isClient && entity instanceof PlayerEntity) {
            SoundUtil.setSound(true);
        }
    }

    public static boolean isOrbitDimension(RegistryKey<World> dimension) {
        return orbitDimensions.stream().anyMatch(dimension::equals);
    }

    public static boolean isPlanet(RegistryKey<World> dimension) {
        return planets.stream().anyMatch(dimension::equals);
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
            BeyondEarth.LOGGER.error("Not an orbit dimension!");
            return World.OVERWORLD;
        }
    }

    public static boolean dimensionHasOxygen(World world) {
        return dimensionsWithoutOxygen.stream().noneMatch(world.getRegistryKey()::equals);
    }
}
