package net.mrscauthd.beyond_earth.client.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.mrscauthd.beyond_earth.client.screens.*;
import net.mrscauthd.beyond_earth.client.screens.planet_selection.PlanetSelectionScreen;
import net.mrscauthd.beyond_earth.registry.ModScreenHandlers;

public class ModScreens {

    @Environment(EnvType.CLIENT)
    public static void register() {
        HandledScreens.register(ModScreenHandlers.SOLAR_PANEL_SCREEN_HANDLER, SolarPanelScreen::new);
        HandledScreens.register(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER, CoalGeneratorScreen::new);
        HandledScreens.register(ModScreenHandlers.COMPRESSOR_SCREEN_HANDLER, CompressorScreen::new);
        HandledScreens.register(ModScreenHandlers.NASA_WORKBENCH_SCREEN_HANDLER, NasaWorkbenchScreen::new);

        HandledScreens.register(ModScreenHandlers.PLANET_SELECTION_SCREEN_HANDLER, PlanetSelectionScreen::new);
    }
}
