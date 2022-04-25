package net.mrscauthd.beyond_earth.data;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.mrscauthd.beyond_earth.BeyondEarth;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class PlanetData {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void register() {

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new SimpleSynchronousResourceReloadListener() {

            @Override
            public Identifier getFabricId() {
                return new ModIdentifier("planet_data");
            }

            @Override
            public void reload(ResourceManager manager) {
                List<Planet> planets = new LinkedList<>();
                List<SolarSystem> solarSystems = new LinkedList<>();

                // Planets.
                for (Identifier id : manager.findResources("planet_data/planets", path -> path.endsWith(".json"))) {
                    try {
                        for (Resource resource : manager.getAllResources(id)) {
                            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                            JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                            if (jsonObject != null) {
                                planets.add(PlanetParser.parse(jsonObject));
                            }
                        }
                    } catch (Exception e) {
                        BeyondEarth.LOGGER.error("Failed to load Beyond Earth planet data from: \"" + id.toString() + "\"", e);
                        e.printStackTrace();
                    }
                }

                // Renderers.
                for (Identifier id : manager.findResources("planet_data/renderers", path -> path.endsWith(".json"))) {
                    try {
                        for (Resource resource : manager.getAllResources(id)) {
                            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                            JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                            if (jsonObject != null) {

                            }
                        }
                    } catch (Exception e) {
                        BeyondEarth.LOGGER.error("Failed to load Beyond Earth world rendering data from: \"" + id.toString() + "\"", e);
                        e.printStackTrace();
                    }
                }

                // Solar Systems.
                for (Identifier id : manager.findResources("planet_data/solar_systems", path -> path.endsWith(".json"))) {
                    try {
                        for (Resource resource : manager.getAllResources(id)) {
                            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                            JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                            if (jsonObject != null) {
                                solarSystems.add(SolarSystemParser.parse(jsonObject));
                            }
                        }
                    } catch (Exception e) {
                        BeyondEarth.LOGGER.error("Failed to load Beyond Earth solar system data from: \"" + id.toString() + "\"", e);
                        e.printStackTrace();
                    }
                }

                BeyondEarth.planets = planets;
                BeyondEarth.solarSystems = solarSystems;
            }
        });
    }
}
