package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record ClientboundPlayStationPacket(String url, Optional<BlockPos> pos) implements Packet<ClientboundPlayStationPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "play_station");
    public static final Handler HANDLER = new Handler();

    public ClientboundPlayStationPacket(String url, BlockPos pos) {
        this(url, Optional.ofNullable(pos));
    }

    public ClientboundPlayStationPacket(String url) {
        this(url, Optional.empty());
    }

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
                ExtraByteCodecs.BLOCK_POS.optionalFieldOf(ClientboundPlayStationPacket::pos),
                ClientboundPlayStationPacket::new
            ));
        }

        @Override
        public PacketContext handle(ClientboundPlayStationPacket packet) {
            return (player, level) -> {
                if (packet.url().isBlank()) {
                    RadioHandler.stop();
                } else {
                    packet.pos.ifPresentOrElse(
                        pos -> RadioHandler.play(packet.url(), player.getRandom(), pos),
                        () -> RadioHandler.play(packet.url(), player.getRandom())
                    );
                }
            };
        }
    }
}