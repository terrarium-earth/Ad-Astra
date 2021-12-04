package net.mrscauthd.boss_tools.keybind;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.mrscauthd.boss_tools.BossToolsMod;
import net.mrscauthd.boss_tools.entity.*;
import net.mrscauthd.boss_tools.events.ClientEventBusSubscriber;
import net.mrscauthd.boss_tools.events.Methodes;
import org.lwjgl.glfw.GLFW;

import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.PacketBuffer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.client.util.InputMappings;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = BossToolsMod.ModId)
public class KeyBindings {
	public static SimpleChannel INSTANCE;
	private static int id = 1;

	public static int nextID() {
		return id++;
	}

	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(BossToolsMod.ModId, "key_bindings"), () -> "1.0", s -> true, s -> true);
		INSTANCE.registerMessage(nextID(), KeyBindingPressedMessage.class, KeyBindingPressedMessage::buffer, KeyBindingPressedMessage::new, KeyBindingPressedMessage::handler);
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onKeyInput1(TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {

			//Key Space
			if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_SPACE)) {
				if (Minecraft.getInstance().currentScreen == null) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(0, 0));
					pressAction(Minecraft.getInstance().player, 0, 0);
				}
			}

			//Key A
			if ((InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_A))) {
				if (Minecraft.getInstance().currentScreen == null) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(2, 0));
					pressAction(Minecraft.getInstance().player, 2, 0);
				}
			}

			//Key D
			if ((InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_D))) {
				if (Minecraft.getInstance().currentScreen == null) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(3, 0));
					pressAction(Minecraft.getInstance().player, 3, 0);
				}
			}

		}
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void onKeyInput2(InputEvent.KeyInputEvent event) {
		if (Minecraft.getInstance().currentScreen == null) {
			if (event.getKey() == ClientEventBusSubscriber.key1.getKey().getKeyCode()) {
				if (event.getAction() == GLFW.GLFW_PRESS) {
					INSTANCE.sendToServer(new KeyBindingPressedMessage(1, 0));
					pressAction(Minecraft.getInstance().player, 1, 0);
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

		public KeyBindingPressedMessage(PacketBuffer buffer) {
			this.type = buffer.readInt();
			this.pressedms = buffer.readInt();
		}

		public static void buffer(KeyBindingPressedMessage message, PacketBuffer buffer) {
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

	private static void pressAction(PlayerEntity player, int type, int pressedms) {
		World world = player.world;
		double x = player.getPosX();
		double y = player.getPosY();
		double z = player.getPosZ();

		if (!world.isBlockLoaded(new BlockPos(x, y, z)))
			return;

		if (type == 0) {

			Entity ridding = player.getRidingEntity();

			if (ridding instanceof LanderEntity) {
				if (!ridding.isOnGround() && !ridding.areEyesInFluid(FluidTags.WATER)) {

					if (ridding.getMotion().getY() < -0.05) {
						ridding.setMotion(ridding.getMotion().getX(), ridding.getMotion().getY() * 0.85, ridding.getMotion().getZ());
					}

					if (world instanceof ServerWorld) {
						for (ServerPlayerEntity p : ((ServerWorld) world).getPlayers()) {
							((ServerWorld) world).spawnParticle(p, ParticleTypes.SPIT, true, ridding.getPosX(), ridding.getPosY() - 0.3, ridding.getPosZ(), 3, 0.1, 0.1, 0.1, 0.001);
						}
					}

				}
				ridding.fallDistance = (float) (ridding.getMotion().getY() * (-1) * 4.5);
			}
		}

		if (type == 1) {
			if (Methodes.isRocket(player.getRidingEntity())) {
				if (player.getRidingEntity() instanceof RocketTier1Entity && player.getRidingEntity().getDataManager().get(RocketTier1Entity.FUEL) == 300) {

					player.getRidingEntity().getDataManager().set(RocketTier1Entity.ROCKET_START, true);
					Methodes.RocketSounds(player.getRidingEntity(), world);

				} else if (player.getRidingEntity() instanceof RocketTier2Entity && player.getRidingEntity().getDataManager().get(RocketTier2Entity.FUEL) == 300) {

					player.getRidingEntity().getDataManager().set(RocketTier2Entity.ROCKET_START, true);
					Methodes.RocketSounds(player.getRidingEntity(), world);

				} else if (player.getRidingEntity() instanceof RocketTier3Entity && player.getRidingEntity().getDataManager().get(RocketTier3Entity.FUEL) == 300) {

					player.getRidingEntity().getDataManager().set(RocketTier3Entity.ROCKET_START, true);
					Methodes.RocketSounds(player.getRidingEntity(), world);

				} else {
					Methodes.noFuelMessage(player);
				}
			}

		}

		if (type == 2) {
			//Rocket
			if (Methodes.isRocket(player.getRidingEntity())) {
				Methodes.vehicleRotation((LivingEntity) player.getRidingEntity(), -1);
			}

			//Rover
			if (player.getRidingEntity() instanceof RoverEntity) {
				float forward = player.moveForward;

				if (player.getRidingEntity().getDataManager().get(RoverEntity.FUEL) != 0 && !player.getRidingEntity().areEyesInFluid(FluidTags.WATER)) {
					if (forward >= 0.01) {
						Methodes.vehicleRotation((LivingEntity) player.getRidingEntity(), -1);
					}
					if (forward <= -0.01) {
						Methodes.vehicleRotation((LivingEntity) player.getRidingEntity(), 1);
					}
				}
			}

		}

		if (type == 3) {
			//Rocket
			if (Methodes.isRocket(player.getRidingEntity())) {
				Methodes.vehicleRotation((LivingEntity) player.getRidingEntity(), 1);
			}

			//Rover
			if (player.getRidingEntity() instanceof RoverEntity) {
				float forward = player.moveForward;

				if (player.getRidingEntity().getDataManager().get(RoverEntity.FUEL) != 0 && !player.getRidingEntity().areEyesInFluid(FluidTags.WATER)) {
					if (forward >= 0.01) {
						Methodes.vehicleRotation((LivingEntity) player.getRidingEntity(), 1);
					}
					if (forward <= -0.01) {
						Methodes.vehicleRotation((LivingEntity) player.getRidingEntity(), -1);
					}
				}
			}
		}

	}
}