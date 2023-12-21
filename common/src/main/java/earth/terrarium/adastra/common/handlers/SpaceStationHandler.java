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
        tag.getAllKeys().forEach(key ->
            spaceStationData.put(new ChunkPos(Long.parseLong(key)), tag.getUUID(key)));
    }

    @Override
    public void saveData(CompoundTag tag) {
        spaceStationData.forEach((pos, owner) ->
            tag.putUUID(String.valueOf(pos.toLong()), owner));
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
