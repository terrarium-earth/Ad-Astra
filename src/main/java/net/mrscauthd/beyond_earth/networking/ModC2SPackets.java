package net.mrscauthd.beyond_earth.networking;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.util.ModUtils;

public class ModC2SPackets {

    public static final Identifier TELEPORT_TO_PLANET_PACKET_ID = new ModIdentifier("teleport_to_planet_packet");

    public static void register() {

        // Send planets packet.
        ServerPlayConnectionEvents.JOIN.register((handler, sender, minecraftServer) -> {
            try {
                sender.sendPacket(ModS2CPackets.DATAPACK_PLANETS_PACKET_ID, createPlanetsDatapackBuf());
                sender.sendPacket(ModS2CPackets.DATAPACK_SOLAR_SYSTEMS_PACKET_ID, createSolarSystemsDatapackBuf());
            } catch (Exception e) {
                BeyondEarth.LOGGER.error("Failed to send datapack values to client: " + e);
                e.printStackTrace();
            }
        });

        // Teleport to a planet.
        ServerPlayNetworking.registerGlobalReceiver(TELEPORT_TO_PLANET_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            RegistryKey<World> targetDimension = RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier());

            // Teleport has to be called on the server thread.
            server.execute(new Runnable() {

                @Override
                public void run() {
                    ModUtils.teleportToWorld(targetDimension, player);
                }
            });
        });
    }

    private static PacketByteBuf createPlanetsDatapackBuf() {
        PacketByteBuf mainBuf = PacketByteBufs.create();
        mainBuf.writeCollection(BeyondEarth.planets, (buf, planet) -> {
            buf.writeString(planet.name());
            buf.writeIdentifier(planet.galaxy());
            buf.writeIdentifier(planet.solarSystem());
            buf.writeIdentifier(planet.dimension().getValue());
            buf.writeIdentifier(planet.orbitDimension().getValue());
            buf.writeIdentifier(planet.parentDimension() == null ? new Identifier("empty") : planet.parentDimension().getValue());
            buf.writeInt(planet.rocketTier());
            buf.writeFloat(planet.gravity());
            buf.writeInt(planet.daysInYear());
            buf.writeFloat(planet.temperature());
            buf.writeBoolean(planet.oxygen());
            buf.writeInt(planet.atmosphereStart());
            buf.writeEnumConstant(planet.buttonColour());
        });
        return mainBuf;
    }

    private static PacketByteBuf createSolarSystemsDatapackBuf() {
        PacketByteBuf mainBuf = PacketByteBufs.create();
        mainBuf.writeCollection(BeyondEarth.solarSystems, (buf, solarSystem) -> {
            buf.writeIdentifier(solarSystem.galaxy());
            buf.writeIdentifier(solarSystem.solarSystem());
            buf.writeEnumConstant(solarSystem.sunType());
            buf.writeCollection(solarSystem.planetaryRings(), (buf1, id) -> {
                buf1.writeIdentifier(id);
            });
        });
        return mainBuf;
    }
}