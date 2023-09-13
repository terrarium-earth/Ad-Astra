package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
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

    public static class Handler extends CodecPacketHandler<ClientboundPlayStationPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ByteCodec.STRING.fieldOf(ClientboundPlayStationPacket::url),
                ClientboundPlayStationPacket::new
            ));
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