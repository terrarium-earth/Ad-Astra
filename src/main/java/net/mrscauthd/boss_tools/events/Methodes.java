package net.mrscauthd.boss_tools.events;

import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.capability.OxygenUtil;
import net.mrscauthd.boss_tools.entity.*;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.boss_tools.events.forgeevents.LivingSetVenusRainEvent;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGui;

import java.util.ArrayList;
import java.util.List;

public class Methodes {

    public static void worldTeleport(Player entity, ResourceLocation Planet, double high) {
        if (!entity.level.isClientSide) {

            ResourceKey<Level> destinationType = ResourceKey.create(Registry.DIMENSION_REGISTRY, Planet);
            ServerLevel nextWorld = entity.getServer().getLevel(destinationType);

            if (nextWorld != null) {
                ((ServerPlayer) entity).connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.WIN_GAME, 0));
                ((ServerPlayer) entity).teleportTo(nextWorld, entity.getX(), high, entity.getZ(), entity.yRotO, entity.xRotO);
                ((ServerPlayer) entity).connection.send(new ClientboundPlayerAbilitiesPacket(entity.getAbilities()));

                for (MobEffectInstance effectinstance : entity.getActiveEffects()) {
                    ((ServerPlayer) entity).connection.send(new ClientboundUpdateMobEffectPacket(entity.getId(), effectinstance));
                }
            }
        }
    }

    public static boolean nethriteSpaceSuitCheck(LivingEntity entity) {
        Boolean item3 = checkArmor(entity, 3, ModInnet.NETHERITE_OXYGEN_MASK.get());
        Boolean item2 = checkArmor(entity, 2, ModInnet.NETHERITE_SPACE_SUIT.get());
        Boolean item1 = checkArmor(entity, 1, ModInnet.NETHERITE_SPACE_PANTS.get());
        Boolean item0 = checkArmor(entity, 0, ModInnet.NETHERITE_SPACE_BOOTS.get());

        if (item0 && item1 && item2 && item3) {
            return true;
        }
        return false;
    }

    public static boolean spaceSuitCheck(LivingEntity entity) {
        Boolean item3 = checkArmor(entity, 3, ModInnet.OXYGEN_MASK.get());
        Boolean item2 = checkArmor(entity, 2, ModInnet.SPACE_SUIT.get());
        Boolean item1 = checkArmor(entity, 1, ModInnet.SPACE_PANTS.get());
        Boolean item0 = checkArmor(entity, 0, ModInnet.SPACE_BOOTS.get());

        if (item0 && item1 && item2 && item3) {
            return true;
        }
        return false;
    }

    public static boolean spaceSuitCheckBoth(LivingEntity entity) {
        Boolean item3 = checkArmor(entity, 3, ModInnet.OXYGEN_MASK.get());
        Boolean item2 = checkArmor(entity, 2, ModInnet.SPACE_SUIT.get());
        Boolean item1 = checkArmor(entity, 1, ModInnet.SPACE_PANTS.get());
        Boolean item0 = checkArmor(entity, 0, ModInnet.SPACE_BOOTS.get());

        Boolean item3_2 = checkArmor(entity, 3, ModInnet.NETHERITE_OXYGEN_MASK.get());
        Boolean item2_2 = checkArmor(entity, 2, ModInnet.NETHERITE_SPACE_SUIT.get());
        Boolean item1_2 = checkArmor(entity, 1, ModInnet.NETHERITE_SPACE_PANTS.get());
        Boolean item0_2 = checkArmor(entity, 0, ModInnet.NETHERITE_SPACE_BOOTS.get());

        Boolean check3 = false;
        Boolean check2 = false;
        Boolean check1 = false;
        Boolean check0 = false;

        if (item3 || item3_2) {
            check3 = true;
        }
        if (item2 || item2_2) {
            check2 = true;
        }
        if (item1 || item1_2) {
            check1 = true;
        }
        if (item0 || item0_2) {
            check0 = true;
        }

        if (check0 && check1 && check2 && check3) {
            return true;
        }

        return false;
    }

    public static boolean checkArmor(LivingEntity entity,int number, Item item) {
        return entity.getItemBySlot(EquipmentSlot.byTypeAndIndex(EquipmentSlot.Type.ARMOR, number)).getItem() == item;
    }

    public static boolean isSpaceWorld(Level world) {
        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"moon"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"moon_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mars"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mars_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mercury"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mercury_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"venus"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"venus_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"overworld_orbit"))) {
            return true;
        }
        return false;
    }

    public static boolean isOrbitWorld(Level world) {
        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"overworld_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"moon_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mars_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mercury_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"venus_orbit"))) {
            return true;
        }
        return false;
    }

    public static boolean isWorld(Level world, ResourceLocation loc) {
        return world.dimension() == ResourceKey.create(Registry.DIMENSION_REGISTRY, loc);
    }

    public static void OxygenDamage(LivingEntity entity) {
        entity.hurt(ModInnet.DAMAGE_SOURCE_OXYGEN, 1.0F);
    }

    public static boolean isRocket(Entity entity) {
        if (entity instanceof RocketTier1Entity || entity instanceof RocketTier2Entity || entity instanceof RocketTier3Entity) {
            return true;
        }
        return false;
    }

    public static boolean AllVehiclesOr(Entity entity) {
        if (entity instanceof RocketTier1Entity || entity instanceof RocketTier2Entity || entity instanceof RocketTier3Entity || entity instanceof LanderEntity || entity instanceof RoverEntity) {
            return true;
        }
        return false;
    }

    public static void RocketSounds(Entity entity, Level world) {
        world.playSound(null, entity, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(BossToolsMod.ModId,"rocket_fly")), SoundSource.NEUTRAL,1,1);
    }

    public static void DropRocket(Player player) {
        Item item1 = player.getMainHandItem().getItem();
        Item item2 = player.getOffhandItem().getItem();

        List<Item> items = new ArrayList<Item>();

        items.add(ModInnet.TIER_1_ROCKET_ITEM.get());
        items.add(ModInnet.TIER_2_ROCKET_ITEM.get());
        items.add(ModInnet.TIER_3_ROCKET_ITEM.get());
        items.add(ModInnet.ROVER_ITEM.get());

        if (items.contains(item1) && items.contains(item2)) {

            ItemEntity spawn = new ItemEntity(player.level, player.getX(),player.getY(),player.getZ(), new ItemStack(item2));
            spawn.setPickUpDelay(0);
            player.level.addFreshEntity(spawn);

            player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(capability -> {
                capability.extractItem(40, 1, false); //40 is offhand
            });

        }
    }

    /**If a entity should not get Fire add it to the Tag "venus_fire"*/
    public static void VenusFire(LivingEntity entity, ResourceLocation planet1, ResourceLocation planet2) {

        ResourceKey<Level> key = entity.level.dimension();

        if (key == ResourceKey.create(Registry.DIMENSION_REGISTRY, planet1) || key == ResourceKey.create(Registry.DIMENSION_REGISTRY, planet2)) {
            if (!Methodes.nethriteSpaceSuitCheck(entity)) {
                if (!MinecraftForge.EVENT_BUS.post(new LivingSetFireInHotPlanetEvent(entity))) {
                    if (!tagCheck(entity, BossToolsMod.ModId + ":entities/venus_fire")) {

                        entity.setRemainingFireTicks(10);
                    }
                }
            }
        }
    }

    /**If a entity should not get Damage add it to the Tag "venus_rain", and if you has a Entity like a car return the damage to false*/
    public static void VenusRain(LivingEntity entity, ResourceLocation planet) {
        if (entity.level.dimension() == ResourceKey.create(Registry.DIMENSION_REGISTRY, planet)) {
            if (entity.level.getLevelData().isRaining() && entity.level.getHeight(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (int) Math.floor(entity.getX()), (int) Math.floor(entity.getZ())) <= Math.floor(entity.getY()) + 1) {
                if (!MinecraftForge.EVENT_BUS.post(new LivingSetVenusRainEvent(entity))) {
                    if (!tagCheck(entity,BossToolsMod.ModId + ":entities/venus_rain")) {

                        entity.hurt(ModInnet.DAMAGE_SOURCE_ACID_RAIN, 1);
                    }
                }
            }
        }

    }

    /**IF a entity should get oxygen damage add it in the tag "oxygen" (don't add the Player, he have a own oxygen system)*/
    public static void EntityOxygen(LivingEntity entity, Level world) {
        if (Config.EntityOxygenSystem && Methodes.isSpaceWorld(world) && tagCheck(entity,"boss_tools:entities/oxygen")) {

            if (!entity.isAffectedByPotions(ModInnet.OXYGEN_EFFECT.get())) {

                entity.getPersistentData().putDouble(BossToolsMod.ModId + ":oxygen_tick", entity.getPersistentData().getDouble(BossToolsMod.ModId + ":oxygen_tick") + 1);

                if (entity.getPersistentData().getDouble(BossToolsMod.ModId + ":oxygen_tick") > 15) {

                    if(!world.isClientSide) {
                        Methodes.OxygenDamage(entity);
                    }

                    entity.getPersistentData().putDouble(BossToolsMod.ModId + ":oxygen_tick", 0);
                }
            }
        }
    }

    public static void vehicleRotation(LivingEntity vehicle, float roation) {
        vehicle.setYRot(vehicle.getYRot() + roation);
        vehicle.setYBodyRot(vehicle.getYRot());
        vehicle.yRotO = vehicle.getYRot();
        vehicle.yBodyRotO = vehicle.getYRot();
    }

    public static void noFuelMessage(Player player) {
        if (!player.level.isClientSide) {
            player.displayClientMessage(new TextComponent("\u00A7cNO FUEL! \u00A77Fill the Rocket with \u00A7cFuel\u00A77. (\u00A76Sneak and Right Click\u00A77)"), false);
        }
    }

    public static boolean tagCheck(Entity entity, String tag) {
        return EntityTypeTags.getAllTags().getTagOrEmpty(new ResourceLocation((tag).toLowerCase(java.util.Locale.ENGLISH))).contains(entity.getType());
    }
    
    public static boolean tagCheck(Fluid fluid, ResourceLocation tag) {
        return FluidTags.getAllTags().getTagOrEmpty(tag).contains(fluid);
    }

    public static void landerTeleport(Player player, ResourceLocation newPlanet) {
        LanderEntity lander = (LanderEntity) player.getVehicle();

        if (player.getY() < 1) {

            ItemStack slot_0 = lander.getInventory().getStackInSlot(0);
            ItemStack slot_1 = lander.getInventory().getStackInSlot(1);
            lander.remove();

            Methodes.worldTeleport(player, newPlanet, 700);

            Level newWorld = player.level;

            if (!player.level.isClientSide) {
                LanderEntity entityToSpawn = new LanderEntity((EntityType<LanderEntity>) ModInnet.LANDER.get(), newWorld);
                entityToSpawn.setLocationAndAngles(player.getPosX(), player.getPosY(), player.getPosZ(), 0, 0);
                entityToSpawn.onInitialSpawn((ServerWorld) newWorld, newWorld.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
                newWorld.addFreshEntity(entityToSpawn);

                entityToSpawn.getInventory().setStackInSlot(0, slot_0);
                entityToSpawn.getInventory().setStackInSlot(1, slot_1);

                player.startRiding(entityToSpawn);
            }
        }
    }

    public static void rocketTeleport(Player player, ResourceLocation planet, ItemStack rocketItem, Boolean SpaceStation) {
        ResourceKey<Level> dim = player.level.dimension();

        if (dim != ResourceKey.create(Registry.DIMENSION_REGISTRY, planet)) {
            Methodes.worldTeleport(player, planet, 700);
        } else {
            player.setPos(player.getX(), 700, player.getZ());

            if (player instanceof ServerPlayer) {
                ((ServerPlayer) player).connection.teleport(player.getX(), 700, player.getY(), player.getYRot(), player.getXRot());
            }
        }

        Level world = player.level;

        if (!world.isClientSide) {
            LanderEntity landerSpawn = new LanderEntity((EntityType<LanderEntity>) ModInnet.LANDER.get(), world);
            landerSpawn.setLocationAndAngles(player.getPosX(), player.getPosY(), player.getPosZ(), 0, 0);
            landerSpawn.onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(landerSpawn.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
            world.addEntity(landerSpawn);

            String itemId = player.getPersistentData().getString(BossToolsMod.ModId + ":slot0");

            landerSpawn.getInventory().setStackInSlot(0, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId)), 1));
            landerSpawn.getInventory().setStackInSlot(1, rocketItem);

            if (SpaceStation) {
                createSpaceStation(player, (ServerLevel) world);
            }

            cleanUpPlayerNBT(player);

            player.startRiding(landerSpawn);
        }
    }

    public static void createSpaceStation(Player player, ServerLevel serverWorld) {
        BlockPos pos = new BlockPos(player.getX() - 15.5, 100, player.getZ() - 15.5);
        serverWorld.getStructureManager().getOrCreate(new ResourceLocation(BossToolsMod.ModId, "space_station")).placeInWorld(serverWorld, pos, pos, new StructurePlaceSettings(), serverWorld.random, 2);
    }

    public static void cleanUpPlayerNBT(Player player) {
        player.getPersistentData().putBoolean(BossToolsMod.ModId + ":planet_selection_gui_open", false);
        player.getPersistentData().putString(BossToolsMod.ModId + ":rocket_type", "");
        player.getPersistentData().putString(BossToolsMod.ModId + ":slot0", "");
    }

    public static void openPlanetGui(Player player) {
        if (!(player.containerMenu instanceof PlanetSelectionGui.GuiContainer) && player.getPersistentData().getBoolean(BossToolsMod.ModId + ":planet_selection_gui_open")) {
            if (player instanceof ServerPlayer) {

                NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return new TextComponent("Planet Selection");
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
                        FriendlyByteBuf packetBuffer = new FriendlyByteBuf(Unpooled.buffer());
                        packetBuffer.writeUtf(player.getPersistentData().getString(BossToolsMod.ModId + ":rocket_type"));
                        return new PlanetSelectionGui.GuiContainer(id, inventory, packetBuffer);
                    }
                }, buf -> {
                    buf.writeUtf(player.getPersistentData().getString(BossToolsMod.ModId + ":rocket_type"));
                });
            }
        }
    }

    public static void teleportButton (Player player, ResourceLocation planet, Boolean SpaceStation) {
        ItemStack itemStack = new ItemStack(Items.AIR, 1);

        if (player.getPersistentData().getString(BossToolsMod.ModId + ":rocket_type").equals("entity." + BossToolsMod.ModId + ".rocket_t1")) {
            itemStack = new ItemStack(ModInnet.TIER_1_ROCKET_ITEM.get(),1);
        }
        if (player.getPersistentData().getString(BossToolsMod.ModId + ":rocket_type").equals("entity." + BossToolsMod.ModId + ".rocket_t2")) {
            itemStack = new ItemStack(ModInnet.TIER_2_ROCKET_ITEM.get(),1);
        }
        if (player.getPersistentData().getString(BossToolsMod.ModId + ":rocket_type").equals("entity." + BossToolsMod.ModId + ".rocket_t3")) {
            itemStack = new ItemStack(ModInnet.TIER_3_ROCKET_ITEM.get(),1);
        }

        Methodes.rocketTeleport(player, planet, itemStack, SpaceStation);
    }

    public static void landerTeleportOrbit(Player player, Level world) {
        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "overworld_orbit"))) {
            Methodes.landerTeleport(player, new ResourceLocation("minecraft:overworld"));
        }
        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "moon_orbit"))) {
            Methodes.landerTeleport(player, new ResourceLocation(BossToolsMod.ModId, "moon"));
        }
        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "mars_orbit"))) {
            Methodes.landerTeleport(player, new ResourceLocation(BossToolsMod.ModId, "mars"));
        }
        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "mercury_orbit"))) {
            Methodes.landerTeleport(player, new ResourceLocation(BossToolsMod.ModId, "mercury"));
        }
        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "venus_orbit"))) {
            Methodes.landerTeleport(player, new ResourceLocation(BossToolsMod.ModId, "venus"));
        }
    }

    public static void playerFalltoPlanet(Level world, Player player) {
        ResourceKey<Level> world2 = world.dimension();

        if (world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId, "overworld_orbit"))) {
            ResourceLocation planet = new ResourceLocation("overworld");
            Methodes.worldTeleport(player, planet, 450);
        }

        if (world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId, "moon_orbit"))) {
            ResourceLocation planet = new ResourceLocation(BossToolsMod.ModId, "moon");
            Methodes.worldTeleport(player, planet, 450);
        }

        if (world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId, "mars_orbit"))) {
            ResourceLocation planet = new ResourceLocation(BossToolsMod.ModId, "mars");
            Methodes.worldTeleport(player, planet, 450);
        }

        if (world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId, "mercury_orbit"))) {
            ResourceLocation planet = new ResourceLocation(BossToolsMod.ModId, "mercury");
            Methodes.worldTeleport(player, planet, 450);
        }

        if (world2 == ResourceKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(BossToolsMod.ModId, "venus_orbit"))) {
            ResourceLocation planet = new ResourceLocation(BossToolsMod.ModId, "venus");
            Methodes.worldTeleport(player, planet, 450);
        }
    }

	public static void extractArmorOxygenUsingTimer(ItemStack itemstack, Player player) {
		if (!player.getAbilities().instabuild && !player.isSpectator() && Methodes.spaceSuitCheckBoth(player) && Config.PlayerOxygenSystem) {
			IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemstack);

            CompoundTag persistentData = player.getPersistentData();
			String key = BossToolsMod.ModId + ":oxygen_timer";
			int oxygenTimer = persistentData.getInt(key);
			oxygenTimer++;

			if (oxygenStorage.getOxygenStored() > 0 && oxygenTimer > 3 && !player.isAffectedByPotions(ModInnet.OXYGEN_EFFECT.get())) {
				oxygenStorage.extractOxygen(1, false);
				oxygenTimer = 0;
			}

			persistentData.putInt(key, oxygenTimer);
		}
	}
}
