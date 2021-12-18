package net.mrscauthd.beyond_earth.events;

import net.minecraftforge.fml.loading.FMLPaths;

import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.io.IOException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.File;
import java.io.BufferedReader;

import com.google.gson.JsonObject;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import net.mrscauthd.beyond_earth.BeyondEarthMod;

@Mod.EventBusSubscriber(modid = BeyondEarthMod.MODID,bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
	public static boolean AlienSpawn = true;
	public static boolean AlienVillageStructure = true;
	public static boolean MeteorStructure = true;
	public static boolean PlayerOxygenSystem = true;
	public static boolean StarCrawlerSpawn = true;
	public static boolean AlienZombieSpawn = true;
	public static boolean EntityOxygenSystem = true;
	public static boolean VenusBulletStructure = true;
	public static boolean VenusTowerStructure = true;
	public static boolean OILWellStructure = true;
	public static boolean CrimsonVillageStructure = true;

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		File beyond_earth = new File((FMLPaths.GAMEDIR.get().toString() + "" + ("//config/")), File.separator + "beyond_earth-config.json");
		if (!beyond_earth.exists()) {
			try {
				beyond_earth.createNewFile();
				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonObject Config = new JsonObject();
				Config.addProperty("Alien Spawn", true);
				Config.addProperty("Star Crawler Spawn", true);
				Config.addProperty("Alien Zombie Spawn", true);
				Config.addProperty("Alien Village Structure", true);
				Config.addProperty("Meteor Structure", true);
				Config.addProperty("Venus Bullet Structure", true);
				Config.addProperty("Venus Tower Structure", true);
				Config.addProperty("Oil Well Structure", true);
				Config.addProperty("Crimson Village Structure", true);
				Config.addProperty("Player Oxygen System", true);
				Config.addProperty("Entity Oxygen System", true);
				try {
					FileWriter beyond_earthfw = new FileWriter(beyond_earth);
					beyond_earthfw.write(gson.toJson(Config));
					beyond_earthfw.close();
				} catch (IOException exception) {
					exception.printStackTrace();
				}
			} catch (IOException exception) {
				exception.printStackTrace();
			}
		}
		{
			try {
				BufferedReader beyond_earthReader = new BufferedReader(new FileReader(beyond_earth));
				StringBuilder jsonstringbuilder = new StringBuilder();
				String line;
				while ((line = beyond_earthReader.readLine()) != null) {
					jsonstringbuilder.append(line);
				}
				JsonObject Config = new Gson().fromJson(jsonstringbuilder.toString(), JsonObject.class);
				if (Config == null) {
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					Config = new JsonObject();
					Config.addProperty("Alien Spawn", true);
					Config.addProperty("Star Crawler Spawn", true);
					Config.addProperty("Alien Zombie Spawn", true);
					Config.addProperty("Alien Village Structure", true);
					Config.addProperty("Meteor Structure", true);
					Config.addProperty("Venus Bullet Structure", true);
					Config.addProperty("Venus Tower Structure", true);
					Config.addProperty("Oil Well Structure", true);
					Config.addProperty("Crimson Village Structure", true);
					Config.addProperty("Player Oxygen System", true);
					Config.addProperty("Entity Oxygen System", true);
					try {
						FileWriter beyond_earthfw = new FileWriter(beyond_earth);
						beyond_earthfw.write(gson.toJson(Config));
						beyond_earthfw.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}

				if (Config.get("Alien Spawn") == null || Config.get("Star Crawler Spawn") == null || Config.get("Alien Zombie Spawn") == null || Config.get("Alien Village Structure") == null || Config.get("Meteor Structure") == null || Config.get("Player Oxygen System") == null || Config.get("Entity Oxygen System") == null || Config.get("Venus Bullet Structure") == null || Config.get("Venus Tower Structure") == null || Config.get("Oil Well Structure") == null || Config.get("Crimson Village Structure") == null) {
					Gson gson = new GsonBuilder().setPrettyPrinting().create();
					Config = new JsonObject();
					Config.addProperty("Alien Spawn", true);
					Config.addProperty("Star Crawler Spawn", true);
					Config.addProperty("Alien Zombie Spawn", true);
					Config.addProperty("Alien Village Structure", true);
					Config.addProperty("Meteor Structure", true);
					Config.addProperty("Venus Bullet Structure", true);
					Config.addProperty("Venus Tower Structure", true);
					Config.addProperty("Oil Well Structure", true);
					Config.addProperty("Crimson Village Structure", true);
					Config.addProperty("Player Oxygen System", true);
					Config.addProperty("Entity Oxygen System", true);
					try {
						FileWriter beyond_earthfw = new FileWriter(beyond_earth);
						beyond_earthfw.write(gson.toJson(Config));
						beyond_earthfw.close();
					} catch (IOException exception) {
						exception.printStackTrace();
					}
				}
				beyond_earthReader.close();
				AlienSpawn = Config.get("Alien Spawn").getAsBoolean();
				StarCrawlerSpawn = Config.get("Star Crawler Spawn").getAsBoolean();
				AlienZombieSpawn = Config.get("Alien Zombie Spawn").getAsBoolean();
				AlienVillageStructure = Config.get("Alien Village Structure").getAsBoolean();
				MeteorStructure = Config.get("Meteor Structure").getAsBoolean();
				VenusBulletStructure = Config.get("Venus Bullet Structure").getAsBoolean();
				VenusTowerStructure = Config.get("Venus Bullet Structure").getAsBoolean();
				OILWellStructure = Config.get("Oil Well Structure").getAsBoolean();
				CrimsonVillageStructure = Config.get("Crimson Village Structure").getAsBoolean();
				PlayerOxygenSystem = Config.get("Player Oxygen System").getAsBoolean();
				EntityOxygenSystem = Config.get("Entity Oxygen System").getAsBoolean();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
