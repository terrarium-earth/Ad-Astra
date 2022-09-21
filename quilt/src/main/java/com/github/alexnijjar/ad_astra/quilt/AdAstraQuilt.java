package com.github.alexnijjar.ad_astra.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

import com.github.alexnijjar.ad_astra.AdAstra;

public class AdAstraQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        AdAstra.init();
    }
}
