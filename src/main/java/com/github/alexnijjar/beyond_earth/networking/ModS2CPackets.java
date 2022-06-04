package com.github.alexnijjar.beyond_earth.networking;

import com.github.alexnijjar.beyond_earth.client.BeyondEarthClient;
import com.github.alexnijjar.beyond_earth.data.ButtonColour;
import com.github.alexnijjar.beyond_earth.data.Planet;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
import com.github.alexnijjar.beyond_earth.world.SoundUtil;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ModS2CPackets {

    public static final Identifier PORTAL_SOUND = new ModIdentifier("portal_sound");
    public static final Identifier DATAPACK_PLANETS = new ModIdentifier("datapack_planets");
    public static final Identifier START_ROCKET = new ModIdentifier("start_rocket");

    @Environment(EnvType.CLIENT)
    public static void register() {
        // Sound packet.
        ClientPlayNetworking.registerGlobalReceiver(PORTAL_SOUND, (client, handler, buf, responseSender) -> {
            SoundUtil.setSound(buf.readBoolean());
        });

        // Receive planet data on client.
        ClientPlayNetworking.registerGlobalReceiver(DATAPACK_PLANETS, (client, handler, mainBuf, responseSender) -> {
            BeyondEarthClient.planets = mainBuf.readList(buf -> {

                String name = buf.readString();
                Identifier galaxy = buf.readIdentifier();
                Identifier solarSystem = buf.readIdentifier();
                RegistryKey<World> dimension = RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier());
                RegistryKey<World> orbitDimension = RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier());
                Identifier id = buf.readIdentifier();
                RegistryKey<World> parentWorld = id.equals(new Identifier("empty")) ? null : RegistryKey.of(Registry.WORLD_KEY, id);
                int rocketTier = buf.readInt();
                float gravity = buf.readFloat();
                int daysInYear = buf.readInt();
                float temperature = buf.readFloat();
                boolean hasOxygen = buf.readBoolean();
                int atmosphereStart = buf.readInt();
                ButtonColour buttonColour = buf.readEnumConstant(ButtonColour.class);

                return new Planet(name, galaxy, solarSystem, dimension, orbitDimension, parentWorld, rocketTier, gravity, daysInYear, temperature, hasOxygen, atmosphereStart, buttonColour);
            });
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