package earth.terrarium.adastra.common.network.packets;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.radio.RadioHolder;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;
import java.util.function.Consumer;

public record ServerboundSetStationPacket(String url,
                                          Optional<BlockPos> pos) implements Packet<ServerboundSetStationPacket> {

    public static final ServerboundPacketType<ServerboundSetStationPacket> TYPE = new Type();

    public ServerboundSetStationPacket(String url, BlockPos pos) {
        this(url, Optional.ofNullable(pos));
    }

    @Override
    public PacketType<ServerboundSetStationPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundSetStationPacket> implements ServerboundPacketType<ServerboundSetStationPacket> {

        public Type() {
            super(
                ServerboundSetStationPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "set_station"),
                ObjectByteCodec.create(
                    ByteCodec.STRING.fieldOf(ServerboundSetStationPacket::url),
                    ExtraByteCodecs.BLOCK_POS.optionalFieldOf(ServerboundSetStationPacket::pos),
                    ServerboundSetStationPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundSetStationPacket packet) {
            return player -> {
                if (packet.pos.isPresent()) {
                    boolean inRange = player.distanceToSqr(packet.pos.get().getCenter()) <= 64;
                    if (inRange && player.level().getBlockEntity(packet.pos.get()) instanceof RadioHolder holder) {
                        playStation(holder, packet.url());
                    }
                } else if (player.getVehicle() instanceof RadioHolder holder) {
                    playStation(holder, packet.url());
                }
            };
        }

        private void playStation(RadioHolder holder, String url) {
            if (StationLoader.hasStation(url) || url.isBlank()) {
                holder.setRadioUrl(url);
            }
        }
    }
}