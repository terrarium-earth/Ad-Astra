package earth.terrarium.adastra.common.utils;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class KeybindManager {
    private static final Map<UUID, KeybindManager> PLAYER_KEYS = new HashMap<>();

    private boolean jumpDown;
    private boolean sprintDown;
    private boolean forwardDown;
    private boolean leftDown;
    private boolean backDown;
    private boolean rightDown;
    private boolean suitFlightEnabled;

    public KeybindManager() {
    }

    public KeybindManager(boolean jumpDown, boolean sprintDown, boolean forwardDown, boolean leftDown, boolean backDown, boolean rightDown, boolean suitFlightEnabled) {
        this.jumpDown = jumpDown;
        this.sprintDown = sprintDown;
        this.forwardDown = forwardDown;
        this.leftDown = leftDown;
        this.backDown = backDown;
        this.rightDown = rightDown;
        this.suitFlightEnabled = suitFlightEnabled;
    }

    public static boolean jumpDown(Player player) {
        return jumpDown(player.getUUID());
    }

    public static boolean jumpDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).jumpDown;
    }

    public static boolean sprintDown(Player player) {
        return sprintDown(player.getUUID());
    }

    public static boolean sprintDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).sprintDown;
    }

    public static boolean forwardDown(Player player) {
        return forwardDown(player.getUUID());
    }

    public static boolean forwardDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).forwardDown;
    }

    public static boolean leftDown(Player player) {
        return leftDown(player.getUUID());
    }

    public static boolean leftDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).leftDown;
    }

    public static boolean backDown(Player player) {
        return backDown(player.getUUID());
    }

    public static boolean backDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).backDown;
    }

    public static boolean rightDown(Player player) {
        return rightDown(player.getUUID());
    }

    public static boolean rightDown(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).rightDown;
    }

    public static boolean suitFlightEnabled(Player player) {
        return suitFlightEnabled(player.getUUID());
    }

    public static boolean suitFlightEnabled(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).suitFlightEnabled;
    }

    public byte pack() {
        byte packed = 0;
        packed |= this.jumpDown ? 1 : 0;
        packed |= this.sprintDown ? 2 : 0;
        packed |= this.forwardDown ? 4 : 0;
        packed |= this.leftDown ? 8 : 0;
        packed |= this.backDown ? 16 : 0;
        packed |= this.rightDown ? 32 : 0;
        packed |= this.suitFlightEnabled ? 64 : 0;
        return packed;
    }

    public static KeybindManager unpack(byte packed) {
        KeybindManager keybinds = new KeybindManager();
        keybinds.jumpDown = (packed & 1) != 0;
        keybinds.sprintDown = (packed & 2) != 0;
        keybinds.forwardDown = (packed & 4) != 0;
        keybinds.leftDown = (packed & 8) != 0;
        keybinds.backDown = (packed & 16) != 0;
        keybinds.rightDown = (packed & 32) != 0;
        keybinds.suitFlightEnabled = (packed & 64) != 0;
        return keybinds;
    }

    public static void set(Player player, KeybindManager keybinds) {
        set(player.getUUID(), keybinds);
    }

    public static void set(UUID player, KeybindManager keybinds) {
        PLAYER_KEYS.put(player, keybinds);
    }
}
