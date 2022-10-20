package earth.terrarium.ad_astra.util;

import java.util.HashMap;
import java.util.UUID;

import earth.terrarium.ad_astra.client.registry.ClientModKeybindings;

import earth.terrarium.ad_astra.networking.packets.client.KeybindPacket;
import dev.architectury.event.events.common.PlayerEvent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Checks if a key is pressed, on both the client or server. On the server, it's saved for every player by mapping their UUID to an instance of this class. Then, when a specific player presses a key,
 * it's only pressed for that player's UUID.
 */
public class ModKeyBindings {
	public static final HashMap<UUID, ModKeyBindings> PLAYER_KEYS = new HashMap<>();

	private boolean clickingJump;
	private boolean clickingSprint;
	private boolean clickingForward;
	private boolean clickingBack;
	private boolean clickingLeft;
	private boolean clickingRight;

	public static boolean jumpKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, KeybindPacket.Keybind.JUMP) : getServerKeyPressed(player, KeybindPacket.Keybind.JUMP);
	}

	public static boolean sprintKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, KeybindPacket.Keybind.SPRINT) : getServerKeyPressed(player, KeybindPacket.Keybind.SPRINT);
	}

	public static boolean forwardKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, KeybindPacket.Keybind.FORWARD) : getServerKeyPressed(player, KeybindPacket.Keybind.FORWARD);
	}

	public static boolean backKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, KeybindPacket.Keybind.BACK) : getServerKeyPressed(player, KeybindPacket.Keybind.BACK);
	}

	public static boolean leftKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, KeybindPacket.Keybind.LEFT) : getServerKeyPressed(player, KeybindPacket.Keybind.LEFT);
	}

	public static boolean rightKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, KeybindPacket.Keybind.RIGHT) : getServerKeyPressed(player, KeybindPacket.Keybind.RIGHT);
	}

	private static boolean getServerKeyPressed(PlayerEntity player, KeybindPacket.Keybind key) {
		PLAYER_KEYS.putIfAbsent(player.getUuid(), new ModKeyBindings());
		return switch (key) {
			case JUMP -> PLAYER_KEYS.get(player.getUuid()).clickingJump;

			case SPRINT -> PLAYER_KEYS.get(player.getUuid()).clickingSprint;

			case FORWARD -> PLAYER_KEYS.get(player.getUuid()).clickingForward;

			case BACK -> PLAYER_KEYS.get(player.getUuid()).clickingBack;

			case LEFT -> PLAYER_KEYS.get(player.getUuid()).clickingLeft;

			case RIGHT -> PLAYER_KEYS.get(player.getUuid()).clickingRight;
		};
	}

	@Environment(EnvType.CLIENT)
	private static boolean getClientKeyPressed(PlayerEntity player, KeybindPacket.Keybind key) {

		MinecraftClient client = MinecraftClient.getInstance();
		if (!player.getUuid().equals(client.player.getUuid())) {
			return false;
		}

		return switch (key) {
			case JUMP -> ClientModKeybindings.clickingJump;

			case SPRINT -> ClientModKeybindings.clickingSprint;

			case FORWARD -> ClientModKeybindings.clickingForward;

			case BACK -> ClientModKeybindings.clickingBack;

			case LEFT -> ClientModKeybindings.clickingLeft;

			case RIGHT -> ClientModKeybindings.clickingRight;
		};
	}

	public static void pressedKeyOnServer(UUID uuid, KeybindPacket.Keybind key, boolean keyDown) {
		PLAYER_KEYS.putIfAbsent(uuid, new ModKeyBindings());

		switch (key) {
			case JUMP -> PLAYER_KEYS.get(uuid).clickingJump = keyDown;

			case SPRINT -> PLAYER_KEYS.get(uuid).clickingSprint = keyDown;

			case FORWARD -> PLAYER_KEYS.get(uuid).clickingForward = keyDown;

			case BACK -> PLAYER_KEYS.get(uuid).clickingBack = keyDown;

			case LEFT -> PLAYER_KEYS.get(uuid).clickingLeft = keyDown;

			case RIGHT -> PLAYER_KEYS.get(uuid).clickingRight = keyDown;
		}
	}

	static {
		PlayerEvent.PLAYER_QUIT.register(player -> PLAYER_KEYS.remove(player.getUuid()));
	}
}
