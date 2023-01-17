package earth.terrarium.ad_astra.common.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.BiConsumer;

@ParametersAreNonnullByDefault
public class PlanetData extends SimpleJsonResourceReloadListener {
    private static final Set<Planet> PLANETS = new HashSet<>();
    private static final Set<ResourceKey<Level>> PLANETS_WITH_OXYGEN = new HashSet<>();
    private static final Map<ResourceKey<Level>, Integer> PLANET_TEMPERATURES = new HashMap<>();
    private static final Map<ResourceKey<Level>, Float> PLANET_GRAVITY_VALUES = new HashMap<>();


    public PlanetData() {
        super(Constants.GSON, "planet_data/planets");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> objects, ResourceManager resourceManager, ProfilerFiller profiler) {
        profiler.push("Ad Astra Planet Deserialization");

        Set<Planet> planets = new LinkedHashSet<>();

        for (Map.Entry<ResourceLocation, JsonElement> entry : objects.entrySet()) {
            JsonObject jsonObject = GsonHelper.convertToJsonObject(entry.getValue(), "planet");
            Planet newPlanet = Planet.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, Constants.LOGGER::error);
            planets.add(newPlanet);
        }

        PlanetData.updatePlanets(planets);
        profiler.pop();
    }

    public static void updatePlanets(Set<Planet> planets) {
        clear();
        PLANETS.addAll(planets);
        PLANETS_WITH_OXYGEN.addAll(planets.stream().filter(Planet::oxygen).map(Planet::planet).toList());
        planets.forEach(planet -> PLANET_TEMPERATURES.put(planet.planet(), planet.temperature()));
        planets.forEach(planet -> PLANET_GRAVITY_VALUES.put(planet.planet(), planet.gravity()));
    }

    private static void clear() {
        PLANETS.clear();
        PLANETS_WITH_OXYGEN.clear();
        PLANET_TEMPERATURES.clear();
    }

    public static Set<Planet> getPlanets() {
        return PLANETS;
    }

    public static Set<ResourceKey<Level>> getPlanetsWithOxygen() {
        return PLANETS_WITH_OXYGEN;
    }

    public static Map<ResourceKey<Level>, Integer> getPlanetTemperatures() {
        return PLANET_TEMPERATURES;
    }

    public static Map<ResourceKey<Level>, Float> getPlanetGravityValues() {
        return PLANET_GRAVITY_VALUES;
    }

    public static void onRegisterReloadListeners(BiConsumer<ResourceLocation, PreparableReloadListener> registry) {
        registry.accept(new ResourceLocation(AdAstra.MOD_ID, "planet_data"), new PlanetData());
    }
}
