package earth.terrarium.adastra.common.handlers;

import com.teamresourceful.resourcefullib.common.utils.SaveHandler;
import earth.terrarium.adastra.common.handlers.base.SpaceStation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;

import java.util.*;

public class SpaceStationHandler extends SaveHandler {
    private final Map<UUID, Set<SpaceStation>> spaceStationData = new HashMap<>();

    @Override
    public void loadData(CompoundTag tag) {
        tag.getAllKeys().forEach(id -> {
            ListTag stationsTag = tag.getList(id, Tag.TAG_COMPOUND);
            Set<SpaceStation> stations = new HashSet<>();
            stationsTag.forEach(stationTag -> {
                CompoundTag stationCompoundTag = (CompoundTag) stationTag;
                Component name = Component.Serializer.fromJson(stationCompoundTag.getString("Name"));
                ChunkPos position = new ChunkPos(stationCompoundTag.getLong("Position"));
                stations.add(new SpaceStation(position, name));
            });
            spaceStationData.put(UUID.fromString(id), stations);
        });
    }

    @Override
    public void saveData(CompoundTag tag) {
        spaceStationData.forEach((id, stations) -> {
            ListTag ownerTag = new ListTag();
            for (var station : stations) {
                CompoundTag stationsTag = new CompoundTag();
                stationsTag.putString("Name", Component.Serializer.toJson(station.name()));
                stationsTag.putLong("Position", station.position().toLong());
                ownerTag.add(stationsTag);
            }
            tag.put(id.toString(), ownerTag);
        });
    }

    public static SpaceStationHandler read(ServerLevel level) {
        return read(level.getDataStorage(), SpaceStationHandler::new, "adastra_space_station_data");
    }

    public static Map<UUID, Set<SpaceStation>> getAllSpaceStations(ServerLevel level) {
        return read(level).spaceStationData;
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    public static void constructSpaceStation(ServerPlayer player, ServerLevel level, Component name) {
        var data = read(level).spaceStationData;
        var stations = data.computeIfAbsent(player.getUUID(), k -> new HashSet<>());
        stations.add(new SpaceStation(player.chunkPosition(), name));
    }

    public static boolean isInSpaceStation(ServerPlayer player, ServerLevel level) {
        for (var stations : read(level).spaceStationData.values()) {
            for (var station : stations) {
                if (station.position().getChessboardDistance(player.chunkPosition()) <= 2) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Set<SpaceStation> getOwnedSpaceStations(ServerPlayer player, ServerLevel level) {
        return getOwnedSpaceStations(player.getUUID(), level);
    }

    public static Set<SpaceStation> getOwnedSpaceStations(UUID id, ServerLevel level) {
        return read(level).spaceStationData.getOrDefault(id, Set.of());
    }
}
