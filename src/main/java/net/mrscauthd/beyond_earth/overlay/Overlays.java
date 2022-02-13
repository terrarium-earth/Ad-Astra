package net.mrscauthd.beyond_earth.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.ModInit;
import net.mrscauthd.beyond_earth.capability.oxygen.CapabilityOxygen;
import net.mrscauthd.beyond_earth.capability.oxygen.IOxygenStorage;
import net.mrscauthd.beyond_earth.entity.*;
import net.mrscauthd.beyond_earth.events.Methods;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.GaugeValueHelper;
import net.mrscauthd.beyond_earth.gui.helper.GuiHelper;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID, value = Dist.CLIENT)
public class Overlays {

    private static boolean check = false;
    private static float counter = 0;

    private static final ResourceLocation warning = new ResourceLocation(BeyondEarthMod.MODID, "textures/overlay/warning.png");

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

    private static final ResourceLocation oxygen_tank_empty = new ResourceLocation(BeyondEarthMod.MODID, "textures/overlay/oxygen_tank_empty.png");
    private static final ResourceLocation oxygen_tank_full = new ResourceLocation(BeyondEarthMod.MODID, "textures/overlay/oxygen_tank_full.png");

    private static final ResourceLocation moon = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_moon.png");
    private static final ResourceLocation mars = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_mars.png");
    private static final ResourceLocation mercury = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_mercury.png");
    private static final ResourceLocation venus = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_venus.png");
    private static final ResourceLocation glacio = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_glacio.png");
    private static final ResourceLocation earth = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_earth.png");
    private static final ResourceLocation orbit = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y_main_orbit.png");

    private static final ResourceLocation rocketY = new ResourceLocation(BeyondEarthMod.MODID, "textures/planet_bar/rocket_y.png");

    /** OVERLAY ENABLE OR DISABLE EVENT */
    @SubscribeEvent
    public static void overlayEnableOrDisable(RenderGameOverlayEvent.PostLayer event) {
        Player player = Minecraft.getInstance().player;
        Item chestItem = player.getItemBySlot(EquipmentSlot.CHEST).getItem();

        /** WARNING OVERLAY */
        if (player.getVehicle() instanceof LanderEntity && !player.getVehicle().isOnGround() && !player.isEyeInFluid(FluidTags.WATER)) {
                OverlayRegistry.enableOverlay(Overlays.WARNING, true);
        } else {
            OverlayRegistry.enableOverlay(Overlays.WARNING, false);
        }

        /** ROCKET TIMER */
        if (Methods.isRocket(player.getVehicle())) {
            OverlayRegistry.enableOverlay(Overlays.ROCKET_TIMER, true);
        } else {
            OverlayRegistry.enableOverlay(Overlays.ROCKET_TIMER, false);
        }

        /** OXYGEN TANK */
        if (chestItem == ModInit.SPACE_SUIT.get() || chestItem == ModInit.NETHERITE_SPACE_SUIT.get()) {
            OverlayRegistry.enableOverlay(Overlays.OXYGEN_TANK, true);
        } else {
            OverlayRegistry.enableOverlay(Overlays.OXYGEN_TANK, false);
        }

        /** ROCKET HEIGHT */
        if (Methods.isRocket(player.getVehicle()) || player.getVehicle() instanceof LanderEntity) {
            OverlayRegistry.enableOverlay(Overlays.ROCKET_HEIGHT, true);
        } else {
            OverlayRegistry.enableOverlay(Overlays.ROCKET_HEIGHT, false);
        }
    }

    /** FLASHING TICK */
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

    /** WARNING OVERLAY */
    public static IIngameOverlay WARNING = new IIngameOverlay() {
        @Override
        public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
            Entity vehicle = Minecraft.getInstance().player.getVehicle();
            RenderSystem.setShader(GameRenderer::getPositionTexShader);

            /** FLASHING */
            RenderSystem.setShaderColor(counter, counter, counter, counter);

            /** WARNING IMAGE */
            RenderSystem.setShaderTexture(0, warning);
            gui.blit(mStack, 0, 0, 0, 0, width, height, width, height);

            /** SPEED TEXT */
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            double speed = Math.round(100.0 * (vehicle).getDeltaMovement().y()) / 100.0;

            Component message = new TranslatableComponent("message." + BeyondEarthMod.MODID + ".speed", speed);
            Minecraft.getInstance().font.draw(mStack, message, width / 2 - 29, height / 2 / 2.3f, -3407872);
        }
    };

    /** ROCKET TIMER OVERLAY */
    public static IIngameOverlay ROCKET_TIMER = new IIngameOverlay() {
        @Override
        public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
            Entity vehicle = Minecraft.getInstance().player.getVehicle();
            int timer = 0;

            /** GET TIMER */
            if (vehicle instanceof RocketTier1Entity) {
                timer = vehicle.getEntityData().get(RocketTier1Entity.START_TIMER);
            }
            else if (vehicle instanceof RocketTier2Entity) {
                timer = vehicle.getEntityData().get(RocketTier2Entity.START_TIMER);
            }
            else if (vehicle instanceof RocketTier3Entity) {
                timer = vehicle.getEntityData().get(RocketTier3Entity.START_TIMER);
            }
            else if (vehicle instanceof RocketTier4Entity) {
                timer = vehicle.getEntityData().get(RocketTier4Entity.START_TIMER);
            }

            int timerWidth = width / 2 - 31;
            int timerHeight = height / 2 / 2;

            /** TIMER */
            if (timer > -1 && timer < 20) {
                RenderSystem.setShaderTexture(0, timerN10);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 20 && timer < 40) {
                RenderSystem.setShaderTexture(0, timerN9);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 40 && timer < 60) {
                RenderSystem.setShaderTexture(0, timerN8);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 60 && timer < 80) {
                RenderSystem.setShaderTexture(0, timerN7);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 80 && timer < 100) {
                RenderSystem.setShaderTexture(0, timerN6);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 100 && timer < 120) {
                RenderSystem.setShaderTexture(0, timerN5);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 120 && timer < 140) {
                RenderSystem.setShaderTexture(0, timerN4);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 140 && timer < 160) {
                RenderSystem.setShaderTexture(0, timerN3);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 160 && timer < 180) {
                RenderSystem.setShaderTexture(0, timerN2);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
            else if (timer > 180 && timer < 200) {
                RenderSystem.setShaderTexture(0, timerN1);
                gui.blit(mStack, timerWidth, timerHeight, 0, 0, 60, 38, 60, 38);
            }
        }
    };

    /** OXYGEN TANK OVERLAY */
    public static IIngameOverlay OXYGEN_TANK = new IIngameOverlay() {
        @Override
        public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
            Player player = Minecraft.getInstance().player;
            ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);

            /** OXYGEN TANK IMAGE */
            IOxygenStorage oxygenStorage = chest.getCapability(CapabilityOxygen.OXYGEN).orElse(null);
            double oxygenStoredRatio = oxygenStorage != null ? oxygenStorage.getOxygenStoredRatio() : 0.0D;

            int x = 5;
            int y = 5;

            int textureWidth = 62;
            int textureHeight = 52;

            GuiHelper.drawVerticalReverse(mStack, x, y, textureWidth, textureHeight, oxygen_tank_empty, oxygenStoredRatio);
            GuiHelper.drawVertical(mStack, x, y, textureWidth, textureHeight, oxygen_tank_full, oxygenStoredRatio);

            /** OXYGEN AMOUNT TEXT */
            MutableComponent text = GaugeTextHelper.getPercentText(GaugeValueHelper.getOxygen(oxygenStorage)).build();
            int textWidth = Minecraft.getInstance().font.width(text);
            Minecraft.getInstance().font.drawShadow(mStack, text, (x + (textureWidth - textWidth) / 2), y + textureHeight + 3, 0xFFFFFF);
        }
    };

    /** ROCKET HEIGHT OVERLAY */
    public static IIngameOverlay ROCKET_HEIGHT = new IIngameOverlay() {
        @Override
        public void render(ForgeIngameGui gui, PoseStack mStack, float partialTicks, int width, int height) {
            Player player = Minecraft.getInstance().player;
            Level world = Minecraft.getInstance().level;

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
            }
            else if (Methods.isWorld(world, Methods.mars)) {
                planet = mars;
            }
            else if (Methods.isWorld(world, Methods.mercury)) {
                planet = mercury;
            }
            else if (Methods.isWorld(world, Methods.venus)) {
                planet = venus;
            }
            else if (Methods.isWorld(world, Methods.glacio)) {
                planet = glacio;
            }
            else if (Methods.isOrbitWorld(world)) {
                planet = orbit;
            }
            else {
                planet = earth;
            }

            /** ROCKET BAR IMAGE */
            RenderSystem.setShaderTexture(0, planet);
            gui.blit(mStack, 0, (height / 2) - 128 / 2, 0, 0, 16, 128, 16, 128);

            /** ROCKET_Y IMAGE */
            RenderSystem.setShaderTexture(0, rocketY);
            GuiHelper.blit(mStack, 4, (height / 2) + (103 / 2) - yHeight, 0, 0, 8, 11, 8, 11);
        }
    };
}
