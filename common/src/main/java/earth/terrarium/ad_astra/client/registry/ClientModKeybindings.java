package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.networking.NetworkHandler;
import earth.terrarium.ad_astra.common.networking.packet.messages.ServerboundKeybindPacket;
import earth.terrarium.ad_astra.common.networking.packet.messages.ServerboundLaunchRocketPacket;
import net.minecraft.client.Minecraft;

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
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.JUMP, true));
                sentJumpPacket = false;
            }

            if (clickingSprint && sentSprintPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.SPRINT, true));
                sentSprintPacket = false;
            }

            if (clickingForward && sentForwardPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.FORWARD, true));
                sentForwardPacket = false;
            }

            if (clickingBack && sentBackPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.BACK, true));
                sentBackPacket = false;
            }

            if (clickingLeft && sentLeftPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.LEFT, true));
                sentLeftPacket = false;
            }

            if (clickingRight && sentRightPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.RIGHT, true));
                sentRightPacket = false;
            }

            if (!clickingJump && !sentJumpPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.JUMP, false));
                sentJumpPacket = true;
            }

            if (!clickingSprint && !sentSprintPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.SPRINT, false));
                sentSprintPacket = true;
            }

            if (!clickingForward && !sentForwardPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.FORWARD, false));
                sentForwardPacket = true;
            }

            if (!clickingBack && !sentBackPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.BACK, false));
                sentBackPacket = true;
            }

            if (!clickingLeft && !sentLeftPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.LEFT, false));
                sentLeftPacket = true;
            }

            if (!clickingRight && !sentRightPacket) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundKeybindPacket(ServerboundKeybindPacket.Keybind.RIGHT, false));
                sentRightPacket = true;
            }
        }
    }

    public static void launchRocket() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level != null && minecraft.player != null) {
            if (minecraft.player.getVehicle() instanceof Rocket rocket && !rocket.isFlying()) {
                NetworkHandler.CHANNEL.sendToServer(new ServerboundLaunchRocketPacket());
            }
        }
    }

    public static void init() {
    }
}
