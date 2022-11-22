package earth.terrarium.ad_astra.networking.packets.server;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.data.Planet;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record DatapackPlanetsPacket(Collection<Planet> planets) implements Packet<DatapackPlanetsPacket> {

    public static final ResourceLocation ID = new ModResourceLocation("datapack_planets_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<DatapackPlanetsPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<DatapackPlanetsPacket> {
        @Override
        public void encode(DatapackPlanetsPacket packet, FriendlyByteBuf buf) {
            buf.writeCollection(packet.planets, (buf2, planet) -> buf2.writeWithCodec(Planet.CODEC, planet));
        }

        @Override
        public DatapackPlanetsPacket decode(FriendlyByteBuf buf) {
            return new DatapackPlanetsPacket(buf.readList(buf2 -> buf2.readWithCodec(Planet.CODEC)));
        }

        @Override
        public PacketContext handle(DatapackPlanetsPacket packet) {
            return (player, level) -> {
                AdAstra.planets = new HashSet<>(packet.planets);
                AdAstra.planetWorlds = AdAstra.planets.stream().map(Planet::level).collect(Collectors.toSet());
                AdAstra.orbitWorlds = AdAstra.planets.stream().map(Planet::orbitWorld).collect(Collectors.toSet());
                AdAstra.adAstraWorlds = Stream.concat(AdAstra.planetWorlds.stream(), AdAstra.orbitWorlds.stream()).collect(Collectors.toSet());
                AdAstra.levelsWithOxygen = AdAstra.planets.stream().filter(Planet::hasOxygen).map(Planet::level).collect(Collectors.toSet());
            };
        }
    }
}
