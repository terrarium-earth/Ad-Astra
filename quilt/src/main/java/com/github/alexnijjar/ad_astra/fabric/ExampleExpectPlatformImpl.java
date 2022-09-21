package com.github.alexnijjar.ad_astra.fabric;

import java.nio.file.Path;

import org.quiltmc.loader.api.QuiltLoader;

import com.github.alexnijjar.ad_astra.ExampleExpectPlatform;

public class ExampleExpectPlatformImpl {
    /**
     * This is our actual method to {@link ExampleExpectPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }
}
