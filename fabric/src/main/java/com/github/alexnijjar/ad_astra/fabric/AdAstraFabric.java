package com.github.alexnijjar.ad_astra.fabric;

import com.github.alexnijjar.ad_astra.AdAstra;

import net.fabricmc.api.ModInitializer;

public class AdAstraFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AdAstra.init();
    }
}
