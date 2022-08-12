package com.github.alexnijjar.ad_astra.items.armour;

import com.github.alexnijjar.ad_astra.AdAstra;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;

import java.util.stream.StreamSupport;

public class NetheriteSpaceSuit extends SpaceSuit {

	public NetheriteSpaceSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
		super(material, slot, settings);
	}

	@Override
	public long getTankSize() {
		return AdAstra.CONFIG.spaceSuit.netheriteSpaceSuitTankSize;
	}

	public static boolean hasFullSet(LivingEntity entity) {
		return StreamSupport.stream(entity.getArmorItems().spliterator(), false).allMatch(s -> s.getItem() instanceof NetheriteSpaceSuit);
	}
}