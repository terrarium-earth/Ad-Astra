package earth.terrarium.adastra.api.upgrades;

import earth.terrarium.adastra.api.ApiHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.Set;

public interface UpgradeApi {

    UpgradeApi API = ApiHelper.load(UpgradeApi.class);

    /**
     * Registers a new upgrade with the given id.
     *
     * @param id The id of the upgrade.
     */
    void register(ResourceLocation id);

    /**
     * Gets all registered upgrades.
     *
     * @return All upgrades.
     */
    Set<ResourceLocation> upgrades();
}
