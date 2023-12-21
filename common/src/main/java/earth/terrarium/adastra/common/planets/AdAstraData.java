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

    public AdAstraData() {
        super(Constants.GSON, "planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        PLANETS.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "planets");
            Planet planet = Planet.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, Constants.LOGGER::error);
            PLANETS.put(planet.dimension().location(), planet);
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

    @Nullable
    public static Planet getPlanet(ResourceKey<Level> level) {
        return getPlanet(level.location());
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
