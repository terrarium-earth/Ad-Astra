package com.github.alexnijjar.beyond_earth.data;

import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import com.github.alexnijjar.beyond_earth.BeyondEarth;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;
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
                Set<Planet> planets = new HashSet<>();

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

                BeyondEarth.planets = planets;
            }
        });
    }
}
