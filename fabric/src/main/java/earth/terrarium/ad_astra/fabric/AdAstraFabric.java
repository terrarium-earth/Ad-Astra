package earth.terrarium.ad_astra.fabric;

import earth.terrarium.ad_astra.AdAstra;

import net.fabricmc.api.ModInitializer;

public class AdAstraFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AdAstra.init();
    }
}
