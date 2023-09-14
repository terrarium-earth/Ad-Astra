package earth.terrarium.adastra.common.registry;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import earth.terrarium.adastra.AdAstra;
import earth.terrarium.adastra.common.menus.machines.BatteryMenu;
import earth.terrarium.adastra.common.menus.machines.HydraulicPressMenu;
import earth.terrarium.adastra.common.menus.machines.SeparatorMenu;
import earth.terrarium.botarium.common.registry.RegistryHelpers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    public static final ResourcefulRegistry<MenuType<?>> MENUS = ResourcefulRegistries.create(BuiltInRegistries.MENU, AdAstra.MOD_ID);

    public static final RegistryEntry<MenuType<BatteryMenu>> BATTERY = MENUS.register("battery", () -> RegistryHelpers.createMenuType(BatteryMenu::new));
    public static final RegistryEntry<MenuType<SeparatorMenu>> SEPARATOR = MENUS.register("separator", () -> RegistryHelpers.createMenuType(SeparatorMenu::new));
    public static final RegistryEntry<MenuType<HydraulicPressMenu>> HYDRAULIC_PRESS = MENUS.register("hydraulic_press", () -> RegistryHelpers.createMenuType(HydraulicPressMenu::new));
}
