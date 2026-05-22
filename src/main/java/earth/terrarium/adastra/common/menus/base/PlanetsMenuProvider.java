package earth.terrarium.adastra.common.menus.base;

import com.teamresourceful.resourcefullib.common.bytecodecs.ExtraByteCodecs;
import com.teamresourceful.resourcefullib.common.menu.ContentMenuProvider;
import earth.terrarium.adastra.common.config.AdAstraConfig;
import earth.terrarium.adastra.common.handlers.LaunchingDimensionHandler;
import earth.terrarium.adastra.common.handlers.SpaceStationHandler;
import earth.terrarium.adastra.common.handlers.base.SpaceStation;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.menus.content.PlanetsContent;
import earth.terrarium.adastra.common.planets.AdAstraData;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;

import java.util.*;

public class PlanetsMenuProvider implements ContentMenuProvider<PlanetsContent> {

    @Override
    public Component getDisplayName() {
        return Component.empty();
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory inventory, Player player) {
        return new PlanetsMenu(containerId, inventory, Set.of(), Map.of(), Set.of());
    }

    public static void writeDisabledPlanetsToBuf(FriendlyByteBuf buffer, Set<ResourceLocation> resourceLocations) {
        StringBuilder builder = new StringBuilder();
        for (ResourceLocation location : resourceLocations) {
            builder.append(location.toString()).append(",");
        }
        buffer.writeUtf(builder.toString());
    }

    public static void writeSpaceStationsToBuf(FriendlyByteBuf buffer, Map<ResourceKey<Level>, Map<UUID, Set<SpaceStation>>> resourceKeyMapMap) {
        buffer.writeVarInt(resourceKeyMapMap.size());
        resourceKeyMapMap.forEach((dimension, groupMap) -> {
            buffer.writeResourceKey(dimension);
            buffer.writeVarInt(groupMap.size());
            groupMap.forEach((id, stations) -> {
                buffer.writeVarInt(stations.size());
                stations.forEach(station -> {
                    ExtraByteCodecs.COMPONENT.encode(station.name(), buffer);
                    buffer.writeChunkPos(station.position());
                });
                buffer.writeUUID(id);
            });
        });
    }

    public static void writeSpawnLocationsToBuf(FriendlyByteBuf buffer, Set<GlobalPos> globalPos) {
        buffer.writeVarInt(globalPos.size());
        globalPos.forEach(buffer::writeGlobalPos);
    }

    public static Set<ResourceLocation> createDisabledPlanetsFromBuf(FriendlyByteBuf buf) {
        Set<ResourceLocation> disabledPlanets = new HashSet<>();
        String[] planets = buf.readUtf().split(",");
        for (var planet : planets) {
            disabledPlanets.add(ResourceLocation.withDefaultNamespace(planet));
        }
        return Collections.unmodifiableSet(disabledPlanets);
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
                    Component stationName = ExtraByteCodecs.COMPONENT.decode(buf);
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

    public static Set<GlobalPos> createSpawnLocationsFromBuf(FriendlyByteBuf buf) {
        Set<GlobalPos> locations = new HashSet<>();
        int locationCount = buf.readVarInt();
        for (int i = 0; i < locationCount; i++) {
            locations.add(buf.readGlobalPos());
        }
        return Collections.unmodifiableSet(locations);
    }

    @Override
    public PlanetsContent createContent(ServerPlayer player) {
        Set<ResourceLocation> disabledPlanets = new HashSet<>();
        String[] planets = AdAstraConfig.disabledPlanets.split(",");
        for (var planet : planets) {
            disabledPlanets.add(ResourceLocation.withDefaultNamespace(planet));
        }

        Map<ResourceKey<Level>, Map<UUID, Set<SpaceStation>>> spaceStationsMap = new HashMap<>();
        AdAstraData.planets().forEach((dimension, planet) -> {
            ServerLevel targetLevel = player.server.getLevel(dimension);
            if (targetLevel == null) throw new IllegalStateException("Dimension " + dimension + " does not exist.");
            var spaceStations = SpaceStationHandler.getAllSpaceStations(targetLevel);
            spaceStationsMap.put(dimension, spaceStations);
        });

        Set<GlobalPos> locations = new HashSet<>();
        AdAstraData.planets().forEach((dimension, planet) ->
            LaunchingDimensionHandler.getSpawningLocation(player, player.serverLevel(), planet).ifPresent(locations::add));

        return new PlanetsContent(
            disabledPlanets,
            spaceStationsMap,
            locations
        );
    }
}