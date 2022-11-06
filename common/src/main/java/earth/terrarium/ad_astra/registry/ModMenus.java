package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.registries.DeferredRegister;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.screen.menu.*;
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(AdAstra.MOD_ID, Registry.MENU_REGISTRY);
    public static final Supplier<MenuType<PlanetSelectionScreenHandler>> PLANET_SELECTION_SCREEN_HANDLER = MENUS.register("planet_selection_menu", () -> RegistryHelpers.createMenuType((syncId, playerInventory, buf) -> new PlanetSelectionScreenHandler(syncId, playerInventory.player, buf)));
    public static final Supplier<MenuType<SolarPanelMenu>> SOLAR_PANEL_SCREEN_HANDLER = MENUS.register("solar_panel_menu", () -> RegistryHelpers.createMenuType(SolarPanelMenu::new));

    public static void register() {
        MENUS.register();
    }

    public static final Supplier<MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU = MENUS.register("coal_generator_menu", () -> RegistryHelpers.createMenuType(CoalGeneratorMenu::new));
    public static final Supplier<MenuType<CompressorMenu>> COMPRESSOR_MENU = MENUS.register("compressor_menu", () -> RegistryHelpers.createMenuType(CompressorMenu::new));
    public static final Supplier<MenuType<NasaWorkbenchMenu>> NASA_WORKBENCH_MENU = MENUS.register("nasa_workbench_menu", () -> RegistryHelpers.createMenuType(NasaWorkbenchMenu::new));
    public static final Supplier<MenuType<ConversionMenu>> CONVERSION_MENU = MENUS.register("conversion_menu", () -> RegistryHelpers.createMenuType(ConversionMenu::new));
    public static final Supplier<MenuType<WaterPumpMenu>> WATER_PUMP_MENU = MENUS.register("water_pump_menu", () -> RegistryHelpers.createMenuType(WaterPumpMenu::new));
    public static final Supplier<MenuType<OxygenDistributorMenu>> OXYGEN_DISTRIBUTOR_MENU = MENUS.register("oxygen_distributor_menu", () -> RegistryHelpers.createMenuType(OxygenDistributorMenu::new));
    public static final Supplier<MenuType<CryoFreezerMenu>> CRYO_FREEZER_MENU = MENUS.register("cryo_freezer_menu", () -> RegistryHelpers.createMenuType(CryoFreezerMenu::new));

    public static final Supplier<MenuType<VehicleMenu>> VEHICLE_MENU = MENUS.register("vehicle_menu", () -> RegistryHelpers.createMenuType(VehicleMenu::new));
    public static final Supplier<MenuType<LargeVehicleMenu>> LARGE_VEHICLE_MENU = MENUS.register("large_vehicle_menu", () -> RegistryHelpers.createMenuType(LargeVehicleMenu::new));

    public static final Supplier<MenuType<LanderMenu>> LANDER_MENU = MENUS.register("lander_menu", () -> RegistryHelpers.createMenuType(LanderMenu::new));


}