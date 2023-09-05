package earth.terrarium.adastra.common.items.upgrades;

import earth.terrarium.adastra.api.upgrades.UpgradeApi;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

public class UpgradeApiImpl implements UpgradeApi {
    private final Set<ResourceLocation> upgrades = new HashSet<>();

    @Override
    public void register(ResourceLocation id) {
        if (this.upgrades.contains(id)) {
            throw new IllegalArgumentException("Upgrade already registered for " + id);
        }
        this.upgrades.add(id);
    }

    @Override
    public Set<ResourceLocation> upgrades() {
        return this.upgrades;
    }
}
