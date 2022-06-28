package com.github.alexnijjar.beyond_earth.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.client.BeyondEarthClient;
import com.github.alexnijjar.beyond_earth.client.utils.ClientOxygenUtils;
import com.github.alexnijjar.beyond_earth.data.Planet;
import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.armour.JetSuit;
import com.github.alexnijjar.beyond_earth.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
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

    public static final float ORBIT_TEMPERATURE = -270.0f;
    public static final float ORBIT_GRAVITY = 0.32f;

    public static boolean modLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    public static void teleportToWorld(RegistryKey<World> world, Entity entity) {
        if (entity.getWorld() instanceof ServerWorld entityWorld) {
            ServerWorld targetWorld = entityWorld.getServer().getWorld(world);
            Vec3d targetPosition = new Vec3d(entity.getX(), getSpawnStart(targetWorld), entity.getZ());
            List<Entity> entitiesToTeleport = new LinkedList<>();

            if (entity instanceof PlayerEntity player) {
                if (player.getVehicle() instanceof RocketEntity rocket) {
                    player.sendMessage(Text.translatable("message." + BeyondEarth.MOD_ID + ".hold_space"), false);
                    entity = createLander(rocket, targetWorld, targetPosition);
                    rocket.remove(RemovalReason.DISCARDED);
                    entitiesToTeleport.add(entity);
                    entitiesToTeleport.add(player);
                } else if (!(player.getVehicle() instanceof LanderEntity)) {
                    entitiesToTeleport.add(entity);
                }
            } else {
                entitiesToTeleport.add(entity);
            }

            if (entity instanceof ItemEntity itemEntity) {
                cookFood(itemEntity);
            }

            entitiesToTeleport.addAll(entity.getPassengerList());

            List<Entity> teleportedEntities = new LinkedList<>();
            for (Entity entityToTeleport : entitiesToTeleport) {
                TeleportTarget target = new TeleportTarget(targetPosition, entityToTeleport.getVelocity(), entityToTeleport.getYaw(), entityToTeleport.getPitch());
                teleportedEntities.add(FabricDimensions.teleport(entityToTeleport, targetWorld, target));
            }

            if (!teleportedEntities.isEmpty()) {
                entity = teleportedEntities.get(0);
                for (int i = 1; i < teleportedEntities.size(); i++) {
                    teleportedEntities.get(i).startRiding(entity);
                }
            }
        }
    }

    public static void teleportPlayer(RegistryKey<World> world, ServerPlayerEntity player) {
        ServerWorld targetWorld = player.getServer().getWorld(world);
        Vec3d targetPosition = new Vec3d(player.getX(), getSpawnStart(targetWorld), player.getZ());
        TeleportTarget target = new TeleportTarget(targetPosition, player.getVelocity(), player.getYaw(), player.getPitch());
        player = FabricDimensions.teleport(player, targetWorld, target);
    }

    public static LanderEntity createLander(RocketEntity rocket, ServerWorld targetWorld, Vec3d targetPosition) {
        LanderEntity lander = new LanderEntity(ModEntityTypes.LANDER, targetWorld);
        lander.setPosition(targetPosition);
        for (int i = 0; i < rocket.getInventorySize(); i++) {
            lander.getInventory().setStack(i, rocket.getInventory().getStack(i));
        }
        lander.getInventory().setStack(10, rocket.getDropStack());
        targetWorld.spawnEntity(lander);
        return lander;
    }

    public static void cookFood(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getStack();
        ItemStack foodOutput = null;

        for (SmokingRecipe recipe : itemEntity.getWorld().getRecipeManager().listAllOfType(RecipeType.SMOKING)) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient.test(stack)) {
                    foodOutput = recipe.getOutput();
                }
            }
        }

        if (foodOutput != null) {
            itemEntity.setStack(new ItemStack(foodOutput.getItem(), stack.getCount()));
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

    public static int getSpawnStart(World world) {
        int spawnStart = 450;
        for (Planet planet : world.isClient ? BeyondEarthClient.planets : BeyondEarth.planets) {
            if (planet.world().equals(world.getRegistryKey())) {
                spawnStart = planet.atmosphereStart();
            }
        }
        return spawnStart;
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
        if (getBeyondEarthDimensions(world.isClient).stream().noneMatch(world.getRegistryKey()::equals)) {
            return true;
        }
        if (getOrbitWorlds(world.isClient).stream().anyMatch(world.getRegistryKey()::equals)) {
            return false;
        }
        return getWorldsWithoutOxygen(world.isClient).stream().anyMatch(world.getRegistryKey()::equals);
    }

    public static boolean worldHasOxygen(World world, LivingEntity entity) {
        return worldHasOxygen(world, entity.getBlockPos().up());
    }

    public static boolean worldHasOxygen(World world, BlockPos pos) {
        boolean hasOxygen = worldHasOxygen(world);
        if (world.isClient) {
            return ClientOxygenUtils.posHasOxygen((ClientWorld) world, pos) || hasOxygen;
        } else {
            return OxygenUtils.posHasOxygen((ServerWorld) world, pos) || hasOxygen;
        }
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
        if (chest.getItem() instanceof SpaceSuit suit) {
            return suit.getAmount(chest) > 0;
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

    public static Set<RegistryKey<World>> getBeyondEarthDimensions(boolean isClient) {
        return Stream.concat(getPlanets(isClient).stream(), getOrbitWorlds(isClient).stream()).collect(Collectors.toSet());
    }

    public static Set<RegistryKey<World>> getOrbitWorlds(boolean isClient) {
        Set<RegistryKey<World>> worlds = new HashSet<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> {
            if (!worlds.contains(planet.orbitWorld()))
                worlds.add(planet.orbitWorld());
        });
        return worlds;
    }

    public static Set<RegistryKey<World>> getPlanets(boolean isClient) {
        Set<RegistryKey<World>> worlds = new HashSet<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> worlds.add(planet.world()));
        return worlds;
    }

    private static final Set<RegistryKey<World>> getWorldsWithoutOxygen(boolean isClient) {
        Set<RegistryKey<World>> worlds = new HashSet<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).stream().filter(planet -> planet.hasOxygen()).forEach(planet -> worlds.add(planet.world()));
        return worlds;
    }

    public static void rotateVehicleYaw(VehicleEntity vehicle, float newYaw) {
        vehicle.setYaw(newYaw);
        vehicle.setBodyYaw(newYaw);
        vehicle.prevYaw = newYaw;
    }
}