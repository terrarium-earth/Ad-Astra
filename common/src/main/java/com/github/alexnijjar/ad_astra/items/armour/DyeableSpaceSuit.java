package com.github.alexnijjar.ad_astra.items.armour;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeableItem;

public class DyeableSpaceSuit extends SpaceSuit implements DyeableItem {
    public DyeableSpaceSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }
}
