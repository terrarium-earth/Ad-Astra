package com.github.alexnijjar.ad_astra.util;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.registry.ClientModKeybindings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashMap;
import java.util.UUID;

/**
 * Checks if a key is pressed, on both the client or server. On the server, it's saved for every player by mapping their UUID to
 * an instance of this class. Then, when a specific player presses a key, it's only pressed for that player's UUID.
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
		return player.world.isClient ? getClientKeyPressed(player, Key.JUMP) : getServerKeyPressed(player, Key.JUMP);
	}

	public static boolean sprintKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, Key.SPRINT) : getServerKeyPressed(player, Key.SPRINT);
	}

	public static boolean forwardKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, Key.FORWARD) : getServerKeyPressed(player, Key.FORWARD);
	}

	public static boolean backKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, Key.BACK) : getServerKeyPressed(player, Key.BACK);
	}

	public static boolean leftKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, Key.LEFT) : getServerKeyPressed(player, Key.LEFT);
	}

	public static boolean rightKeyDown(PlayerEntity player) {
		return player.world.isClient ? getClientKeyPressed(player, Key.RIGHT) : getServerKeyPressed(player, Key.RIGHT);
	}

	private static boolean getServerKeyPressed(PlayerEntity player, Key key) {
		PLAYER_KEYS.putIfAbsent(player.getUuid(), new ModKeyBindings());
		return switch (key) {
			case JUMP ->  PLAYER_KEYS.get(player.getUuid()).clickingJump;
			case SPRINT -> PLAYER_KEYS.get(player.getUuid()).clickingSprint;
			case FORWARD -> PLAYER_KEYS.get(player.getUuid()).clickingForward;
			case BACK -> PLAYER_KEYS.get(player.getUuid()).clickingBack;
			case LEFT -> PLAYER_KEYS.get(player.getUuid()).clickingLeft;
			case RIGHT -> PLAYER_KEYS.get(player.getUuid()).clickingRight;
			default -> {
				AdAstra.LOGGER.warn("Invalid Keypress on server: " + key);
				yield  false;
			}
		};
	}

	@Environment(EnvType.CLIENT)
	private static boolean getClientKeyPressed(PlayerEntity player, Key key) {

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
			default -> {
				AdAstra.LOGGER.warn("Invalid Keypress on client: " + key);
				yield false;
			}
		};
	}

	public static void pressedKeyOnServer(UUID uuid, Key key, boolean keyDown) {
		PLAYER_KEYS.putIfAbsent(uuid, new ModKeyBindings());
		switch (key) {
			case JUMP -> PLAYER_KEYS.get(uuid).clickingJump = keyDown;
			case SPRINT -> PLAYER_KEYS.get(uuid).clickingSprint = keyDown;
			case FORWARD -> PLAYER_KEYS.get(uuid).clickingForward = keyDown;
			case BACK -> PLAYER_KEYS.get(uuid).clickingBack = keyDown;
			case LEFT -> PLAYER_KEYS.get(uuid).clickingLeft = keyDown;
			case RIGHT -> PLAYER_KEYS.get(uuid).clickingRight = keyDown;
			default -> AdAstra.LOGGER.warn("Invalid Keypress on server packet: " + key);
		}
	}

	static {
		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
			PLAYER_KEYS.remove(handler.getPlayer().getUuid());
		});
	}

	public enum Key {
		JUMP,
		SPRINT,
		FORWARD,
		BACK,
		LEFT,
		RIGHT
	}
}
