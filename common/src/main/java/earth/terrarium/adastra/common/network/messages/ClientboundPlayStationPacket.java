package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.radio.audio.RadioHandler;
import earth.terrarium.adastra.common.network.CodecPacketType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;
import java.util.Optional;

public record ClientboundPlayStationPacket(String url,
                                           Optional<BlockPos> pos) implements Packet<ClientboundPlayStationPacket> {

    public static final ClientboundPacketType<ClientboundPlayStationPacket> TYPE = new Type();

    public ClientboundPlayStationPacket(String url, BlockPos pos) {
        this(url, Optional.ofNullable(pos));
    }

    public ClientboundPlayStationPacket(String url) {
        this(url, Optional.empty());
    }

    @Override
    public PacketType<ClientboundPlayStationPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ClientboundPlayStationPacket> implements ClientboundPacketType<ClientboundPlayStationPacket> {

        public Type() {
            super(
                ClientboundPlayStationPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "play_station"),
                ObjectByteCodec.create(
                    ByteCodec.STRING.fieldOf(ClientboundPlayStationPacket::url),
                    ExtraByteCodecs.BLOCK_POS.optionalFieldOf(ClientboundPlayStationPacket::pos),
                    ClientboundPlayStationPacket::new
                )
            );
        }

        @Override
        public Runnable handle(ClientboundPlayStationPacket packet) {
            return () -> {
                Player player = Objects.requireNonNull(Minecraft.getInstance().player);
                if (packet.url.isBlank()) {
                    RadioHandler.stop();
                } else {
                    packet.pos.ifPresentOrElse(
                        pos -> RadioHandler.play(packet.url, player.getRandom(), pos),
                        () -> RadioHandler.play(packet.url, player.getRandom())
                    );
                }
            };
        }
    }
}
