package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.mrscauthd.beyond_earth.gui.screen_handlers.CoalGeneratorScreenHandler;
import net.mrscauthd.beyond_earth.gui.screen_handlers.CompressorScreenHandler;
import net.mrscauthd.beyond_earth.gui.screen_handlers.NasaWorkbenchScreenHandler;
import net.mrscauthd.beyond_earth.gui.screen_handlers.SolarPanelScreenHandler;
import net.mrscauthd.beyond_earth.util.ModIdentifier;

public class ModScreenHandlers {

    public static ScreenHandlerType<SolarPanelScreenHandler> SOLAR_PANEL_SCREEN_HANDLER;
    public static ScreenHandlerType<CoalGeneratorScreenHandler> COAL_GENERATOR_SCREEN_HANDLER;
    public static ScreenHandlerType<CompressorScreenHandler> COMPRESSOR_SCREEN_HANDLER;
    public static ScreenHandlerType<NasaWorkbenchScreenHandler> NASA_WORKBENCH_SCREEN_HANDLER;

    public static void register() {

        SOLAR_PANEL_SCREEN_HANDLER = register("solar_panel_gui", SolarPanelScreenHandler::new);
        COAL_GENERATOR_SCREEN_HANDLER = register("coal_generator_gui", CoalGeneratorScreenHandler::new);
        COMPRESSOR_SCREEN_HANDLER = register("compressor_gui", CompressorScreenHandler::new);
        NASA_WORKBENCH_SCREEN_HANDLER = register("nasa_workbench_gui", NasaWorkbenchScreenHandler::new);
    }

    public static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerRegistry.ExtendedClientHandlerFactory<T> screenHandler) {
        return ScreenHandlerRegistry.registerExtended(new ModIdentifier(id), screenHandler);
    }
}
