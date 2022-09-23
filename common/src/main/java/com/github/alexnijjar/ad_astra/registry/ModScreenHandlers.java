package com.github.alexnijjar.ad_astra.registry;

import java.util.function.Supplier;

import com.github.alexnijjar.ad_astra.AdAstra;
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

import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlers {
	public static final DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLERS = DeferredRegister.create(AdAstra.MOD_ID, Registry.MENU_KEY);

	public static Supplier<ScreenHandlerType<SolarPanelScreenHandler>> SOLAR_PANEL_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<CoalGeneratorScreenHandler>> COAL_GENERATOR_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<CompressorScreenHandler>> COMPRESSOR_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<NasaWorkbenchScreenHandler>> NASA_WORKBENCH_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<ConversionScreenHandler>> CONVERSION_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<WaterPumpScreenHandler>> WATER_PUMP_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<OxygenDistributorScreenHandler>> OXYGEN_DISTRIBUTOR_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<CryoFreezerScreenHandler>> CRYO_FREEZER_SCREEN_HANDLER;

	public static Supplier<ScreenHandlerType<VehicleScreenHandler>> VEHICLE_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<LargeVehicleScreenHandler>> LARGE_VEHICLE_SCREEN_HANDLER;
	public static Supplier<ScreenHandlerType<LanderScreenHandler>> LANDER_SCREEN_HANDLER;

	public static Supplier<ScreenHandlerType<PlanetSelectionScreenHandler>> PLANET_SELECTION_SCREEN_HANDLER;

	public static void register() {

		SOLAR_PANEL_SCREEN_HANDLER = SCREEN_HANDLERS.register("solar_panel_screen_handler", () -> RegistryHelpers.createMenuType(SolarPanelScreenHandler::new));
		COAL_GENERATOR_SCREEN_HANDLER = SCREEN_HANDLERS.register("coal_generator_screen_handler", () -> RegistryHelpers.createMenuType(CoalGeneratorScreenHandler::new));
		COMPRESSOR_SCREEN_HANDLER = SCREEN_HANDLERS.register("compressor_screen_handler", () -> RegistryHelpers.createMenuType(CompressorScreenHandler::new));
		NASA_WORKBENCH_SCREEN_HANDLER = SCREEN_HANDLERS.register("nasa_workbench_screen_handler", () -> RegistryHelpers.createMenuType(NasaWorkbenchScreenHandler::new));
		CONVERSION_SCREEN_HANDLER = SCREEN_HANDLERS.register("conversion_screen_handler", () -> RegistryHelpers.createMenuType(ConversionScreenHandler::new));
		WATER_PUMP_SCREEN_HANDLER = SCREEN_HANDLERS.register("water_pump_screen_handler", () -> RegistryHelpers.createMenuType(WaterPumpScreenHandler::new));
		OXYGEN_DISTRIBUTOR_SCREEN_HANDLER = SCREEN_HANDLERS.register("oxygen_distributor_screen_handler", () -> RegistryHelpers.createMenuType(OxygenDistributorScreenHandler::new));
		CRYO_FREEZER_SCREEN_HANDLER = SCREEN_HANDLERS.register("cryo_freezer_screen_handler", () -> RegistryHelpers.createMenuType(CryoFreezerScreenHandler::new));

		VEHICLE_SCREEN_HANDLER = SCREEN_HANDLERS.register("vehicle_screen_handler", () -> RegistryHelpers.createMenuType(VehicleScreenHandler::new));
		LARGE_VEHICLE_SCREEN_HANDLER = SCREEN_HANDLERS.register("large_vehicle_screen_handler", () -> RegistryHelpers.createMenuType(LargeVehicleScreenHandler::new));
		LANDER_SCREEN_HANDLER = SCREEN_HANDLERS.register("lander_screen_handler", () -> RegistryHelpers.createMenuType(LanderScreenHandler::new));

		PLANET_SELECTION_SCREEN_HANDLER = SCREEN_HANDLERS.register("planet_selection_screen_handler", () -> RegistryHelpers.createMenuType((syncId, playerInventory, buf) -> new PlanetSelectionScreenHandler(syncId, playerInventory.player, buf)));

		SCREEN_HANDLERS.register();
	}

}