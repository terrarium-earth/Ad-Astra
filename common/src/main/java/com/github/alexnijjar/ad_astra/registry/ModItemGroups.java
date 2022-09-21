package com.github.alexnijjar.ad_astra.registry;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroups {
	public static final ItemGroup ITEM_GROUP_NORMAL = CreativeTabRegistry.create(new ModIdentifier("tab_normal"), () -> new ItemStack(ModItems.TIER_1_ROCKET.get()));
	public static final ItemGroup ITEM_GROUP_MACHINES = CreativeTabRegistry.create(new ModIdentifier("tab_machines"), () -> new ItemStack(ModItems.NASA_WORKBENCH.get()));
	public static final ItemGroup ITEM_GROUP_BASICS = CreativeTabRegistry.create(new ModIdentifier("tab_basics"), () -> new ItemStack(ModItems.DESH_ENGINE.get()));
	public static final ItemGroup ITEM_GROUP_MATERIALS = CreativeTabRegistry.create(new ModIdentifier("tab_materials"), () -> new ItemStack(ModItems.IRON_PLATE.get()));
	public static final ItemGroup ITEM_GROUP_FLAGS = CreativeTabRegistry.create(new ModIdentifier("tab_flags"), () -> new ItemStack(ModBlocks.PURPLE_FLAG.get()));
	public static final ItemGroup ITEM_GROUP_GLOBES = CreativeTabRegistry.create(new ModIdentifier("tab_globes"), () -> new ItemStack(ModItems.GLACIO_GLOBE.get()));
	public static final ItemGroup ITEM_GROUP_BLOCKS = CreativeTabRegistry.create(new ModIdentifier("tab_blocks"), () -> new ItemStack(ModBlocks.MOON_IRON_ORE.get()));
	public static final ItemGroup ITEM_GROUP_SPAWN_EGGS = CreativeTabRegistry.create(new ModIdentifier("tab_spawn_eggs"), () -> new ItemStack(ModItems.LUNARIAN_SPAWN_EGG.get()));
}