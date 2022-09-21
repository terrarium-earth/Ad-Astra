package com.github.alexnijjar.ad_astra.client.resourcepack;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.client.AdAstraClient;
import com.github.alexnijjar.ad_astra.client.registry.ClientModSkies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import dev.architectury.registry.ReloadListenerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.SynchronousResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

@Environment(EnvType.CLIENT)
public class PlanetResources {

	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

	public static void register() {

		ReloadListenerRegistry.register(ResourceType.CLIENT_RESOURCES, new SynchronousResourceReloader() {

			@Override
			public void reload(ResourceManager manager) {

				List<SkyRenderer> skyRenderers = new ArrayList<>();
				List<SolarSystem> solarSystems = new ArrayList<>();
				List<PlanetRing> planetRings = new ArrayList<>();
				List<Galaxy> galaxies = new ArrayList<>();

				// Sky Renderers
				for (Identifier id : manager.findResources("planet_resources/sky_renderers", path -> path.getPath().endsWith(".json")).keySet()) {
					try {
						for (Resource resource : manager.getAllResources(id)) {
							InputStreamReader reader = new InputStreamReader(resource.open());
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

				// Solar Systems
				for (Identifier id : manager.findResources("planet_resources/solar_systems", path -> path.getPath().endsWith(".json")).keySet()) {
					try {
						for (Resource resource : manager.getAllResources(id)) {
							InputStreamReader reader = new InputStreamReader(resource.open());
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

				for (Identifier id : manager.findResources("planet_resources/planet_rings", path -> path.getPath().endsWith(".json")).keySet()) {
					try {
						for (Resource resource : manager.getAllResources(id)) {
							InputStreamReader reader = new InputStreamReader(resource.open());
							JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

							if (jsonObject != null) {
								planetRings.add(PlanetRingParser.parse(jsonObject));
							}
						}
					} catch (Exception e) {
						AdAstra.LOGGER.error("Failed to load Ad Astra planet ring assets from: \"" + id.toString() + "\"", e);
						e.printStackTrace();
					}
				}

				for (Identifier id : manager.findResources("planet_resources/galaxy", path -> path.getPath().endsWith(".json")).keySet()) {
					try {
						for (Resource resource : manager.getAllResources(id)) {
							InputStreamReader reader = new InputStreamReader(resource.open());
							JsonObject jsonObject = JsonHelper.deserialize(GSON, reader, JsonObject.class);

							if (jsonObject != null) {
								galaxies.add(GalaxyParser.parse(jsonObject));
							}
						}
					} catch (Exception e) {
						AdAstra.LOGGER.error("Failed to load Ad Astra galaxy assets from: \"" + id.toString() + "\"", e);
						e.printStackTrace();
					}
				}

				solarSystems.sort((s1, s2) -> s1.solarSystem().compareTo(s2.solarSystem()));
				galaxies.sort((g1, g2) -> g1.galaxy().compareTo(g2.galaxy()));
				AdAstraClient.skyRenderers = skyRenderers;
				AdAstraClient.solarSystems = solarSystems;
				AdAstraClient.planetRings = planetRings;
				AdAstraClient.galaxies = galaxies;
				ClientModSkies.register();
			}
		});
	}
}
