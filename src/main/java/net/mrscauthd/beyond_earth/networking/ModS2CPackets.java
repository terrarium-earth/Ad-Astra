package net.mrscauthd.beyond_earth.networking;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.client.BeyondEarthClient;
import net.mrscauthd.beyond_earth.data.ButtonColour;
import net.mrscauthd.beyond_earth.data.Planet;
import net.mrscauthd.beyond_earth.data.SolarSystem;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.world.SoundUtil;

public class ModS2CPackets {

    public static final Identifier PORTAL_SOUND_PACKET_ID = new ModIdentifier("portal_sound_packet");
    public static final Identifier DATAPACK_PLANETS_PACKET_ID = new ModIdentifier("datapack_planets_packet");
    public static final Identifier DATAPACK_RENDERERS_PACKET_ID = new ModIdentifier("datapack_renderers_packet");
    public static final Identifier DATAPACK_SOLAR_SYSTEMS_PACKET_ID = new ModIdentifier("datapack_solar_systems_packet");

    @Environment(EnvType.CLIENT)
    public static void register() {
        // Sound packet.
        ClientPlayNetworking.registerGlobalReceiver(PORTAL_SOUND_PACKET_ID, (client, handler, buf, responseSender) -> {
            SoundUtil.setSound(buf.readBoolean());
        });

        // Receive planet data on client.
        ClientPlayNetworking.registerGlobalReceiver(DATAPACK_PLANETS_PACKET_ID, (client, handler, mainBuf, responseSender) -> {
            BeyondEarthClient.planets = mainBuf.readList(buf -> {

                String name = buf.readString();
                Identifier galaxy = buf.readIdentifier();
                Identifier solarSystem = buf.readIdentifier();
                RegistryKey<World> dimension = RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier());
                RegistryKey<World> orbitDimension = RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier());
                Identifier id = buf.readIdentifier();
                RegistryKey<World> parentDimension = id.equals(new Identifier("empty")) ? null : RegistryKey.of(Registry.WORLD_KEY, id);
                int rocketTier = buf.readInt();
                float gravity = buf.readFloat();
                int daysInYear = buf.readInt();
                int temperature = buf.readInt();
                boolean oxygen = buf.readBoolean();
                int atmosphereStart = buf.readInt();
                ButtonColour buttonColour = buf.readEnumConstant(ButtonColour.class);

                return new Planet(name, galaxy, solarSystem, dimension, orbitDimension, parentDimension, rocketTier, gravity, daysInYear, temperature, oxygen, atmosphereStart, buttonColour);
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(DATAPACK_SOLAR_SYSTEMS_PACKET_ID, (client, handler, mainBuf, responseSender) -> {
            BeyondEarthClient.solarSystems = mainBuf.readList(buf -> {

                Identifier galaxy = buf.readIdentifier();
                Identifier solarSystem = buf.readIdentifier();
                SolarSystem.SunType sunType = buf.readEnumConstant(SolarSystem.SunType.class);
                
                List<Pair<Identifier, Double>> planetaryRings = buf.readList(buf1 -> {
                    return Pair.of(buf1.readIdentifier(), buf1.readDouble());
                });

                return new SolarSystem(galaxy, solarSystem, sunType, planetaryRings);
            });
        });
    }
}