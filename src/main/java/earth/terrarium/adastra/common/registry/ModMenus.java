package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.menu.MenuContentHelper;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.menus.content.EntityContent;
import earth.terrarium.adastra.common.menus.content.PlanetsContent;
import earth.terrarium.adastra.common.menus.content.PositionContent;
import earth.terrarium.adastra.common.menus.machines.*;
import earth.terrarium.adastra.common.menus.vehicles.LanderMenu;
import earth.terrarium.adastra.common.menus.vehicles.RocketMenu;
import earth.terrarium.adastra.common.menus.vehicles.RoverMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {

    public static final ResourcefulRegistry<MenuType<?>> MENUS = ResourcefulRegistries.create(BuiltInRegistries.MENU, AdAstra.MOD_ID);

    public static final RegistryEntry<MenuType<CoalGeneratorMenu>> COAL_GENERATOR = MENUS.register("coal_generator_menu", () -> MenuContentHelper.create(CoalGeneratorMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<CompressorMenu>> COMPRESSOR = MENUS.register("compressor_menu", () -> MenuContentHelper.create(CompressorMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<EtrionicBlastFurnaceMenu>> ETRIONIC_BLAST_FURNACE = MENUS.register("etrionic_blast_furnace_menu", () -> MenuContentHelper.create(EtrionicBlastFurnaceMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<OxygenLoaderMenu>> OXYGEN_LOADER = MENUS.register("oxygen_loader_menu", () -> MenuContentHelper.create(OxygenLoaderMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<FuelRefineryMenu>> FUEL_REFINERY = MENUS.register("fuel_refinery_menu", () -> MenuContentHelper.create(FuelRefineryMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<WaterPumpMenu>> WATER_PUMP = MENUS.register("water_pump_menu", () -> MenuContentHelper.create(WaterPumpMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<SolarPanelMenu>> SOLAR_PANEL = MENUS.register("solar_panel_menu", () -> MenuContentHelper.create(SolarPanelMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<OxygenDistributorMenu>> OXYGEN_DISTRIBUTOR = MENUS.register("oxygen_distributor_menu", () -> MenuContentHelper.create(OxygenDistributorMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<GravityNormalizerMenu>> GRAVITY_NORMALIZER = MENUS.register("gravity_normalizer_menu", () -> MenuContentHelper.create(GravityNormalizerMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<CryoFreezerMenu>> CRYO_FREEZER = MENUS.register("cryo_freezer_menu", () -> MenuContentHelper.create(CryoFreezerMenu::new, PositionContent.SERIALIZER));
    public static final RegistryEntry<MenuType<NasaWorkbenchMenu>> NASA_WORKBENCH = MENUS.register("nasa_workbench_menu", () -> MenuContentHelper.create(NasaWorkbenchMenu::new, PositionContent.SERIALIZER));

    public static final RegistryEntry<MenuType<RoverMenu>> ROVER = MENUS.register("rover_menu", () -> MenuContentHelper.create(RoverMenu::new, EntityContent.SERIALIZER));
    public static final RegistryEntry<MenuType<RocketMenu>> ROCKET = MENUS.register("rocket_menu", () -> MenuContentHelper.create(RocketMenu::new, EntityContent.SERIALIZER));
    public static final RegistryEntry<MenuType<LanderMenu>> LANDER = MENUS.register("lander_menu", () -> MenuContentHelper.create(LanderMenu::new, EntityContent.SERIALIZER));

    public static final RegistryEntry<MenuType<PlanetsMenu>> PLANETS = MENUS.register("planets_menu", () -> MenuContentHelper.create(PlanetsMenu::new, PlanetsContent.SERIALIZER));

}
