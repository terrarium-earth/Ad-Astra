package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.mrscauthd.beyond_earth.entities.vehicles.RocketEntity;
import net.mrscauthd.beyond_earth.networking.ModC2SPackets;

@Environment(EnvType.CLIENT)
public class ClientModKeybindings {
    
    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.options.jumpKey.isPressed()) {
                if (client.player.getVehicle() instanceof RocketEntity rocket) {
                    if (!rocket.isFlying()) {
                        ClientPlayNetworking.send(ModC2SPackets.LAUNCH_ROCKET_PACKET_ID, PacketByteBufs.empty());
                        rocket.initiateLaunchSequence();
                    }
                }
            }
        });
    }
}
