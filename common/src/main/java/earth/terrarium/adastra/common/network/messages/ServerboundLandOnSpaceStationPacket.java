package earth.terrarium.adastra.common.network.messages;

import com.teamresourceful.bytecodecs.base.object.ObjectByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.networking.base.CodecPacketHandler;
import com.teamresourceful.resourcefullib.common.networking.base.Packet;
import com.teamresourceful.resourcefullib.common.networking.base.PacketContext;
import com.teamresourceful.resourcefullib.common.networking.base.PacketHandler;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.planets.PlanetApi;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.handlers.LaunchingDimensionHandler;
import earth.terrarium.adastra.common.handlers.SpaceStationHandler;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.utils.ModUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public record ServerboundLandOnSpaceStationPacket(ResourceKey<Level> dimension,
                                                  ChunkPos spaceStationPos) implements Packet<ServerboundLandOnSpaceStationPacket> {

    public static final ResourceLocation ID = new ResourceLocation(AdAstra.MOD_ID, "land_on_space_station");
    public static final Handler HANDLER = new Handler();

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public PacketHandler<ServerboundLandOnSpaceStationPacket> getHandler() {
        return HANDLER;
    }

    private static class Handler extends CodecPacketHandler<ServerboundLandOnSpaceStationPacket> {
        public Handler() {
            super(ObjectByteCodec.create(
                ExtraByteCodecs.DIMENSION.fieldOf(ServerboundLandOnSpaceStationPacket::dimension),
                ExtraByteCodecs.CHUNK_POS.fieldOf(ServerboundLandOnSpaceStationPacket::spaceStationPos),
                ServerboundLandOnSpaceStationPacket::new
            ));
        }

        @Override
        public PacketContext handle(ServerboundLandOnSpaceStationPacket packet) {
            return (player, level) -> {
                if (!(level instanceof ServerLevel serverLevel)) return;
                if (!(player.containerMenu instanceof PlanetsMenu)) return;

                var planet = PlanetApi.API.getPlanet(packet.dimension);
                if (planet == null) return;

                var server = serverLevel.getServer();
                ServerLevel targetLevel = server.getLevel(planet.dimension());
                if (targetLevel == null) return;

                var targetPos = packet.spaceStationPos();
                if (SpaceStationHandler.getOwnedSpaceStations((ServerPlayer) player, targetLevel)
                    .stream()
                    .noneMatch(station -> station.position().equals(targetPos))) return;

                LaunchingDimensionHandler.addSpawnLocation(player, serverLevel);
                BlockPos middleBlockPosition = targetPos.getMiddleBlockPosition(AdAstraConfig.atmosphereLeave);
                ModUtils.land((ServerPlayer) player, targetLevel, new Vec3(middleBlockPosition.getX() - 0.5f, middleBlockPosition.getY(), middleBlockPosition.getZ() - 0.5f));
            };
        }
    }
}