package earth.terrarium.ad_astra.common.entity;

import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

public class LunarianMerchantListener implements ResourceManagerReloadListener {

    @Override
    public void onResourceManagerReload(ResourceManager var1) {
        LunarianMerchantOffer.markNeedReload();
    }
}
