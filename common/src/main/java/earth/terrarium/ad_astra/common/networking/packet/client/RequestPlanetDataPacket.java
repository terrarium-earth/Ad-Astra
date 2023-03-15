package earth.terrarium.ad_astra.common.networking.packet.client;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.block.machine.entity.OxygenDistributorBlockEntity;
import earth.terrarium.ad_astra.common.data.PlanetData;
import earth.terrarium.ad_astra.common.networking.NetworkHandling;
import earth.terrarium.ad_astra.common.networking.packet.server.ReturnPlanetDataPacket;
import io.netty.buffer.Unpooled;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record RequestPlanetDataPacket() implements Packet<RequestPlanetDataPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "request_planet_data_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<RequestPlanetDataPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<RequestPlanetDataPacket> {
        @Override
        public void encode(RequestPlanetDataPacket packet, FriendlyByteBuf buf) {
        }

        @Override
        public RequestPlanetDataPacket decode(FriendlyByteBuf buf) {
            return new RequestPlanetDataPacket();
        }

        @Override
        public PacketContext handle(RequestPlanetDataPacket packet) {
            return (player, level) -> {
                NetworkHandling.CHANNEL.sendToPlayer(new ReturnPlanetDataPacket(), player);
            };
        }
    }
}
