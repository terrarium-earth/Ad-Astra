package net.mrscauthd.boss_tools.itemtiers;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.mrscauthd.boss_tools.ModInnet;

public class SteelItemTier {
    public static IItemTier ITEM_TIER = new IItemTier() {
        public int getMaxUses() {
            return 1661;
        }

        public float getEfficiency() {
            return 7f;
        }

        public float getAttackDamage() {
            return 2f;
        }

        public int getHarvestLevel() {
            return 3;
        }

        public int getEnchantability() {
            return 14;
        }

        public Ingredient getRepairMaterial() {
            return Ingredient.fromStacks(new ItemStack(ModInnet.STEEL_INGOT.get(),1));
        }
    };
}
