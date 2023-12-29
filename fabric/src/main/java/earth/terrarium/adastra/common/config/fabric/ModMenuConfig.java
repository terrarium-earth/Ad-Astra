package earth.terrarium.adastra.common.config.fabric;

import com.teamresourceful.resourcefulconfig.client.ConfigScreen;
import com.teamresourceful.resourcefulconfig.common.config.ResourcefulConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.config.AdAstraConfig;

public class ModMenuConfig implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ResourcefulConfig config = AdAstra.CONFIGURATOR.getConfig(AdAstraConfig.class);
            if (config == null) return null;
            return new ConfigScreen(null, config);
        };
    }
}