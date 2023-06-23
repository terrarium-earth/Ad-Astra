package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.ClientPlatformUtils;
import earth.terrarium.ad_astra.client.screen.*;
import earth.terrarium.ad_astra.client.screen.util.PlanetSelectionScreen;
import earth.terrarium.ad_astra.common.registry.ModMenus;

public class ClientModScreens {
    public static void init() {
        ClientPlatformUtils.registerScreen(ModMenus.SOLAR_PANEL_SCREEN_HANDLER.get(), SolarPanelScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.COAL_GENERATOR_MENU.get(), CoalGeneratorScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.COMPRESSOR_MENU.get(), CompressorScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.NASA_WORKBENCH_MENU.get(), NasaWorkbenchScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.CONVERSION_MENU.get(), ConversionScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.WATER_PUMP_MENU.get(), WaterPumpScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.OXYGEN_DISTRIBUTOR_MENU.get(), OxygenDistributorScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.CRYO_FREEZER_MENU.get(), CryoFreezerScreen::new);

        ClientPlatformUtils.registerScreen(ModMenus.VEHICLE_MENU.get(), VehicleScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.LARGE_VEHICLE_MENU.get(), LargeVehicleScreen::new);
        ClientPlatformUtils.registerScreen(ModMenus.LANDER_MENU.get(), LanderScreen::new);

        ClientPlatformUtils.registerScreen(ModMenus.PLANET_SELECTION_SCREEN_HANDLER.get(), PlanetSelectionScreen::new);
    }
}