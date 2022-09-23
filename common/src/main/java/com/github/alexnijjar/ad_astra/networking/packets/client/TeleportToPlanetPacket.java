package com.github.alexnijjar.ad_astra.networking.packets.client;

import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.github.alexnijjar.ad_astra.util.ModUtils;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public record TeleportToPlanetPacket(Identifier id) implements Packet<TeleportToPlanetPacket> {

    public static final Identifier ID = new ModIdentifier("teleport_to_planet");
    public static final Handler HANDLER = new Handler();

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public PacketHandler<TeleportToPlanetPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<TeleportToPlanetPacket> {
        @Override
        public void encode(TeleportToPlanetPacket keybindPacket, PacketByteBuf buf) {
            buf.writeIdentifier(keybindPacket.id);
        }

        @Override
        public TeleportToPlanetPacket decode(PacketByteBuf buf) {
            return new TeleportToPlanetPacket(buf.readIdentifier());
        }

        @Override
        public PacketContext handle(TeleportToPlanetPacket message) {
            return (player, world) -> {
                if (player.getVehicle() instanceof RocketEntity) {
                    ModUtils.teleportToWorld(getWorld(message.id()), player);
                } else if (player.isCreativeLevelTwoOp()) {
                    ModUtils.teleportPlayer(getWorld(message.id()), (ServerPlayerEntity) player);
                }
            };
        }
    }

    private static RegistryKey<World> getWorld(Identifier id) {
        RegistryKey<World> targetDimension = RegistryKey.of(Registry.WORLD_KEY, id);
        // Change the "earth" registry key to the "overworld" registry key.
        if (targetDimension.getValue().equals(new ModIdentifier("earth"))) {
            targetDimension = World.OVERWORLD;
        }
        return targetDimension;
    }
}
