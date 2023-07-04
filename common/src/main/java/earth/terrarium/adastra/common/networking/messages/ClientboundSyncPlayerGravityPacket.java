package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.AdAstraClient;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientboundSyncPlayerGravityPacket(float gravity) implements Packet<ClientboundSyncPlayerGravityPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sync_gravity");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundSyncPlayerGravityPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ClientboundSyncPlayerGravityPacket> {
        @Override
        public void encode(ClientboundSyncPlayerGravityPacket packet, FriendlyByteBuf buf) {
            buf.writeFloat(packet.gravity);
        }

        @Override
        public ClientboundSyncPlayerGravityPacket decode(FriendlyByteBuf buf) {
            return new ClientboundSyncPlayerGravityPacket(buf.readFloat());
        }

        @Override
        public PacketContext handle(ClientboundSyncPlayerGravityPacket packet) {
            return (player, level) -> AdAstraClient.localGravity = packet.gravity;
        }
    }
}