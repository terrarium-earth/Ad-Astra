package earth.terrarium.ad_astra.client.registry;

import earth.terrarium.ad_astra.client.ClientUtils;
import earth.terrarium.ad_astra.client.screens.*;
import earth.terrarium.ad_astra.client.screens.utils.PlanetSelectionScreen;
import earth.terrarium.ad_astra.registry.ModScreenHandlers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class ClientModScreens {
	public static void register() {
		ClientUtils.registerScreen(ModScreenHandlers.SOLAR_PANEL_SCREEN_HANDLER.get(), SolarPanelScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.COAL_GENERATOR_SCREEN_HANDLER.get(), CoalGeneratorScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.COMPRESSOR_SCREEN_HANDLER.get(), CompressorScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.NASA_WORKBENCH_SCREEN_HANDLER.get(), NasaWorkbenchScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.CONVERSION_SCREEN_HANDLER.get(), ConversionScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.WATER_PUMP_SCREEN_HANDLER.get(), WaterPumpScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.OXYGEN_DISTRIBUTOR_SCREEN_HANDLER.get(), OxygenDistributorScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.CRYO_FREEZER_SCREEN_HANDLER.get(), CryoFreezerScreen::new);

		ClientUtils.registerScreen(ModScreenHandlers.VEHICLE_SCREEN_HANDLER.get(), VehicleScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.LARGE_VEHICLE_SCREEN_HANDLER.get(), LargeVehicleScreen::new);
		ClientUtils.registerScreen(ModScreenHandlers.LANDER_SCREEN_HANDLER.get(), LanderScreen::new);

		ClientUtils.registerScreen(ModScreenHandlers.PLANET_SELECTION_SCREEN_HANDLER.get(), PlanetSelectionScreen::new);
	}
}