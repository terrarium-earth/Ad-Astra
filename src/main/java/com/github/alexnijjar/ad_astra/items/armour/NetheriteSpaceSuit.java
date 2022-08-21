package com.github.alexnijjar.ad_astra.items.armour;

import com.github.alexnijjar.ad_astra.AdAstra;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;

public class NetheriteSpaceSuit extends SpaceSuit {

    public static final long TANK_SIZE = AdAstra.CONFIG.spaceSuit.netheriteSpaceSuitTankSize;

    public NetheriteSpaceSuit(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public long getTankSize() {
        return TANK_SIZE;
    }
}