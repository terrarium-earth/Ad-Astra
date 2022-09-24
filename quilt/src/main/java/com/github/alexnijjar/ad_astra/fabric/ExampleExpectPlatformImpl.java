package com.github.alexnijjar.ad_astra.fabric;

import java.nio.file.Path;

import org.quiltmc.loader.api.QuiltLoader;

import com.github.alexnijjar.ad_astra.util.PlatformUtils;

public class ExampleExpectPlatformImpl {
    /**
     * This is our actual method to {@link PlatformUtils#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return QuiltLoader.getConfigDir();
    }
}
