package earth.terrarium.adastra.api.ti69.client;

import earth.terrarium.adastra.api.ApiHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public interface Ti69AppApi {

    Ti69AppApi API = ApiHelper.load(Ti69AppApi.class);

    /**
     * Registers a new {@link Ti69App} with the given id.
     *
     * @param id  The id of the app.
     * @param app The app to register.
     */
    void register(ResourceLocation id, Ti69App app);

    /**
     * Gets the {@link Ti69App} with the given id.
     *
     * @param id The id of the app.
     * @return The app.
     */
    Ti69App get(ResourceLocation id);

    /**
     * Gets all registered {@link Ti69App}s.
     *
     * @return All apps.
     */
    Map<ResourceLocation, Ti69App> apps();
}
