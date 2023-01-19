package earth.terrarium.ad_astra.datagen.provider.base;

import com.google.gson.JsonElement;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import earth.terrarium.ad_astra.common.data.Planet;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;


public abstract class PlanetProvider implements DataProvider {
    protected final PackOutput.PathProvider pathProvider;

    public PlanetProvider(PackOutput output) {
        pathProvider = output.createPathProvider(PackOutput.Target.DATA_PACK, "planet_data/planets");
    }

    @Override
    public CompletableFuture<?> run(CachedOutput output) {
        List<CompletableFuture<?>> list = new ArrayList<>();

        buildPlanets(planet -> {
            DataResult<JsonElement> dataResult = Planet.CODEC.encodeStart(JsonOps.INSTANCE, planet);
            JsonElement jsonElement = dataResult.getOrThrow(false, Constants.LOGGER::error);
            Path path = pathProvider.json(planet.planet().location());
            list.add(DataProvider.saveStable(output, jsonElement, path));
        });

        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    protected abstract void buildPlanets(Consumer<Planet> writer);

    @Override
    public String getName() {
        return "Planets";
    }
}
