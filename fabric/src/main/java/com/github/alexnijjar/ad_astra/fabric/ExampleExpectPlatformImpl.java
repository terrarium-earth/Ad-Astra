package com.github.alexnijjar.ad_astra.fabric;

import java.nio.file.Path;

import com.github.alexnijjar.ad_astra.util.PlatformUtils;

import net.fabricmc.loader.api.FabricLoader;

public class ExampleExpectPlatformImpl {
    /**
     * This is our actual method to {@link PlatformUtils#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
