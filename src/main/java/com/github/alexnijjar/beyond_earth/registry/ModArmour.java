package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.items.armour.JetSuitMaterial;
import com.github.alexnijjar.beyond_earth.items.armour.NetheriteSpaceSuitMaterial;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuitMaterial;

import net.minecraft.item.ArmorMaterial;

public class ModArmour {

        public static final ArmorMaterial SPACE_SUIT_ARMOUR_MATERIAL = new SpaceSuitMaterial();
        public static final ArmorMaterial NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL = new NetheriteSpaceSuitMaterial();
        public static final ArmorMaterial JET_SUIT_ARMOUR_MATERIAL = new JetSuitMaterial();

        public static void register() {
                // Space suit oxygen tank.
                ModItems.registerTank(ModItems.SPACE_SUIT);
                ModItems.registerTank(ModItems.NETHERITE_SPACE_SUIT);
                ModItems.registerTank(ModItems.JET_SUIT);
        }
}