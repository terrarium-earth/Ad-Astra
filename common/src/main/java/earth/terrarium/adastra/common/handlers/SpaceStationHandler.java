package earth.terrarium.adastra.common.handlers;

import com.teamresourceful.resourcefullib.common.utils.SaveHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpaceStationHandler extends SaveHandler {
    private final Map<ChunkPos, UUID> spaceStationData = new HashMap<>();

    @Override
    public void loadData(CompoundTag tag) {
        var data = tag.getLongArray("");
        if (data.length % 3 != 0) throw new RuntimeException("Invalid data length");
        for (int i = 0; i < data.length; i += 3) {
            spaceStationData.put(new ChunkPos(data[i]), new UUID(data[i + 1], data[i + 2]));
        }
    }

    @Override
    public void saveData(CompoundTag tag) {
        long[] data = new long[spaceStationData.size() * 3];
        int i = 0;
        for (var entry : spaceStationData.entrySet()) {
            data[i++] = entry.getKey().toLong();
            data[i++] = entry.getValue().getMostSignificantBits();
            data[i++] = entry.getValue().getLeastSignificantBits();
        }
        tag.putLongArray("", data);
    }

    public static SpaceStationHandler read(ServerLevel level) {
        return read(level.getDataStorage(), SpaceStationHandler::new, "adastra_space_station_data");
    }

    public static boolean isSpaceStationAt(ServerLevel level, ChunkPos pos) {
        return read(level).spaceStationData.containsKey(pos);
    }

    public static boolean ownsSpaceStation(ServerLevel level, ChunkPos pos, UUID player) {
        UUID owner = read(level).spaceStationData.get(pos);
        if (owner == null) return false;
        return owner.equals(player);
    }

    public static void addSpaceStation(ServerLevel level, ChunkPos pos, UUID player) {
        read(level).spaceStationData.put(pos, player);
    }

    public static Map<ResourceLocation, Map<ChunkPos, UUID>> getAllSpaceStations(ServerLevel level) {
        Map<ResourceLocation, Map<ChunkPos, UUID>> spaceStations = new HashMap<>();
        read(level).spaceStationData.forEach((pos, owner) -> {
            var location = level.dimension().location();
            spaceStations.computeIfAbsent(location, k -> new HashMap<>()).put(pos, owner);
        });
        return spaceStations;
    }

    @Override
    public boolean isDirty() {
        return true;
    }
}
