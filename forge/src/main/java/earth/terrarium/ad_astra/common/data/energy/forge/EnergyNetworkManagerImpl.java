package earth.terrarium.ad_astra.common.data.energy.forge;

import earth.terrarium.ad_astra.common.data.energy.EnergyNetworkManager;
import earth.terrarium.ad_astra.common.data.energy.EnergyNetworkVisibility;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;

public class EnergyNetworkManagerImpl {
    @SuppressWarnings("unchecked")
    public static DefaultedRegistry<EnergyNetworkVisibility> getVisibilityRegistry() {
        return (DefaultedRegistry<EnergyNetworkVisibility>) BuiltInRegistries.REGISTRY.get(EnergyNetworkManager.VISIBILITY_REGISTRY_ID);
    }
}
