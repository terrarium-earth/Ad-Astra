package earth.terrarium.adastra.common.network.packets;

import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.radio.screen.RadioScreen;
import earth.terrarium.adastra.common.utils.radio.StationInfo;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ClientboundSendStationsPacket(
    List<StationInfo> stations) implements Packet<ClientboundSendStationsPacket> {

    public static final ClientboundPacketType<ClientboundSendStationsPacket> TYPE = new Type();

    @Override
    public PacketType<ClientboundSendStationsPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ClientboundSendStationsPacket> implements ClientboundPacketType<ClientboundSendStationsPacket> {

        private static final StreamCodec<RegistryFriendlyByteBuf, ClientboundSendStationsPacket> STREAM_CODEC = StreamCodec.composite(
            StationInfo.STREAM_CODEC.apply(ByteBufCodecs.list()),
            ClientboundSendStationsPacket::stations,
            ClientboundSendStationsPacket::new
        );

        public Type() {
            super(
                ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, "send_stations"),
                STREAM_CODEC
            );
        }

        @Override
        public Runnable handle(ClientboundSendStationsPacket packet) {
            return () -> RadioScreen.handleStationUpdates(packet.stations());
        }
    }
}
