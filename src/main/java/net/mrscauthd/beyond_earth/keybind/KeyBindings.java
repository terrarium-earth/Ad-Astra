package net.mrscauthd.beyond_earth.keybind;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.mrscauthd.beyond_earth.BeyondEarthMod;
import net.mrscauthd.beyond_earth.entity.*;
import net.mrscauthd.beyond_earth.events.ClientEventBusSubscriber;
import net.mrscauthd.beyond_earth.events.Methodes;
import org.lwjgl.glfw.GLFW;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID)
public class KeyBindings {
	public static SimpleChannel INSTANCE;
	private static int id = 1;

	public static int nextID() {
		return id++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BeyondEarthMod.MODID, "key_bindings"), () -> "1.0", s -> true, s -> true);
		INSTANCE.registerMessage(nextID(), KeyBindingPressedMessage.class, KeyBindingPressedMessage::buffer, KeyBindingPressedMessage::new, KeyBindingPressedMessage::handler);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onKeyInput1(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {

			//Key Space
			if (InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_SPACE)) {
				if (Minecraft.getInstance().screen == null) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(0, 0));
					//pressAction(Minecraft.getInstance().player, 0, 0);
				}
			}

			//Key A
			if ((InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_A))) {
				if (Minecraft.getInstance().screen == null) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(2, 0));
					//(Minecraft.getInstance().player, 2, 0);
				}
			}

			//Key D
			if ((InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), GLFW.GLFW_KEY_D))) {
				if (Minecraft.getInstance().screen == null) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(3, 0));
					//pressAction(Minecraft.getInstance().player, 3, 0);
				}
			}

		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onKeyInput2(InputEvent.KeyInputEvent event) {
		if (Minecraft.getInstance().screen == null) {
			if (event.getKey() == ClientEventBusSubscriber.key1.getKey().getValue()) {
				if (event.getAction() == GLFW.GLFW_PRESS) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(1, 0));
					//pressAction(Minecraft.getInstance().player, 1, 0);
				}
			}
		}
	}

	public static class KeyBindingPressedMessage {
		int type, pressedms;

		public KeyBindingPressedMessage(int type, int pressedms) {
			this.type = type;
			this.pressedms = pressedms;
		}

		public KeyBindingPressedMessage(FriendlyByteBuf buffer) {
			this.type = buffer.readInt();
			this.pressedms = buffer.readInt();
		}

		public static void buffer(KeyBindingPressedMessage message, FriendlyByteBuf buffer) {
			buffer.writeInt(message.type);
			buffer.writeInt(message.pressedms);
		}

		public static void handler(KeyBindingPressedMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				pressAction(context.getSender(), message.type, message.pressedms);
			});
			context.setPacketHandled(true);
		}
	}

	private static void pressAction(ServerPlayer player, int type, int pressedms) {
		Level world = player.level;
		double x = player.getX();
		double y = player.getY();
		double z = player.getZ();

		if (!world.hasChunkAt(new BlockPos(x, y, z)))
			return;

		if (type == 0) {

			Entity ridding = player.getVehicle();

			if (ridding instanceof LanderEntity) {
				if (!ridding.isOnGround() && !ridding.isEyeInFluid(FluidTags.WATER)) {

					if (ridding.getDeltaMovement().y() < -0.05) {
						ridding.setDeltaMovement(ridding.getDeltaMovement().x(), ridding.getDeltaMovement().y() * 0.85, ridding.getDeltaMovement().z());
					}

					if (world instanceof ServerLevel) {
						for (ServerPlayer p : ((ServerLevel) player.level).getServer().getPlayerList().getPlayers()) {
							((ServerLevel) world).sendParticles(p, ParticleTypes.SPIT, true, ridding.getX(), ridding.getY() - 0.3, ridding.getZ(), 3, 0.1, 0.1, 0.1, 0.001);
						}
					}

				}
				ridding.fallDistance = (float) (ridding.getDeltaMovement().y() * (-1) * 4.5);
			}
		}

		if (type == 1) {
			if (Methodes.isRocket(player.getVehicle())) {
				if (player.getVehicle() instanceof RocketTier1Entity && player.getVehicle().getEntityData().get(RocketTier1Entity.FUEL) == 300) {

					if (!player.getVehicle().getEntityData().get(RocketTier1Entity.ROCKET_START)) {
						player.getVehicle().getEntityData().set(RocketTier1Entity.ROCKET_START, true);
						Methodes.RocketSounds(player.getVehicle(), world);
					}

				} else if (player.getVehicle() instanceof RocketTier2Entity && player.getVehicle().getEntityData().get(RocketTier2Entity.FUEL) == 300) {

					if (!player.getVehicle().getEntityData().get(RocketTier2Entity.ROCKET_START)) {
						player.getVehicle().getEntityData().set(RocketTier2Entity.ROCKET_START, true);
						Methodes.RocketSounds(player.getVehicle(), world);
					}

				} else if (player.getVehicle() instanceof RocketTier3Entity && player.getVehicle().getEntityData().get(RocketTier3Entity.FUEL) == 300) {

					if (!player.getVehicle().getEntityData().get(RocketTier3Entity.ROCKET_START)) {
						player.getVehicle().getEntityData().set(RocketTier3Entity.ROCKET_START, true);
						Methodes.RocketSounds(player.getVehicle(), world);
					}

				} else {
					Methodes.noFuelMessage(player);
				}
			}

		}

		if (type == 2) {
			//Rocket
			if (Methodes.isRocket(player.getVehicle())) {
				Methodes.vehicleRotation((LivingEntity) player.getVehicle(), -1);
			}

			//Rover
			if (player.getVehicle() instanceof RoverEntity) {
				float forward = player.zza;

				if (player.getVehicle().getEntityData().get(RoverEntity.FUEL) != 0 && !player.getVehicle().isEyeInFluid(FluidTags.WATER)) {
					if (forward >= 0.01) {
						Methodes.vehicleRotation((LivingEntity) player.getVehicle(), -1);
					}
					if (forward <= -0.01) {
						Methodes.vehicleRotation((LivingEntity) player.getVehicle(), 1);
					}
				}
			}

		}

		if (type == 3) {
			//Rocket
			if (Methodes.isRocket(player.getVehicle())) {
				Methodes.vehicleRotation((LivingEntity) player.getVehicle(), 1);
			}

			//Rover
			if (player.getVehicle() instanceof RoverEntity) {
				float forward = player.zza;

				if (player.getVehicle().getEntityData().get(RoverEntity.FUEL) != 0 && !player.getVehicle().isEyeInFluid(FluidTags.WATER)) {
					if (forward >= 0.01) {
						Methodes.vehicleRotation((LivingEntity) player.getVehicle(), 1);
					}
					if (forward <= -0.01) {
						Methodes.vehicleRotation((LivingEntity) player.getVehicle(), -1);
					}
				}
			}
		}

	}
}