package com.github.alexnijjar.beyond_earth.items.armour;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;

public class NetheriteSpaceSuit extends SpaceSuit {
    public static final int MAX_OXYGEN = BASE_OXYGEN * 2;
    public NetheriteSpaceSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public int getMaxOxygen() {
        return MAX_OXYGEN;
    }
}