package net.mrscauthd.beyond_earth.events;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.sounds.ElytraOnPlayerSoundInstance;
import net.minecraft.client.resources.sounds.MinecartSoundInstance;
import net.minecraft.client.resources.sounds.RidingMinecartSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.entity.*;
import net.mrscauthd.beyond_earth.events.forgeevents.RenderHandItemEvent;
import net.mrscauthd.beyond_earth.events.forgeevents.SetupLivingBipedAnimEvent;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID)
public class Events {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Player player = event.player;
            Level world = player.level;

            //Lander Teleport System
            if (player.getVehicle() instanceof LanderEntity) {
                Methodes.landerTeleportOrbit(player, world);
            }

            //Planet Gui Open
            Methodes.openPlanetGui(player);

            //Oxygen System
            OxygenSystem.OxygenSystem(player);

            //Gravity Methode Call
            Gravity.Gravity(player, Gravity.GravityType.PLAYER, world);

            //Drop Off Hand Item
            Methodes.DropRocket(player);

            //Player orbit Fall Teleport
            if (player.getY() < 1 && !(player.getVehicle() instanceof LanderEntity)) {
                Methodes.playerFalltoPlanet(world, player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingEntityTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();
        Level world = entity.level;

        Methodes.EntityOxygen(entity,world);

        //Gravity Methode Call
        Gravity.Gravity(entity, Gravity.GravityType.LIVING, world);

        //Venus Rain
        Methodes.VenusRain(entity, Methodes.venus);

        //Venus Fire
        Methodes.PlanetFire(entity, Methodes.venus, Methodes.mercury);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void CameraPos(EntityViewRenderEvent.CameraSetup event) {
        Entity ridding = event.getCamera().getEntity().getVehicle();

        if (Methodes.isRocket(ridding) || ridding instanceof LanderEntity) {
            CameraType pointOfView = Minecraft.getInstance().options.getCameraType();

            if (pointOfView.equals(CameraType.THIRD_PERSON_FRONT) || pointOfView.equals(CameraType.THIRD_PERSON_BACK)) {
                event.getCamera().move(-event.getCamera().getMaxZoom(12d), 0d, 0);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void renderPlayerArm(RenderArmEvent event) {
        PlayerModel<AbstractClientPlayer> playerModel = ((PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(event.getPlayer())).getModel();

        Player player = event.getPlayer();

        Item item = player.getOffhandItem().getItem();
        Item item2 = player.getMainHandItem().getItem();

        /**Cancel Event if it Hold a Vehicle in off hand*/
        if (item == ModInit.TIER_1_ROCKET_ITEM.get() || item == ModInit.TIER_2_ROCKET_ITEM.get() || item == ModInit.TIER_3_ROCKET_ITEM.get() || item == ModInit.TIER_4_ROCKET_ITEM.get() || item == ModInit.ROVER_ITEM.get()
            || item2 == ModInit.TIER_1_ROCKET_ITEM.get() || item2 == ModInit.TIER_2_ROCKET_ITEM.get() || item2 == ModInit.TIER_3_ROCKET_ITEM.get() || item2 == ModInit.TIER_4_ROCKET_ITEM.get() || item2 == ModInit.ROVER_ITEM.get()) {
            event.setCanceled(true);
            return;
        }

        if (event.getArm() == HumanoidArm.RIGHT) {
            if (Methodes.checkArmor(event.getPlayer(), 2, ModInit.SPACE_SUIT.get())) {

                Methodes.renderArm(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/arm/space_suit.png"), event.getPlayer(), playerModel, playerModel.rightArm);
                event.setCanceled(true);
            } else if (Methodes.checkArmor(event.getPlayer(), 2, ModInit.NETHERITE_SPACE_SUIT.get())) {

                Methodes.renderArm(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/arm/netherite_space_suit.png"), event.getPlayer(), playerModel, playerModel.rightArm);
                event.setCanceled(true);
            }
        }

        if (event.getArm() == HumanoidArm.LEFT) {
            if (Methodes.checkArmor(event.getPlayer(), 2, ModInit.SPACE_SUIT.get())) {

                Methodes.renderArm(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/arm/space_suit.png"), event.getPlayer(), playerModel, playerModel.leftArm);
                event.setCanceled(true);
            } else if (Methodes.checkArmor(event.getPlayer(), 2, ModInit.NETHERITE_SPACE_SUIT.get())) {

                Methodes.renderArm(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), new ResourceLocation(BeyondEarthMod.MODID, "textures/models/armor/arm/netherite_space_suit.png"), event.getPlayer(), playerModel, playerModel.leftArm);
                event.setCanceled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void render(RenderPlayerEvent event) {
        if (event.getEntity().getVehicle() instanceof LanderEntity) {
            event.setCanceled(true);
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void setupPlayerAngles(SetupLivingBipedAnimEvent.Post event) {
        if (event.getLivingEntity() instanceof Player) {
            Player player = (Player) event.getLivingEntity();
            HumanoidModel model = event.getModel();

            //Player Rocket Sit Rotations
            if (Methodes.isRocket(player.getVehicle())) {
                model.rightLeg.xRot = (float) Math.toRadians(0F);
                model.leftLeg.xRot = (float) Math.toRadians(0F);
                model.leftLeg.yRot = (float) Math.toRadians(3F);
                model.leftLeg.yRot = (float) Math.toRadians(3F);
                // Arms
                model.leftArm.xRot = -0.07f;
                model.rightArm.xRot = -0.07f;
            }

            //Player Hold Vehicles Rotation
            if (!Methodes.isRocket(player.getVehicle())) {
                Item item1 = player.getMainHandItem().getItem();
                Item item2 = player.getOffhandItem().getItem();
                if (item1 == ModInit.TIER_1_ROCKET_ITEM.get()
                        || item1 == ModInit.TIER_2_ROCKET_ITEM.get()
                        || item1 == ModInit.TIER_3_ROCKET_ITEM.get()
                        || item1 == ModInit.TIER_4_ROCKET_ITEM.get()
                        || item1 == ModInit.ROVER_ITEM.get()
                        //Off Hand
                        || item2 == ModInit.TIER_1_ROCKET_ITEM.get()
                        || item2 == ModInit.TIER_2_ROCKET_ITEM.get()
                        || item2 == ModInit.TIER_3_ROCKET_ITEM.get()
                        || item2 == ModInit.TIER_4_ROCKET_ITEM.get()
                        || item2 == ModInit.ROVER_ITEM.get()) {
                    model.rightArm.xRot = 10;
                    model.leftArm.xRot = 10;
                    model.rightArm.zRot = 0;
                    model.leftArm.zRot = 0;
                    model.rightArm.yRot = 0;
                    model.leftArm.yRot = 0;
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void ItemRender(RenderHandItemEvent.Pre event) {
        if (event.getLivingEntity() instanceof Player) {
            Player player = (Player) event.getLivingEntity();

            if (Methodes.isRocket(player.getVehicle())) {
                event.setCanceled(true);
            }

            Item item1 = player.getMainHandItem().getItem();
            Item item2 = player.getOffhandItem().getItem();

            if (item1 == ModInit.TIER_1_ROCKET_ITEM.get()
                    || item1 == ModInit.TIER_2_ROCKET_ITEM.get()
                    || item1 == ModInit.TIER_3_ROCKET_ITEM.get()
                    || item1 == ModInit.TIER_4_ROCKET_ITEM.get()
                    || item1 == ModInit.ROVER_ITEM.get()) {

                if (event.getHandSide() == HumanoidArm.LEFT) {
                    event.setCanceled(true);
                }

            }

            if (item2 == ModInit.TIER_1_ROCKET_ITEM.get()
                    || item2 == ModInit.TIER_2_ROCKET_ITEM.get()
                    || item2 == ModInit.TIER_3_ROCKET_ITEM.get()
                    || item2 == ModInit.TIER_4_ROCKET_ITEM.get()
                    || item2 == ModInit.ROVER_ITEM.get()) {

                if (event.getHandSide() == HumanoidArm.RIGHT) {
                    event.setCanceled(true);
                }

            }

        }
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.WorldTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            Level world = event.world;

            if (Methodes.isWorld(world, Methodes.moon)
             || Methodes.isWorld(world, Methodes.moon_orbit)
             || Methodes.isWorld(world, Methodes.mars)
             || Methodes.isWorld(world, Methodes.mars_orbit)
             || Methodes.isWorld(world, Methodes.mercury)
             || Methodes.isWorld(world, Methodes.mercury_orbit)
             || Methodes.isWorld(world, Methodes.venus_orbit)
             || Methodes.isWorld(world, Methodes.glacio)
             || Methodes.isWorld(world, Methodes.glacio_orbit)
             || Methodes.isWorld(world, Methodes.overworld_orbit)) {
                world.thunderLevel = 0;
                world.rainLevel = 0;
            }

            if (Methodes.isWorld(world, Methodes.venus)) {
                world.thunderLevel = 0;
            }
        }
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        if (event != null && event.getEntity() instanceof Player) {
            Player entity = (Player) event.getEntity();

            if (Methodes.nethriteSpaceSuitCheck(entity)) {
                if (event.getSource().isFire()) {
                    entity.setRemainingFireTicks(0);
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void FishingBobberTick(ProjectileImpactEvent event) {
        if (event.getRayTraceResult().getType() == HitResult.Type.ENTITY) {
            Entity entity = ((EntityHitResult) event.getRayTraceResult()).getEntity();
            if (Methodes.AllVehiclesOr(entity)) {
                event.setCanceled(true);
            }
        }
    }

    //TODO Remove if you change it to Entity
    @SubscribeEvent
    public static void interact(PlayerInteractEvent.EntityInteract event) {
        if (event.getItemStack().getItem() == Items.NAME_TAG) {
            if (Methodes.AllVehiclesOr(event.getTarget())) {
                event.setCanceled(true);
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void SpaceSounds(PlaySoundEvent event) {
        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null && Methodes.checkSound(event.getSound().getSource()) && Methodes.isSpaceWorldWithoutOxygen(Minecraft.getInstance().player.level)) {
            if (event.getSound().getSound() != null && !(event.getSound() instanceof ElytraOnPlayerSoundInstance) && !(event.getSound() instanceof MinecartSoundInstance) && !(event.getSound() instanceof RidingMinecartSoundInstance)) {
                event.setSound(new SpaceSoundSystem(event.getSound()));
            } else if (event.getSound().getSound() != null && event.getSound() instanceof ElytraOnPlayerSoundInstance) {
                event.setSound(new ElytraSpaceOnPlayerSoundInstance(Minecraft.getInstance().player));
            }
        }
    }

    @SubscribeEvent
    public static void onKill(LivingDeathEvent event) {
        if (event.getEntity() instanceof Player && event.getEntity().getPersistentData().getBoolean(BeyondEarthMod.MODID + ":planet_selection_gui_open")) {
            ((Player) event.getEntity()).closeContainer();
            Methodes.cleanUpPlayerNBT((Player) event.getEntity());
            event.getEntity().setNoGravity(false);
        }
    }
}
