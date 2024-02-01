package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.systems.PlanetData;
import earth.terrarium.adastra.client.utils.ClientData;
import net.minecraft.resources.ResourceLocation;

public record ClientboundSyncLocalPlanetDataPacket(
    PlanetData localData
) implements Packet<ClientboundSyncLocalPlanetDataPacket> {

    public static final ClientboundPacketType<ClientboundSyncLocalPlanetDataPacket> TYPE = new Type();

    @Override
    public PacketType<ClientboundSyncLocalPlanetDataPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ClientboundSyncLocalPlanetDataPacket> implements ClientboundPacketType<ClientboundSyncLocalPlanetDataPacket> {

        public Type() {
            super(
                ClientboundSyncLocalPlanetDataPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "sync_local_planet_data"),
                ObjectByteCodec.create(
                    ByteCodec.INT.map(PlanetData::unpack, PlanetData::pack).fieldOf(ClientboundSyncLocalPlanetDataPacket::localData),
                    ClientboundSyncLocalPlanetDataPacket::new
                )
            );
        }

        @Override
        public Runnable handle(ClientboundSyncLocalPlanetDataPacket packet) {
            return () -> ClientData.updateLocalData(packet.localData());
        }
    }
}
