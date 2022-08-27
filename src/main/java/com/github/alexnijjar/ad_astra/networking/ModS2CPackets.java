package com.github.alexnijjar.ad_astra.networking;

import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.data.ButtonColour;
import com.github.alexnijjar.ad_astra.data.Planet;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ModS2CPackets {

	public static final Identifier DATAPACK_PLANETS = new ModIdentifier("datapack_planets");
	public static final Identifier START_ROCKET = new ModIdentifier("start_rocket");

	@Environment(EnvType.CLIENT)
	public static void register() {
		// Sound packet.

		// Receive planet data on client.
		ClientPlayNetworking.registerGlobalReceiver(DATAPACK_PLANETS, (client, handler, mainBuf, responseSender) -> {
			AdAstra.planets = new HashSet<>(mainBuf.readList(buf -> {

				String translation = buf.readString();
				Identifier galaxy = buf.readIdentifier();
				Identifier solarSystem = buf.readIdentifier();
				RegistryKey<World> dimension = RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier());
				RegistryKey<World> orbitDimension = RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier());
				Identifier id = buf.readIdentifier();
				RegistryKey<World> parentWorld = id.equals(new Identifier("empty")) ? null : RegistryKey.of(Registry.WORLD_KEY, id);
				int rocketTier = buf.readInt();
				float gravity = buf.readFloat();
				boolean hasAtmosphere = buf.readBoolean();
				int daysInYear = buf.readInt();
				float temperature = buf.readFloat();
				long solarPower = buf.readLong();
				long orbitSolarPower = buf.readLong();
				boolean hasOxygen = buf.readBoolean();
				ButtonColour buttonColour = buf.readEnumConstant(ButtonColour.class);

				return new Planet(translation, galaxy, solarSystem, dimension, orbitDimension, parentWorld, rocketTier, gravity, hasAtmosphere, daysInYear, temperature, solarPower, orbitSolarPower, hasOxygen, buttonColour);
			}));
			AdAstra.planetWorlds = AdAstra.planets.stream().map(Planet::world).collect(Collectors.toSet());
			AdAstra.orbitWorlds = AdAstra.planets.stream().map(Planet::orbitWorld).collect(Collectors.toSet());
			AdAstra.adAstraWorlds = Stream.concat(AdAstra.planetWorlds.stream(), AdAstra.orbitWorlds.stream()).collect(Collectors.toSet());
			AdAstra.worldsWithOxygen = AdAstra.planets.stream().filter(Planet::hasOxygen).map(Planet::world).collect(Collectors.toSet());
		});

		// Tells each client to start rendering the rocket launch.
		ClientPlayNetworking.registerGlobalReceiver(START_ROCKET, (client, handler, buf, responseSender) -> {
			int id = buf.readInt();
			if (client.world.getEntityById(id) instanceof RocketEntity rocket) {
				if (!rocket.isFlying()) {
					rocket.initiateLaunchSequence();
				}
			}
		});
	}
}