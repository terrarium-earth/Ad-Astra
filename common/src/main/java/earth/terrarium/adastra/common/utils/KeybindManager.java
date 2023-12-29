package earth.terrarium.adastra.common.utils;

import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class KeybindManager {
    private static final Map<UUID, KeybindManager> PLAYER_KEYS = new HashMap<>();

    private boolean suitFlightEnabled;

    public KeybindManager(boolean suitFlightEnabled) {
        this.suitFlightEnabled = suitFlightEnabled;
    }

    public static boolean suitFlightEnabled(Player player) {
        return suitFlightEnabled(player.getUUID());
    }

    public static boolean suitFlightEnabled(UUID player) {
        return PLAYER_KEYS.getOrDefault(player, new KeybindManager(false)).suitFlightEnabled;
    }

    public static void set(Player player, boolean suitFlightEnabled) {
        set(player.getUUID(), suitFlightEnabled);
    }

    public static void set(UUID player, boolean jumpDown) {
        PLAYER_KEYS.put(player, new KeybindManager(jumpDown));
    }
}
