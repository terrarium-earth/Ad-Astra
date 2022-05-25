package com.github.alexnijjar.beyond_earth.client.registry;

import com.github.alexnijjar.beyond_earth.entities.vehicles.LanderEntity;
import com.github.alexnijjar.beyond_earth.entities.vehicles.RocketEntity;
import com.github.alexnijjar.beyond_earth.networking.ModC2SPackets;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;

@Environment(EnvType.CLIENT)
public class ClientModKeybindings {

    public static void register() {

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player != null) {
                if (client.options.jumpKey.isPressed()) {
                    if (client.player.getVehicle() instanceof RocketEntity rocket) {
                        if (!rocket.isFlying()) {
                            PacketByteBuf buf = PacketByteBufs.create();
                            buf.writeInt(rocket.getId());
                            ClientPlayNetworking.send(ModC2SPackets.LAUNCH_ROCKET_PACKET_ID, buf);
                        }
                    }

                    if (client.player.getVehicle() instanceof LanderEntity lander) {
                        if (!lander.isPressingSpace) {
                            lander.isPressingSpace = true;
                            ClientPlayNetworking.send(ModC2SPackets.START_LANDER_BOOSTERS, PacketByteBufs.empty());
                        }
                    }
                }

                if (!client.options.jumpKey.isPressed()) {
                    if (client.player.getVehicle() instanceof LanderEntity lander) {
                        if (lander.isPressingSpace) {
                            lander.isPressingSpace = false;
                            ClientPlayNetworking.send(ModC2SPackets.END_LANDER_BOOSTERS, PacketByteBufs.empty());
                        }
                    }
                }
            }
        });
    }
}
