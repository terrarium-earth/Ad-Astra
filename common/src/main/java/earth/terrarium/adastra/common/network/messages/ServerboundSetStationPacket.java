package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.utils.radio.RadioHolder;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public record ServerboundSetStationPacket(String url, Optional<BlockPos> pos) implements Packet<ServerboundSetStationPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "set_station");
    public static final Handler HANDLER = new Handler();

    public ServerboundSetStationPacket(String url, BlockPos pos) {
        this(url, Optional.ofNullable(pos));
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundSetStationPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundSetStationPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ByteCodec.STRING.fieldOf(ServerboundSetStationPacket::url),
                ExtraByteCodecs.BLOCK_POS.optionalFieldOf(ServerboundSetStationPacket::pos),
                ServerboundSetStationPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundSetStationPacket packet) {
            return (player, level) -> {
                if (packet.pos.isPresent()) {
                    boolean inRange = player.distanceToSqr(packet.pos.get().getCenter()) <= 64;
                    if (inRange && level.getBlockEntity(packet.pos.get()) instanceof RadioHolder holder) {
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