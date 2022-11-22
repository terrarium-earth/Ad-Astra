package earth.terrarium.ad_astra.networking.packets.server;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.ad_astra.entities.vehicles.Rocket;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record StartRocketPacket(int entityId) implements Packet<StartRocketPacket> {

    public static final ResourceLocation ID = new ModResourceLocation("start_rocket_packet");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<StartRocketPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<StartRocketPacket> {
        @Override
        public void encode(StartRocketPacket packet, FriendlyByteBuf buf) {
            buf.writeVarInt(packet.entityId);
        }

        @Override
        public StartRocketPacket decode(FriendlyByteBuf buf) {
            return new StartRocketPacket(buf.readVarInt());
        }

        @Override
        public PacketContext handle(StartRocketPacket packet) {
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
