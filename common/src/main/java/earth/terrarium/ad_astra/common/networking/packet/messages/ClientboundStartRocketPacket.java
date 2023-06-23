package earth.terrarium.ad_astra.common.networking.packet.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.entity.vehicle.Rocket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientboundStartRocketPacket(int entityId) implements Packet<ClientboundStartRocketPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "start_rocket");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundStartRocketPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ClientboundStartRocketPacket> {
        @Override
        public void encode(ClientboundStartRocketPacket packet, FriendlyByteBuf buf) {
            buf.writeVarInt(packet.entityId);
        }

        @Override
        public ClientboundStartRocketPacket decode(FriendlyByteBuf buf) {
            return new ClientboundStartRocketPacket(buf.readVarInt());
        }

        @Override
        public PacketContext handle(ClientboundStartRocketPacket packet) {
            return (player, level) -> {
                if (level.getEntity(packet.entityId) instanceof Rocket rocket) {
                    if (!rocket.isFlying()) {
                        rocket.initiateLaunchSequence();
                    }
                }
            };
        }
    }
}
