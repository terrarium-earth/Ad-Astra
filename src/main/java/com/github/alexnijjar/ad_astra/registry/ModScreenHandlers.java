package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.screen.handler.CoalGeneratorScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.CompressorScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.ConversionScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.CryoFreezerScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.LanderScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.LargeVehicleScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.NasaWorkbenchScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.OxygenDistributorScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.PlanetSelectionScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.SolarPanelScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.VehicleScreenHandler;
import com.github.alexnijjar.ad_astra.screen.handler.WaterPumpScreenHandler;
import com.github.alexnijjar.ad_astra.util.ModIdentifier;

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
    public static ScreenHandlerType<OxygenDistributorScreenHandler> OXYGEN_DISTRIBUTOR_SCREEN_HANDLER;
    public static ScreenHandlerType<CryoFreezerScreenHandler> CRYO_FREEZER_SCREEN_HANDLER;

    public static ScreenHandlerType<VehicleScreenHandler> VEHICLE_SCREEN_HANDLER;
    public static ScreenHandlerType<LargeVehicleScreenHandler> LARGE_VEHICLE_SCREEN_HANDLER;
    public static ScreenHandlerType<LanderScreenHandler> LANDER_SCREEN_HANDLER;

    public static ScreenHandlerType<PlanetSelectionScreenHandler> PLANET_SELECTION_SCREEN_HANDLER;

    public static void register() {

        SOLAR_PANEL_SCREEN_HANDLER = register("solar_panel_screen_handler", SolarPanelScreenHandler::new);
        COAL_GENERATOR_SCREEN_HANDLER = register("coal_generator_screen_handler", CoalGeneratorScreenHandler::new);
        COMPRESSOR_SCREEN_HANDLER = register("compressor_screen_handler", CompressorScreenHandler::new);
        NASA_WORKBENCH_SCREEN_HANDLER = register("nasa_workbench_screen_handler", NasaWorkbenchScreenHandler::new);
        CONVERSION_SCREEN_HANDLER = register("conversion_screen_handler", ConversionScreenHandler::new);
        WATER_PUMP_SCREEN_HANDLER = register("water_pump_screen_handler", WaterPumpScreenHandler::new);
        CRYO_FREEZER_SCREEN_HANDLER = register("cryo_freezer_screen_handler", CryoFreezerScreenHandler::new);

        VEHICLE_SCREEN_HANDLER = register("vehicle_screen_handler", VehicleScreenHandler::new);
        LARGE_VEHICLE_SCREEN_HANDLER = register("large_vehicle_screen_handler", LargeVehicleScreenHandler::new);
        LANDER_SCREEN_HANDLER = register("lander_screen_handler", LanderScreenHandler::new);
        OXYGEN_DISTRIBUTOR_SCREEN_HANDLER = register("oxygen_distributor_screen_handler", OxygenDistributorScreenHandler::new);

        PLANET_SELECTION_SCREEN_HANDLER = register("planet_selection_screen_handler", (syncId, playerInventory, buf) -> new PlanetSelectionScreenHandler(syncId, playerInventory.player, buf));
    }

    public static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ExtendedScreenHandlerType.ExtendedFactory<T> screenHandler) {
        return Registry.register(Registry.SCREEN_HANDLER, new ModIdentifier(id), new ExtendedScreenHandlerType<>(screenHandler));
    }
}