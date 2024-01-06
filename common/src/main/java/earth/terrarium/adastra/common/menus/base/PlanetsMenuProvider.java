package earth.terrarium.adastra.common.menus.base;

import com.google.common.collect.ImmutableSet;
import earth.terrarium.adastra.common.compat.cadmus.CadmusIntegration;
import earth.terrarium.adastra.common.handlers.LaunchingDimensionHandler;
import earth.terrarium.adastra.common.handlers.SpaceStationHandler;
import earth.terrarium.adastra.common.handlers.base.SpaceStation;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMaps;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.*;

public class PlanetsMenuProvider implements ExtraDataMenuProvider {

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new PlanetsMenu(containerId, inventory, Map.of(), Object2BooleanMaps.emptyMap(), Set.of());
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {

        buffer.writeVarInt(AdAstraData.planets().size());
        AdAstraData.planets().keySet().forEach(dimension -> {
            buffer.writeResourceKey(dimension);

            var spaceStations = SpaceStationHandler.getAllSpaceStations(player.server.getLevel(dimension));
            buffer.writeVarInt(spaceStations.size());

            spaceStations.forEach((id, stations) -> {
                buffer.writeVarInt(stations.size());
                stations.forEach(station -> {
                    buffer.writeComponent(station.name());
                    buffer.writeChunkPos(station.position());
                });
                buffer.writeUUID(id);
            });
        });

        if (CadmusIntegration.cadmusLoaded()) {
            buffer.writeVarInt(AdAstraData.planets().size());
            AdAstraData.planets().keySet().forEach(dimension -> {
                buffer.writeResourceKey(dimension);
                buffer.writeBoolean(CadmusIntegration.isClaimed(player.server.getLevel(dimension), player.chunkPosition()));
            });
        }

        AdAstraData.planets().keySet().forEach(dimension -> {
            Collection<GlobalPos> locations = LaunchingDimensionHandler.getAllSpawnLocations(player, player.server);
            buffer.writeVarInt(locations.size());
            locations.forEach(buffer::writeGlobalPos);
        });
    }

    public static Map<ResourceKey<Level>, Map<UUID, Set<SpaceStation>>> createSpaceStationsFromBuf(FriendlyByteBuf buf) {
        Map<ResourceKey<Level>, Map<UUID, Set<SpaceStation>>> spaceStationsMap = new HashMap<>();

        int planetsSize = buf.readVarInt();
        for (int i = 0; i < planetsSize; i++) {
            ResourceKey<Level> planetKey = buf.readResourceKey(Registries.DIMENSION);
            int spaceStationsSize = buf.readVarInt();

            Map<UUID, Set<SpaceStation>> spaceStationGroupMap = new HashMap<>();
            for (int j = 0; j < spaceStationsSize; j++) {
                int stationGroupSize = buf.readVarInt();
                Set<SpaceStation> spaceStations = new HashSet<>();

                for (int k = 0; k < stationGroupSize; k++) {
                    Component stationName = buf.readComponent();
                    ChunkPos stationPos = buf.readChunkPos();
                    spaceStations.add(new SpaceStation(stationPos, stationName));
                }

                UUID id = buf.readUUID();
                spaceStationGroupMap.put(id, spaceStations);
            }

            spaceStationsMap.put(planetKey, spaceStationGroupMap);
        }

        return Collections.unmodifiableMap(spaceStationsMap);
    }


    public static Object2BooleanMap<ResourceKey<Level>> createClaimedChunksFromBuf(FriendlyByteBuf buf) {
        int dimensionCount = buf.readVarInt();
        Object2BooleanMap<ResourceKey<Level>> claimedChunks = new Object2BooleanOpenHashMap<>();

        for (int i = 0; i < dimensionCount; i++) {
            ResourceKey<Level> dimension = buf.readResourceKey(Registries.DIMENSION);
            claimedChunks.put(dimension, buf.readBoolean());
        }

        return Object2BooleanMaps.unmodifiable(claimedChunks);
    }

    public static Set<GlobalPos> createSpawnLocationsFromBuf(FriendlyByteBuf buf) {
        ImmutableSet.Builder<GlobalPos> locations = new ImmutableSet.Builder<>();
        int locationCount = buf.readVarInt();
        for (int i = 0; i < locationCount; i++) {
            locations.add(buf.readGlobalPos());
        }
        return locations.build();
    }
}
