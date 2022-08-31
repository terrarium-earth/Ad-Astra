package com.github.alexnijjar.ad_astra.networking;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.registry.ModRecipes;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import com.github.alexnijjar.ad_astra.util.ModUtils;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ModC2SPackets {

	public static final Identifier TELEPORT_TO_PLANET = new ModIdentifier("teleport_to_planet");
	public static final Identifier DELETE_SPACE_STATION_ITEMS = new ModIdentifier("delete_space_station_item");
	public static final Identifier CREATE_SPACE_STATION = new ModIdentifier("create_space_station");

	public static final Identifier LAUNCH_ROCKET = new ModIdentifier("launch_rocket");

	public static final Identifier JUMP_KEY_CHANGED = new ModIdentifier("jump_key_changed");
	public static final Identifier SPRINT_KEY_CHANGED = new ModIdentifier("sprint_key_changed");
	public static final Identifier FORWARD_KEY_CHANGED = new ModIdentifier("forward_key_changed");
	public static final Identifier BACK_KEY_CHANGED = new ModIdentifier("back_key_changed");
	public static final Identifier LEFT_KEY_CHANGED = new ModIdentifier("left_key_changed");
	public static final Identifier RIGHT_KEY_CHANGED = new ModIdentifier("right_key_changed");

	public static final Identifier TOGGLE_SHOW_DISTRIBUTOR = new ModIdentifier("toggle_show_distributor");

	public static void register() {

		// Send planets packet.
		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			try {
				sender.sendPacket(ModS2CPackets.DATAPACK_PLANETS, createPlanetsDatapackBuf());
			} catch (Exception e) {
				AdAstra.LOGGER.error("Failed to send datapack values to client: " + e);
				e.printStackTrace();
			}
		});

		// Teleport to a planet.
		ServerPlayNetworking.registerGlobalReceiver(TELEPORT_TO_PLANET, (server, player, handler, buf, responseSender) -> {
			RegistryKey<World> targetDimension = getWorld(buf.readIdentifier());

			// Teleport has to be called on the server thread.
			server.execute(new Runnable() {

				@Override
				public void run() {
					if (player.getVehicle() instanceof RocketEntity) {
						ModUtils.teleportToWorld(targetDimension, player);
					} else {
						ModUtils.teleportPlayer(targetDimension, player);
					}
				}
			});
		});

		// Delete Space Station items.
		ServerPlayNetworking.registerGlobalReceiver(DELETE_SPACE_STATION_ITEMS, (server, player, handler, buf, responseSender) -> {
			PlayerInventory inventory = player.getInventory();

			ModRecipes.SPACE_STATION_RECIPE.getRecipes(player.world).forEach(recipe -> {
				for (int i = 0; i < recipe.getIngredients().size(); i++) {
					inventory.remove(recipe.getIngredients().get(i)::test, recipe.getStackCounts().get(i), inventory);
				}
			});
		});

		// Spawn the Space Station in the world.
		ServerPlayNetworking.registerGlobalReceiver(CREATE_SPACE_STATION, (server, player, handler, buf, responseSender) -> {
			ServerWorld world = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier()));
			// Create the Space Station from the nbt file.
			Structure structure = world.getStructureTemplateManager().getStructureOrBlank(new ModIdentifier("space_station"));
			BlockPos pos = new BlockPos(player.getX() - (structure.getSize().getX() / 2), 100, player.getZ() - (structure.getSize().getZ() / 2));
			structure.place(world, pos, pos, new StructurePlacementData(), world.random, 2);
		});

		// Space was pressed while the player was inside of a rocket.
		ServerPlayNetworking.registerGlobalReceiver(LAUNCH_ROCKET, (server, player, handler, buf, responseSender) -> {
			if (player.getVehicle() instanceof RocketEntity rocket) {
				if (!rocket.isFlying()) {
					rocket.initiateLaunchSequenceFromServer();

					// Tell all clients to start rendering the rocket launch.
					int id = buf.readInt();
					for (ServerPlayerEntity serverPlayer : server.getPlayerManager().getPlayerList()) {
						PacketByteBuf buffer = PacketByteBufs.create();
						buffer.writeInt(id);
						serverPlayer.networkHandler.sendPacket(responseSender.createPacket(ModS2CPackets.START_ROCKET, buffer));
					}
				}
			}
		});

		ServerPlayNetworking.registerGlobalReceiver(JUMP_KEY_CHANGED, (server, player, handler, buf, responseSender) -> {
			ModKeyBindings.pressedKeyOnServer(buf.readUuid(), "jump", buf.readBoolean());
		});

		ServerPlayNetworking.registerGlobalReceiver(SPRINT_KEY_CHANGED, (server, player, handler, buf, responseSender) -> {
			ModKeyBindings.pressedKeyOnServer(buf.readUuid(), "sprint", buf.readBoolean());
		});

		ServerPlayNetworking.registerGlobalReceiver(FORWARD_KEY_CHANGED, (server, player, handler, buf, responseSender) -> {
			ModKeyBindings.pressedKeyOnServer(buf.readUuid(), "forward", buf.readBoolean());
		});

		ServerPlayNetworking.registerGlobalReceiver(BACK_KEY_CHANGED, (server, player, handler, buf, responseSender) -> {
			ModKeyBindings.pressedKeyOnServer(buf.readUuid(), "back", buf.readBoolean());
		});

		ServerPlayNetworking.registerGlobalReceiver(LEFT_KEY_CHANGED, (server, player, handler, buf, responseSender) -> {
			ModKeyBindings.pressedKeyOnServer(buf.readUuid(), "left", buf.readBoolean());
		});

		ServerPlayNetworking.registerGlobalReceiver(RIGHT_KEY_CHANGED, (server, player, handler, buf, responseSender) -> {
			ModKeyBindings.pressedKeyOnServer(buf.readUuid(), "right", buf.readBoolean());
		});

		ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOW_DISTRIBUTOR, (server, player, handler, buf, responseSender) -> {
			ServerWorld world = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier()));
			BlockPos pos = buf.readBlockPos();

			server.execute(new Runnable() {
				@Override
				public void run() {
					if (world.getBlockEntity(pos) instanceof OxygenDistributorBlockEntity oxygenDistributor) {
						oxygenDistributor.setShowOxygen(!oxygenDistributor.shouldShowOxygen());
					}
				}
			});
		});
	}

	private static RegistryKey<World> getWorld(Identifier id) {
		RegistryKey<World> targetDimension = RegistryKey.of(Registry.WORLD_KEY, id);
		// Change the "earth" registry key to the "overworld" registry key.
		if (targetDimension.getValue().equals(new ModIdentifier("earth"))) {
			targetDimension = World.OVERWORLD;
		}
		return targetDimension;
	}

	private static PacketByteBuf createPlanetsDatapackBuf() {
		PacketByteBuf mainBuf = PacketByteBufs.create();
		mainBuf.writeCollection(AdAstra.planets, (buf, planet) -> {
			buf.writeString(planet.translation());
			buf.writeIdentifier(planet.galaxy());
			buf.writeIdentifier(planet.solarSystem());
			buf.writeIdentifier(planet.world().getValue());
			buf.writeIdentifier(planet.orbitWorld().getValue());
			buf.writeIdentifier(planet.parentWorld() == null ? new Identifier("empty") : planet.parentWorld().getValue());
			buf.writeInt(planet.rocketTier());
			buf.writeFloat(planet.gravity());
			buf.writeBoolean(planet.hasAtmosphere());
			buf.writeInt(planet.daysInYear());
			buf.writeFloat(planet.temperature());
			buf.writeLong(planet.solarPower());
			buf.writeLong(planet.orbitSolarPower());
			buf.writeBoolean(planet.hasOxygen());
			buf.writeEnumConstant(planet.buttonColour());
		});
		return mainBuf;
	}
}