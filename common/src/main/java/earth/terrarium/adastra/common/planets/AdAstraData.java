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

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AdAstraData extends SimpleJsonResourceReloadListener {
    private static final Map<ResourceKey<Level>, Planet> PLANETS = new HashMap<>();

    public AdAstraData() {
        super(Constants.GSON, "planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        PLANETS.clear();
        for (Map.Entry<ResourceLocation, JsonElement> entry : object.entrySet()) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "planets");
            Planet planet = Planet.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, Constants.LOGGER::error);
            PLANETS.put(planet.dimension(), planet);
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
        return PLANETS.get(level);
    }

    public static boolean isPlanet(ResourceKey<Level> level) {
        return PLANETS.containsKey(level);
    }

    public static Map<ResourceKey<Level>, Planet> planets() {
        return PLANETS;
    }

    public static void setPlanets(Map<ResourceKey<Level>, Planet> planets) {
        PLANETS.clear();
        PLANETS.putAll(planets);
    }
}
