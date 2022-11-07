package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.ClientUtils;
import earth.terrarium.ad_astra.client.screens.*;
import earth.terrarium.ad_astra.client.screens.utils.PlanetSelectionScreen;
import earth.terrarium.ad_astra.registry.ModMenuTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientModScreens {
    public static void register() {
        ClientUtils.registerScreen(ModMenuTypes.SOLAR_PANEL_SCREEN_HANDLER.get(), SolarPanelScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.COAL_GENERATOR_MENU.get(), CoalGeneratorScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.COMPRESSOR_MENU.get(), CompressorScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.NASA_WORKBENCH_MENU.get(), NasaWorkbenchScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.CONVERSION_MENU.get(), ConversionScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.WATER_PUMP_MENU.get(), WaterPumpScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.OXYGEN_DISTRIBUTOR_MENU.get(), OxygenDistributorScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.CRYO_FREEZER_MENU.get(), CryoFreezerScreen::new);

        ClientUtils.registerScreen(ModMenuTypes.VEHICLE_MENU.get(), VehicleScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.LARGE_VEHICLE_MENU.get(), LargeVehicleScreen::new);
        ClientUtils.registerScreen(ModMenuTypes.LANDER_MENU.get(), LanderScreen::new);

        ClientUtils.registerScreen(ModMenuTypes.PLANET_SELECTION_SCREEN_HANDLER.get(), PlanetSelectionScreen::new);
    }
}