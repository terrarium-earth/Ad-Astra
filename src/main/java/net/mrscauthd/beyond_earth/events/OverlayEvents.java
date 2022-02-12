package net.mrscauthd.beyond_earth.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.capability.oxygen.CapabilityOxygen;
import net.mrscauthd.beyond_earth.capability.oxygen.IOxygenStorage;
import net.mrscauthd.beyond_earth.entity.*;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, value = Dist.CLIENT)
public class OverlayEvents {

    private static boolean check = false;
    private static float counter = 0;

    private static final ResourceLocation moon = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_moon.png");
    private static final ResourceLocation mars = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_mars.png");
    private static final ResourceLocation mercury = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_mercury.png");
    private static final ResourceLocation venus = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_venus.png");
    private static final ResourceLocation glacio = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_glacio.png");
    private static final ResourceLocation earth = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_earth.png");
    private static final ResourceLocation orbit = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_orbit.png");

    private static final ResourceLocation timerN1 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer1.png");
    private static final ResourceLocation timerN2 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer2.png");
    private static final ResourceLocation timerN3 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer3.png");
    private static final ResourceLocation timerN4 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer4.png");
    private static final ResourceLocation timerN5 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer5.png");
    private static final ResourceLocation timerN6 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer6.png");
    private static final ResourceLocation timerN7 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer7.png");
    private static final ResourceLocation timerN8 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer8.png");
    private static final ResourceLocation timerN9 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer9.png");
    private static final ResourceLocation timerN10 = new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer10.png");

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (check) {
                counter = counter + 0.10F;
                if (counter > 1) {
                    check = false;
                }
            } else {
                counter = counter - 0.10F;
                if (counter < 0.2) {
                    check = true;
                }
            }
        }
    }

    public static void startOverlaySettings() {
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void stopOverlaySettings() {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(true);
        RenderSystem.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @SubscribeEvent
    public static void overlayRegistry(RenderGameOverlayEvent.PreLayer event) {
        /**Disable Food Overlay*/
        if (event.getOverlay() == ForgeIngameGui.MOUNT_HEALTH_ELEMENT) {
            Player player = Minecraft.getInstance().player;

            if (Methods.isVehicle(player.getVehicle())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void Overlay(RenderGameOverlayEvent.PostLayer event) {

        /**Lander Warning Overlay*/
        if (!event.isCancelable() && event.getOverlay() == ForgeIngameGui.HELMET_ELEMENT) {
            Player player = Minecraft.getInstance().player;

            startOverlaySettings();

            if (player.getVehicle() instanceof LanderEntity && !player.getVehicle().isOnGround() && !player.isEyeInFluid(FluidTags.WATER)) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);

                RenderSystem.setShaderColor(counter, counter, counter, counter);

                RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/overlay/warning.png"));
                Minecraft.getInstance().gui.blit(event.getMatrixStack(), 0, 0, 0, 0, event.getWindow().getGuiScaledWidth(), event.getWindow().getGuiScaledHeight(), event.getWindow().getGuiScaledWidth(), event.getWindow().getGuiScaledHeight());
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            if (player.getVehicle() instanceof LanderEntity && !player.getVehicle().isOnGround() && !player.isEyeInFluid(FluidTags.WATER)) {

                double speed = Math.round(100.0 * (player.getVehicle()).getDeltaMovement().y()) / 100.0;
                
                Component message = new TranslatableComponent("message." + BeyondEarthMod.MODID + ".speed", speed);
                Minecraft.getInstance().font.draw(event.getMatrixStack(), message, event.getWindow().getGuiScaledWidth() / 2 - 29, event.getWindow().getGuiScaledHeight() / 2 / 2.3f, -3407872);
            }

            stopOverlaySettings();
        }

        /**Rocket Timer*/
        if (!event.isCancelable() && event.getOverlay() == ForgeIngameGui.HELMET_ELEMENT) {
            Player player = Minecraft.getInstance().player;

            startOverlaySettings();

            if (Methods.isRocket(player.getVehicle())) {
                int timer = 0;

                if (player.getVehicle() instanceof RocketTier1Entity) {
                    timer = player.getVehicle().getEntityData().get(RocketTier1Entity.START_TIMER);
                }
                else if (player.getVehicle() instanceof RocketTier2Entity) {
                    timer = player.getVehicle().getEntityData().get(RocketTier2Entity.START_TIMER);
                }
                else if (player.getVehicle() instanceof RocketTier3Entity) {
                    timer = player.getVehicle().getEntityData().get(RocketTier3Entity.START_TIMER);
                }
                else if (player.getVehicle() instanceof RocketTier4Entity) {
                    timer = player.getVehicle().getEntityData().get(RocketTier4Entity.START_TIMER);
                }

                int width = event.getWindow().getGuiScaledWidth() / 2 - 31;
                int high = event.getWindow().getGuiScaledHeight() / 2 / 2;

                Gui mc = Minecraft.getInstance().gui;

                if (timer > -1 && timer < 20) {
                    RenderSystem.setShaderTexture(0, timerN10);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 20 && timer < 40) {
                    RenderSystem.setShaderTexture(0, timerN9);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 40 && timer < 60) {
                    RenderSystem.setShaderTexture(0, timerN8);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 60 && timer < 80) {
                    RenderSystem.setShaderTexture(0, timerN7);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 80 && timer < 100) {
                    RenderSystem.setShaderTexture(0, timerN6);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 100 && timer < 120) {
                    RenderSystem.setShaderTexture(0, timerN5);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 120 && timer < 140) {
                    RenderSystem.setShaderTexture(0, timerN4);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 140 && timer < 160) {
                    RenderSystem.setShaderTexture(0, timerN3);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 160 && timer < 180) {
                    RenderSystem.setShaderTexture(0, timerN2);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                else if (timer > 180 && timer < 200) {
                    RenderSystem.setShaderTexture(0, timerN1);
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
            }
            stopOverlaySettings();
        }

        /**Oxygen Tank Overlay*/
        if (!event.isCancelable() && event.getOverlay() == ForgeIngameGui.HELMET_ELEMENT) {

            Player player = Minecraft.getInstance().player;
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
            Item chestItem = chest.getItem();

            startOverlaySettings();

            if (chestItem == ModInit.SPACE_SUIT.get() || chestItem == ModInit.NETHERITE_SPACE_SUIT.get()) {

                IOxygenStorage oxygenStorage = chest.getCapability(CapabilityOxygen.OXYGEN).orElse(null);
                double oxygenStoredRatio = oxygenStorage != null ? oxygenStorage.getOxygenStoredRatio() : 0.0D;
                ResourceLocation empty = new ResourceLocation(BeyondEarthMod.MODID, "textures/overlay/oxygentankcheck_empty.png");
                ResourceLocation full = new ResourceLocation(BeyondEarthMod.MODID, "textures/overlay/oxygentankcheck_full.png");

                int x = 5;
                int y = 5;
                int width = 62;
                int height = 52;
                
                GuiHelper.drawVerticalReverse(event.getMatrixStack(), x, y, width, height, empty, oxygenStoredRatio);
                GuiHelper.drawVertical(event.getMatrixStack(), x, y, width, height, full, oxygenStoredRatio);

                MutableComponent text = GaugeTextHelper.getPercentText(GaugeValueHelper.getOxygen(oxygenStorage)).build();
                int textWidth = Minecraft.getInstance().font.width(text);
                Minecraft.getInstance().font.drawShadow(event.getMatrixStack(), text, (x + (width - textWidth) / 2), y + height + 3, 0xFFFFFF);
            }

            stopOverlaySettings();
        }

        /**ROCKET HIGH OVERLAY*/
        if (!event.isCancelable() && event.getOverlay() == ForgeIngameGui.HELMET_ELEMENT) {

            Player player = Minecraft.getInstance().player;
            Gui mc = Minecraft.getInstance().gui;
            Level world = Minecraft.getInstance().level;

            startOverlaySettings();

            if (Methods.isRocket(player.getVehicle()) || player.getVehicle() instanceof LanderEntity) {
                int high = event.getWindow().getGuiScaledHeight();

                float yHeight = (float) player.getY() / 5.3F;

                if (yHeight < 0) {
                    yHeight = 0;
                }
                else if (yHeight > 113) {
                    yHeight = 113;
                }

                ResourceLocation planet;

                if (Methods.isWorld(world, Methods.moon)) {
                    planet = moon;

                } else if (Methods.isWorld(world, Methods.mars)) {
                    planet = mars;

                } else if (Methods.isWorld(world, Methods.mercury)) {
                    planet = mercury;

                } else if (Methods.isWorld(world, Methods.venus)) {
                    planet = venus;

                } else if (Methods.isWorld(world, Methods.glacio)) {
                    planet = glacio;

                } else if (Methods.isOrbitWorld(world)) {
                    planet = orbit;

                } else {
                    planet = earth;
                }

                RenderSystem.setShaderTexture(0, planet);
                mc.blit(event.getMatrixStack(), 0, (high / 2) - 128 / 2, 0, 0, 16, 128, 16, 128);

                RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y.png"));
                GuiHelper.blit(event.getMatrixStack(), 4, (high / 2) + (103 / 2) - yHeight, 0, 0, 8, 11, 8, 11);
            }
            stopOverlaySettings();
        }
    }
}
