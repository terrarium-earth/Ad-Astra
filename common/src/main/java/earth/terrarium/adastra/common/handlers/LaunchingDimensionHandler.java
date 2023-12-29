package earth.terrarium.adastra.common.handlers;

import com.teamresourceful.resourcefullib.common.utils.SaveHandler;
import earth.terrarium.adastra.common.planets.AdAstraData;
import earth.terrarium.adastra.common.planets.Planet;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class LaunchingDimensionHandler extends SaveHandler {
    private final Map<UUID, LaunchedDimensions> data = new HashMap<>();

    @Override
    public void loadData(CompoundTag tag) {
        tag.getAllKeys().forEach(key -> {
            UUID uuid = UUID.fromString(key);
            CompoundTag planetsTag = tag.getCompound(key);
            Map<ResourceLocation, GlobalPos> planets = new HashMap<>();
            for (String allKey : planetsTag.getAllKeys()) {
                GlobalPos.CODEC.parse(NbtOps.INSTANCE, planetsTag.getCompound(allKey))
                    .result()
                    .ifPresent(pos -> {
                        ResourceLocation planet = ResourceLocation.tryParse(allKey);
                        planets.put(planet, pos);
                    });
            }
            this.data.put(uuid, new LaunchedDimensions(planets));
        });
    }

    @Override
    public void saveData(CompoundTag tag) {
        this.data.forEach((uuid, data) -> {
            CompoundTag planetsTag = new CompoundTag();
            for (var entry : data.dimensions.entrySet()) {
                GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue())
                    .result()
                    .ifPresent(pos -> planetsTag.put(entry.getKey().toString(), pos));
            }
            tag.put(uuid.toString(), planetsTag);
        });
    }

    public static LaunchingDimensionHandler read(ServerLevel level) {
        return read(level, null, LaunchingDimensionHandler::new, "adastra_launching_dimensions");
    }

    private static LaunchedDimensions get(Player player, boolean create) {
        if (player.level() instanceof ServerLevel level) {
            var data = read(level).data;
            if (data.containsKey(player.getUUID())) {
                return data.get(player.getUUID());
            } else if (create) {
                var dimensions = new LaunchedDimensions();
                data.put(player.getUUID(), dimensions);
                return dimensions;
            }
        }
        return null;
    }

    public static Optional<GlobalPos> getSpawningLocation(Player player, Planet planet) {
        LaunchedDimensions dimensions = get(player, false);
        if (dimensions == null) return Optional.empty();
        GlobalPos pos = dimensions.dimensions.get(planet.dimensionLocation());
        if (pos != null) {
            boolean validPlanet = planet.additionalLaunchDimensions().contains(pos.dimension()) || planet.dimension().equals(pos.dimension());
            if (validPlanet) {
                return Optional.of(pos);
            }
        }
        return Optional.empty();
    }

    public static void addSpawnLocation(Player player) {
        LaunchedDimensions dimensions = get(player, true);
        if (dimensions == null) return;
        if (player.level() instanceof ServerLevel level) {
            ResourceLocation planet = AdAstraData.getPlanetLocation(level.dimension());
            if (planet == null) return;
            dimensions.dimensions.put(planet, GlobalPos.of(level.dimension(), player.blockPosition()));
        }
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    private record LaunchedDimensions(Map<ResourceLocation, GlobalPos> dimensions) {
        public LaunchedDimensions() {
            this(new HashMap<>());
        }
    }
}
