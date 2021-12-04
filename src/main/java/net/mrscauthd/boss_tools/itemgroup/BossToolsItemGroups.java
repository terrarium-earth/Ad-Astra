package net.mrscauthd.boss_tools.itemgroup;

import net.mrscauthd.boss_tools.ModInnet;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

public class BossToolsItemGroups {
	public static ItemGroup tab_normal = new ItemGroup("tab_normal") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.TIER_1_ROCKET_ITEM.get(), 1);
		}
	};
	public static ItemGroup tab_machines = new ItemGroup("tab_machines") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.NASA_WORKBENCH_ITEM.get(), 1);
		}
	};
	public static ItemGroup tab_basics = new ItemGroup("tab_basics") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.GOLDEN_ENGINE.get(), 1);
		}
	};
	public static ItemGroup tab_materials = new ItemGroup("tab_materials") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.IRON_PLATE.get(), 1);
		}
	};
	public static ItemGroup tab_flags = new ItemGroup("tab_flags") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.FLAG_PURPLE_BLOCK.get(), 1);
		}
	};
	public static ItemGroup tab_blocks = new ItemGroup("tab_blocks") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.MOON_IRON_ORE.get(), 1);
		}
	};
	public static ItemGroup tab_spawn_eggs = new ItemGroup("tab_spawn_eggs") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack createIcon() {
			return new ItemStack(ModInnet.ALIEN_SPAWN_EGG.get(), 1);
		}
	};
}
