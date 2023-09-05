package earth.terrarium.adastra.common.items.upgrades;

import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.api.upgrades.UpgradeApi;
import net.minecraft.resources.ResourceLocation;

public class Upgrades {

    public static final ResourceLocation CAPACITY_UPGRADE = new ResourceLocation(AdAstra.MOD_ID, "capacity");
    public static final ResourceLocation SPEED_UPGRADE = new ResourceLocation(AdAstra.MOD_ID, "speed");

    public static void init() {
        UpgradeApi.API.register(CAPACITY_UPGRADE);
        UpgradeApi.API.register(SPEED_UPGRADE);
    }
}
