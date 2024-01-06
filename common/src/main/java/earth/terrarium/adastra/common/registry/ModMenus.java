package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.blockentities.machines.*;
import earth.terrarium.adastra.common.menus.PlanetsMenu;
import earth.terrarium.adastra.common.menus.base.BaseContainerMenu;
import earth.terrarium.adastra.common.menus.machines.*;
import earth.terrarium.adastra.common.menus.vehicles.LanderMenu;
import earth.terrarium.adastra.common.menus.vehicles.RocketMenu;
import earth.terrarium.adastra.common.menus.vehicles.RoverMenu;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;

public class ModMenus {
    public static final ResourcefulRegistry<MenuType<?>> MENUS = ResourcefulRegistries.create(BuiltInRegistries.MENU, AdAstra.MOD_ID);

    public static final RegistryEntry<MenuType<CoalGeneratorMenu>> COAL_GENERATOR = MENUS.register("coal_generator_menu", () -> createMenuType(CoalGeneratorMenu::new, CoalGeneratorBlockEntity.class));
    public static final RegistryEntry<MenuType<CompressorMenu>> COMPRESSOR = MENUS.register("compressor_menu", () -> createMenuType(CompressorMenu::new, CompressorBlockEntity.class));
    public static final RegistryEntry<MenuType<EtrionicBlastFurnaceMenu>> ETRIONIC_BLAST_FURNACE = MENUS.register("etrionic_blast_furnace_menu", () -> createMenuType(EtrionicBlastFurnaceMenu::new, EtrionicBlastFurnaceBlockEntity.class));
    public static final RegistryEntry<MenuType<OxygenLoaderMenu>> OXYGEN_LOADER = MENUS.register("oxygen_loader_menu", () -> createMenuType(OxygenLoaderMenu::new, OxygenLoaderBlockEntity.class));
    public static final RegistryEntry<MenuType<FuelRefineryMenu>> FUEL_REFINERY = MENUS.register("fuel_refinery_menu", () -> createMenuType(FuelRefineryMenu::new, FuelRefineryBlockEntity.class));
    public static final RegistryEntry<MenuType<WaterPumpMenu>> WATER_PUMP = MENUS.register("water_pump_menu", () -> createMenuType(WaterPumpMenu::new, WaterPumpBlockEntity.class));
    public static final RegistryEntry<MenuType<SolarPanelMenu>> SOLAR_PANEL = MENUS.register("solar_panel_menu", () -> createMenuType(SolarPanelMenu::new, SolarPanelBlockEntity.class));
    public static final RegistryEntry<MenuType<OxygenDistributorMenu>> OXYGEN_DISTRIBUTOR = MENUS.register("oxygen_distributor_menu", () -> createMenuType(OxygenDistributorMenu::new, OxygenDistributorBlockEntity.class));
    public static final RegistryEntry<MenuType<GravityNormalizerMenu>> GRAVITY_NORMALIZER = MENUS.register("gravity_normalizer_menu", () -> createMenuType(GravityNormalizerMenu::new, GravityNormalizerBlockEntity.class));
    public static final RegistryEntry<MenuType<CryoFreezerMenu>> CRYO_FREEZER = MENUS.register("cryo_freezer_menu", () -> createMenuType(CryoFreezerMenu::new, CryoFreezerBlockEntity.class));
    public static final RegistryEntry<MenuType<NasaWorkbenchMenu>> NASA_WORKBENCH = MENUS.register("nasa_workbench_menu", () -> createMenuType(NasaWorkbenchMenu::new, NasaWorkbenchBlockEntity.class));

    public static final RegistryEntry<MenuType<RoverMenu>> ROVER = MENUS.register("rover_menu", () -> RegistryHelpers.createMenuType(RoverMenu::new));
    public static final RegistryEntry<MenuType<RocketMenu>> ROCKET = MENUS.register("rocket_menu", () -> RegistryHelpers.createMenuType(RocketMenu::new));
    public static final RegistryEntry<MenuType<LanderMenu>> LANDER = MENUS.register("lander_menu", () -> RegistryHelpers.createMenuType(LanderMenu::new));

    public static final RegistryEntry<MenuType<PlanetsMenu>> PLANETS = MENUS.register("planets_menu", () -> RegistryHelpers.createMenuType(PlanetsMenu::new));

    private static <T extends BaseContainerMenu<E>, E extends BlockEntity> MenuType<T> createMenuType(Factory<T, E> factory, Class<E> clazz) {
        return RegistryHelpers.createMenuType((id, inventory, buf) -> factory.create(
            id,
            inventory,
            BaseContainerMenu.getBlockEntityFromBuf(inventory.player.level(), buf, clazz)
        ));
    }

    public interface Factory<T extends BaseContainerMenu<E>, E extends BlockEntity> {
        T create(int syncId, Inventory inventory, E blockEntity);
    }
}
