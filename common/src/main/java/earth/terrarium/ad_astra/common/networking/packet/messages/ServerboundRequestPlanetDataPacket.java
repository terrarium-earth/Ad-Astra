package earth.terrarium.ad_astra.common.networking.packet.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.networking.NetworkHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ServerboundRequestPlanetDataPacket() implements Packet<ServerboundRequestPlanetDataPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "request_planet_data");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundRequestPlanetDataPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ServerboundRequestPlanetDataPacket> {
        @Override
        public void encode(ServerboundRequestPlanetDataPacket packet, FriendlyByteBuf buf) {
        }

        @Override
        public ServerboundRequestPlanetDataPacket decode(FriendlyByteBuf buf) {
            return new ServerboundRequestPlanetDataPacket();
        }

        @Override
        public PacketContext handle(ServerboundRequestPlanetDataPacket packet) {
            return (player, level) -> {
                NetworkHandler.CHANNEL.sendToPlayer(new ClientboundReturnPlanetDataPacket(), player);
            };
        }
    }
}
