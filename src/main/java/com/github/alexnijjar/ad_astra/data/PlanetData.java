package com.github.alexnijjar.ad_astra.data;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;
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

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

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
				List<Planet> planets = new ArrayList<>();

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
						AdAstra.LOGGER.error("Failed to load Ad Astra planet data from: \"" + id.toString() + "\"", e);
						e.printStackTrace();
					}
				}

				AdAstra.planets = new HashSet<>(planets);
			}
		});
	}
}
