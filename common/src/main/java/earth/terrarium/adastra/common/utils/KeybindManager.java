package earth.terrarium.adastra.common.utils;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class KeybindManager {
    private static final Map<UUID, KeybindManager> PLAYER_KEYS = new HashMap<>();

    private boolean jumpDown;
    private boolean sprintDown;
    private boolean suitFlightEnabled;

    public KeybindManager() {
    }

    public KeybindManager(boolean jumpDown, boolean sprintDown, boolean suitFlightEnabled) {
        this.jumpDown = jumpDown;
        this.sprintDown = sprintDown;
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

    public static boolean suitFlightEnabled(Player player) {
        return suitFlightEnabled(player.getUUID());
    }

    public static boolean suitFlightEnabled(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager()).suitFlightEnabled;
    }

    public static void set(Player player, boolean jumpDown, boolean sprintDown, boolean suitFlightEnabled) {
        set(player.getUUID(), jumpDown, sprintDown, suitFlightEnabled);
    }

    public static void set(UUID player, boolean jumpDown, boolean sprintDown, boolean suitFlightEnabled) {
        PLAYER_KEYS.put(player, new KeybindManager(jumpDown, sprintDown, suitFlightEnabled));
    }
}