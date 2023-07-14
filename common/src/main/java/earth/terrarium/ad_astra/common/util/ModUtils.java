package earth.terrarium.ad_astra.common.util;

import com.mojang.serialization.Codec;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.config.AdAstraConfig;
import earth.terrarium.ad_astra.common.config.VehiclesConfig;
import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.entity.vehicle.Lander;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.entity.vehicle.Vehicle;
import earth.terrarium.ad_astra.common.item.vehicle.VehicleItem;
import earth.terrarium.ad_astra.common.registry.ModCriteria;
import earth.terrarium.ad_astra.common.registry.ModEntityTypes;
import earth.terrarium.ad_astra.common.registry.ModTags;
import earth.terrarium.ad_astra.common.util.algorithm.LandFinder;
import earth.terrarium.botarium.api.item.ItemStackHolder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.TicketType;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmokingRecipe;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.portal.PortalInfo;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Set;
import java.util.stream.StreamSupport;

public class ModUtils {

    public static final ResourceKey<Level> MOON_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "moon"));
    public static final ResourceKey<Level> MARS_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "mars"));
    public static final ResourceKey<Level> VENUS_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "venus"));
    public static final ResourceKey<Level> MERCURY_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "mercury"));
    public static final ResourceKey<Level> GLACIO_KEY = ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(AdAstra.MOD_ID, "glacio"));

    public static final float VANILLA_GRAVITY = 9.806f;
    public static final float ORBIT_TEMPERATURE = -270.0f;

    /**
     * Teleports an entity to a different dimension. If the entity is a player in a rocket, the player will teleport with a lander. If the entity is raw food, the food will be cooked.
     *
     * @param targetWorld The level to the entity teleport to
     * @param entity      The entity to teleport
     * @see #teleportPlayer(ResourceKey, ServerPlayer)
     */
    public static void teleportToLevel(ResourceKey<Level> targetWorld, Entity entity) {
        if (entity.getLevel() instanceof ServerLevel oldWorld) {
            ServerLevel level = oldWorld.getServer().getLevel(targetWorld);
            if (level == null) return;
            Set<Entity> entitiesToTeleport = new LinkedHashSet<>();

            Vec3 targetPos = new Vec3(entity.getX(), VehiclesConfig.RocketConfig.atmosphereLeave, entity.getZ());

            if (entity instanceof ServerPlayer player) {
                if (player.getVehicle() instanceof Rocket rocket) {
                    rocket.ejectPassengers();
                    player.displayClientMessage(Component.translatable("message." + AdAstra.MOD_ID + ".hold_space"), false);
                    entity = createLander(rocket, level, targetPos);
                    rocket.discard();
                    entitiesToTeleport.add(entity);
                    entitiesToTeleport.add(player);

                } else if (!(player.getVehicle() != null && player.getVehicle().getPassengers().size() > 0)) {
                    entitiesToTeleport.add(entity);
                }
            } else {
                entitiesToTeleport.add(entity);
            }

            if (entity instanceof ItemEntity itemEntity) {
                cookFood(itemEntity);
            }

            entitiesToTeleport.addAll(entity.getPassengers());

            for (Entity entityToTeleport : entitiesToTeleport) {
                if (entityToTeleport instanceof ServerPlayer) {
                    ChunkPos chunkPos = new ChunkPos(new BlockPos(targetPos.x(), targetPos.y(), targetPos.z()));
                    level.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 1, entityToTeleport.getId());
                    break;
                }
            }

            LinkedList<Entity> teleportedEntities = new LinkedList<>();
            for (Entity entityToTeleport : entitiesToTeleport) {
                PortalInfo target = new PortalInfo(targetPos, entityToTeleport.getDeltaMovement(), entityToTeleport.getYRot(), entityToTeleport.getXRot());
                teleportedEntities.add(PlatformUtils.teleportToDimension(entityToTeleport, level, target));
            }

            Entity first = teleportedEntities.poll();

            // Move the lander to the closest land
            if (first instanceof Lander) {
                Vec3 nearestLand = LandFinder.findNearestLand(first.getLevel(), new Vec3(first.getX(), VehiclesConfig.RocketConfig.atmosphereLeave, first.getZ()), 70);
                first.moveTo(nearestLand.x(), nearestLand.y(), nearestLand.z(), first.getYRot(), first.getXRot());
            }

            for (Entity teleportedEntity : teleportedEntities) {
                if (first instanceof Lander) {
                    Vec3 nearestLand = LandFinder.findNearestLand(teleportedEntity.getLevel(), new Vec3(teleportedEntity.getX(), VehiclesConfig.RocketConfig.atmosphereLeave, teleportedEntity.getZ()), 70);
                    teleportedEntity.moveTo(nearestLand.x(), nearestLand.y(), nearestLand.z(), teleportedEntity.getYRot(), teleportedEntity.getXRot());
                }
                if (teleportedEntity != null) {
                    teleportedEntity.startRiding(first, true);
                }
            }
        }
    }

    public static void teleportPlayer(ResourceKey<Level> targetWorld, ServerPlayer player) {
        ServerLevel level = player.getServer().getLevel(targetWorld);
        Vec3 targetPos = new Vec3(player.blockPosition().getX(), VehiclesConfig.RocketConfig.atmosphereLeave, player.blockPosition().getZ());
        ChunkPos chunkPos = new ChunkPos(new BlockPos(targetPos.x(), targetPos.y(), targetPos.z()));
        level.getChunkSource().addRegionTicket(TicketType.POST_TELEPORT, chunkPos, 1, player.getId());
        PortalInfo target = new PortalInfo(targetPos, player.getDeltaMovement(), player.getYRot(), player.getXRot());
        PlatformUtils.teleportToDimension(player, level, target);
    }

    /**
     * Spawns a lander in a target level and position.
     *
     * @param rocket      The rocket to create a lander from
     * @param targetWorld The level to spawn the lander in
     * @param target      The position to spawn the lander at
     * @return A spawned lander entity at the same position as the rocket and with the same inventory
     */
    public static Lander createLander(Rocket rocket, ServerLevel targetWorld, Vec3 target) {
        Lander lander = new Lander(ModEntityTypes.LANDER.get(), targetWorld);
        lander.setPos(target);

        for (int i = 0; i < rocket.getInventorySize(); i++) {
            lander.getInventory().setItem(i, rocket.getInventory().getItem(i));
        }
        ItemStackHolder stack = new ItemStackHolder(rocket.getDropStack());
        ((VehicleItem) stack.getStack().getItem()).insert(stack, rocket.getTankHolder());
        lander.getInventory().setItem(10, stack.getStack());

        // On Fabric, this is required for some reason as it does not teleport the entity.
        if (ArchitecturyTarget.getCurrentTarget().equals("fabric")) {
            targetWorld.addFreshEntity(lander);
        }
        return lander;
    }

    /**
     * Gets the cooked variant of a raw food, if it exists, and then spawns the item entity. The cooked variant is obtained by using a smoking recipe, and then obtaining the result of that recipe.
     *
     * @param itemEntity The item to try to convert into cooked food
     */
    public static void cookFood(ItemEntity itemEntity) {
        ItemStack stack = itemEntity.getItem();
        ItemStack foodOutput = ItemStack.EMPTY;

        for (SmokingRecipe recipe : itemEntity.getLevel().getRecipeManager().getAllRecipesFor(RecipeType.SMOKING)) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                if (ingredient.test(stack)) {
                    foodOutput = recipe.getResultItem();
                }
            }
        }

        if (!foodOutput.isEmpty()) {
            itemEntity.setItem(new ItemStack(foodOutput.getItem(), stack.getCount()));
            ServerPlayer playerEntity = (ServerPlayer) itemEntity.level.getPlayerByUUID(itemEntity.getThrower());
            if (playerEntity != null) {
                ModCriteria.FOOD_COOKED_IN_ATMOSPHERE.trigger(playerEntity);
            }
        }
    }

    /**
     * Gets the level's orbit dimension. The orbit dimension is where the planet's space station spawns and where the lander drops.
     *
     * @return The level's orbit dimension, or the overlevel if no orbit is defined
     */
    public static ResourceKey<Level> getPlanetOrbit(Level level) {
        return PlanetData.getPlanetFromOrbit(level.dimension()).map(Planet::level).orElse(Level.OVERWORLD);
    }

    public static float getEntityGravity(Entity entity) {
        return getPlanetGravity(entity.getLevel());
    }

    /**
     * Gets the gravity of the level, in ratio to earth gravity. So a gravity of 1.0 is equivalent to earth gravity, while 0.5 would be half of earth's gravity and 2.0 would be twice the earth's gravity.
     *
     * @return The gravity of the level or earth gravity if the level does not have a defined gravity
     */
    public static float getPlanetGravity(Level level) {
        // Do not affect gravity for non-Ad Astra dimensions
        if (!ModUtils.isSpacelevel(level)) {
            return 1.0f;
        }

        if (isOrbitlevel(level)) {
            return AdAstraConfig.orbitGravity / VANILLA_GRAVITY;
        }
        return PlanetData.getPlanetFromLevel(level.dimension()).map(Planet::gravity).orElse(VANILLA_GRAVITY) / VANILLA_GRAVITY;
    }

    public static boolean planetHasAtmosphere(Level level) {
        return PlanetData.getPlanetFromLevel(level.dimension()).map(Planet::hasAtmosphere).orElse(false);
    }

    /**
     * Gets the temperature of the level in celsius.
     *
     * @return The temperature of the level, or 20° for dimensions without a defined temperature
     */
    public static float getWorldTemperature(Level level) {
        if (isOrbitlevel(level)) {
            return ORBIT_TEMPERATURE;
        }
        return PlanetData.getPlanetFromLevel(level.dimension()).map(Planet::temperature).orElse(20.0f);
    }

    /**
     * Checks if the level is either a planet or an orbit level.
     */
    public static boolean isSpacelevel(Level level) {
        return isPlanet(level) || isOrbitlevel(level);
    }

    /**
     * Check if the level is labeled as a planet dimension.
     */
    public static boolean isPlanet(Level level) {
        if (AdAstraConfig.avoidOverworldChecks && Level.OVERWORLD.equals(level.dimension())) {
            return false;
        }
        return PlanetData.isPlanetLevel(level);
    }

    /**
     * Checks if the level is labeled as an orbit dimension.
     */
    public static boolean isOrbitlevel(Level level) {
        return PlanetData.isOrbitLevel(level.dimension());
    }

    /**
     * Spawns a server-side particle that renders regardless of the distance away from the player. This is important as normal particles are only rendered at up to 32 blocks away.
     */
    public static <T extends ParticleOptions> void spawnForcedParticles(ServerLevel level, T particle, double x, double y, double z, int count, double deltaX, double deltaY, double deltaZ, double speed) {
        for (ServerPlayer player : level.players()) {
            level.sendParticles(player, particle, true, x, y, z, count, deltaX, deltaY, deltaZ, speed);
        }
    }

    /**
     * Rotates the vehicle yaw without causing any stuttering effect or visual glitches.
     *
     * @param vehicle The vehicle to apply the rotation
     * @param newYaw  The new yaw to apply to the vehicle
     */
    public static void rotateVehicleYaw(Vehicle vehicle, float newYaw) {
        vehicle.setYRot(newYaw);
        vehicle.setYBodyRot(newYaw);
        vehicle.yRotO = newYaw;
    }

    public static boolean checkTag(Entity entity, TagKey<EntityType<?>> tag) {
        return entity.getType().is(tag);
    }

    public static boolean checkTag(ItemStack stack, TagKey<Item> tag) {
        return stack.is(tag);
    }

    public static boolean armourIsFreezeResistant(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(s -> s.is(ModTags.FREEZE_RESISTANT));
    }

    public static boolean armourIsHeatResistant(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(s -> s.is(ModTags.HEAT_RESISTANT));
    }

    public static boolean armourIsOxygenated(LivingEntity entity) {
        return StreamSupport.stream(entity.getArmorSlots().spliterator(), false).allMatch(s -> s.is(ModTags.OXYGENATED_ARMOR));
    }

    public static long getSolarEnergy(Level level) {
        if (isOrbitlevel(level)) {
            return PlanetData.getPlanetFromOrbit(level.dimension()).map(Planet::orbitSolarPower).orElse(15L);
        } else if (isPlanet(level)) {
            return PlanetData.getPlanetFromLevel(level.dimension()).map(Planet::solarPower).orElse(15L);
        } else {
            return 15L;
        }
    }

    public static <T extends Enum<T>> Codec<T> createEnumCodec(Class<T> enumClass) {
        return Codec.STRING.xmap(s -> Enum.valueOf(enumClass, s.toUpperCase(Locale.ROOT)), Enum::name);
    }

    // syncs movement between server and client
    public static void sendUpdatePacket(ServerPlayer player) {
        player.hurtMarked = true;
    }
}