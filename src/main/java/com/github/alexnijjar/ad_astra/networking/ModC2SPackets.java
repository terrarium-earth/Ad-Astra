package com.github.alexnijjar.ad_astra.networking;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.blocks.flags.FlagBlockEntity;
import com.github.alexnijjar.ad_astra.blocks.machines.entity.OxygenDistributorBlockEntity;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.networking.packets.CreateSpaceStationPacket;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.util.ModKeyBindings;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ModC2SPackets {

	public static final Identifier TELEPORT_TO_PLANET = new ModIdentifier("teleport_to_planet");
	public static final Identifier CREATE_SPACE_STATION = new ModIdentifier("create_space_station");
	public static final Identifier LAUNCH_ROCKET = new ModIdentifier("launch_rocket");
	public static final Identifier KEY_CHANGED = new ModIdentifier("key_changed");
	public static final Identifier TOGGLE_SHOW_DISTRIBUTOR = new ModIdentifier("toggle_show_distributor");
	public static final Identifier FLAG_URL = new ModIdentifier("flag_url");

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
			server.execute(() -> {
				if (player.getVehicle() instanceof RocketEntity) {
					ModUtils.teleportToWorld(targetDimension, player);
				} else {
					ModUtils.teleportPlayer(targetDimension, player);
				}
			});
		});

		// Spawn the Space Station in the world.
		ServerPlayNetworking.registerGlobalReceiver(CREATE_SPACE_STATION, new CreateSpaceStationPacket());

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

		ServerPlayNetworking.registerGlobalReceiver(KEY_CHANGED, (server, player, handler, buf, responseSender) ->
				ModKeyBindings.pressedKeyOnServer(player.getUuid(), buf.readEnumConstant(ModKeyBindings.Key.class), buf.readBoolean())
		);

		ServerPlayNetworking.registerGlobalReceiver(TOGGLE_SHOW_DISTRIBUTOR, (server, player, handler, buf, responseSender) -> server.execute(() -> {
			if (player.world.getBlockEntity(buf.readBlockPos()) instanceof OxygenDistributorBlockEntity oxygenDistributor) {
				oxygenDistributor.setShowOxygen(!oxygenDistributor.shouldShowOxygen());
			}
		}));

		ServerPlayNetworking.registerGlobalReceiver(FLAG_URL, (server, player, handler, buf, responseSender) -> {
			BlockPos pos = buf.readBlockPos();
			String url = buf.readString();

			if ((AdAstra.CONFIG.general.allowFlagImages || player.isCreativeLevelTwoOp())) {
				server.execute(() -> {
					if (player.world.getBlockEntity(pos) instanceof FlagBlockEntity flag && flag.getOwner() != null && player.getUuid().equals(flag.getOwner().getId())) {
						flag.setId(url);
						var blockState = player.world.getBlockState(pos);
						player.world.updateListeners(pos, blockState, blockState, Block.NOTIFY_ALL);
					}
				});
			}
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