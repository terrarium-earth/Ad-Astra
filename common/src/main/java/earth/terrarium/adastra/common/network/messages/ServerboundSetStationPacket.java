package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.entities.base.RadioHolder;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public record ServerboundSetStationPacket(String url) implements Packet<ServerboundSetStationPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "set_station");
    public static final Handler HANDLER = new Handler();

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
                ServerboundSetStationPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundSetStationPacket packet) {
            return (player, level) -> {
                if (player.getVehicle() instanceof RadioHolder holder) {
                    if (StationLoader.hasStation(packet.url()) || packet.url().isBlank()) {
                        holder.setRadioUrl(packet.url());
                        for (Entity passenger : player.getVehicle().getPassengers()) {
                            if (passenger instanceof Player playerPassenger) {
                                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundPlayStationPacket(packet.url()), playerPassenger);
                            }
                        }
                    }
                } else if (/*TODO TI 69 STUFF HERE*/ (StationLoader.hasStation(packet.url()) || packet.url().isBlank())) {
                    NetworkHandler.CHANNEL.sendToPlayer(new ClientboundPlayStationPacket(packet.url()), player);
                }
            };
        }
    }
}