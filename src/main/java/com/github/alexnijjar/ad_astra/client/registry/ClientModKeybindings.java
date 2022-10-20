package com.github.alexnijjar.ad_astra.client.registry;

import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.networking.ModC2SPackets;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
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
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.JUMP));
					sentJumpPacket = false;
				}

				if (clickingSprint && sentSprintPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.SPRINT));
					sentSprintPacket = false;
				}

				if (clickingForward && sentForwardPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.FORWARD));
					sentForwardPacket = false;
				}

				if (clickingBack && sentBackPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.BACK));
					sentBackPacket = false;
				}

				if (clickingLeft && sentLeftPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.LEFT));
					sentLeftPacket = false;
				}

				if (clickingRight && sentRightPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyDownBuf(ModKeyBindings.Key.RIGHT));
					sentRightPacket = false;
				}

				if (!clickingJump && !sentJumpPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.JUMP));
					sentJumpPacket = true;
				}

				if (!clickingSprint && !sentSprintPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.SPRINT));
					sentSprintPacket = true;
				}

				if (!clickingForward && !sentForwardPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.FORWARD));
					sentForwardPacket = true;
				}

				if (!clickingBack && !sentBackPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.BACK));
					sentBackPacket = true;
				}

				if (!clickingLeft && !sentLeftPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.LEFT));
					sentLeftPacket = true;
				}

				if (!clickingRight && !sentRightPacket) {
					ClientPlayNetworking.send(ModC2SPackets.KEY_CHANGED, createKeyUpBuf(ModKeyBindings.Key.RIGHT));
					sentRightPacket = true;
				}
			}
		});
	}

	private static PacketByteBuf createKeyDownBuf(ModKeyBindings.Key key) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeEnumConstant(key);
		buf.writeBoolean(true);
		return buf;
	}

	private static PacketByteBuf createKeyUpBuf(ModKeyBindings.Key key) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeEnumConstant(key);
		buf.writeBoolean(false);
		return buf;
	}
}
