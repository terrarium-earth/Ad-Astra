package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientboundPlayStationPacket(String url) implements Packet<ClientboundPlayStationPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "play_station");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundPlayStationPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ClientboundPlayStationPacket> {
        @Override
        public void encode(ClientboundPlayStationPacket packet, FriendlyByteBuf buf) {
            buf.writeUtf(packet.url);
        }

        @Override
        public ClientboundPlayStationPacket decode(FriendlyByteBuf buf) {
            return new ClientboundPlayStationPacket(buf.readUtf());
        }

        @Override
        public PacketContext handle(ClientboundPlayStationPacket packet) {
            return (player, level) -> {
                if (packet.url().isBlank()) {
                    RadioHandler.stop();
                } else {
                    RadioHandler.play(packet.url(), player.getRandom());
                }
            };
        }
    }
}