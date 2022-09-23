package com.github.alexnijjar.ad_astra.networking.packets.client;

import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.networking.NetworkHandling;
import com.github.alexnijjar.ad_astra.networking.packets.server.StartRocketPacket;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public record LaunchRocketPacket() implements Packet<LaunchRocketPacket> {

    public static final Identifier ID = new ModIdentifier("launch_rocket");
    public static final Handler HANDLER = new Handler();

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public PacketHandler<LaunchRocketPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<LaunchRocketPacket> {
        @Override
        public void encode(LaunchRocketPacket keybindPacket, PacketByteBuf buf) {
        }

        @Override
        public LaunchRocketPacket decode(PacketByteBuf buf) {
            return new LaunchRocketPacket();
        }

        @Override
        public PacketContext handle(LaunchRocketPacket message) {
            return (player, world) -> {
                if (player.getVehicle() instanceof RocketEntity rocket) {
                    if (!rocket.isFlying()) {
                        rocket.initiateLaunchSequenceFromServer();
                        // Tell all clients to start rendering the rocket launch
                        NetworkHandling.CHANNEL.sendToAllLoaded(new StartRocketPacket(rocket.getId()), world, rocket.getBlockPos());
                    }
                }
            };
        }
    }
}
