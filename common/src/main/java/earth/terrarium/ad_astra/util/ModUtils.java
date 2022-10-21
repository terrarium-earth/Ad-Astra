package earth.terrarium.ad_astra.util;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;
import java.util.stream.StreamSupport;

import dev.architectury.injectables.targets.ArchitecturyTarget;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.data.Planet;
import earth.terrarium.ad_astra.entities.vehicles.LanderEntity;
import earth.terrarium.ad_astra.entities.vehicles.RocketEntity;
import earth.terrarium.ad_astra.entities.vehicles.VehicleEntity;
import earth.terrarium.ad_astra.items.vehicles.VehicleItem;
import earth.terrarium.ad_astra.registry.ModCriteria;
import earth.terrarium.ad_astra.registry.ModEntityTypes;
import earth.terrarium.ad_astra.registry.ModTags;
import earth.terrarium.ad_astra.util.algorithms.LandFinder;

import com.mojang.serialization.Codec;
import dev.architectury.platform.Platform;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmokingRecipe;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.TagKey;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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

	public static boolean modLoaded(String modId) {
		return Platform.isModLoaded(modId);
	}

	/**
	 * Teleports an entity to a different dimension. If the entity is a player in a rocket, the player will teleport with a lander. If the entity is raw food, the food will be cooked.
	 *
	 * @param targetWorld The world to the entity teleport to
	 * @param entity      The entity to teleport
	 * @see #teleportPlayer(RegistryKey, ServerPlayerEntity)
	 */
	public static void teleportToWorld(RegistryKey<World> targetWorld, Entity entity) {
		if (entity.getWorld() instanceof ServerWorld oldWorld) {
			ServerWorld world = oldWorld.getServer().getWorld(targetWorld);
			Set<Entity> entitiesToTeleport = new LinkedHashSet<>();

			Vec3d targetPos = new Vec3d(entity.getX(), AdAstra.CONFIG.rocket.atmosphereLeave, entity.getZ());

			if (entity instanceof ServerPlayerEntity player) {
				if (player.getVehicle() instanceof RocketEntity rocket) {
					rocket.removeAllPassengers();
					player.sendMessage(Text.translatable("message." + AdAstra.MOD_ID + ".hold_space"), false);
					entity = createLander(rocket, world, targetPos);
					rocket.discard();
					entitiesToTeleport.add(entity);
					entitiesToTeleport.add(player);

				} else if (!(player.getVehicle() != null && player.getVehicle().getPassengerList().size() > 0)) {
					entitiesToTeleport.add(entity);
				}
			} else {
				entitiesToTeleport.add(entity);
			}

			if (entity instanceof ItemEntity itemEntity) {
				cookFood(itemEntity);
			}

			entitiesToTeleport.addAll(entity.getPassengerList());

			for (Entity entityToTeleport : entitiesToTeleport) {
				if (entityToTeleport instanceof ServerPlayerEntity) {
					ChunkPos chunkPos = new ChunkPos(new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ()));
					world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, entityToTeleport.getId());
					break;
				}
			}

			LinkedList<Entity> teleportedEntities = new LinkedList<>();
			for (Entity entityToTeleport : entitiesToTeleport) {
				TeleportTarget target = new TeleportTarget(targetPos, entityToTeleport.getVelocity(), entityToTeleport.getYaw(), entityToTeleport.getPitch());
				teleportedEntities.add(PlatformUtils.teleportToDimension(entityToTeleport, world, target));
			}

			Entity first = teleportedEntities.poll();

			// Move the lander to the closest land
			if (first instanceof LanderEntity) {
				Vec3d nearestLand = LandFinder.findNearestLand(first.getWorld(), new Vec3d(first.getX(), AdAstra.CONFIG.rocket.atmosphereLeave, first.getZ()), 70);
				first.refreshPositionAndAngles(nearestLand.getX(), nearestLand.getY(), nearestLand.getZ(), first.getYaw(), first.getPitch());
			}

			for (Entity teleportedEntity : teleportedEntities) {
				if (first instanceof LanderEntity) {
					Vec3d nearestLand = LandFinder.findNearestLand(teleportedEntity.getWorld(), new Vec3d(teleportedEntity.getX(), AdAstra.CONFIG.rocket.atmosphereLeave, teleportedEntity.getZ()), 70);
					teleportedEntity.refreshPositionAndAngles(nearestLand.getX(), nearestLand.getY(), nearestLand.getZ(), teleportedEntity.getYaw(), teleportedEntity.getPitch());
				}
				teleportedEntity.startRiding(first, true);
			}
		}
	}

	public static void teleportPlayer(RegistryKey<World> targetWorld, ServerPlayerEntity player) {
		ServerWorld world = player.getServer().getWorld(targetWorld);
		Vec3d targetPos = new Vec3d(player.getBlockPos().getX(), AdAstra.CONFIG.rocket.atmosphereLeave, player.getBlockPos().getZ());
		ChunkPos chunkPos = new ChunkPos(new BlockPos(targetPos.getX(), targetPos.getY(), targetPos.getZ()));
		world.getChunkManager().addTicket(ChunkTicketType.POST_TELEPORT, chunkPos, 1, player.getId());
		TeleportTarget target = new TeleportTarget(targetPos, player.getVelocity(), player.getYaw(), player.getPitch());
		PlatformUtils.teleportToDimension(player, world, target);
	}

	/**
	 * Spawns a lander in a target world and position.
	 *
	 * @param rocket         The rocket to create a lander from
	 * @param targetWorld    The world to spawn the lander in
	 * @param target The position to spawn the lander at
	 * @return A spawned lander entity at the same position as the rocket and with the same inventory
	 */
	public static LanderEntity createLander(RocketEntity rocket, ServerWorld targetWorld, Vec3d target) {
		LanderEntity lander = new LanderEntity(ModEntityTypes.LANDER.get(), targetWorld);
		lander.setPosition(target);

		for (int i = 0; i < rocket.getInventorySize(); i++) {
			lander.getInventory().setStack(i, rocket.getInventory().getStack(i));
		}
		ItemStack stack = rocket.getDropStack();
		((VehicleItem) stack.getItem()).setFluid(stack, rocket.getFluidHolder());
		lander.getInventory().setStack(10, stack);

		// On Fabric, this is required for some reason as it does not teleport the entity.
		if (ArchitecturyTarget.getCurrentTarget().equals("fabric")) {
			targetWorld.spawnEntity(lander);
		}
		return lander;
	}

	/**
	 * Gets the cooked variant of a raw food, if it exists, and then spawns the item entity. The cooked variant is obtained by using a smoking recipe, and then obtaining the result of that recipe.
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
		return AdAstra.planets.stream().filter(p -> p.orbitWorld().equals(world.getRegistryKey())).map(Planet::world).findFirst().orElse(World.OVERWORLD);
	}

	/**
	 * Gets the gravity of the world, in ratio to earth gravity. So a gravity of 1.0 is equivalent to earth gravity, while 0.5 would be half of earth's gravity and 2.0 would be twice the earth's gravity.
	 *
	 * @return The gravity of the world or earth gravity if the world does not have a defined gravity
	 */
	public static float getPlanetGravity(World world) {
		// Do not affect gravity for non-Ad Astra dimensions
		if (!ModUtils.isSpaceWorld(world)) {
			return 1.0f;
		}

		if (isOrbitWorld(world)) {
			return 3.0f / VANILLA_GRAVITY;
		}
		return AdAstra.planets.stream().filter(p -> p.world().equals(world.getRegistryKey())).map(Planet::gravity).findFirst().orElse(VANILLA_GRAVITY) / VANILLA_GRAVITY;
	}

	public static boolean planetHasAtmosphere(World world) {
		return AdAstra.planets.stream().filter(p -> p.world().equals(world.getRegistryKey())).map(Planet::hasAtmosphere).findFirst().orElse(false);
	}

	/**
	 * Gets the temperature of the world in celsius.
	 *
	 * @return The temperature of the world, or 20° for dimensions without a defined temperature
	 */
	public static float getWorldTemperature(World world) {
		if (isOrbitWorld(world)) {
			return ORBIT_TEMPERATURE;
		}
		return AdAstra.planets.stream().filter(p -> p.world().equals(world.getRegistryKey())).map(Planet::temperature).findFirst().orElse(20.0f);
	}

	/**
	 * Checks if the world is either a planet or an orbit world.
	 */
	public static boolean isSpaceWorld(World world) {
		return isPlanet(world) || isOrbitWorld(world);
	}

	/**
	 * Check if the world is labeled as a planet dimension.
	 */
	public static boolean isPlanet(World world) {
		if (World.OVERWORLD.equals(world.getRegistryKey())) {
			return false;
		}
		return AdAstra.planetWorlds.contains(world.getRegistryKey());
	}

	/**
	 * Checks if the world is labeled as an orbit dimension.
	 */
	public static boolean isOrbitWorld(World world) {
		return AdAstra.orbitWorlds.contains(world.getRegistryKey());
	}

	/**
	 * Spawns a server-side particle that renders regardless of the distance away from the player. This is important as normal particles are only rendered at up to 32 blocks away.
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

	public static boolean armourIsFreezeResistant(LivingEntity entity) {
		return StreamSupport.stream(entity.getArmorItems().spliterator(), false).allMatch(s -> s.isIn(ModTags.FREEZE_RESISTANT));
	}

	public static boolean armourIsHeatResistant(LivingEntity entity) {
		return StreamSupport.stream(entity.getArmorItems().spliterator(), false).allMatch(s -> s.isIn(ModTags.HEAT_RESISTANT));
	}

	public static boolean armourIsOxygenated(LivingEntity entity) {
		return StreamSupport.stream(entity.getArmorItems().spliterator(), false).allMatch(s -> s.isIn(ModTags.OXYGENATED_ARMOR));
	}

	public static long getSolarEnergy(World world) {
		if (isOrbitWorld(world)) {
			return AdAstra.planets.stream().filter(p -> p.orbitWorld().equals(world.getRegistryKey())).map(Planet::orbitSolarPower).findFirst().orElse(15L);
		} else if (isPlanet(world)) {
			return AdAstra.planets.stream().filter(p -> p.world().equals(world.getRegistryKey())).map(Planet::solarPower).findFirst().orElse(15L);
		} else {
			return 15L;
		}
	}

	public static <T extends Enum<T>> Codec<T> createEnumCodec(Class<T> enumClass) {
		return Codec.STRING.xmap(s -> Enum.valueOf(enumClass, s.toUpperCase(Locale.ROOT)), Enum::name);
	}
}