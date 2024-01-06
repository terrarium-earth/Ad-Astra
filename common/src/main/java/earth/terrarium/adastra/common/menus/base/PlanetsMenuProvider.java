package earth.terrarium.adastra.common.menus.base;

import com.google.common.collect.ImmutableSet;
import earth.terrarium.adastra.common.handlers.LaunchingDimensionHandler;
import earth.terrarium.adastra.common.handlers.SpaceStationHandler;
import earth.terrarium.adastra.common.handlers.base.SpaceStation;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.botarium.common.menu.ExtraDataMenuProvider;
import net.minecraft.core.GlobalPos;
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
        return new PlanetsMenu(containerId, inventory, Map.of(), Set.of());
    }

    @Override
    public void writeExtraData(ServerPlayer player, FriendlyByteBuf buffer) {
        AdAstraData.planets().keySet().forEach(dimension -> {
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

        AdAstraData.planets().keySet().forEach(dimension -> {
            Collection<GlobalPos> locations = LaunchingDimensionHandler.getAllSpawnLocations(player, player.server);
            buffer.writeVarInt(locations.size());
            locations.forEach(buffer::writeGlobalPos);
        });
    }

    public static Map<ResourceKey<Level>, Map<UUID, Set<SpaceStation>>> createSpaceStationsFromBuf(FriendlyByteBuf buf) {
        Map<ResourceKey<Level>, Map<UUID, Set<SpaceStation>>> spaceStations = new HashMap<>();
        AdAstraData.planets().keySet().forEach(dimension -> {
            int spaceStationCount = buf.readVarInt();
            for (int i = 0; i < spaceStationCount; i++) {
                ImmutableSet.Builder<SpaceStation> stations = new ImmutableSet.Builder<>();
                int stationCount = buf.readVarInt();
                for (int j = 0; j < stationCount; j++) {
                    Component name = buf.readComponent();
                    ChunkPos position = buf.readChunkPos();
                    stations.add(new SpaceStation(position, name));
                }

                var id = buf.readUUID();
                spaceStations.put(dimension, Map.of(id, stations.build()));
            }
        });
        return Collections.unmodifiableMap(spaceStations);
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
