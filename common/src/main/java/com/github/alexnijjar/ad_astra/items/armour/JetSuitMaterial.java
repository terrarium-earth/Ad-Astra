package com.github.alexnijjar.ad_astra.items.armour;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.registry.ModItems;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class JetSuitMaterial implements ArmorMaterial {

	private static final int[] BASE_DURABILITY = new int[] { 15, 17, 18, 13 };
	private static final int[] PROTECTION_VALUES = new int[] { 4, 7, 9, 4 };

	@Override
	public int getDurability(EquipmentSlot slot) {
		return BASE_DURABILITY[slot.getEntitySlotId()] * 37;
	}

	@Override
	public int getProtectionAmount(EquipmentSlot slot) {
		return PROTECTION_VALUES[slot.getEntitySlotId()] * AdAstra.CONFIG.spaceSuit.jetSuitProtectionMultiplier;
	}

	@Override
	public int getEnchantability() {
		return 15;
	}

	@Override
	public SoundEvent getEquipSound() {
		return SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE;
	}

	@Override
	public Ingredient getRepairIngredient() {
		return Ingredient.ofItems(ModItems.CALORITE_INGOT.get());
	}

	@Override
	public String getName() {
		return "jet_suit";
	}

	@Override
	public float getToughness() {
		return AdAstra.CONFIG.spaceSuit.jetSuitArmorToughness;
	}

	@Override
	public float getKnockbackResistance() {
		return 0.125f;
	}
}