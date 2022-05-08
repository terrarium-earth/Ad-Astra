package net.mrscauthd.beyond_earth.util;

import java.util.LinkedList;
import java.util.List;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
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
import net.mrscauthd.beyond_earth.client.BeyondEarthClient;
import net.mrscauthd.beyond_earth.data.Planet;
import net.mrscauthd.beyond_earth.items.armour.NetheriteSpaceSuit;
import net.mrscauthd.beyond_earth.items.armour.SpaceSuit;
import net.mrscauthd.beyond_earth.networking.ModS2CPackets;
import net.mrscauthd.beyond_earth.world.SoundUtil;

public class ModUtils {

    public static final float VANILLA_GRAVITY = 9.806f;

    public static final Identifier EARTH_ORBIT = new ModIdentifier("earth_orbit");
    public static final Identifier MOON = new ModIdentifier("moon");
    public static final Identifier MOON_ORBIT = new ModIdentifier("moon_orbit");
    public static final Identifier MARS = new ModIdentifier("mars");
    public static final Identifier MARS_ORBIT = new ModIdentifier("mars_orbit");
    public static final Identifier VENUS = new ModIdentifier("venus");
    public static final Identifier VENUS_ORBIT = new ModIdentifier("venus_orbit");
    public static final Identifier MERCURY = new ModIdentifier("mercury");
    public static final Identifier MERCURY_ORBIT = new ModIdentifier("mercury_orbit");
    public static final Identifier GLACIO = new ModIdentifier("glacio");
    public static final Identifier GLACIO_ORBIT = new ModIdentifier("glacio_orbit");

    public static final RegistryKey<World> EARTH_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, EARTH_ORBIT);
    public static final RegistryKey<World> MOON_KEY = RegistryKey.of(Registry.WORLD_KEY, MOON);
    public static final RegistryKey<World> MOON_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, MOON_ORBIT);
    public static final RegistryKey<World> MARS_KEY = RegistryKey.of(Registry.WORLD_KEY, MARS);
    public static final RegistryKey<World> MARS_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, MARS_ORBIT);
    public static final RegistryKey<World> VENUS_KEY = RegistryKey.of(Registry.WORLD_KEY, VENUS);
    public static final RegistryKey<World> VENUS_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, VENUS_ORBIT);
    public static final RegistryKey<World> MERCURY_KEY = RegistryKey.of(Registry.WORLD_KEY, MERCURY);
    public static final RegistryKey<World> MERCURY_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, MERCURY_ORBIT);
    public static final RegistryKey<World> GLACIO_KEY = RegistryKey.of(Registry.WORLD_KEY, GLACIO);
    public static final RegistryKey<World> GLACIO_ORBIT_KEY = RegistryKey.of(Registry.WORLD_KEY, GLACIO_ORBIT);

    public static final float ORBIT_TRUE_GRAVITY = 0.0f;
    public static final float ORBIT_TEMPERATURE = -270.0f;
    public static final float ORBIT_GRAVITY = 0.32f;

    public static boolean modLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    // Server.
    public static void teleportToWorld(RegistryKey<World> world, Entity entity) {
        if (entity.getWorld() instanceof ServerWorld entityWorld && entity.canUsePortals()) {

            int spawnStart = 450;
            for (Planet planet : BeyondEarth.planets) {
                if (planet.dimension().equals(world)) {
                    spawnStart = planet.atmosphereStart();
                }
            }

            Vec3d newPos = new Vec3d(entity.getX(), spawnStart, entity.getZ());
            TeleportTarget target = new TeleportTarget(newPos, entity.getVelocity(), entity.getYaw(), entity.getPitch());

            // Change the "earth" registry key to the "overworld" registry key.
            if (world.getValue().equals(new ModIdentifier("earth"))) {
                world = World.OVERWORLD;
            }

            entity = FabricDimensions.teleport(entity, entityWorld.getServer().getWorld(world), target);
            if (entity instanceof PlayerEntity player) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(true);
                ServerPlayNetworking.send((ServerPlayerEntity) player, ModS2CPackets.PORTAL_SOUND_PACKET_ID, buf);
            }

            // Cook food.
            if (entity instanceof ItemEntity item) {
                ItemStack stack = item.getStack();
                Item stackItem = stack.getItem();
                if (stackItem.equals(Items.CHICKEN)) {
                    item.setStack(new ItemStack(Items.COOKED_CHICKEN, stack.getCount()));
                } else if (stackItem.equals(Items.BEEF)) {
                    item.setStack(new ItemStack(Items.COOKED_BEEF, stack.getCount()));
                } else if (stackItem.equals(Items.PORKCHOP)) {
                    item.setStack(new ItemStack(Items.COOKED_PORKCHOP, stack.getCount()));
                } else if (stackItem.equals(Items.MUTTON)) {
                    item.setStack(new ItemStack(Items.COOKED_MUTTON, stack.getCount()));
                } else if (stackItem.equals(Items.RABBIT)) {
                    item.setStack(new ItemStack(Items.COOKED_RABBIT, stack.getCount()));
                } else if (stackItem.equals(Items.COD)) {
                    item.setStack(new ItemStack(Items.COOKED_COD, stack.getCount()));
                } else if (stackItem.equals(Items.SALMON)) {
                    item.setStack(new ItemStack(Items.COOKED_SALMON, stack.getCount()));
                }
            }
        }

        if (entity instanceof PlayerEntity && entity.getWorld().isClient) {
            SoundUtil.setSound(true);
        }
    }

    // Client and server.
    public static RegistryKey<World> getPlanetOrbit(boolean isClient, RegistryKey<World> world) {
        if (isSpaceDimension(isClient, world)) {
            for (Planet planet : (isClient ? BeyondEarthClient.planets : BeyondEarth.planets)) {
                if (planet.orbitDimension().equals(world)) {
                    return planet.dimension();
                }
            }
            BeyondEarth.LOGGER.error(world.getValue().toString() + " does not have an orbit world!");
        } else {
            BeyondEarth.LOGGER.error(world.getValue().toString() + " is not a planet or orbit!");
        }
        return World.OVERWORLD;
    }

    public static float getPlanetGravity(boolean isClient, RegistryKey<World> world) {
        for (Planet planet : isClient ? BeyondEarthClient.planets : BeyondEarth.planets) {
            if (planet.dimension().equals(world)) {
                return planet.gravity() / VANILLA_GRAVITY;
            }
        }
        // Orbit gravity is 0.25, allowing for slow fall.
        if (isOrbitDimension(isClient, world)) {
            return ORBIT_GRAVITY;
        }
        if (isSpaceDimension(isClient, world)) {
            BeyondEarth.LOGGER.error(world.getValue().toString() + " does not have a defined gravity!");
        }
        return 1.0f;
    }

    public static final List<RegistryKey<World>> getOrbitDimensions(boolean isClient) {
        List<RegistryKey<World>> worlds = new LinkedList<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> {
            if (!worlds.contains(planet.orbitDimension()))
                worlds.add(planet.orbitDimension());
        });
        return worlds;
    }

    public static boolean isOrbitDimension(boolean isClient, RegistryKey<World> world) {
        return getOrbitDimensions(isClient).stream().anyMatch(world::equals);
    }

    public static final List<RegistryKey<World>> getPlanets(boolean isClient) {
        List<RegistryKey<World>> worlds = new LinkedList<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> worlds.add(planet.dimension()));
        return worlds;
    }

    public static boolean isPlanet(boolean isClient, RegistryKey<World> world) {
        return getPlanets(isClient).stream().anyMatch(world::equals);
    }

    public static boolean isSpaceDimension(boolean isClient, RegistryKey<World> world) {
        return isPlanet(isClient, world) || isOrbitDimension(isClient, world);
    }

    public static final List<RegistryKey<World>> getDimensionsWithoutOxygen(boolean isClient) {
        List<RegistryKey<World>> worlds = new LinkedList<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> {
            if (planet.hasOxygen()) {
                worlds.add(planet.dimension());
            }
        });
        return worlds;
    }

    public static final float getDimensionTemperature(boolean isClient, World world) {
        return getDimensionTemperature(isClient, world.getRegistryKey());
    }

    public static final float getDimensionTemperature(boolean isClient, RegistryKey<World> world) {
        for (Planet planet : isClient ? BeyondEarthClient.planets : BeyondEarth.planets) {
            if (planet.dimension().equals(world)) {
                return planet.temperature();
            }
        }
        if (isOrbitDimension(isClient, world)) {
            return ORBIT_TEMPERATURE;
        }
        if (isSpaceDimension(isClient, world)) {
            BeyondEarth.LOGGER.error(world.getValue().toString() + " does not have a defined temperature!");
        }
        return 20.0f;
    }

    public static boolean dimensionHasOxygen(boolean isClient, World world) {
        return dimensionHasOxygen(isClient, world.getRegistryKey());
    }

    public static boolean dimensionHasOxygen(boolean isClient, RegistryKey<World> world) {
        return getDimensionsWithoutOxygen(isClient).stream().anyMatch(world::equals);
    }

    // Mixin Util.
    public static double getMixinGravity(double value, Object mixin) {
        Entity entity = (Entity) mixin;
        return value * getPlanetGravity(false, entity.world.getRegistryKey());
    }

    public static float getMixinGravity(float value, Object mixin) {
        return (float) getMixinGravity((double) value, mixin);
    }

    public static boolean hasOxygenatedSpaceSuit(PlayerEntity player) {
        ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
        NbtCompound nbt = chest.getNbt();

        if (nbt.contains("Oxygen")) {
            if (nbt.getInt("Oxygen") > 0) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasFullSpaceSet(PlayerEntity player) {
        for (int i = 0; i < 4; i++) {
            if (!(player.getInventory().getArmorStack(i).getItem() instanceof SpaceSuit)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasFullNetheriteSpaceSet(PlayerEntity player) {
        for (int i = 0; i < 4; i++) {
            if (!(player.getInventory().getArmorStack(i).getItem() instanceof NetheriteSpaceSuit)) {
                return false;
            }
        }
        return true;
    }
}