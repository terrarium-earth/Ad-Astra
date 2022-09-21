package com.github.alexnijjar.ad_astra.forge;

import com.github.alexnijjar.ad_astra.AdAstra;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AdAstra.MOD_ID)
public class AdAstraForge {
    public AdAstraForge() {
        // Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(AdAstra.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AdAstra.init();
    }
}
