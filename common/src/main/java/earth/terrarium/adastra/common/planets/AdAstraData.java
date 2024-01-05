package earth.terrarium.adastra.common.planets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import com.teamresourceful.resourcefullib.common.networking.PacketHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class AdAstraData extends SimpleJsonResourceReloadListener {
    private static final Map<ResourceLocation, Planet> PLANETS = new HashMap<>();
    private static final Map<ResourceLocation, ResourceLocation> DIMENSIONS_TO_PLANETS = new HashMap<>();

    public AdAstraData() {
        super(Constants.GSON, "planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        PLANETS.clear();
        DIMENSIONS_TO_PLANETS.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            JsonObject json = GsonHelper.convertToJsonObject(entry.getValue(), "planets");
            Planet planet = Planet.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(false, Constants.LOGGER::error);
            PLANETS.put(planet.dimensionLocation(), planet);
            DIMENSIONS_TO_PLANETS.put(planet.dimensionLocation(), planet.dimensionLocation());
            for (ResourceKey<Level> dimension : planet.additionalLaunchDimensions()) {
                DIMENSIONS_TO_PLANETS.put(dimension.location(), planet.dimensionLocation());
            }
        }
    }

    public static void encodePlanets(FriendlyByteBuf buf) {
        PacketHelper.writeWithYabn(buf, Planet.CODEC.listOf(), planets().values().stream().toList(), true)
            .get()
            .mapRight(DataResult.PartialResult::message)
            .ifRight(Constants.LOGGER::error);
    }

    public static Collection<Planet> decodePlanets(FriendlyByteBuf buf) {
        return PacketHelper.readWithYabn(buf, Planet.CODEC.listOf(), true)
            .get()
            .mapRight(DataResult.PartialResult::message)
            .ifRight(Constants.LOGGER::error)
            .left()
            .orElse(Collections.emptyList());
    }

    public static ResourceLocation getPlanetLocation(ResourceKey<Level> dimension) {
        return DIMENSIONS_TO_PLANETS.get(dimension.location());
    }

    @Nullable
    public static Planet getPlanet(ResourceLocation location) {
        return PLANETS.get(location);
    }

    public static boolean isPlanet(ResourceLocation location) {
        return PLANETS.containsKey(location);
    }

    public static boolean isSpace(ResourceLocation location) {
        return isPlanet(location) && PLANETS.get(location).isSpace();
    }

    public static boolean canLaunchFrom(ResourceKey<Level> dimension) {
        return DIMENSIONS_TO_PLANETS.containsKey(dimension.location());
    }

    public static Map<ResourceLocation, Planet> planets() {
        return PLANETS;
    }

    public static Set<ResourceLocation> solarSystems() {
        return PLANETS.values().stream().map(Planet::solarSystem).collect(Collectors.toUnmodifiableSet());
    }

    public static void setPlanets(Map<ResourceLocation, Planet> planets) {
        PLANETS.clear();
        PLANETS.putAll(planets);
    }
}
