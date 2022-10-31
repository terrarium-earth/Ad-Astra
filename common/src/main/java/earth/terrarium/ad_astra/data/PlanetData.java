package earth.terrarium.ad_astra.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import earth.terrarium.ad_astra.AdAstra;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.GsonHelper;

public class PlanetData implements ResourceManagerReloadListener {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public void onResourceManagerReload(ResourceManager manager) {
        List<Planet> planets = new ArrayList<>();

        // Planets.
        for (ResourceLocation id : manager.listResources("planet_data/planets", path -> path.getPath().endsWith(".json")).keySet()) {
            try {
                for (Resource resource : manager.getResourceStack(id)) {
                    InputStreamReader reader = new InputStreamReader(resource.open());
                    JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonObject.class);

                    if (jsonObject != null) {
                        planets.add(Planet.CODEC.parse(JsonOps.INSTANCE, jsonObject).getOrThrow(false, AdAstra.LOGGER::error));
                    }
                }
            } catch (Exception e) {
                AdAstra.LOGGER.error("Failed to load Ad Astra planet data from: \"" + id.toString() + "\"", e);
                e.printStackTrace();
            }
        }

        AdAstra.planets = new HashSet<>(planets);
        AdAstra.planetWorlds = AdAstra.planets.stream().map(Planet::level).collect(Collectors.toSet());
        AdAstra.orbitWorlds = AdAstra.planets.stream().map(Planet::orbitWorld).collect(Collectors.toSet());
        AdAstra.adAstraWorlds = Stream.concat(AdAstra.planetWorlds.stream(), AdAstra.orbitWorlds.stream()).collect(Collectors.toSet());
        AdAstra.levelsWithOxygen = AdAstra.planets.stream().filter(Planet::hasOxygen).map(Planet::level).collect(Collectors.toSet());
    }
}
