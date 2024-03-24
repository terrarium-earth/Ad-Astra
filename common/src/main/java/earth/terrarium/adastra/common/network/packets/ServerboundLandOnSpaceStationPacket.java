package earth.terrarium.adastra.common.network.packets;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.teamresourceful.resourcefullib.common.network.base.ServerboundPacketType;
import com.teamresourceful.resourcefullib.common.network.defaults.CodecPacketType;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.compat.argonauts.ArgonautsIntegration;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.handlers.SpaceStationHandler;
import earth.terrarium.adastra.common.handlers.base.SpaceStation;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public record ServerboundLandOnSpaceStationPacket(ResourceKey<Level> dimension,
                                                  ChunkPos spaceStationPos) implements Packet<ServerboundLandOnSpaceStationPacket> {

    public static final ServerboundPacketType<ServerboundLandOnSpaceStationPacket> TYPE = new Type();

    @Override
    public PacketType<ServerboundLandOnSpaceStationPacket> type() {
        return TYPE;
    }

    private static class Type extends CodecPacketType<ServerboundLandOnSpaceStationPacket> implements ServerboundPacketType<ServerboundLandOnSpaceStationPacket> {

        public Type() {
            super(
                ServerboundLandOnSpaceStationPacket.class,
                new ResourceLocation(AdAstra.MOD_ID, "land_on_space_station"),
                ObjectByteCodec.create(
                    ExtraByteCodecs.DIMENSION.fieldOf(ServerboundLandOnSpaceStationPacket::dimension),
                    ExtraByteCodecs.CHUNK_POS.fieldOf(ServerboundLandOnSpaceStationPacket::spaceStationPos),
                    ServerboundLandOnSpaceStationPacket::new
                )
            );
        }

        @Override
        public Consumer<Player> handle(ServerboundLandOnSpaceStationPacket packet) {
            return player -> {
                if (!(player.level() instanceof ServerLevel serverLevel)) return;
                if (!(player instanceof ServerPlayer serverPlayer)) return;
                var planet = PlanetApi.API.getPlanet(packet.dimension);
                if (planet == null) return;

                ServerLevel targetLevel = serverLevel.getServer().getLevel(planet.dimension());
                if (targetLevel == null) return;

                // Shouldn't be able to land on space stations outside of orbit.
                if (!PlanetApi.API.isSpace(targetLevel)) return;
                if (!ModUtils.canTeleportToPlanet(player, planet)) return;

                var targetPos = packet.spaceStationPos();
                if (!isAllowed(serverPlayer, targetLevel, targetPos)) return;

                BlockPos middleBlockPosition = targetPos.getMiddleBlockPosition(AdAstraConfig.atmosphereLeave);
                ModUtils.land(serverPlayer, targetLevel, new Vec3(middleBlockPosition.getX() - 0.5f, middleBlockPosition.getY(), middleBlockPosition.getZ() - 0.5f));
            };
        }
    }

    private static boolean isAllowed(ServerPlayer player, ServerLevel level, ChunkPos targetPos) {
        Set<SpaceStation> stations = new HashSet<>(SpaceStationHandler.getOwnedSpaceStations(player, level));

        if (!ArgonautsIntegration.argonautsLoaded()) return stations
            .stream()
            .anyMatch(station -> station.position().equals(targetPos));

        for (var member : ArgonautsIntegration.getClientPartyMembers(player.getUUID())) {
            stations.addAll(SpaceStationHandler.getOwnedSpaceStations(member.getId(), level));
        }
        for (var member : ArgonautsIntegration.getClientGuildMembers(player.getUUID())) {
            stations.addAll(SpaceStationHandler.getOwnedSpaceStations(member.getId(), level));
        }

        return stations
            .stream()
            .anyMatch(station -> station.position().equals(targetPos));
    }
}