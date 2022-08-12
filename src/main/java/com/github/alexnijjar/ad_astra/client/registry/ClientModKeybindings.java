package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class ClientModKeybindings {

	public static boolean clickingJump;
	public static boolean clickingSprint;
	public static boolean clickingForward;
	public static boolean clickingBack;
	public static boolean clickingLeft;
	public static boolean clickingRight;
	public static boolean clickingShift;

	private static boolean sentJumpPacket;
	private static boolean sentSprintPacket;
	private static boolean sentForwardPacket;
	private static boolean sentBackPacket;
	private static boolean sentLeftPacket;
	private static boolean sentRightPacket;

	public static void register() {
		ClientTickEvents.END_CLIENT_TICK.register(client -> {

			clickingJump = client.options.jumpKey.isPressed();
			clickingSprint = client.options.sprintKey.isPressed();
			clickingForward = client.options.forwardKey.isPressed();
			clickingBack = client.options.backKey.isPressed();
			clickingLeft = client.options.leftKey.isPressed();
			clickingRight = client.options.rightKey.isPressed();

			if (client.world != null) {
				if (client.player != null) {
					if (client.options.jumpKey.isPressed()) {
						if (client.player.getVehicle() instanceof RocketEntity rocket) {
							if (!rocket.isFlying()) {
								if (rocket.getFluidAmount() >= RocketEntity.getRequiredAmountForLaunch(rocket.getFluidVariant())) {
									PacketByteBuf buf = PacketByteBufs.create();
									buf.writeInt(rocket.getId());
									ClientPlayNetworking.send(ModC2SPackets.LAUNCH_ROCKET, buf);
								} else if (sentJumpPacket) {
									client.player.sendMessage(new TranslatableText("message.ad_astra.no_fuel"), false);
								}
							}
						}
					}
				}

				if (clickingJump && sentJumpPacket) {
					ClientPlayNetworking.send(ModC2SPackets.JUMP_KEY_CHANGED, createKeyDownBuf());
					sentJumpPacket = false;
				}

				if (clickingSprint && sentSprintPacket) {
					ClientPlayNetworking.send(ModC2SPackets.SPRINT_KEY_CHANGED, createKeyDownBuf());
					sentSprintPacket = false;
				}

				if (clickingForward && sentForwardPacket) {
					ClientPlayNetworking.send(ModC2SPackets.FORWARD_KEY_CHANGED, createKeyDownBuf());
					sentForwardPacket = false;
				}

				if (clickingBack && sentBackPacket) {
					ClientPlayNetworking.send(ModC2SPackets.BACK_KEY_CHANGED, createKeyDownBuf());
					sentBackPacket = false;
				}

				if (clickingLeft && sentLeftPacket) {
					ClientPlayNetworking.send(ModC2SPackets.LEFT_KEY_CHANGED, createKeyDownBuf());
					sentLeftPacket = false;
				}

				if (clickingRight && sentRightPacket) {
					ClientPlayNetworking.send(ModC2SPackets.RIGHT_KEY_CHANGED, createKeyDownBuf());
					sentRightPacket = false;
				}

				if (!clickingJump && !sentJumpPacket) {
					ClientPlayNetworking.send(ModC2SPackets.JUMP_KEY_CHANGED, createKeyUpBuf());
					sentJumpPacket = true;
				}

				if (!clickingSprint && !sentSprintPacket) {
					ClientPlayNetworking.send(ModC2SPackets.SPRINT_KEY_CHANGED, createKeyUpBuf());
					sentSprintPacket = true;
				}

				if (!clickingForward && !sentForwardPacket) {
					ClientPlayNetworking.send(ModC2SPackets.FORWARD_KEY_CHANGED, createKeyUpBuf());
					sentForwardPacket = true;
				}

				if (!clickingBack && !sentBackPacket) {
					ClientPlayNetworking.send(ModC2SPackets.BACK_KEY_CHANGED, createKeyUpBuf());
					sentBackPacket = true;
				}

				if (!clickingLeft && !sentLeftPacket) {
					ClientPlayNetworking.send(ModC2SPackets.LEFT_KEY_CHANGED, createKeyUpBuf());
					sentLeftPacket = true;
				}

				if (!clickingRight && !sentRightPacket) {
					ClientPlayNetworking.send(ModC2SPackets.RIGHT_KEY_CHANGED, createKeyUpBuf());
					sentRightPacket = true;
				}
			}
		});
	}

	private static PacketByteBuf createKeyDownBuf() {
		PacketByteBuf buf = PacketByteBufs.create();
		MinecraftClient client = MinecraftClient.getInstance();
		buf.writeUuid(client.player.getUuid());
		buf.writeBoolean(true);
		return buf;
	}

	private static PacketByteBuf createKeyUpBuf() {
		PacketByteBuf buf = PacketByteBufs.create();
		MinecraftClient client = MinecraftClient.getInstance();
		buf.writeUuid(client.player.getUuid());
		buf.writeBoolean(false);
		return buf;
	}
}
