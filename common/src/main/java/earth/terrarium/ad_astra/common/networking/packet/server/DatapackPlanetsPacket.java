package earth.terrarium.ad_astra.common.networking.packet.server;

import com.mojang.serialization.Codec;
import com.teamresourceful.resourcefullib.common.codecs.yabn.YabnOps;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import com.teamresourceful.resourcefullib.common.utils.readers.ByteBufByteReader;
import com.teamresourceful.resourcefullib.common.yabn.YabnParser;
import com.teamresourceful.resourcefullib.common.yabn.base.YabnElement;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.data.Planet;
import earth.terrarium.ad_astra.common.data.PlanetData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Collection;
import java.util.List;

public record DatapackPlanetsPacket(Collection<Planet> planets) implements Packet<DatapackPlanetsPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "datapack_planets_packet");
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

        private static final Codec<List<Planet>> CODEC = Planet.CODEC.listOf();

        @Override
        public void encode(DatapackPlanetsPacket packet, FriendlyByteBuf buf) {
            YabnElement element = CODEC.encodeStart(YabnOps.COMPRESSED, packet.planets().stream().toList())
                    .getOrThrow(false, AdAstra.LOGGER::error);
            buf.writeBytes(element.toData());
        }

        @Override
        public DatapackPlanetsPacket decode(FriendlyByteBuf buf) {
            try {
                return new DatapackPlanetsPacket(CODEC.parse(YabnOps.COMPRESSED, YabnParser.parse(new ByteBufByteReader(buf)))
                        .result()
                        .orElse(List.of()));
            }catch (Exception e) {
                e.printStackTrace();
                return new DatapackPlanetsPacket(List.of());
            }
        }

        @Override
        public PacketContext handle(DatapackPlanetsPacket packet) {
            return (player, level) -> PlanetData.updatePlanets(packet.planets);
        }
    }
}
