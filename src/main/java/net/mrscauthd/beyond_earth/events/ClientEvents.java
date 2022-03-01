package net.mrscauthd.beyond_earth.events;

import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.sounds.TickableSoundInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderArmEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.IRocketEntity;
import net.mrscauthd.beyond_earth.entity.LanderEntity;
import net.mrscauthd.beyond_earth.events.forgeevents.RenderHandItemEvent;
import net.mrscauthd.beyond_earth.events.forgeevents.RenderViewEvent;
import net.mrscauthd.beyond_earth.events.forgeevents.SetupLivingBipedAnimEvent;
import net.mrscauthd.beyond_earth.item.VehicleItem;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void spaceSounds(PlaySoundEvent event) {
        if (event.getSound() == null) {
            return;
        }

        if (Minecraft.getInstance().player != null && Minecraft.getInstance().player.level != null && Methods.checkSound(event.getSound().getSource()) && Methods.isSpaceWorldWithoutOxygen(Minecraft.getInstance().player.level)) {

            if (!(event.getSound() instanceof TickableSoundInstance)) {
                event.setSound(new SpaceSoundSystem(event.getSound()));

            } else if (event.getSound() instanceof TickableSoundInstance) {
                event.setSound(new TickableSpaceSoundSystem((TickableSoundInstance) event.getSound()));
            }
        }
    }

    @SubscribeEvent
    public static void itemRender(RenderHandItemEvent.Pre event) {
        if (!(event.getLivingEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getLivingEntity();

        /** Rocket */
        if (Methods.isRocket(player.getVehicle())) {
            event.setCanceled(true);
        }

        /** Arm not Rendering if you have a VehicleItem in your Hand */
        if (event.getHandSide() == HumanoidArm.LEFT) {
            Item item = player.getMainHandItem().getItem();

            if (item instanceof VehicleItem) {
                event.setCanceled(true);
            }
        } else {
            Item item = player.getOffhandItem().getItem();

            if (item instanceof VehicleItem) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void cameraPos(EntityViewRenderEvent.CameraSetup event) {
        Entity ridding = event.getCamera().getEntity().getVehicle();

        if (Methods.isRocket(ridding) || ridding instanceof LanderEntity) {
            CameraType cameraType = Minecraft.getInstance().options.getCameraType();

            if (cameraType.equals(CameraType.THIRD_PERSON_FRONT) || cameraType.equals(CameraType.THIRD_PERSON_BACK)) {
                event.getCamera().move(-event.getCamera().getMaxZoom(12d), 0d, 0);
            }
        }
    }

    @SubscribeEvent
    public static void bobViewer(RenderViewEvent event) {
        Minecraft mc = Minecraft.getInstance();
        Entity ridding = mc.player.getVehicle();

        if (Methods.isRocket(ridding)) {
            CameraType cameraType = mc.options.getCameraType();

            if (cameraType.equals(CameraType.THIRD_PERSON_FRONT) || cameraType.equals(CameraType.THIRD_PERSON_BACK)) {
                event.setCanceled(true);

                if (ridding.getEntityData().get(IRocketEntity.ROCKET_START)) {
                    Methods.bobView(event.getPoseStack(), event.getTick());
                }
            }
        }
    }

    @SubscribeEvent
    public static void renderPlayerArm(RenderArmEvent event) {
        AbstractClientPlayer player = event.getPlayer();
        PlayerRenderer renderer = (PlayerRenderer) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(player);
        PlayerModel<AbstractClientPlayer> playerModel = renderer.getModel();

        Item item = player.getOffhandItem().getItem();
        Item item2 = player.getMainHandItem().getItem();

        if (item instanceof VehicleItem || item2 instanceof VehicleItem) {
            event.setCanceled(true);
            return;
        }

        if (event.getArm() == HumanoidArm.RIGHT) {
            event.setCanceled(Methods.armRenderer(player, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), playerModel, renderer));
        } else {
            event.setCanceled(Methods.armRenderer(player, event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), playerModel, renderer));
        }
    }

    @SubscribeEvent
    public static void render(RenderPlayerEvent event) {
        if (event.getEntity().getVehicle() instanceof LanderEntity) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void setupPlayerAngles(SetupLivingBipedAnimEvent.Post event) {
        if (!(event.getLivingEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getLivingEntity();
        HumanoidModel model = event.getModel();

        //Player Rocket Sit Rotations, Player Hold Rotation
        if (Methods.isRocket(player.getVehicle())) {
            model.rightLeg.xRot = 0F;
            model.leftLeg.xRot = 0F;
            model.rightLeg.yRot = 0F;
            model.leftLeg.yRot = 0F;
            model.rightLeg.zRot = 0F;
            model.leftLeg.zRot = 0F;

            // Arms
            model.rightArm.xRot = -0.07f;
            model.leftArm.xRot = -0.07f;
        }
        else if (!Methods.isRocket(player.getVehicle())) {
            Item item1 = player.getMainHandItem().getItem();
            Item item2 = player.getOffhandItem().getItem();

            if (item1 instanceof VehicleItem || item2 instanceof VehicleItem) {
                model.rightArm.xRot = 10F;
                model.leftArm.xRot = 10F;
                model.rightArm.yRot = 0F;
                model.leftArm.yRot = 0F;
                model.rightArm.zRot = 0F;
                model.leftArm.zRot = 0F;
            }
        }
    }
}
