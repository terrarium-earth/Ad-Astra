package earth.terrarium.ad_astra.client.registry;

import java.util.function.Consumer;

import org.lwjgl.glfw.GLFW;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.client.KeybindPacket;
import earth.terrarium.ad_astra.common.networking.packet.client.LaunchRocketPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

@Environment(EnvType.CLIENT)
public class ClientModKeybindings {

    public static final String CATEGORY = "key.categories." + AdAstra.MOD_ID;
    public static final KeyMapping JET_SUIT_TOGGLE_POWER_KEY = createKeyMapping(KeybindPacket.Keybind.JET_SUIT_TOGGLE_POWER, GLFW.GLFW_KEY_COMMA);
    public static final KeyMapping JET_SUIT_TOGGLE_HOVER_KEY = createKeyMapping(KeybindPacket.Keybind.JET_SUIT_TOGGLE_HOVER, GLFW.GLFW_KEY_PERIOD);

    public static boolean clickingJump;
    public static boolean clickingSprint;
    public static boolean clickingForward;
    public static boolean clickingBack;
    public static boolean clickingLeft;
    public static boolean clickingRight;
    public static boolean clickingJetSuitTogglePower;
    public static boolean clickingJetSuitToggleHover;

    private static boolean sentJumpPacket;
    private static boolean sentSprintPacket;
    private static boolean sentForwardPacket;
    private static boolean sentBackPacket;
    private static boolean sentLeftPacket;
    private static boolean sentRightPacket;
    private static boolean sentJetSuitTogglePower;
    private static boolean sentJetSuitToggleHover;

    private static KeyMapping createKeyMapping(KeybindPacket.Keybind key, int keyCode) {
        return new KeyMapping("key." + AdAstra.MOD_ID + "." + key.name().toLowerCase(), keyCode, CATEGORY);
    }

    public static void onRegisterKeyMappings(Consumer<KeyMapping> register) {
        register.accept(JET_SUIT_TOGGLE_POWER_KEY);
        register.accept(JET_SUIT_TOGGLE_HOVER_KEY);
    }

    public static void onStartTick(Minecraft minecraft) {

        clickingJump = minecraft.options.keyJump.isDown();
        clickingSprint = minecraft.options.keySprint.isDown();
        clickingForward = minecraft.options.keyUp.isDown();
        clickingBack = minecraft.options.keyDown.isDown();
        clickingLeft = minecraft.options.keyLeft.isDown();
        clickingRight = minecraft.options.keyRight.isDown();
        clickingJetSuitTogglePower = JET_SUIT_TOGGLE_POWER_KEY.isDown();
        clickingJetSuitToggleHover = JET_SUIT_TOGGLE_HOVER_KEY.isDown();

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

            if (sentJetSuitTogglePower != clickingJetSuitTogglePower) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.JET_SUIT_TOGGLE_POWER, clickingJetSuitTogglePower));
                sentJetSuitTogglePower = clickingJetSuitTogglePower;
            }

            if (sentJetSuitToggleHover != clickingJetSuitToggleHover) {
                NetworkHandling.CHANNEL.sendToServer(new KeybindPacket(KeybindPacket.Keybind.JET_SUIT_TOGGLE_HOVER, clickingJetSuitToggleHover));
                sentJetSuitToggleHover = clickingJetSuitToggleHover;
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
