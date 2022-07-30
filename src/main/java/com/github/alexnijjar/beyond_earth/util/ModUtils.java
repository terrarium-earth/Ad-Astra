package com.github.alexnijjar.beyond_earth.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.advancement.ModCriteria;
import com.github.alexnijjar.beyond_earth.client.BeyondEarthClient;
import com.github.alexnijjar.beyond_earth.data.Planet;
import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.VehicleEntity;
import com.github.alexnijjar.beyond_earth.items.vehicles.VehicleItem;
import com.github.alexnijjar.beyond_earth.registry.ModEntityTypes;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

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

    public static final float VANILLA_GRAVITY = 9.806f;
    public static final float ORBIT_TEMPERATURE = -270.0f;
    public static final float ORBIT_GRAVITY = 0.32f;

    public static boolean modLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    /**
     * Teleports an entity to a different dimension. If the entity is a player in a rocket, the player will teleport with a lander.
     * If the entity is raw food, the food will be cooked.
     * 
     * @param targetWorld The world to the entity teleport to
     * @param entity      The entity to teleport
     * @see #teleportPlayer(RegistryKey, ServerPlayerEntity)
     */
    public static void teleportToWorld(RegistryKey<World> targetWorld, Entity entity) {
        if (entity.getWorld() instanceof ServerWorld entityWorld) {
            ServerWorld world = entityWorld.getServer().getWorld(targetWorld);
            Vec3d targetPosition = new Vec3d(entity.getPos().getX(), getSpawnStart(world), entity.getZ());
            List<Entity> entitiesToTeleport = new LinkedList<>();

            if (entity instanceof PlayerEntity player) {
                if (player.getVehicle() instanceof RocketEntity rocket) {
                    player.sendMessage(new TranslatableText("message." + BeyondEarth.MOD_ID + ".hold_space"), false);
                    entity = createLander(rocket, world, targetPosition);
                    targetPosition = new Vec3d(rocket.getBlockPos().getX() - 1.5f, getSpawnStart(rocket.world), rocket.getBlockPos().getZ() + 0.3f);
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
                teleportedEntities.add(FabricDimensions.teleport(entityToTeleport, world, target));
            }

            if (!teleportedEntities.isEmpty()) {
                entity = teleportedEntities.get(0);
                for (int i = 1; i < teleportedEntities.size(); i++) {
                    teleportedEntities.get(i).startRiding(entity);
                }
            }
        }
    }

    /**
     * A simplified version of {@link #teleportToWorld(RegistryKey, World)} for teleporting players.
     * 
     * @param targetWorld The world to teleport the player at
     * @param player      The player to teleport
     */
    public static void teleportPlayer(RegistryKey<World> targetWorld, ServerPlayerEntity player) {
        ServerWorld world = player.getServer().getWorld(targetWorld);
        Vec3d targetPosition = new Vec3d(player.getX(), getSpawnStart(world), player.getZ());
        TeleportTarget target = new TeleportTarget(targetPosition, player.getVelocity(), player.getYaw(), player.getPitch());
        player = FabricDimensions.teleport(player, world, target);
    }

    /**
     * Spawns a lander in a target world and position.
     * 
     * @param rocket         The rocket to create a lander from
     * @param targetWorld    The world to spawn the lander in
     * @param targetPosition The position to spawn the lander at
     * @return A spawned lander entity at the same position as the rocket and with the same inventory
     */
    public static LanderEntity createLander(RocketEntity rocket, ServerWorld targetWorld, Vec3d targetPosition) {
        LanderEntity lander = new LanderEntity(ModEntityTypes.LANDER, targetWorld);
        lander.setPosition(new Vec3d(rocket.getBlockPos().getX(), getSpawnStart(rocket.world), rocket.getBlockPos().getZ()));
        for (int i = 0; i < rocket.getInventorySize(); i++) {
            lander.getInventory().setStack(i, rocket.getInventory().getStack(i));
        }
        ItemStack stack = rocket.getDropStack();
        ((VehicleItem) stack.getItem()).setFluid(stack, rocket.getFluidVariant());
        ((VehicleItem) stack.getItem()).setAmount(stack, rocket.getFluidAmount());
        lander.getInventory().setStack(10, stack);
        targetWorld.spawnEntity(lander);
        return lander;
    }

    /**
     * Gets the cooked variant of a raw food, if it exists, and then spawns the item entity. The cooked variant is obtained by using
     * a smoking recipe, and then obtaining the result of that recipe.
     * 
     * @param itemEntity The item to try to convert into cooked food
     */
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
            ServerPlayerEntity playerEntity = (ServerPlayerEntity) itemEntity.world.getPlayerByUuid(itemEntity.getThrower());
            if (playerEntity != null) {
                ModCriteria.FOOD_COOKED_IN_ATMOSPHERE.trigger(playerEntity);
            }
        }
    }

    /**
     * Gets the world's orbit dimension. The orbit dimension is where the planet's space station spawns and where the lander drops.
     * 
     * @return The world's orbit dimension, or the overworld if no orbit is defined
     */
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

    /**
     * Gets the gravity of the world, in ratio to earth gravity. So a gravity of 1.0 is equivalent to earth gravity, while 0.5 would
     * be half of earth's gravity and 2.0 would be twice the earth's gravity.
     * 
     * @return The gravity of the world or earth gravity if the world does not have a defined gravity
     */
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

    /**
     * Gets the spawn start of the world. The spawn start is the position that the player will spawn at after falling through an
     * orbit dimension.
     * 
     * @return The spawn start of the world, or y 450 if the world does not have a defined spawn start
     */
    public static int getSpawnStart(World world) {
        int spawnStart = 450;
        for (Planet planet : world.isClient ? BeyondEarthClient.planets : BeyondEarth.planets) {
            if (planet.world().equals(world.getRegistryKey())) {
                spawnStart = planet.atmosphereStart();
            }
        }
        return spawnStart;
    }

    /**
     * Gets the temperature of the world in celsius.
     * 
     * @return The temperature of the world, or 20° for dimensions without a defined temperature
     */
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

    // Mixin Util
    public static double getMixinGravity(double value, Object mixin) {
        Entity entity = (Entity) mixin;
        return value * getPlanetGravity(entity.world);
    }

    public static float getMixinGravity(float value, Object mixin) {
        return (float) getMixinGravity((double) value, mixin);
    }

    /**
     * Gets every Beyond Earth dimension, regardless of whether it is a planet or an orbit.
     */
    public static Set<RegistryKey<World>> getBeyondEarthDimensions(boolean isClient) {
        return Stream.concat(getPlanets(isClient).stream(), getOrbitWorlds(isClient).stream()).collect(Collectors.toSet());
    }

    /**
     * Checks if the world is labeled as an orbit dimension.
     */
    public static boolean isOrbitWorld(World world) {
        return getOrbitWorlds(world.isClient).stream().anyMatch(world.getRegistryKey()::equals);
    }

    /**
     * Gets all of the dimensions that are considered orbit worlds.
     */
    public static Set<RegistryKey<World>> getOrbitWorlds(boolean isClient) {
        Set<RegistryKey<World>> worlds = new HashSet<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> {
            if (!worlds.contains(planet.orbitWorld()))
                worlds.add(planet.orbitWorld());
        });
        return worlds;
    }

    /**
     * Check if the world is labeled as a planet dimension.
     */
    public static boolean isPlanet(World world) {
        return getPlanets(world.isClient).stream().anyMatch(world.getRegistryKey()::equals);
    }

    /**
     * Gets all of the dimensions that are considered planets or moons, i.e. a dimension from this mod that is not an orbit world.
     */
    public static Set<RegistryKey<World>> getPlanets(boolean isClient) {
        Set<RegistryKey<World>> worlds = new HashSet<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).forEach(planet -> worlds.add(planet.world()));
        return worlds;
    }

    /**
     * Checks if the world is either a planet or an orbit world.
     */
    public static boolean isSpaceWorld(World world) {
        return isPlanet(world) || isOrbitWorld(world);
    }

    /**
     * Gets all of the dimensions in Beyond Earth that have no oxygen.
     */
    public static final Set<RegistryKey<World>> getWorldsWithoutOxygen(boolean isClient) {
        Set<RegistryKey<World>> worlds = new HashSet<>();
        (isClient ? BeyondEarthClient.planets : BeyondEarth.planets).stream().filter(planet -> planet.hasOxygen()).forEach(planet -> worlds.add(planet.world()));
        return worlds;
    }

    /**
     * Spawns a server-side particle that renders regardless of the distance away from the player. This is important as normal
     * particles are only rendered at up to 32 blocks away.
     */
    public static <T extends ParticleEffect> void spawnForcedParticles(ServerWorld world, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        for (ServerPlayerEntity player : world.getPlayers()) {
            world.spawnParticles(player, particle, true, x, y, z, count, deltaX, deltaY, deltaZ, speed);
        }
    }

    /**
     * Rotates the vehicle yaw without causing any stuttering effect or visual glitches.
     * 
     * @param vehicle The vehicle to apply the rotation
     * @param newYaw  The new yaw to apply to the vehicle
     */
    public static void rotateVehicleYaw(VehicleEntity vehicle, float newYaw) {
        vehicle.setYaw(newYaw);
        vehicle.setBodyYaw(newYaw);
        vehicle.prevYaw = newYaw;
    }

    public static boolean checkTag(Entity entity, TagKey<EntityType<?>> tag) {
        return entity.getType().isIn(tag);
    }

    public static boolean checkTag(ItemStack stack, TagKey<Item> tag) {
        return stack.isIn(tag);
    }
}