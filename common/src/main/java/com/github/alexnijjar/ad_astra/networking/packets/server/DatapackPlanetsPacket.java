package com.github.alexnijjar.ad_astra.networking.packets.server;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.data.Planet;
import com.github.alexnijjar.ad_astra.entities.vehicles.RocketEntity;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record DatapackPlanetsPacket(Collection<Planet> planets) implements Packet<DatapackPlanetsPacket> {

    public static final Identifier ID = new ModIdentifier("datapack_planets");
    public static final Handler HANDLER = new Handler();

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public PacketHandler<DatapackPlanetsPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<DatapackPlanetsPacket> {
        @Override
        public void encode(DatapackPlanetsPacket keybindPacket, PacketByteBuf buf) {
            buf.writeCollection(keybindPacket.planets, (buf2, planet) -> {
                buf2.encode(Planet.CODEC, planet);
            });
        }

        @Override
        public DatapackPlanetsPacket decode(PacketByteBuf buf) {
            return new DatapackPlanetsPacket(buf.readList(buf2 -> buf2.decode(Planet.CODEC)));
        }

        @Override
        public PacketContext handle(DatapackPlanetsPacket message) {
            return (player, world) -> {
                AdAstra.planets = new HashSet<>(message.planets);
                AdAstra.planetWorlds = AdAstra.planets.stream().map(Planet::world).collect(Collectors.toSet());
                AdAstra.orbitWorlds = AdAstra.planets.stream().map(Planet::orbitWorld).collect(Collectors.toSet());
                AdAstra.adAstraWorlds = Stream.concat(AdAstra.planetWorlds.stream(), AdAstra.orbitWorlds.stream()).collect(Collectors.toSet());
                AdAstra.worldsWithOxygen = AdAstra.planets.stream().filter(Planet::hasOxygen).map(Planet::world).collect(Collectors.toSet());
            };
        }
    }
}
