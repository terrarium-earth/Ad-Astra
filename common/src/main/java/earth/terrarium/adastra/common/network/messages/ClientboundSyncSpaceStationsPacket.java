package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.bytecodecs.defaults.MapCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.client.screens.PlanetsScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;

import java.util.Map;
import java.util.UUID;

public record ClientboundSyncSpaceStationsPacket(
    Map<ResourceLocation, Map<ChunkPos, UUID>> spaceStations) implements Packet<ClientboundSyncSpaceStationsPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "sync_space_stations");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ClientboundSyncSpaceStationsPacket> getHandler() {
        return HANDLER;
    }

    public static class Handler extends CodecPacketHandler<ClientboundSyncSpaceStationsPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                new MapCodec<>(ExtraByteCodecs.RESOURCE_LOCATION, new MapCodec<>(ExtraByteCodecs.CHUNK_POS, ByteCodec.UUID)).fieldOf(ClientboundSyncSpaceStationsPacket::spaceStations),
                ClientboundSyncSpaceStationsPacket::new
            ));
        }

        @Override
        public PacketContext handle(ClientboundSyncSpaceStationsPacket packet) {
            return (player, level) -> PlanetsScreen.setSpaceStations(packet.spaceStations());
        }
    }
}