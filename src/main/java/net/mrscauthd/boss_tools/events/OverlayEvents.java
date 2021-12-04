package net.mrscauthd.boss_tools.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.CapabilityOxygen;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.entity.*;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId, value = Dist.CLIENT)
public class OverlayEvents {

    public static boolean check = false;
    public static double counter = 0;

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!check) {
                counter = counter - 0.10;
                if (counter < 0.2) {
                    check = true;
                }
            }
            if (check) {
                counter = counter + 0.10;
                if (counter > 1) {
                    check = false;
                }
            }
        }
    }

    public static void startOverlaySettings() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableBlend();
        RenderSystem.disableAlphaTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void stopOverlaySettings() {
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @SuppressWarnings("resource")
	@SubscribeEvent(priority = EventPriority.HIGH)
    public static void Overlay(RenderGameOverlayEvent event) {

        /**Disable Food Overlay*/
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTHMOUNT) {
            PlayerEntity entity = Minecraft.getInstance().player;
            if (Methodes.AllVehiclesOr(entity.getRidingEntity())) {
                event.setCanceled(true);
            }
        }

        /**Lander Warning Overlay*/
        if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
            PlayerEntity entity = Minecraft.getInstance().player;

            startOverlaySettings();

            if (entity.getRidingEntity() instanceof LanderEntity && !entity.getRidingEntity().isOnGround() && !entity.areEyesInFluid(FluidTags.WATER)) {

                RenderSystem.color4f((float) counter, (float) counter, (float) counter, (float) counter);

                Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/overlay/warning.png"));
                Minecraft.getInstance().ingameGUI.blit(event.getMatrixStack(), 0, 0, 0, 0, event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight(), event.getWindow().getScaledWidth(), event.getWindow().getScaledHeight());
            }

            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

            if (entity.getRidingEntity() instanceof LanderEntity && !entity.getRidingEntity().isOnGround() && !entity.areEyesInFluid(FluidTags.WATER)) {

                double speed = Math.round(100.0 * (entity.getRidingEntity()).getMotion().getY()) / 100.0;
                double speedcheck = speed;

                Minecraft.getInstance().fontRenderer.drawString(event.getMatrixStack(), "" + speedcheck + " Speed", event.getWindow().getScaledWidth() / 2 - 29, event.getWindow().getScaledHeight() / 2 / 2.3f, -3407872);
            }

            stopOverlaySettings();
        }

        /**Rocket Timer*/
        if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {
            PlayerEntity entity = Minecraft.getInstance().player;

            startOverlaySettings();

            if (Methodes.isRocket(entity.getRidingEntity())) {
                int timer = 0;

                if (entity.getRidingEntity() instanceof RocketTier1Entity) {
                    timer = entity.getRidingEntity().getDataManager().get(RocketTier1Entity.START_TIMER);
                }

                if (entity.getRidingEntity() instanceof RocketTier2Entity) {
                    timer = entity.getRidingEntity().getDataManager().get(RocketTier2Entity.START_TIMER);
                }

                if (entity.getRidingEntity() instanceof RocketTier3Entity) {
                    timer = entity.getRidingEntity().getDataManager().get(RocketTier3Entity.START_TIMER);
                }

                int width = event.getWindow().getScaledWidth() / 2 - 31;
                int high = event.getWindow().getScaledHeight() / 2 / 2;

                IngameGui mc = Minecraft.getInstance().ingameGUI;
                TextureManager manager = Minecraft.getInstance().textureManager;

                if (timer > -1 && timer < 20) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer10.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 20 && timer < 40) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer9.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 40 && timer < 60) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer8.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 60 && timer < 80) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer7.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 80 && timer < 100) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer6.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 100 && timer < 120) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer5.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 120 && timer < 140) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer4.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 140 && timer < 160) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer3.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 160 && timer < 180) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer2.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 180 && timer < 200) {
                    manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/timer/timer1.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }

            }
            stopOverlaySettings();
        }

        /**Oxygen Tank Overlay*/
        if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {

            PlayerEntity entity = Minecraft.getInstance().player;
            ItemStack chest = entity.getItemStackFromSlot(EquipmentSlotType.CHEST);
            Item chestItem = chest.getItem();

            startOverlaySettings();

            if (chestItem == ModInnet.SPACE_SUIT.get() || chestItem == ModInnet.NETHERITE_SPACE_SUIT.get()) {

                IOxygenStorage oxygenStorage = chest.getCapability(CapabilityOxygen.OXYGEN).orElse(null);
                double oxygenStoredRatio = oxygenStorage != null ? oxygenStorage.getOxygenStoredRatio() : 0.0D;
                ResourceLocation empty = new ResourceLocation(BossToolsMod.ModId, "textures/overlay/oxygentankcheck_empty.png");
                ResourceLocation full = new ResourceLocation(BossToolsMod.ModId, "textures/overlay/oxygentankcheck_full.png");

                int x = 5;
                int y = 5;
                int width = 62;
                int height = 52;
                
                GuiHelper.drawVerticalReverse(event.getMatrixStack(), x, y, width, height, empty, oxygenStoredRatio);
                GuiHelper.drawVertical(event.getMatrixStack(), x, y, width, height, full, oxygenStoredRatio);
                
                IFormattableTextComponent text = GaugeTextHelper.getPercentText(GaugeValueHelper.getOxygen(oxygenStorage)).build();
                int textWidth = Minecraft.getInstance().fontRenderer.getStringPropertyWidth(text);
                Minecraft.getInstance().fontRenderer.func_243246_a(event.getMatrixStack(), text, (x + (width - textWidth) / 2), y + height + 3, 0xFFFFFF);
            }

            stopOverlaySettings();
        }

        /**ROCKET HIGH OVERLAY*/
        if (!event.isCancelable() && event.getType() == RenderGameOverlayEvent.ElementType.HELMET) {

            PlayerEntity entity = Minecraft.getInstance().player;
            IngameGui mc = Minecraft.getInstance().ingameGUI;
            TextureManager manager = Minecraft.getInstance().textureManager;
            World world = Minecraft.getInstance().world;

            startOverlaySettings();

            if (Methodes.isRocket(entity.getRidingEntity()) || entity.getRidingEntity() instanceof LanderEntity) {
                int width = event.getWindow().getScaledWidth();
                int high = event.getWindow().getScaledHeight();

                float yHeight = (float) entity.getPosY() / 5.3F;

                if (yHeight < 0) {
                    yHeight = 0;
                }

                if (yHeight > 113) {
                    yHeight = 113;
                }

                ResourceLocation planet;

                if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "moon"))) {
                    planet = new ResourceLocation(BossToolsMod.ModId, "textures/planet_bar/rocket_y_main_moon.png");

                } else if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "mars"))) {
                    planet = new ResourceLocation(BossToolsMod.ModId, "textures/planet_bar/rocket_y_main_mars.png");

                } else if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "mercury"))) {
                    planet = new ResourceLocation(BossToolsMod.ModId, "textures/planet_bar/rocket_y_main_mercury.png");

                } else if (Methodes.isWorld(world, new ResourceLocation(BossToolsMod.ModId, "venus"))) {
                    planet = new ResourceLocation(BossToolsMod.ModId, "textures/planet_bar/rocket_y_main_venus.png");

                } else if (Methodes.isOrbitWorld(world)) {
                    planet = new ResourceLocation(BossToolsMod.ModId, "textures/planet_bar/rocket_y_main_orbit.png");

                } else {
                    planet = new ResourceLocation(BossToolsMod.ModId, "textures/planet_bar/rocket_y_main_earth.png");
                }

                manager.bindTexture(planet);
                mc.blit(event.getMatrixStack(), 0, (high / 2) - 128 / 2, 0, 0, 16, 128, 16, 128);

                manager.bindTexture(new ResourceLocation(BossToolsMod.ModId, "textures/planet_bar/rocket_y.png"));
                GuiHelper.blit(event.getMatrixStack(), 4, (high / 2) + (103 / 2) - yHeight, 0, 0, 8, 11, 8, 11);
            }
            stopOverlaySettings();
        }
    }
}
