package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.utils.ClientData;
import earth.terrarium.adastra.common.handlers.base.PlanetData;
import net.minecraft.resources.ResourceLocation;

public record ClientboundSyncLocalPlanetDataPacket(
    PlanetData localData
) implements Packet<ClientboundSyncLocalPlanetDataPacket> {

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

    private static class Handler extends CodecPacketHandler<ClientboundSyncLocalPlanetDataPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ByteCodec.INT.map(PlanetData::unpack, PlanetData::pack).fieldOf(ClientboundSyncLocalPlanetDataPacket::localData),
                ClientboundSyncLocalPlanetDataPacket::new
            ));
        }

        @Override
        public PacketContext handle(ClientboundSyncLocalPlanetDataPacket packet) {
            return (player, level) -> ClientData.localData = packet.localData();
        }
    }
}
