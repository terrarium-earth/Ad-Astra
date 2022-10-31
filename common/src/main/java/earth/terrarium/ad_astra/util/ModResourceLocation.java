package earth.terrarium.ad_astra.util;

import earth.terrarium.ad_astra.AdAstra;
import net.minecraft.resources.ResourceLocation;

public class ModResourceLocation extends ResourceLocation {
    public ModResourceLocation(String path) {
        super(AdAstra.MOD_ID, path);
    }
}