package net.mrscauthd.beyond_earth.itemgroup;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.ModInit;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.mrscauthd.beyond_earth.item.FilledAltArmorItem;
import net.mrscauthd.beyond_earth.item.FilledAltVehicleItem;

import java.util.ArrayList;
import java.util.List;

public class ItemGroups {
	public static CreativeModeTab tab_normal = new CreativeModeTab("tab_normal") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModInit.TIER_1_ROCKET_ITEM.get(), 1);
		}

		@OnlyIn(Dist.CLIENT)
		@Override
		public void fillItemList(NonNullList<ItemStack> list) {
			/**Full Vehicle Items*/
			List<FilledAltVehicleItem> altItems = new ArrayList<>();
			altItems.add(ModInit.TIER_1_ROCKET_ITEM.get());
			altItems.add(ModInit.TIER_2_ROCKET_ITEM.get());
			altItems.add(ModInit.TIER_3_ROCKET_ITEM.get());
			altItems.add(ModInit.TIER_4_ROCKET_ITEM.get());
			altItems.add(ModInit.ROVER_ITEM.get());

			for (FilledAltVehicleItem item : altItems) {
				item.fillItemCategoryAlt(this, list);
			}

			int columns = 9;
			int emptyCount = columns - (list.size() % columns);

			for (int i = 0; i < emptyCount; i++) {
				list.add(ItemStack.EMPTY);
			}

			/**Not Full Vehicle Item*/
			for (FilledAltVehicleItem item : altItems) {
				item.itemCategoryAlt(this, list);
			}

			int columns2 = 9;
			int emptyCount2 = columns2 - (list.size() % columns2);

			for (int i = 0; i < emptyCount2; i++) {
				list.add(ItemStack.EMPTY);
			}



			/**SPACE SUIT Armor Items*/
			List<FilledAltArmorItem> spaceSuitList = new ArrayList<>();
			spaceSuitList.add((FilledAltArmorItem) ModInit.OXYGEN_MASK.get());
			spaceSuitList.add((FilledAltArmorItem) ModInit.SPACE_SUIT.get());
			spaceSuitList.add((FilledAltArmorItem) ModInit.SPACE_PANTS.get());
			spaceSuitList.add((FilledAltArmorItem) ModInit.SPACE_BOOTS.get());

			FilledAltArmorItem spaceSuit = (FilledAltArmorItem) ModInit.SPACE_SUIT.get();

			for (FilledAltArmorItem item : spaceSuitList) {
				if (!item.isMainItem()) {
					item.itemCategoryAlt(this, list);
				} else {
					spaceSuit.itemCategoryAlt(this, list);
				}
			}
			spaceSuit.fillItemCategoryAlt(this, list);

			int spaceSuitColums = 9;
			int spaceSuitEmptyCount = spaceSuitColums - (list.size() % spaceSuitColums);

			for (int i = 0; i < spaceSuitEmptyCount; i++) {
				list.add(ItemStack.EMPTY);
			}



			/**NETHERITE SPACE SUIT Armor Items*/
			List<FilledAltArmorItem> netheriteSpaceSuitList = new ArrayList<>();
			netheriteSpaceSuitList.add((FilledAltArmorItem) ModInit.NETHERITE_OXYGEN_MASK.get());
			netheriteSpaceSuitList.add((FilledAltArmorItem) ModInit.NETHERITE_SPACE_SUIT.get());
			netheriteSpaceSuitList.add((FilledAltArmorItem) ModInit.NETHERITE_SPACE_PANTS.get());
			netheriteSpaceSuitList.add((FilledAltArmorItem) ModInit.NETHERITE_SPACE_BOOTS.get());

			FilledAltArmorItem netheriteSpaceSuit = (FilledAltArmorItem) ModInit.NETHERITE_SPACE_SUIT.get();

			for (FilledAltArmorItem item : netheriteSpaceSuitList) {
				if (!item.isMainItem()) {
					item.itemCategoryAlt(this, list);
				} else {
					netheriteSpaceSuit.itemCategoryAlt(this, list);
				}
			}
			netheriteSpaceSuit.fillItemCategoryAlt(this, list);

			int netheriteSpaceSuitColums = 9;
			int netheriteSpaceSuitEmptyCount = netheriteSpaceSuitColums - (list.size() % netheriteSpaceSuitColums);

			for (int i = 0; i < netheriteSpaceSuitEmptyCount; i++) {
				list.add(ItemStack.EMPTY);
			}

			/**Default Items*/
			super.fillItemList(list);
		}
	};
	public static CreativeModeTab tab_machines = new CreativeModeTab("tab_machines") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModInit.NASA_WORKBENCH_ITEM.get(), 1);
		}
	};
	public static CreativeModeTab tab_basics = new CreativeModeTab("tab_basics") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModInit.DESH_ENGINE.get(), 1);
		}
	};
	public static CreativeModeTab tab_materials = new CreativeModeTab("tab_materials") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModInit.IRON_PLATE.get(), 1);
		}
	};
	public static CreativeModeTab tab_flags = new CreativeModeTab("tab_flags") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModInit.FLAG_PURPLE_BLOCK.get(), 1);
		}
	};
	public static CreativeModeTab tab_blocks = new CreativeModeTab("tab_blocks") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModInit.MOON_IRON_ORE.get(), 1);
		}
	};
	public static CreativeModeTab tab_spawn_eggs = new CreativeModeTab("tab_spawn_eggs") {
		@OnlyIn(Dist.CLIENT)
		@Override
		public ItemStack makeIcon() {
			return new ItemStack(ModInit.ALIEN_SPAWN_EGG.get(), 1);
		}
	};
}
