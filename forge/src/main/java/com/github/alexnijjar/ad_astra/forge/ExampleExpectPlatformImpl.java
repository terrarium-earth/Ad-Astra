package com.github.alexnijjar.ad_astra.forge;

import java.nio.file.Path;

import com.github.alexnijjar.ad_astra.util.PlatformUtils;

import net.minecraftforge.fml.loading.FMLPaths;

public class ExampleExpectPlatformImpl {
    /**
     * This is our actual method to {@link PlatformUtils#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
