package net.mrscauthd.beyond_earth.events;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
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
            Player entity = Minecraft.getInstance().player;
            if (Methods.AllVehiclesOr(entity.getVehicle())) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void Overlay(RenderGameOverlayEvent.PostLayer event) {
        /**Lander Warning Overlay*/
        if (!event.isCancelable() && event.getOverlay() == ForgeIngameGui.HELMET_ELEMENT) {
            Player entity = Minecraft.getInstance().player;

            startOverlaySettings();

            if (entity.getVehicle() instanceof LanderEntity && !entity.getVehicle().isOnGround() && !entity.isEyeInFluid(FluidTags.WATER)) {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);

                RenderSystem.setShaderColor((float) counter, (float) counter, (float) counter, (float) counter);

                RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/overlay/warning.png"));
                Minecraft.getInstance().gui.blit(event.getMatrixStack(), 0, 0, 0, 0, event.getWindow().getGuiScaledWidth(), event.getWindow().getGuiScaledHeight(), event.getWindow().getGuiScaledWidth(), event.getWindow().getGuiScaledHeight());
            }

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

            if (entity.getVehicle() instanceof LanderEntity && !entity.getVehicle().isOnGround() && !entity.isEyeInFluid(FluidTags.WATER)) {

                double speed = Math.round(100.0 * (entity.getVehicle()).getDeltaMovement().y()) / 100.0;
                double speedcheck = speed;
                
                Component message = new TranslatableComponent("message." + BeyondEarthMod.MODID + ".speed", speedcheck);
                Minecraft.getInstance().font.draw(event.getMatrixStack(), message, event.getWindow().getGuiScaledWidth() / 2 - 29, event.getWindow().getGuiScaledHeight() / 2 / 2.3f, -3407872);
            }

            stopOverlaySettings();
        }

        /**Rocket Timer*/
        if (!event.isCancelable() && event.getOverlay() == ForgeIngameGui.HELMET_ELEMENT) {
            Player entity = Minecraft.getInstance().player;

            startOverlaySettings();

            if (Methods.isRocket(entity.getVehicle())) {
                int timer = 0;

                if (entity.getVehicle() instanceof RocketTier1Entity) {
                    timer = entity.getVehicle().getEntityData().get(RocketTier1Entity.START_TIMER);
                }

                if (entity.getVehicle() instanceof RocketTier2Entity) {
                    timer = entity.getVehicle().getEntityData().get(RocketTier2Entity.START_TIMER);
                }

                if (entity.getVehicle() instanceof RocketTier3Entity) {
                    timer = entity.getVehicle().getEntityData().get(RocketTier3Entity.START_TIMER);
                }

                if (entity.getVehicle() instanceof RocketTier4Entity) {
                    timer = entity.getVehicle().getEntityData().get(RocketTier4Entity.START_TIMER);
                }

                int width = event.getWindow().getGuiScaledWidth() / 2 - 31;
                int high = event.getWindow().getGuiScaledHeight() / 2 / 2;

                Gui mc = Minecraft.getInstance().gui;

                if (timer > -1 && timer < 20) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer10.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 20 && timer < 40) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer9.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 40 && timer < 60) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer8.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 60 && timer < 80) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer7.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 80 && timer < 100) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer6.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 100 && timer < 120) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer5.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 120 && timer < 140) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer4.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 140 && timer < 160) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer3.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 160 && timer < 180) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer2.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }
                if (timer > 180 && timer < 200) {
                    RenderSystem.setShaderTexture(0, new ResourceLocation(BeyondEarthMod.MODID, "textures/timer/timer1.png"));
                    mc.blit(event.getMatrixStack(), width, high, 0, 0, 60, 38, 60, 38);
                }

            }
            stopOverlaySettings();
        }

        /**Oxygen Tank Overlay*/
        if (!event.isCancelable() && event.getOverlay() == ForgeIngameGui.HELMET_ELEMENT) {

            Player entity = Minecraft.getInstance().player;
            ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
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

            Player entity = Minecraft.getInstance().player;
            Gui mc = Minecraft.getInstance().gui;
            TextureManager manager = Minecraft.getInstance().textureManager;
            Level world = Minecraft.getInstance().level;

            startOverlaySettings();

            if (Methods.isRocket(entity.getVehicle()) || entity.getVehicle() instanceof LanderEntity) {
                int width = event.getWindow().getGuiScaledWidth();
                int high = event.getWindow().getGuiScaledHeight();

                float yHeight = (float) entity.getY() / 5.3F;

                if (yHeight < 0) {
                    yHeight = 0;
                }

                if (yHeight > 113) {
                    yHeight = 113;
                }

                ResourceLocation planet;

                if (Methods.isWorld(world, Methods.moon)) {
                    planet = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_moon.png");

                } else if (Methods.isWorld(world, Methods.mars)) {
                    planet = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_mars.png");

                } else if (Methods.isWorld(world, Methods.mercury)) {
                    planet = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_mercury.png");

                } else if (Methods.isWorld(world, Methods.venus)) {
                    planet = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_venus.png");

                } else if (Methods.isWorld(world, Methods.glacio)) {
                    planet = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_glacio.png");

                } else if (Methods.isOrbitWorld(world)) {
                    planet = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_orbit.png");

                } else {
                    planet = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_earth.png");
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
