package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.screen.menu.*;
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final RegistryHolder<MenuType<?>> MENU_TYPES = new RegistryHolder<>(Registry.MENU, AdAstra.MOD_ID);

    public static final Supplier<MenuType<PlanetSelectionMenu>> PLANET_SELECTION_SCREEN_HANDLER = MENU_TYPES.register("planet_selection_menu", () -> RegistryHelpers.createMenuType((syncId, playerInventory, buf) -> new PlanetSelectionMenu(syncId, playerInventory.player, buf)));
    public static final Supplier<MenuType<SolarPanelMenu>> SOLAR_PANEL_SCREEN_HANDLER = MENU_TYPES.register("solar_panel_menu", () -> RegistryHelpers.createMenuType(SolarPanelMenu::new));
    public static final Supplier<MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU = MENU_TYPES.register("coal_generator_menu", () -> RegistryHelpers.createMenuType(CoalGeneratorMenu::new));
    public static final Supplier<MenuType<CompressorMenu>> COMPRESSOR_MENU = MENU_TYPES.register("compressor_menu", () -> RegistryHelpers.createMenuType(CompressorMenu::new));
    public static final Supplier<MenuType<NasaWorkbenchMenu>> NASA_WORKBENCH_MENU = MENU_TYPES.register("nasa_workbench_menu", () -> RegistryHelpers.createMenuType(NasaWorkbenchMenu::new));
    public static final Supplier<MenuType<ConversionMenu>> CONVERSION_MENU = MENU_TYPES.register("conversion_menu", () -> RegistryHelpers.createMenuType(ConversionMenu::new));
    public static final Supplier<MenuType<WaterPumpMenu>> WATER_PUMP_MENU = MENU_TYPES.register("water_pump_menu", () -> RegistryHelpers.createMenuType(WaterPumpMenu::new));
    public static final Supplier<MenuType<OxygenDistributorMenu>> OXYGEN_DISTRIBUTOR_MENU = MENU_TYPES.register("oxygen_distributor_menu", () -> RegistryHelpers.createMenuType(OxygenDistributorMenu::new));
    public static final Supplier<MenuType<CryoFreezerMenu>> CRYO_FREEZER_MENU = MENU_TYPES.register("cryo_freezer_menu", () -> RegistryHelpers.createMenuType(CryoFreezerMenu::new));
    public static final Supplier<MenuType<VehicleMenu>> VEHICLE_MENU = MENU_TYPES.register("vehicle_menu", () -> RegistryHelpers.createMenuType(VehicleMenu::new));
    public static final Supplier<MenuType<LargeVehicleMenu>> LARGE_VEHICLE_MENU = MENU_TYPES.register("large_vehicle_menu", () -> RegistryHelpers.createMenuType(LargeVehicleMenu::new));
    public static final Supplier<MenuType<LanderMenu>> LANDER_MENU = MENU_TYPES.register("lander_menu", () -> RegistryHelpers.createMenuType(LanderMenu::new));
}