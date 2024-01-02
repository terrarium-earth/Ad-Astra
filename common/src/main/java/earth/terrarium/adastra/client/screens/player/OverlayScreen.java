package earth.terrarium.adastra.client.screens.player;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.config.AdAstraConfigClient;
import earth.terrarium.adastra.client.utils.ClientData;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.entities.vehicles.Lander;
import earth.terrarium.adastra.common.entities.vehicles.Rocket;
import earth.terrarium.adastra.common.handlers.base.PlanetData;
import earth.terrarium.adastra.common.items.armor.JetSuitItem;
import earth.terrarium.adastra.common.items.armor.SpaceSuitItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.Heightmap;

public class OverlayScreen {

    public static final ResourceLocation BATTERY_EMPTY = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/overlay/battery_empty.png");
    public static final ResourceLocation BATTERY = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/overlay/battery.png");
    public static final ResourceLocation OXYGEN_TANK_EMPTY = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/overlay/oxygen_tank_empty.png");
    public static final ResourceLocation OXYGEN_TANK = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/overlay/oxygen_tank.png");
    public static final ResourceLocation ROCKET_BAR = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/overlay/rocket_bar.png");
    public static final ResourceLocation ROCKET = new ResourceLocation(AdAstra.MOD_ID, "textures/gui/sprites/overlay/rocket.png");

    public static void render(GuiGraphics graphics, float partialTick) {
        var player = Minecraft.getInstance().player;
        if (player == null || player.isSpectator()) return;
        var level = player.level();

        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.options.renderDebug) return;
        var font = minecraft.font;
        int width = minecraft.getWindow().getGuiScaledWidth();
        int height = minecraft.getWindow().getGuiScaledHeight();
        PoseStack poseStack = graphics.pose();

        // Rocket overlay
        if (player.getVehicle() instanceof Rocket rocket) {
            int countdown = Mth.ceil(rocket.launchTicks() / 20f);
            if (rocket.isLaunching()) {
                poseStack.pushPose();
                poseStack.translate(width / 2f, height / 2f, 0);
                poseStack.scale(4, 4, 4);
                graphics.drawCenteredString(font, String.valueOf(countdown), 0, -10, 0xe53253);
                poseStack.popPose();
            }

            graphics.blit(ROCKET_BAR, 0, height / 2, 0, 0, 16, 128, 15, 128);

            poseStack.pushPose();
            double y = Mth.clamp(rocket.getY(), 100, AdAstraConfig.atmosphereLeave);
            poseStack.translate(0.3f, (AdAstraConfig.atmosphereLeave - y - 500) / 4.5, 0);
            graphics.blit(ROCKET, 3, height / 2 + 113, 0, 0, 8, 11, 8, 11);
            poseStack.popPose();
        }

        // Oxygen overlay
        var chestStack = player.getInventory().getArmor(2);
        if (SpaceSuitItem.hasFullSet(player) && chestStack.getItem() instanceof SpaceSuitItem spaceSuit) {
            long amount = SpaceSuitItem.getOxygenAmount(player);
            long capacity = spaceSuit.getFluidContainer(chestStack).getTankCapacity(0);
            double ratio = (double) amount / capacity;
            int barHeight = (int) (ratio * 52);

            int x = AdAstraConfigClient.oxygenBarX;
            int y = AdAstraConfigClient.oxygenBarY;
            float scale = AdAstraConfigClient.oxygenBarScale;

            poseStack.pushPose();
            poseStack.scale(scale, scale, scale);
            graphics.blit(OXYGEN_TANK_EMPTY, x, y, 0, 0, 62, 52, 62, 52);
            graphics.blit(OXYGEN_TANK, x, y + 52 - barHeight, 0, 52 - barHeight, 62, barHeight, 62, 52);

            var text = String.format("%.1f%%", ratio * 100);
            int textWidth = font.width(text);
            int color = ratio <= 0 ? 0xDC143C : 0xFFFFFF;
            PlanetData localData = ClientData.getLocalData();
            if (localData != null && localData.oxygen()) {
                color = 0x55ff55;
            }
            graphics.drawString(font, text, (int) (x + (62 - textWidth) / 2f), y + 52 + 3, color);
            poseStack.popPose();
        }

        // Battery overlay
        if (JetSuitItem.hasFullSet(player) && chestStack.getItem() instanceof JetSuitItem jetSuit) {
            long amount = jetSuit.getEnergyStorage(chestStack).getStoredEnergy();
            long capacity = jetSuit.getEnergyStorage(chestStack).getMaxCapacity();
            double ratio = (double) amount / capacity;
            int barWidth = (int) (ratio * 49);

            int x = AdAstraConfigClient.energyBarX;
            int y = AdAstraConfigClient.energyBarY;
            float scale = AdAstraConfigClient.energyBarScale;

            poseStack.pushPose();
            poseStack.scale(scale, scale, scale);
            graphics.blit(BATTERY_EMPTY, x, y, 0, 0, 49, 27, 49, 27);
            graphics.blit(BATTERY, x, y, 0, 27, barWidth, 27, 49, 27);


            var text = String.format("%.1f%%", ratio * 100);
            int textWidth = font.width(text);
            int color = ratio <= 0 ? 0xDC143C : 0x55ffff;
            graphics.drawString(font, text, (int) (x + (49 - textWidth) / 2f), y + 27 + 3, color);
            poseStack.popPose();
        }

        // Lander overlay TODO
        if (player.getVehicle() instanceof Lander lander) {
            int ground = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, lander.blockPosition()).getY();
            int distance = lander.blockPosition().getY() - ground;
        }
    }
}
