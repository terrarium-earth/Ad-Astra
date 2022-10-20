package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.AdAstraClient;
import earth.terrarium.ad_astra.client.renderer.sky.ModSky;
import earth.terrarium.ad_astra.client.renderer.sky.WorldRenderContext;
import earth.terrarium.ad_astra.client.renderer.sky.cloud_renderer.ModCloudRenderer;
import earth.terrarium.ad_astra.client.renderer.sky.dimension_effects.ModDimensionEffects;
import earth.terrarium.ad_astra.client.renderer.sky.weather_renderer.ModWeatherRenderer;
import earth.terrarium.ad_astra.client.resourcepack.PlanetSkyRenderer;
import earth.terrarium.ad_astra.util.ColourUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public class ClientModSkies {

	public static final Map<RegistryKey<World>, SkyRenderer> SKY_RENDERERS = new HashMap<>();
	public static final Map<RegistryKey<World>, SkyProperties> EFFECTS = new HashMap<>();
	public static final Map<RegistryKey<World>, CloudRenderer> CLOUD_RENDERERS = new HashMap<>();
	public static final Map<RegistryKey<World>, WeatherRenderer> WEATHER_RENDERERS = new HashMap<>();
	public static void register() {

		for (PlanetSkyRenderer skyRenderer : AdAstraClient.skyRenderers) {

			ModSky sky = new ModSky();
			sky.setStars(skyRenderer.starsRenderer());
			sky.setSunsetColour(skyRenderer.sunsetColour());
			sky.setSkyObjects(skyRenderer.skyObjects());
			sky.setHorizonAngle(skyRenderer.horizonAngle());
			sky.disableRenderingWhileRaining(!skyRenderer.weatherEffects().equals(PlanetSkyRenderer.WeatherEffects.NONE));
			SKY_RENDERERS.put(skyRenderer.dimension(), sky);

			// Dimension Effects
			switch (skyRenderer.effects().type()) {
				case SIMPLE -> EFFECTS.put(skyRenderer.dimension(), new ModDimensionEffects());
				case NONE -> EFFECTS.put(skyRenderer.dimension(), new ModDimensionEffects() {
					public float[] getFogColorOverride(float skyAngle, float tickDelta) {
						return null;
					}
				});
				case FOGGY_REVERSED -> EFFECTS.put(skyRenderer.dimension(), new ModDimensionEffects() {
					@Override
					public boolean useThickFog(int camX, int camY) {
						return true;
					}

					@Override
					public float[] getFogColorOverride(float skyAngle, float tickDelta) {
						return null;
					}

				});
				case FOGGY -> EFFECTS.put(skyRenderer.dimension(), new ModDimensionEffects() {
					@Override
					public boolean useThickFog(int camX, int camY) {
						return true;
					}
				});
				case COLORED_HORIZON -> EFFECTS.put(skyRenderer.dimension(), new ModDimensionEffects() {
					@Override
					public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
						return ColourUtils.toVector(skyRenderer.effects().colour());
					}
				});
			}

			// Cloud renderer
			switch (skyRenderer.cloudEffects()) {
				case NONE -> CLOUD_RENDERERS.put(skyRenderer.dimension(), context -> {});
				case VANILLA -> {}
				case VENUS -> CLOUD_RENDERERS.put(skyRenderer.dimension(), new ModCloudRenderer().withVenus());
			}

			// Weather renderer
			switch (skyRenderer.weatherEffects()) {
				case NONE -> {}
				case VENUS -> WEATHER_RENDERERS.put(skyRenderer.dimension(), new ModWeatherRenderer().withVenus());
			}
		}
	}

	@FunctionalInterface
	public interface SkyRenderer {
		void render(WorldRenderContext context);
	}

	@FunctionalInterface
	public interface WeatherRenderer {
		void render(WorldRenderContext context);
	}

	public @FunctionalInterface
	interface CloudRenderer {
		void render(WorldRenderContext context);
	}
}