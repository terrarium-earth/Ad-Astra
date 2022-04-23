package net.mrscauthd.beyond_earth.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.mrscauthd.beyond_earth.util.ModIdentifier;
import net.mrscauthd.beyond_earth.util.ModUtils;

public class ModC2SPackets {

    public static final Identifier TELEPORT_TO_PLANET_PACKET_ID = new ModIdentifier("teleport_to_planet_packet");

    public static void register() {

        // Teleport to a planet.
        ServerPlayNetworking.registerGlobalReceiver(TELEPORT_TO_PLANET_PACKET_ID,
                (server, player, handler, buf, responseSender) -> {
                    RegistryKey<World> targetDimension = RegistryKey.of(Registry.WORLD_KEY, buf.readIdentifier());

                    // Teleport has to be called on the server thread.
                    server.execute(new Runnable() {

                        @Override
                        public void run() {
                            ModUtils.teleportToDimension(server.getWorld(targetDimension), player);
                        }
                    });
                });
    }
}