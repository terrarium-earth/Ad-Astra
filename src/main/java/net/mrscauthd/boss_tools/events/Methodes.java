package net.mrscauthd.boss_tools.events;

import io.netty.buffer.Unpooled;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SChangeGameStatePacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SPlayerAbilitiesPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.CapabilityItemHandler;
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
import java.util.Collections;
import java.util.List;

public class Methodes {

    public static void worldTeleport(PlayerEntity entity, ResourceLocation Planet, double high) {
        if (!entity.world.isRemote) {

            RegistryKey<World> destinationType = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, Planet);
            ServerWorld nextWorld = entity.getServer().getWorld(destinationType);

            if (nextWorld != null) {
                ((ServerPlayerEntity) entity).connection.sendPacket(new SChangeGameStatePacket(SChangeGameStatePacket.field_241768_e_, 0));
                ((ServerPlayerEntity) entity).teleport(nextWorld, entity.getPosX(), high, entity.getPosZ(), entity.rotationYaw, entity.rotationPitch);
                ((ServerPlayerEntity) entity).connection.sendPacket(new SPlayerAbilitiesPacket(entity.abilities));

                for (EffectInstance effectinstance : entity.getActivePotionEffects()) {
                    ((ServerPlayerEntity) entity).connection.sendPacket(new SPlayEntityEffectPacket(entity.getEntityId(), effectinstance));
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
        if (entity.getItemStackFromSlot(EquipmentSlotType.fromSlotTypeAndIndex(EquipmentSlotType.Group.ARMOR, number)).getItem() == item) {
            return true;
        }
        return false;
    }

    public static boolean isSpaceWorld(World world) {
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

    public static boolean isOrbitWorld(World world) {
        if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"overworld_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"moon_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mars_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"mercury_orbit"))
                || Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId,"venus_orbit"))) {
            return true;
        }
        return false;
    }

    public static boolean isWorld(World world, ResourceLocation loc) {
        RegistryKey<World> world2 = world.getDimensionKey();
        if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, loc)) {
            return true;
        }
        return false;
    }

    public static void OxygenDamage(LivingEntity entity) {
        entity.attackEntityFrom(ModInnet.DAMAGE_SOURCE_OXYGEN, 1.0F);
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

    public static void RocketSounds(Entity entity, World world) {
        world.playMovingSound(null, entity, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation(BossToolsMod.ModId,"rocket_fly")), SoundCategory.NEUTRAL,1,1);
    }

    public static void DropRocket(PlayerEntity player) {
        Item item1 = player.getHeldItemMainhand().getItem();
        Item item2 = player.getHeldItemOffhand().getItem();

        List<Item> items = new ArrayList<Item>();

        items.add(ModInnet.TIER_1_ROCKET_ITEM.get());
        items.add(ModInnet.TIER_2_ROCKET_ITEM.get());
        items.add(ModInnet.TIER_3_ROCKET_ITEM.get());
        items.add(ModInnet.ROVER_ITEM.get());

        if (items.contains(item1) && items.contains(item2)) {

            ItemEntity spawn = new ItemEntity(player.world, player.getPosX(),player.getPosY(),player.getPosZ(), new ItemStack(item2));
            spawn.setPickupDelay(0);
            player.world.addEntity(spawn);

            player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(capability -> {
                capability.extractItem(40, 1, false); //40 is offhand
            });

        }
    }

    /**If a entity should not get Fire add it to the Tag "venus_fire"*/
    public static void VenusFire(LivingEntity entity, ResourceLocation planet1, ResourceLocation planet2) {

        RegistryKey<World> key = entity.world.getDimensionKey();

        if (key == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, planet1) || key == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, planet2)) {
            if (!Methodes.nethriteSpaceSuitCheck(entity)) {
                if (!MinecraftForge.EVENT_BUS.post(new LivingSetFireInHotPlanetEvent(entity))) {
                    if (!tagCheck(entity, BossToolsMod.ModId + ":entities/venus_fire")) {

                        entity.setFire(10);
                    }
                }
            }
        }
    }

    /**If a entity should not get Damage add it to the Tag "venus_rain", and if you has a Entity like a car return the damage to false*/
    public static void VenusRain(LivingEntity entity, ResourceLocation planet) {
        if (entity.world.getDimensionKey() == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, planet)) {
            if (entity.world.getWorldInfo().isRaining() && entity.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, (int) Math.floor(entity.getPosX()), (int) Math.floor(entity.getPosZ())) <= Math.floor(entity.getPosY()) + 1) {
                if (!MinecraftForge.EVENT_BUS.post(new LivingSetVenusRainEvent(entity))) {
                    if (!tagCheck(entity,BossToolsMod.ModId + ":entities/venus_rain")) {

                        entity.attackEntityFrom(ModInnet.DAMAGE_SOURCE_ACID_RAIN, 1);
                    }
                }
            }
        }

    }

    /**IF a entity should get oxygen damage add it in the tag "oxygen" (don't add the Player, he have a own oxygen system)*/
    public static void EntityOxygen(LivingEntity entity, World world) {
        if (Config.EntityOxygenSystem && Methodes.isSpaceWorld(world) && tagCheck(entity,"boss_tools:entities/oxygen")) {

            if (!entity.isPotionActive(ModInnet.OXYGEN_EFFECT.get())) {

                entity.getPersistentData().putDouble(BossToolsMod.ModId + ":oxygen_tick", entity.getPersistentData().getDouble(BossToolsMod.ModId + ":oxygen_tick") + 1);

                if (entity.getPersistentData().getDouble(BossToolsMod.ModId + ":oxygen_tick") > 15) {

                    if(!world.isRemote) {
                        Methodes.OxygenDamage(entity);
                    }

                    entity.getPersistentData().putDouble(BossToolsMod.ModId + ":oxygen_tick", 0);
                }
            }
        }
    }

    public static void vehicleRotation(LivingEntity vehicle, float roation) {
        vehicle.rotationYaw = vehicle.rotationYaw + roation;
        vehicle.setRenderYawOffset(vehicle.rotationYaw);
        vehicle.prevRotationYaw = vehicle.rotationYaw;
        vehicle.prevRenderYawOffset = vehicle.rotationYaw;
    }

    public static void noFuelMessage(PlayerEntity player) {
        if (!player.world.isRemote()) {
            player.sendStatusMessage(new StringTextComponent("\u00A7cNO FUEL! \u00A77Fill the Rocket with \u00A7cFuel\u00A77. (\u00A76Sneak and Right Click\u00A77)"), false);
        }
    }

    public static boolean tagCheck(Entity entity, String tag) {
        if (EntityTypeTags.getCollection().getTagByID(new ResourceLocation((tag).toLowerCase(java.util.Locale.ENGLISH))).contains(entity.getType())) {
            return true;
        }
        return false;
    }
    
    public static boolean tagCheck(Fluid fluid, ResourceLocation tag) {
        if (FluidTags.getCollection().getTagByID(tag).contains(fluid)) {
            return true;
        }
        return false;
    }

    public static void landerTeleport(PlayerEntity player, ResourceLocation newPlanet) {
        LanderEntity lander = (LanderEntity) player.getRidingEntity();

        if (player.getPosY() < 1) {

            ItemStack slot_0 = lander.getInventory().getStackInSlot(0);
            ItemStack slot_1 = lander.getInventory().getStackInSlot(1);
            lander.remove();

            Methodes.worldTeleport(player, newPlanet, 700);

            World newWorld = player.world;

            if (!player.world.isRemote()) {
                LanderEntity entityToSpawn = new LanderEntity((EntityType<LanderEntity>) ModInnet.LANDER.get(), newWorld);
                entityToSpawn.setLocationAndAngles(player.getPosX(), player.getPosY(), player.getPosZ(), 0, 0);
                entityToSpawn.onInitialSpawn((ServerWorld) newWorld, newWorld.getDifficultyForLocation(entityToSpawn.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
                newWorld.addEntity(entityToSpawn);

                entityToSpawn.getInventory().setStackInSlot(0, slot_0);
                entityToSpawn.getInventory().setStackInSlot(1, slot_1);

                player.startRiding(entityToSpawn);
            }
        }
    }

    public static void rocketTeleport(PlayerEntity player, ResourceLocation planet, ItemStack rocketItem, Boolean SpaceStation) {
        RegistryKey<World> dim = player.world.getDimensionKey();

        if (dim != RegistryKey.getOrCreateKey(Registry.WORLD_KEY, planet)) {
            Methodes.worldTeleport(player, planet, 700);
        } else {
            player.setPositionAndUpdate(player.getPosX(), 700, player.getPosZ());

            if (player instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) player).connection.setPlayerLocation(player.getPosX(), 700, player.getPosZ(), player.rotationYaw, player.rotationPitch);
            }
        }

        World world = player.world;

        if (!world.isRemote()) {
            LanderEntity landerSpawn = new LanderEntity((EntityType<LanderEntity>) ModInnet.LANDER.get(), world);
            landerSpawn.setLocationAndAngles(player.getPosX(), player.getPosY(), player.getPosZ(), 0, 0);
            landerSpawn.onInitialSpawn((ServerWorld) world, world.getDifficultyForLocation(landerSpawn.getPosition()), SpawnReason.MOB_SUMMONED, null, null);
            world.addEntity(landerSpawn);

            String itemId = player.getPersistentData().getString(BossToolsMod.ModId + ":slot0");

            landerSpawn.getInventory().setStackInSlot(0, new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemId)), 1));
            landerSpawn.getInventory().setStackInSlot(1, rocketItem);

            if (SpaceStation) {
                createSpaceStation(player, (ServerWorld) world);
            }

            cleanUpPlayerNBT(player);

            player.startRiding(landerSpawn);
        }
    }

    public static void createSpaceStation(PlayerEntity player, ServerWorld serverWorld) {
        BlockPos pos = new BlockPos(player.getPosX() - 15.5, 100, player.getPosZ() - 15.5);
        serverWorld.getStructureTemplateManager().getTemplate(new ResourceLocation(BossToolsMod.ModId, "space_station")).func_237144_a_(serverWorld, pos, new PlacementSettings(), serverWorld.rand);
    }

    public static void cleanUpPlayerNBT(PlayerEntity player) {
        player.getPersistentData().putBoolean(BossToolsMod.ModId + ":planet_selection_gui_open", false);
        player.getPersistentData().putString(BossToolsMod.ModId + ":rocket_type", "");
        player.getPersistentData().putString(BossToolsMod.ModId + ":slot0", "");
    }

    public static void openPlanetGui(PlayerEntity player) {
        if (!(player.openContainer instanceof PlanetSelectionGui.GuiContainer) && player.getPersistentData().getBoolean(BossToolsMod.ModId + ":planet_selection_gui_open")) {
            if (player instanceof ServerPlayerEntity) {

                NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new StringTextComponent("Planet Selection");
                    }

                    @Override
                    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
                        packetBuffer.writeString(player.getPersistentData().getString(BossToolsMod.ModId + ":rocket_type"));
                        return new PlanetSelectionGui.GuiContainer(id, inventory, packetBuffer);
                    }
                }, buf -> {
                    buf.writeString(player.getPersistentData().getString(BossToolsMod.ModId + ":rocket_type"));
                });
            }
        }
    }

    public static void teleportButton (PlayerEntity player, ResourceLocation planet, Boolean SpaceStation) {
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

    public static void landerTeleportOrbit(PlayerEntity player, World world) {
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

    public static void playerFalltoPlanet(World world, PlayerEntity player) {
        RegistryKey<World> world2 = world.getDimensionKey();

        if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(BossToolsMod.ModId, "overworld_orbit"))) {
            ResourceLocation planet = new ResourceLocation("overworld");
            Methodes.worldTeleport(player, planet, 450);
        }

        if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(BossToolsMod.ModId, "moon_orbit"))) {
            ResourceLocation planet = new ResourceLocation(BossToolsMod.ModId, "moon");
            Methodes.worldTeleport(player, planet, 450);
        }

        if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(BossToolsMod.ModId, "mars_orbit"))) {
            ResourceLocation planet = new ResourceLocation(BossToolsMod.ModId, "mars");
            Methodes.worldTeleport(player, planet, 450);
        }

        if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(BossToolsMod.ModId, "mercury_orbit"))) {
            ResourceLocation planet = new ResourceLocation(BossToolsMod.ModId, "mercury");
            Methodes.worldTeleport(player, planet, 450);
        }

        if (world2 == RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(BossToolsMod.ModId, "venus_orbit"))) {
            ResourceLocation planet = new ResourceLocation(BossToolsMod.ModId, "venus");
            Methodes.worldTeleport(player, planet, 450);
        }
    }

	public static void extractArmorOxygenUsingTimer(ItemStack itemstack, PlayerEntity player) {
		if (!player.abilities.isCreativeMode && !player.isSpectator() && Methodes.spaceSuitCheckBoth(player) && Config.PlayerOxygenSystem) {
			IOxygenStorage oxygenStorage = OxygenUtil.getItemStackOxygenStorage(itemstack);

			CompoundNBT persistentData = player.getPersistentData();
			String key = BossToolsMod.ModId + ":oxygen_timer";
			int oxygenTimer = persistentData.getInt(key);
			oxygenTimer++;

			if (oxygenStorage.getOxygenStored() > 0 && oxygenTimer > 3 && !player.isPotionActive(ModInnet.OXYGEN_EFFECT.get())) {
				oxygenStorage.extractOxygen(1, false);
				oxygenTimer = 0;
			}

			persistentData.putInt(key, oxygenTimer);
		}
	}
}
