package earth.terrarium.ad_astra.networking.packets.server;

import earth.terrarium.ad_astra.entities.vehicles.RocketEntity;
import earth.terrarium.ad_astra.util.ModIdentifier;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public record StartRocketPacket(int entityId) implements Packet<StartRocketPacket> {

    public static final Identifier ID = new ModIdentifier("start_rocket");
    public static final Handler HANDLER = new Handler();

    @Override
    public Identifier getID() {
        return ID;
    }

    @Override
    public PacketHandler<StartRocketPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<StartRocketPacket> {
        @Override
        public void encode(StartRocketPacket keybindPacket, PacketByteBuf buf) {
            buf.writeVarInt(keybindPacket.entityId);
        }

        @Override
        public StartRocketPacket decode(PacketByteBuf buf) {
            return new StartRocketPacket(buf.readVarInt());
        }

        @Override
        public PacketContext handle(StartRocketPacket message) {
            return (player, world) -> {
                if (world.getEntityById(message.entityId) instanceof RocketEntity rocket) {
                    if (!rocket.isFlying()) {
                        rocket.initiateLaunchSequence();
                    }
                }
            };
        }
    }
}
