package com.github.alexnijjar.ad_astra.items.armour;

import java.util.stream.StreamSupport;

import org.apache.commons.lang3.Range;
import org.jetbrains.annotations.Nullable;

import com.github.alexnijjar.ad_astra.AdAstra;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;

public class NetheriteSpaceSuit extends SpaceSuit {

	public NetheriteSpaceSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
		super(material, slot, settings);
	}

	@Override
	public long getTankSize() {
		return AdAstra.CONFIG.spaceSuit.netheriteSpaceSuitTankSize;
	}

	@Override
	public Range<Integer> getTemperatureThreshold() {
		return Range.between(-300, 500);
	}

	public static boolean hasFullSet(LivingEntity entity) {
		return StreamSupport.stream(entity.getArmorItems().spliterator(), false).allMatch(s -> s.getItem() instanceof NetheriteSpaceSuit);
	}

	@Override
	public @Nullable String getArmorTexture(ItemStack stack, LivingEntity entity, EquipmentSlot slot, String type) {
		return AdAstra.MOD_ID + ":textures/entity/armour/netherite_space_suit.png";
	}
}