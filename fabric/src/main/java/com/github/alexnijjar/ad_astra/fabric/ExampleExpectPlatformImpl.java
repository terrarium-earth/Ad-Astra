package com.github.alexnijjar.ad_astra.fabric;

import java.nio.file.Path;

import com.github.alexnijjar.ad_astra.ExampleExpectPlatform;

import net.fabricmc.loader.api.FabricLoader;

public class ExampleExpectPlatformImpl {
    /**
     * This is our actual method to {@link ExampleExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
