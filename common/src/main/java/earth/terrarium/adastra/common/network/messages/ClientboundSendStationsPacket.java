package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.radio.screen.RadioScreen;
import earth.terrarium.adastra.common.utils.radio.StationInfo;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record ClientboundSendStationsPacket(
    List<StationInfo> stations
) implements Packet<ClientboundSendStationsPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "send_stations");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundSendStationsPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ClientboundSendStationsPacket> {

        public Handler() {
            super(ObjectByteCodec.create(
                StationInfo.BYTE_CODEC.listOf().fieldOf(ClientboundSendStationsPacket::stations),
                ClientboundSendStationsPacket::new
            ));
        }

        @Override
        public PacketContext handle(ClientboundSendStationsPacket packet) {
            return (player, level) -> RadioScreen.handleStationUpdates(packet.stations);
        }
    }
}