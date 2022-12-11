package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.client.KeybindPacket;
import earth.terrarium.ad_astra.common.networking.packet.client.LaunchRocketPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class ClientModKeybindings {

    public static boolean clickingJump;
    public static boolean clickingSprint;
    public static boolean clickingForward;
    public static boolean clickingBack;
    public static boolean clickingLeft;
    public static boolean clickingRight;

    private static boolean sentJumpPacket;
    private static boolean sentSprintPacket;
    private static boolean sentForwardPacket;
    private static boolean sentBackPacket;
    private static boolean sentLeftPacket;
    private static boolean sentRightPacket;

    public static void onStartTick(Minecraft minecraft) {

        clickingJump = minecraft.options.keyJump.isDown();
        clickingSprint = minecraft.options.keySprint.isDown();
        clickingForward = minecraft.options.keyUp.isDown();
        clickingBack = minecraft.options.keyDown.isDown();
        clickingLeft = minecraft.options.keyLeft.isDown();
        clickingRight = minecraft.options.keyRight.isDown();

        if (minecraft.level != null) {

            if (clickingJump && sentJumpPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.JUMP, true));
                sentJumpPacket = false;
            }

            if (clickingSprint && sentSprintPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.SPRINT, true));
                sentSprintPacket = false;
            }

            if (clickingForward && sentForwardPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.FORWARD, true));
                sentForwardPacket = false;
            }

            if (clickingBack && sentBackPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.BACK, true));
                sentBackPacket = false;
            }

            if (clickingLeft && sentLeftPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.LEFT, true));
                sentLeftPacket = false;
            }

            if (clickingRight && sentRightPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.RIGHT, true));
                sentRightPacket = false;
            }

            if (!clickingJump && !sentJumpPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.JUMP, false));
                sentJumpPacket = true;
            }

            if (!clickingSprint && !sentSprintPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.SPRINT, false));
                sentSprintPacket = true;
            }

            if (!clickingForward && !sentForwardPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.FORWARD, false));
                sentForwardPacket = true;
            }

            if (!clickingBack && !sentBackPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.BACK, false));
                sentBackPacket = true;
            }

            if (!clickingLeft && !sentLeftPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.LEFT, false));
                sentLeftPacket = true;
            }

            if (!clickingRight && !sentRightPacket) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.RIGHT, false));
                sentRightPacket = true;
            }
        }
    }

    public static void launchRocket() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.player != null) {
            if (minecraft.player.getVehicle() instanceof Rocket rocket && !rocket.isFlying()) {
                NetworkHandling.CHANNEL.sendToServer(new LaunchRocketPacket());
            }
        }
    }

    public static void init() {
    }
}
