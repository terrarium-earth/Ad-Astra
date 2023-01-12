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

    public static final RegistryEntry<MenuType<EtrionicGeneratorMenu>> ETRIONIC_GENERATOR_MENU = MENUS.register("etrionic_generator", () -> RegistryHelpers.createMenuType(EtrionicGeneratorMenu::new));
    public static final RegistryEntry<MenuType<CombustionGeneratorMenu>> COMBUSTION_GENERATOR_MENU = MENUS.register("combustion_generator", () -> RegistryHelpers.createMenuType(CombustionGeneratorMenu::new));
    public static final RegistryEntry<MenuType<SolarPanelMenu>> SOLAR_PANEL_MENU = MENUS.register("solar_panel", () -> RegistryHelpers.createMenuType(SolarPanelMenu::new));
    public static final RegistryEntry<MenuType<EtrionicBlastFurnaceMenu>> ETRIONIC_BLAST_FURNACE_MENU = MENUS.register("etrionic_blast_furnace", () -> RegistryHelpers.createMenuType(EtrionicBlastFurnaceMenu::new));
    public static final RegistryEntry<MenuType<HydraulicPressMenu>> HYDRAULIC_PRESS_MENU = MENUS.register("hydraulic_press", () -> RegistryHelpers.createMenuType(HydraulicPressMenu::new));
}