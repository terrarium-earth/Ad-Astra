package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.utils.ClientData;
import net.minecraft.resources.ResourceLocation;

public record ClientboundSyncWeatherPacket(
    int clearWeatherTime, int rainTime,
    int thunderTime, boolean raining,
    boolean thundering
) implements Packet<ClientboundSyncWeatherPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sync_weather");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundSyncWeatherPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ClientboundSyncWeatherPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ByteCodec.INT.fieldOf(ClientboundSyncWeatherPacket::clearWeatherTime),
                ByteCodec.INT.fieldOf(ClientboundSyncWeatherPacket::rainTime),
                ByteCodec.INT.fieldOf(ClientboundSyncWeatherPacket::thunderTime),
                ByteCodec.BOOLEAN.fieldOf(ClientboundSyncWeatherPacket::raining),
                ByteCodec.BOOLEAN.fieldOf(ClientboundSyncWeatherPacket::thundering),
                ClientboundSyncWeatherPacket::new
            ));
        }

        @Override
        public PacketContext handle(ClientboundSyncWeatherPacket packet) {
            return (player, level) -> {
                ClientData.clearTime = packet.clearWeatherTime;
                ClientData.rainTime = packet.rainTime;
                ClientData.thunderTime = packet.thunderTime;
                ClientData.raining = packet.raining;
                ClientData.thundering = packet.thundering;
            };
        }
    }
}