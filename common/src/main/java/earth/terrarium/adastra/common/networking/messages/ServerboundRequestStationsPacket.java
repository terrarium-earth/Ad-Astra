package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.networking.NetworkHandler;
import earth.terrarium.adastra.common.utils.radio.StationLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ServerboundRequestStationsPacket() implements Packet<ServerboundRequestStationsPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "request_stations");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundRequestStationsPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundRequestStationsPacket> {
        @Override
        public void encode(ServerboundRequestStationsPacket packet, FriendlyByteBuf buf) {
        }

        @Override
        public ServerboundRequestStationsPacket decode(FriendlyByteBuf buf) {
            return new ServerboundRequestStationsPacket();
        }

        @Override
        public PacketContext handle(ServerboundRequestStationsPacket packet) {
            return (player, level) ->
                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundSendStationsPacket(StationLoader.stations()), player);
        }
    }
}