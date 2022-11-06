package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.ClientUtils;
import earth.terrarium.ad_astra.client.screens.*;
import earth.terrarium.ad_astra.client.screens.utils.PlanetSelectionScreen;
import earth.terrarium.ad_astra.registry.ModMenus;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientModScreens {
    public static void register() {
        ClientUtils.registerScreen(ModMenus.SOLAR_PANEL_SCREEN_HANDLER.get(), SolarPanelScreen::new);
        ClientUtils.registerScreen(ModMenus.COAL_GENERATOR_MENU.get(), CoalGeneratorScreen::new);
        ClientUtils.registerScreen(ModMenus.COMPRESSOR_MENU.get(), CompressorScreen::new);
        ClientUtils.registerScreen(ModMenus.NASA_WORKBENCH_MENU.get(), NasaWorkbenchScreen::new);
        ClientUtils.registerScreen(ModMenus.CONVERSION_MENU.get(), ConversionScreen::new);
        ClientUtils.registerScreen(ModMenus.WATER_PUMP_MENU.get(), WaterPumpScreen::new);
        ClientUtils.registerScreen(ModMenus.OXYGEN_DISTRIBUTOR_MENU.get(), OxygenDistributorScreen::new);
        ClientUtils.registerScreen(ModMenus.CRYO_FREEZER_MENU.get(), CryoFreezerScreen::new);

        ClientUtils.registerScreen(ModMenus.VEHICLE_MENU.get(), VehicleScreen::new);
        ClientUtils.registerScreen(ModMenus.LARGE_VEHICLE_MENU.get(), LargeVehicleScreen::new);
        ClientUtils.registerScreen(ModMenus.LANDER_MENU.get(), LanderScreen::new);

        ClientUtils.registerScreen(ModMenus.PLANET_SELECTION_SCREEN_HANDLER.get(), PlanetSelectionScreen::new);
    }
}