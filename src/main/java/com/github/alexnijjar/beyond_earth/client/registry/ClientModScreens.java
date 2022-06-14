package com.github.alexnijjar.beyond_earth.client.registry;

import com.github.alexnijjar.beyond_earth.client.screens.CoalGeneratorScreen;
import com.github.alexnijjar.beyond_earth.client.screens.CompressorScreen;
import com.github.alexnijjar.beyond_earth.client.screens.FuelRefineryScreen;
import com.github.alexnijjar.beyond_earth.client.screens.NasaWorkbenchScreen;
import com.github.alexnijjar.beyond_earth.client.screens.SolarPanelScreen;
import com.github.alexnijjar.beyond_earth.client.screens.planet_selection.PlanetSelectionScreen;
import com.github.alexnijjar.beyond_earth.registry.ModScreenHandlers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

@Environment(EnvType.CLIENT)
public class ClientModScreens {

    public static void register() {
        HandledScreens.register(ModScreenHandlers.SOLAR_PANEL_SCREEN_HANDLER, SolarPanelScreen::new);
        HandledScreens.register(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, CoalGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.COMPRESSOR_SCREEN_HANDLER, CompressorScreen::new);
        HandledScreens.register(ModScreenHandlers.NASA_WORKBENCH_SCREEN_HANDLER, NasaWorkbenchScreen::new);
        HandledScreens.register(ModScreenHandlers.FUEL_REFINERY_SCREEN_HANDLER, FuelRefineryScreen::new);

        HandledScreens.register(ModScreenHandlers.PLANET_SELECTION_SCREEN_HANDLER, PlanetSelectionScreen::new);
    }
}