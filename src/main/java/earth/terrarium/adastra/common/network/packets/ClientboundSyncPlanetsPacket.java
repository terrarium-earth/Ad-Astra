package earth.terrarium.adastra.common.network.packets;

import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.Planet;
import earth.terrarium.adastra.common.planets.AdAstraData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.Map;

public record ClientboundSyncPlanetsPacket(
    Map<ResourceKey<Level>, Planet> planets
) implements Packet<ClientboundSyncPlanetsPacket> {

    public static final ClientboundPacketType<ClientboundSyncPlanetsPacket> TYPE = new Type();

    @Override
    public PacketType<ClientboundSyncPlanetsPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ClientboundSyncPlanetsPacket> implements ClientboundPacketType<ClientboundSyncPlanetsPacket> {

        public Type() {
            super(
                ResourceLocation.fromNamespaceAndPath(AdAstra.MOD_ID, "sync_planets"),
                ObjectByteCodec.create(
                    ByteCodec.mapOf(ExtraByteCodecs.resourceKey(Registries.DIMENSION), Planet.BYTE_CODEC)
                        .fieldOf(ClientboundSyncPlanetsPacket::planets),
                    ClientboundSyncPlanetsPacket::new));
        }

        @Override
        public Runnable handle(ClientboundSyncPlanetsPacket packet) {
            return () -> AdAstraData.setPlanets(Map.copyOf(packet.planets));
        }
    }
}