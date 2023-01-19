package earth.terrarium.ad_astra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.ad_astra.AdAstra;
import earth.terrarium.ad_astra.common.screen.machine.*;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    public static final ResourcefulRegistry<MenuType<?>> MENUS = ResourcefulRegistries.create(BuiltInRegistries.MENU, AdAstra.MOD_ID);

    public static final RegistryEntry<MenuType<EtrionicGeneratorMenu>> ETRIONIC_GENERATOR = MENUS.register("etrionic_generator", () -> RegistryHelpers.createMenuType(EtrionicGeneratorMenu::new));
    public static final RegistryEntry<MenuType<CombustionGeneratorMenu>> COMBUSTION_GENERATOR = MENUS.register("combustion_generator", () -> RegistryHelpers.createMenuType(CombustionGeneratorMenu::new));
    public static final RegistryEntry<MenuType<SolarPanelMenu>> SOLAR_PANEL = MENUS.register("solar_panel", () -> RegistryHelpers.createMenuType(SolarPanelMenu::new));
    public static final RegistryEntry<MenuType<EtrionicBlastFurnaceMenu>> ETRIONIC_BLAST_FURNACE = MENUS.register("etrionic_blast_furnace", () -> RegistryHelpers.createMenuType(EtrionicBlastFurnaceMenu::new));
    public static final RegistryEntry<MenuType<HydraulicPressMenu>> HYDRAULIC_PRESS = MENUS.register("hydraulic_press", () -> RegistryHelpers.createMenuType(HydraulicPressMenu::new));
    public static final RegistryEntry<MenuType<ElecrolyzerMenu>> ELECTROLYZER = MENUS.register("electrolyzer", () -> RegistryHelpers.createMenuType(ElecrolyzerMenu::new));
    public static final RegistryEntry<MenuType<GeothermalGeneratorMenu>> GEOTHERMAL_GENERATING = MENUS.register("geothermal_generating", () -> RegistryHelpers.createMenuType(GeothermalGeneratorMenu::new));
    public static final RegistryEntry<MenuType<OilRefineryMenu>> OIL_REFINERY = MENUS.register("oil_refinery", () -> RegistryHelpers.createMenuType(OilRefineryMenu::new));
    public static final RegistryEntry<MenuType<CryogenicFreezerMenu>> CRYOGENIC_FREEZER = MENUS.register("cryogenic_freezer", () -> RegistryHelpers.createMenuType(CryogenicFreezerMenu::new));
    public static final RegistryEntry<MenuType<RecyclerMenu>> RECYCLER = MENUS.register("recycler", () -> RegistryHelpers.createMenuType(RecyclerMenu::new));
    public static final RegistryEntry<MenuType<OxygenDistributorMenu>> OXYGEN_DISTRIBUTOR = MENUS.register("oxygen_distributor", () -> RegistryHelpers.createMenuType(OxygenDistributorMenu::new));
    public static final RegistryEntry<MenuType<TemperatureRegulatorMenu>> TEMPERATURE_REGULATOR = MENUS.register("temperature_regulator", () -> RegistryHelpers.createMenuType(TemperatureRegulatorMenu::new));
    public static final RegistryEntry<MenuType<GravityNormalizerMenu>> GRAVITY_NORMALIZER = MENUS.register("gravity_normalizer", () -> RegistryHelpers.createMenuType(GravityNormalizerMenu::new));
    public static final RegistryEntry<MenuType<WaterPumpMenu>> WATER_PUMP = MENUS.register("water_pump", () -> RegistryHelpers.createMenuType(WaterPumpMenu::new));
}
