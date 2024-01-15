package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.network.NetworkHandler;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.function.Consumer;

public record ServerboundRequestStationsPacket() implements Packet<ServerboundRequestStationsPacket> {

    public static final ServerboundPacketType<ServerboundRequestStationsPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundRequestStationsPacket> type() {
        return TYPE;
    }

    private static class Type implements ServerboundPacketType<ServerboundRequestStationsPacket> {

        @Override
        public Class<ServerboundRequestStationsPacket> type() {
            return ServerboundRequestStationsPacket.class;
        }

        @Override
        public ResourceLocation id() {
            return new ResourceLocation(AdAstra.MOD_ID, "request_stations");
        }

        @Override
        public void encode(ServerboundRequestStationsPacket message, FriendlyByteBuf buffer) {}

        @Override
        public ServerboundRequestStationsPacket decode(FriendlyByteBuf buffer) {
            return new ServerboundRequestStationsPacket();
        }

        @Override
        public Consumer<Player> handle(ServerboundRequestStationsPacket packet) {
            return player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSendStationsPacket(StationLoader.stations()), serverPlayer);
                }
            };
        }
    }
}
