package earth.terrarium.adastra.client.ti69;

import earth.terrarium.adastra.api.ti69.client.Ti69App;
import earth.terrarium.adastra.api.ti69.client.Ti69AppApi;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class Ti69AppApiImpl implements Ti69AppApi {
    private final Map<ResourceLocation, Ti69App> apps = new HashMap<>();

    @Override
    public void register(ResourceLocation id, Ti69App app) {
        if (this.apps.containsKey(id)) {
            throw new IllegalArgumentException("Ti-69 App already registered for " + id);
        }
        this.apps.put(id, app);
    }

    @Override
    public Ti69App get(ResourceLocation id) {
        return this.apps.get(id);
    }

    @Override
    public Map<ResourceLocation, Ti69App> apps() {
        return this.apps;
    }
}
