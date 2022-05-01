package net.mrscauthd.beyond_earth.networking;

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
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.world.SoundUtil;

public class ModS2CPackets {

    public static final Identifier PORTAL_SOUND_PACKET_ID = new ModIdentifier("portal_sound_packet");
    public static final Identifier DATAPACK_PLANETS_PACKET_ID = new ModIdentifier("datapack_planets_packet");

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
                float temperature = buf.readFloat();
                boolean hasOxygen = buf.readBoolean();
                int atmosphereStart = buf.readInt();
                ButtonColour buttonColour = buf.readEnumConstant(ButtonColour.class);

                return new Planet(name, galaxy, solarSystem, dimension, orbitDimension, parentDimension, rocketTier, gravity, daysInYear, temperature, hasOxygen, atmosphereStart, buttonColour);
            });
        });
    }
}