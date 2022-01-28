package net.mrscauthd.beyond_earth.itemgroup;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.ModInit;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;
import net.mrscauthd.beyond_earth.item.FilledAltArmorItem;

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
			ModInit.TIER_1_ROCKET_ITEM.get().fillItemCategoryAlt(this, list);
			ModInit.TIER_2_ROCKET_ITEM.get().fillItemCategoryAlt(this, list);
			ModInit.TIER_3_ROCKET_ITEM.get().fillItemCategoryAlt(this, list);
			ModInit.TIER_4_ROCKET_ITEM.get().fillItemCategoryAlt(this, list);
			ModInit.ROVER_ITEM.get().fillItemCategoryAlt(this, list);

			int emptyCount = 9 - 5; //9 for max slots 5 how many that are used

			for (int i = 0; i < emptyCount; i++) {
				list.add(ItemStack.EMPTY);
			}

			/**Not Full Vehicle Item*/
			ModInit.TIER_1_ROCKET_ITEM.get().itemCategoryAlt(this, list);
			ModInit.TIER_2_ROCKET_ITEM.get().itemCategoryAlt(this, list);
			ModInit.TIER_3_ROCKET_ITEM.get().itemCategoryAlt(this, list);
			ModInit.TIER_4_ROCKET_ITEM.get().itemCategoryAlt(this, list);
			ModInit.ROVER_ITEM.get().itemCategoryAlt(this, list);

			emptyCount = 9 - 5;

			for (int i = 0; i < emptyCount; i++) {
				list.add(ItemStack.EMPTY);
			}

			/**SPACE SUIT*/
			((FilledAltArmorItem) ModInit.OXYGEN_MASK.get()).itemCategoryAlt(this, list);
			((FilledAltArmorItem) ModInit.SPACE_SUIT.get()).itemCategoryAlt(this, list);
			((FilledAltArmorItem) ModInit.SPACE_PANTS.get()).itemCategoryAlt(this, list);
			((FilledAltArmorItem) ModInit.SPACE_BOOTS.get()).itemCategoryAlt(this, list);
			//Full Space Suit
			((FilledAltArmorItem) ModInit.SPACE_SUIT.get()).fillItemCategoryAlt(this, list);

			emptyCount = 9 - 5;

			for (int i = 0; i < emptyCount; i++) {
				list.add(ItemStack.EMPTY);
			}

			/**NETHERITE SPACE SUIT*/
			((FilledAltArmorItem) ModInit.NETHERITE_OXYGEN_MASK.get()).itemCategoryAlt(this, list);
			((FilledAltArmorItem) ModInit.NETHERITE_SPACE_SUIT.get()).itemCategoryAlt(this, list);
			((FilledAltArmorItem) ModInit.NETHERITE_SPACE_PANTS.get()).itemCategoryAlt(this, list);
			((FilledAltArmorItem) ModInit.NETHERITE_SPACE_BOOTS.get()).itemCategoryAlt(this, list);
			//Full Space Suit
			((FilledAltArmorItem) ModInit.NETHERITE_SPACE_SUIT.get()).fillItemCategoryAlt(this, list);

			emptyCount = 9 - 5;

			for (int i = 0; i < emptyCount; i++) {
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
