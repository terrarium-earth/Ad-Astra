package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.radio.screen.RadioScreen;
import earth.terrarium.adastra.common.network.CodecPacketType;
import earth.terrarium.adastra.common.utils.radio.StationInfo;
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

        public Type() {
            super(
                ClientboundSendStationsPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "send_stations"),
                ObjectByteCodec.create(
                    StationInfo.BYTE_CODEC.listOf().fieldOf(ClientboundSendStationsPacket::stations),
                    ClientboundSendStationsPacket::new
                )
            );
        }

        @Override
        public Runnable handle(ClientboundSendStationsPacket packet) {
            return () -> RadioScreen.handleStationUpdates(packet.stations());
        }
    }
}
