package earth.terrarium.ad_astra.registry;

import dev.architectury.registry.CreativeTabRegistry;
import earth.terrarium.ad_astra.util.ModResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModItemGroups {
    public static final CreativeModeTab ITEM_GROUP_NORMAL = CreativeTabRegistry.create(new ModResourceLocation("tab_normal"), () -> new ItemStack(ModItems.TIER_1_ROCKET.get()));
    public static final CreativeModeTab ITEM_GROUP_MACHINES = CreativeTabRegistry.create(new ModResourceLocation("tab_machines"), () -> new ItemStack(ModItems.NASA_WORKBENCH.get()));
    public static final CreativeModeTab ITEM_GROUP_BASICS = CreativeTabRegistry.create(new ModResourceLocation("tab_basics"), () -> new ItemStack(ModItems.DESH_ENGINE.get()));
    public static final CreativeModeTab ITEM_GROUP_MATERIALS = CreativeTabRegistry.create(new ModResourceLocation("tab_materials"), () -> new ItemStack(ModItems.IRON_PLATE.get()));
    public static final CreativeModeTab ITEM_GROUP_FLAGS = CreativeTabRegistry.create(new ModResourceLocation("tab_flags"), () -> new ItemStack(ModBlocks.PURPLE_FLAG.get()));
    public static final CreativeModeTab ITEM_GROUP_GLOBES = CreativeTabRegistry.create(new ModResourceLocation("tab_globes"), () -> new ItemStack(ModItems.GLACIO_GLOBE.get()));
    public static final CreativeModeTab ITEM_GROUP_BLOCKS = CreativeTabRegistry.create(new ModResourceLocation("tab_blocks"), () -> new ItemStack(ModBlocks.MOON_IRON_ORE.get()));
    public static final CreativeModeTab ITEM_GROUP_SPAWN_EGGS = CreativeTabRegistry.create(new ModResourceLocation("tab_spawn_eggs"), () -> new ItemStack(ModItems.LUNARIAN_SPAWN_EGG.get()));
}