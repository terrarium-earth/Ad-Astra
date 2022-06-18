package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.gui.screen_handlers.CoalGeneratorScreenHandler;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.CompressorScreenHandler;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.ConversionScreenHandler;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.NasaWorkbenchScreenHandler;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.PlanetSelectionScreenHandler;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.SolarPanelScreenHandler;
import com.github.alexnijjar.beyond_earth.gui.screen_handlers.WaterPumpScreenHandler;
import com.github.alexnijjar.beyond_earth.util.ModIdentifier;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {

    public static ScreenHandlerType<SolarPanelScreenHandler> SOLAR_PANEL_SCREEN_HANDLER;
    public static ScreenHandlerType<CoalGeneratorScreenHandler> COAL_GENERATOR_SCREEN_HANDLER;
    public static ScreenHandlerType<CompressorScreenHandler> COMPRESSOR_SCREEN_HANDLER;
    public static ScreenHandlerType<NasaWorkbenchScreenHandler> NASA_WORKBENCH_SCREEN_HANDLER;
    public static ScreenHandlerType<ConversionScreenHandler> CONVERSION_SCREEN_HANDLER;
    public static ScreenHandlerType<WaterPumpScreenHandler> WATER_PUMP_SCREEN_HANDLER;

    public static ScreenHandlerType<PlanetSelectionScreenHandler> PLANET_SELECTION_SCREEN_HANDLER;

    public static void register() {

        SOLAR_PANEL_SCREEN_HANDLER = register("solar_panel_gui", SolarPanelScreenHandler::new);
        COAL_GENERATOR_SCREEN_HANDLER = register("coal_generator_gui", CoalGeneratorScreenHandler::new);
        COMPRESSOR_SCREEN_HANDLER = register("compressor_gui", CompressorScreenHandler::new);
        NASA_WORKBENCH_SCREEN_HANDLER = register("nasa_workbench_gui", NasaWorkbenchScreenHandler::new);
        CONVERSION_SCREEN_HANDLER = register("conversion_gui", ConversionScreenHandler::new);
        WATER_PUMP_SCREEN_HANDLER = register("water_pump_gui", WaterPumpScreenHandler::new);

        PLANET_SELECTION_SCREEN_HANDLER = register("planet_selection_gui", (syncId, playerInventory, buf) -> new PlanetSelectionScreenHandler(syncId, playerInventory.player, buf));
    }

    // Simple.
    @SuppressWarnings("unused")
    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> screenHandler) {
        return Registry.register(Registry.SCREEN_HANDLER, new ModIdentifier(id), new ScreenHandlerType<>(screenHandler));
    }

    // Extended.
    public static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ExtendedScreenHandlerType.ExtendedFactory<T> screenHandler) {
        return Registry.register(Registry.SCREEN_HANDLER, new ModIdentifier(id), new ExtendedScreenHandlerType<>(screenHandler));
    }
}