package earth.terrarium.ad_astra.common.registry;

import earth.terrarium.ad_astra.common.screen.menu.*;
import earth.terrarium.botarium.api.registry.RegistryHelpers;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.function.Supplier;

public class ModMenuTypes {
    public static final Supplier<MenuType<PlanetSelectionMenu>> PLANET_SELECTION_SCREEN_HANDLER = register("planet_selection_menu", () -> RegistryHelpers.createMenuType((syncId, playerInventory, buf) -> new PlanetSelectionMenu(syncId, playerInventory.player, buf)));
    public static final Supplier<MenuType<SolarPanelMenu>> SOLAR_PANEL_SCREEN_HANDLER = register("solar_panel_menu", () -> RegistryHelpers.createMenuType(SolarPanelMenu::new));
    public static final Supplier<MenuType<CoalGeneratorMenu>> COAL_GENERATOR_MENU = register("coal_generator_menu", () -> RegistryHelpers.createMenuType(CoalGeneratorMenu::new));
    public static final Supplier<MenuType<CompressorMenu>> COMPRESSOR_MENU = register("compressor_menu", () -> RegistryHelpers.createMenuType(CompressorMenu::new));
    public static final Supplier<MenuType<NasaWorkbenchMenu>> NASA_WORKBENCH_MENU = register("nasa_workbench_menu", () -> RegistryHelpers.createMenuType(NasaWorkbenchMenu::new));
    public static final Supplier<MenuType<ConversionMenu>> CONVERSION_MENU = register("conversion_menu", () -> RegistryHelpers.createMenuType(ConversionMenu::new));
    public static final Supplier<MenuType<WaterPumpMenu>> WATER_PUMP_MENU = register("water_pump_menu", () -> RegistryHelpers.createMenuType(WaterPumpMenu::new));
    public static final Supplier<MenuType<OxygenDistributorMenu>> OXYGEN_DISTRIBUTOR_MENU = register("oxygen_distributor_menu", () -> RegistryHelpers.createMenuType(OxygenDistributorMenu::new));
    public static final Supplier<MenuType<CryoFreezerMenu>> CRYO_FREEZER_MENU = register("cryo_freezer_menu", () -> RegistryHelpers.createMenuType(CryoFreezerMenu::new));
    public static final Supplier<MenuType<VehicleMenu>> VEHICLE_MENU = register("vehicle_menu", () -> RegistryHelpers.createMenuType(VehicleMenu::new));
    public static final Supplier<MenuType<LargeVehicleMenu>> LARGE_VEHICLE_MENU = register("large_vehicle_menu", () -> RegistryHelpers.createMenuType(LargeVehicleMenu::new));
    public static final Supplier<MenuType<LanderMenu>> LANDER_MENU = register("lander_menu", () -> RegistryHelpers.createMenuType(LanderMenu::new));

    private static <T extends MenuType<E>, E extends AbstractContainerMenu> Supplier<T> register(String id, Supplier<T> object) {
        return ModRegistryHelpers.register(Registry.MENU, id, object);
    }

    public static void init() {
    }
}