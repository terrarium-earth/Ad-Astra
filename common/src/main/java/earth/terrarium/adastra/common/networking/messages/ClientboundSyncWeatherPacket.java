package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.utils.ClientData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientboundSyncWeatherPacket(int clearWeatherTime, int rainTime,
                                           int thunderTime, boolean raining,
                                           boolean thundering) implements Packet<ClientboundSyncWeatherPacket> {

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

    private static class Handler implements PacketHandler<ClientboundSyncWeatherPacket> {
        @Override
        public void encode(ClientboundSyncWeatherPacket packet, FriendlyByteBuf buf) {
            buf.writeInt(packet.clearWeatherTime);
            buf.writeInt(packet.rainTime);
            buf.writeInt(packet.thunderTime);
            buf.writeBoolean(packet.raining);
            buf.writeBoolean(packet.thundering);
        }

        @Override
        public ClientboundSyncWeatherPacket decode(FriendlyByteBuf buf) {
            return new ClientboundSyncWeatherPacket(
                buf.readInt(),
                buf.readInt(),
                buf.readInt(),
                buf.readBoolean(),
                buf.readBoolean());
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