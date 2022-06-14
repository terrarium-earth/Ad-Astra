package com.github.alexnijjar.beyond_earth.util;

import java.util.LinkedList;
import java.util.List;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.client.BeyondEarthClient;
import com.github.alexnijjar.beyond_earth.data.Planet;
import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.armour.JetSuit;
import com.github.alexnijjar.beyond_earth.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.networking.ModS2CPackets;
import com.github.alexnijjar.beyond_earth.registry.ModEntities;
import com.github.alexnijjar.beyond_earth.world.SoundUtil;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

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

    // Real gravity.
    public static final float ORBIT_TRUE_GRAVITY = 0.0f;
    public static final float ORBIT_TEMPERATURE = -270.0f;
    // Simulated gravity.
    public static final float ORBIT_GRAVITY = 0.32f;

    public static boolean modLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    // Server.
    public static void teleportToWorld(RegistryKey<World> world, Entity entity, boolean spawnLander) {
        if (entity.getWorld() instanceof ServerWorld entityWorld && entity.canUsePortals()) {

            Entity vehicle = entity.getVehicle();
            Entity landerRider = null;
            if (entity instanceof LanderEntity lander) {
                landerRider = lander.getFirstPassenger();
            }

            int spawnStart = 450;
            for (Planet planet : BeyondEarth.planets) {
                if (planet.world().equals(world)) {
                    spawnStart = planet.atmosphereStart();
                }
            }

            Vec3d newPos = new Vec3d(entity.getX(), spawnStart, entity.getZ());
            TeleportTarget target = new TeleportTarget(newPos, entity.getVelocity(), entity.getYaw(), entity.getPitch());

            // Change the "earth" registry key to the "overworld" registry key.
            if (world.getValue().equals(new ModIdentifier("earth"))) {
                world = World.OVERWORLD;
            }

            ServerWorld targetWorld = entityWorld.getServer().getWorld(world);
            entity = FabricDimensions.teleport(entity, targetWorld, target);
            if (entity instanceof PlayerEntity player) {
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBoolean(true);
                ServerPlayNetworking.send((ServerPlayerEntity) player, ModS2CPackets.PORTAL_SOUND, buf);

                if (vehicle instanceof RocketEntity) {
                    if (spawnLander) {

                        // Delete the rocket.
                        vehicle.remove(RemovalReason.DISCARDED);

                        LanderEntity lander = new LanderEntity(ModEntities.LANDER, targetWorld);

                        lander.setPosition(player.getPos());
                        targetWorld.spawnEntity(lander);
                        player.sendMessage(Text.translatable("message." + BeyondEarth.MOD_ID + ".hold_space"), false);

                        player.startRiding(lander);
                    }
                }
            }

            if (landerRider != null) {
                teleportToWorld(world, landerRider, false);
                landerRider.startRiding(entity);
            }

            cookFood(entity);
        }

        if (entity instanceof PlayerEntity && entity.getWorld().isClient) {
            SoundUtil.setSound(true);
        }
    }

    public static void cookFood(Entity entity) {
        if (entity instanceof ItemEntity item) {
            ItemStack stack = item.getStack();
            ItemStack foodOutput = null;

            for (SmokingRecipe recipe : entity.getWorld().getRecipeManager().listAllOfType(RecipeType.SMOKING)) {
                for (Ingredient ingredient : recipe.getIngredients()) {
                    if (ingredient.test(stack)) {
                        foodOutput = recipe.getOutput();
                    }
                }
            }

            if (foodOutput != null) {
                item.setStack(new ItemStack(foodOutput.getItem(), stack.getCount()));
            }
        }
    }

    public static RegistryKey<World> getPlanetOrbit(World world) {
        if (isSpaceWorld(world)) {
            for (Planet planet : (world.isClient ? BeyondEarthClient.planets : BeyondEarth.planets)) {
                if (planet.orbitWorld().equals(world.getRegistryKey())) {
                    return planet.world();
                }
            }
            BeyondEarth.LOGGER.error(world.getRegistryKey().getValue().toString() + " does not have an orbit world!");
        } else {
            BeyondEarth.LOGGER.error(world.getRegistryKey().getValue().toString() + " is not a planet or orbit!");
        }
        return World.OVERWORLD;
    }

    public static float getPlanetGravity(World world) {
        for (Planet planet : world.isClient ? BeyondEarthClient.planets : BeyondEarth.planets) {
            if (planet.world().equals(world.getRegistryKey())) {
                return planet.gravity() / VANILLA_GRAVITY;
            }
        }
        // Orbit gravity is 0.25, allowing for slow fall.
        if (isOrbitWorld(world)) {
            return ORBIT_GRAVITY;
        }
        if (isSpaceWorld(world)) {
            BeyondEarth.LOGGER.error(world.getRegistryKey().getValue().toString() + " does not have a defined gravity!");
        }
        return 1.0f;
    }

    public static boolean isOrbitWorld(World world) {
        return getOrbitWorlds(world.isClient).stream().anyMatch(world.getRegistryKey()::equals);
    }

    public static boolean isPlanet(World world) {
        return getPlanets(world.isClient).stream().anyMatch(world.getRegistryKey()::equals);
    }

    public static boolean isSpaceWorld(World world) {
        return isPlanet(world) || isOrbitWorld(world);
    }

    public static final float getWorldTemperature(World world) {
        for (Planet planet : world.isClient ? BeyondEarthClient.planets : BeyondEarth.planets) {
            if (planet.world().equals(world.getRegistryKey())) {
                return planet.temperature();
            }
        }
        if (isOrbitWorld(world)) {
            return ORBIT_TEMPERATURE;
        }
        if (isSpaceWorld(world)) {
            BeyondEarth.LOGGER.error(world.getRegistryKey().getValue().toString() + " does not have a defined temperature!");
        }
        return 20.0f;
    }

    public static boolean worldHasOxygen(World world) {
        // Make all worlds that don't have a defined oxygen, like vanilla and modded dimensions have oxygen.
        boolean worldIsDefinedInDataPack = false;
        for (Planet planet : world.isClient ? BeyondEarthClient.planets : BeyondEarth.planets) {
            if (planet.world().equals(world.getRegistryKey())) {
                worldIsDefinedInDataPack = true;
                break;
            }
        }
        if (!worldIsDefinedInDataPack) {
            return true;
        }
        return getWorldsWithoutOxygen(world.isClient).stream().anyMatch(world.getRegistryKey()::equals);
    }

    // Mixin Util.
    public static double getMixinGravity(double value, Object mixin) {
        Entity entity = (Entity) mixin;
        return value * getPlanetGravity(entity.world);
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

    public static boolean hasFullSpaceSet(LivingEntity entity) {
        for (ItemStack armourItem : entity.getArmorItems()) {
            if (!(armourItem.getItem() instanceof SpaceSuit)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasFullNetheriteSpaceSet(LivingEntity entity) {
        for (ItemStack armourItem : entity.getArmorItems()) {
            if (!(armourItem.getItem() instanceof NetheriteSpaceSuit)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasFullJetSuitSet(LivingEntity entity) {
        for (ItemStack armourItem : entity.getArmorItems()) {
            if (!(armourItem.getItem() instanceof JetSuit)) {
                return false;
            }
        }
        return true;
    }

    public static boolean checkTag(Entity entity, TagKey<EntityType<?>> tag) {
        return entity.getType().isIn(tag);
    }

    public static boolean checkTag(ItemStack stack, TagKey<Item> tag) {
        return stack.isIn(tag);
    }

    public static <T extends ParticleEffect> void spawnForcedParticles(ServerWorld world, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            world.spawnParticles(player, particle, true, x, y, z, count, deltaX, deltaY, deltaZ, speed);
        }
    }

    private static final List<RegistryKey<World>> getOrbitWorlds(boolean isClient) {
        List<RegistryKey<World>> worlds = new LinkedList<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> {
            if (!worlds.contains(planet.orbitWorld()))
                worlds.add(planet.orbitWorld());
        });
        return worlds;
    }

    private static final List<RegistryKey<World>> getPlanets(boolean isClient) {
        List<RegistryKey<World>> worlds = new LinkedList<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> worlds.add(planet.world()));
        return worlds;
    }

    private static final List<RegistryKey<World>> getWorldsWithoutOxygen(boolean isClient) {
        List<RegistryKey<World>> worlds = new LinkedList<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> {
            if (planet.hasOxygen()) {
                worlds.add(planet.world());
            }
        });
        return worlds;
    }

    public static void rotateVehicleYaw(VehicleEntity vehicle, float newYaw) {
        vehicle.setYaw(newYaw);
        vehicle.setBodyYaw(newYaw);
        vehicle.prevYaw = newYaw;
    }

    public static long dropletsToMillibuckets(long droplets) {
        return droplets / 81;
    }
}