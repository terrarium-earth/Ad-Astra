package com.github.alexnijjar.ad_astra.client.resource_pack;

import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.AdAstraClient;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

@Environment(EnvType.CLIENT)
public class PlanetResources {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public static void register() {

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {

            @Override
            public Identifier getFabricId() {
                return new ModIdentifier("planet_resources");
            }

            @Override
            public void reload(ResourceManager manager) {

                List<SkyRenderer> skyRenderers = new LinkedList<>();
                List<SolarSystem> solarSystems = new LinkedList<>();

                // Sky Renderers.
                for (Identifier id : manager.findResources("planet_resources/sky_renderers", path -> path.getPath().endsWith(".json")).keySet()) {
                    try {
                        for (Resource resource : manager.getAllResources(id)) {
                            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                            JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                            if (jsonObject != null) {
                                skyRenderers.add(SkyRendererParser.parse(jsonObject));
                            }
                        }
                    } catch (Exception e) {
                        AdAstra.LOGGER.error("Failed to load Ad Astra sky rendering assets from: \"" + id.toString() + "\"", e);
                        e.printStackTrace();
                    }
                }

                // Solar Systems.
                for (Identifier id : manager.findResources("planet_resources/solar_systems", path -> path.getPath().endsWith(".json")).keySet()) {
                    try {
                        for (Resource resource : manager.getAllResources(id)) {
                            InputStreamReader reader = new InputStreamReader(resource.getInputStream());
                            JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

                            if (jsonObject != null) {
                                solarSystems.add(SolarSystemParser.parse(jsonObject));
                            }
                        }
                    } catch (Exception e) {
                        AdAstra.LOGGER.error("Failed to load Ad Astra solar system assets from: \"" + id.toString() + "\"", e);
                        e.printStackTrace();
                    }
                }

                AdAstraClient.skyRenderers = skyRenderers;
                AdAstraClient.solarSystems = solarSystems;
                AdAstraClient.postAssetRegister();
            }
        });
    }
}
