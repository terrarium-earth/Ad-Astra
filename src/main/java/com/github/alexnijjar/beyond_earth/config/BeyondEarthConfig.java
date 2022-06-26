package com.github.alexnijjar.beyond_earth.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "beyond_earth")
@Config.Gui.Background("beyond_earth:textures/blocks/machine_down.png")
public class BeyondEarthConfig implements ConfigData {

    @ConfigEntry.Category("Main")
    @ConfigEntry.Gui.TransitiveObject
    public MainConfig mainConfig = new MainConfig();
}