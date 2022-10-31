package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.screen.handler.*;
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import java.util.function.Supplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class ModScreenHandlers {
    public static final DeferredRegister<MenuType<?>> SCREEN_HANDLERS = DeferredRegister.create(AdAstra.MOD_ID, Registry.MENU_REGISTRY);
    public static final Supplier<MenuType<PlanetSelectionScreenHandler>> PLANET_SELECTION_SCREEN_HANDLER = SCREEN_HANDLERS.register("planet_selection_screen_handler", () -> RegistryHelpers.createMenuType((syncId, playerInventory, buf) -> new PlanetSelectionScreenHandler(syncId, playerInventory.player, buf)));    public static final Supplier<MenuType<SolarPanelScreenHandler>> SOLAR_PANEL_SCREEN_HANDLER = SCREEN_HANDLERS.register("solar_panel_screen_handler", () -> RegistryHelpers.createMenuType(SolarPanelScreenHandler::new));

    public static void register() {
        SCREEN_HANDLERS.register();
    }    public static final Supplier<MenuType<CoalGeneratorScreenHandler>> COAL_GENERATOR_SCREEN_HANDLER = SCREEN_HANDLERS.register("coal_generator_screen_handler", () -> RegistryHelpers.createMenuType(CoalGeneratorScreenHandler::new));
    public static final Supplier<MenuType<CompressorScreenHandler>> COMPRESSOR_SCREEN_HANDLER = SCREEN_HANDLERS.register("compressor_screen_handler", () -> RegistryHelpers.createMenuType(CompressorScreenHandler::new));
    public static final Supplier<MenuType<NasaWorkbenchScreenHandler>> NASA_WORKBENCH_SCREEN_HANDLER = SCREEN_HANDLERS.register("nasa_workbench_screen_handler", () -> RegistryHelpers.createMenuType(NasaWorkbenchScreenHandler::new));
    public static final Supplier<MenuType<ConversionScreenHandler>> CONVERSION_SCREEN_HANDLER = SCREEN_HANDLERS.register("conversion_screen_handler", () -> RegistryHelpers.createMenuType(ConversionScreenHandler::new));
    public static final Supplier<MenuType<WaterPumpScreenHandler>> WATER_PUMP_SCREEN_HANDLER = SCREEN_HANDLERS.register("water_pump_screen_handler", () -> RegistryHelpers.createMenuType(WaterPumpScreenHandler::new));
    public static final Supplier<MenuType<OxygenDistributorScreenHandler>> OXYGEN_DISTRIBUTOR_SCREEN_HANDLER = SCREEN_HANDLERS.register("oxygen_distributor_screen_handler", () -> RegistryHelpers.createMenuType(OxygenDistributorScreenHandler::new));
    public static final Supplier<MenuType<CryoFreezerScreenHandler>> CRYO_FREEZER_SCREEN_HANDLER = SCREEN_HANDLERS.register("cryo_freezer_screen_handler", () -> RegistryHelpers.createMenuType(CryoFreezerScreenHandler::new));

    public static final Supplier<MenuType<VehicleScreenHandler>> VEHICLE_SCREEN_HANDLER = SCREEN_HANDLERS.register("vehicle_screen_handler", () -> RegistryHelpers.createMenuType(VehicleScreenHandler::new));
    public static final Supplier<MenuType<LargeVehicleScreenHandler>> LARGE_VEHICLE_SCREEN_HANDLER = SCREEN_HANDLERS.register("large_vehicle_screen_handler", () -> RegistryHelpers.createMenuType(LargeVehicleScreenHandler::new));

    public static final Supplier<MenuType<LanderScreenHandler>> LANDER_SCREEN_HANDLER = SCREEN_HANDLERS.register("lander_screen_handler", () -> RegistryHelpers.createMenuType(LanderScreenHandler::new));




}