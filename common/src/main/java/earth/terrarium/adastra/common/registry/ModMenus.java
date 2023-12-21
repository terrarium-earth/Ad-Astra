package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.menus.machines.*;
import earth.terrarium.adastra.common.menus.vehicles.LanderMenu;
import earth.terrarium.adastra.common.menus.vehicles.RocketMenu;
import earth.terrarium.adastra.common.menus.vehicles.RoverMenu;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    public static final ResourcefulRegistry<MenuType<?>> MENUS = ResourcefulRegistries.create(BuiltInRegistries.MENU, AdAstra.MOD_ID);

    public static final RegistryEntry<MenuType<CompressorMenu>> COMPRESSOR = MENUS.register("compressor_menu", () -> RegistryHelpers.createMenuType(CompressorMenu::new));
    public static final RegistryEntry<MenuType<CoalGeneratorMenu>> COAL_GENERATOR = MENUS.register("coal_generator_menu", () -> RegistryHelpers.createMenuType(CoalGeneratorMenu::new));
    public static final RegistryEntry<MenuType<OxygenLoaderMenu>> OXYGEN_LOADER = MENUS.register("oxygen_loader_menu", () -> RegistryHelpers.createMenuType(OxygenLoaderMenu::new));
    public static final RegistryEntry<MenuType<FuelRefineryMenu>> FUEL_REFINERY = MENUS.register("fuel_refinery_menu", () -> RegistryHelpers.createMenuType(FuelRefineryMenu::new));
    public static final RegistryEntry<MenuType<WaterPumpMenu>> WATER_PUMP = MENUS.register("water_pump_menu", () -> RegistryHelpers.createMenuType(WaterPumpMenu::new));
    public static final RegistryEntry<MenuType<SolarPanelMenu>> SOLAR_PANEL = MENUS.register("solar_panel_menu", () -> RegistryHelpers.createMenuType(SolarPanelMenu::new));
    public static final RegistryEntry<MenuType<OxygenDistributorMenu>> OXYGEN_DISTRIBUTOR = MENUS.register("oxygen_distributor_menu", () -> RegistryHelpers.createMenuType(OxygenDistributorMenu::new));
    public static final RegistryEntry<MenuType<CryoFreezerMenu>> CRYO_FREEZER = MENUS.register("cryo_freezer_menu", () -> RegistryHelpers.createMenuType(CryoFreezerMenu::new));
    public static final RegistryEntry<MenuType<NasaWorkbenchMenu>> NASA_WORKBENCH = MENUS.register("nasa_workbench_menu", () -> RegistryHelpers.createMenuType(NasaWorkbenchMenu::new));

    public static final RegistryEntry<MenuType<RoverMenu>> ROVER = MENUS.register("rover_menu", () -> RegistryHelpers.createMenuType(RoverMenu::new));
    public static final RegistryEntry<MenuType<RocketMenu>> ROCKET = MENUS.register("rocket_menu", () -> RegistryHelpers.createMenuType(RocketMenu::new));
    public static final RegistryEntry<MenuType<LanderMenu>> LANDER = MENUS.register("lander_menu", () -> RegistryHelpers.createMenuType(LanderMenu::new));

    public static final RegistryEntry<MenuType<PlanetsMenu>> PLANETS = MENUS.register("planets_menu", () -> new MenuType<>(PlanetsMenu::new, FeatureFlags.VANILLA_SET));
}
