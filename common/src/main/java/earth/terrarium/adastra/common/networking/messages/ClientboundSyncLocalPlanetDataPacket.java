package earth.terrarium.adastra.common.networking.messages;

import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.utils.ClientData;
import earth.terrarium.adastra.common.handlers.PlanetData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public record ClientboundSyncLocalPlanetDataPacket(
    PlanetData localData) implements Packet<ClientboundSyncLocalPlanetDataPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sync_local_planet_data");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundSyncLocalPlanetDataPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler implements PacketHandler<ClientboundSyncLocalPlanetDataPacket> {
        @Override
        public void encode(ClientboundSyncLocalPlanetDataPacket packet, FriendlyByteBuf buf) {
            buf.writeLong(packet.localData().pack());
        }

        @Override
        public ClientboundSyncLocalPlanetDataPacket decode(FriendlyByteBuf buf) {
            return new ClientboundSyncLocalPlanetDataPacket(PlanetData.unpack(buf.readLong()));
        }

        @Override
        public PacketContext handle(ClientboundSyncLocalPlanetDataPacket packet) {
            return (player, level) -> ClientData.localData = packet.localData();
        }
    }
}