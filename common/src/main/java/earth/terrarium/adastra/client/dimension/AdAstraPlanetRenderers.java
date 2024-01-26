package earth.terrarium.adastra.client.dimension;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import earth.terrarium.adastra.client.ClientPlatformUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public class AdAstraPlanetRenderers extends SimpleJsonResourceReloadListener {
    public AdAstraPlanetRenderers() {
        super(Constants.GSON, "planet_renderers");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<ResourceKey<Level>, ModDimensionSpecialEffects> effects = new HashMap<>();
        object.forEach((key, value) -> {
            JsonObject json = GsonHelper.convertToJsonObject(value, "planets");
            PlanetRenderer renderer = PlanetRenderer.CODEC.parse(JsonOps.INSTANCE, json).getOrThrow(false, Constants.LOGGER::error);
            effects.put(renderer.dimension(), new ModDimensionSpecialEffects(renderer));
        });
        ClientPlatformUtils.registerPlanetRenderers(effects);
    }
}
