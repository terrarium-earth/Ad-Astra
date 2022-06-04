package com.github.alexnijjar.beyond_earth.registry;

import com.github.alexnijjar.beyond_earth.items.armour.JetSuit;
import com.github.alexnijjar.beyond_earth.items.armour.JetSuitMaterial;
import com.github.alexnijjar.beyond_earth.items.armour.NetheriteSpaceSuit;
import com.github.alexnijjar.beyond_earth.items.armour.NetheriteSpaceSuitMaterial;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuit;
import com.github.alexnijjar.beyond_earth.items.armour.SpaceSuitMaterial;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;

public class ModArmour {

        public static final ArmorMaterial SPACE_SUIT_ARMOUR_MATERIAL = new SpaceSuitMaterial();
        public static final ArmorMaterial NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL = new NetheriteSpaceSuitMaterial();
        public static final ArmorMaterial JET_SUIT_ARMOUR_MATERIAL = new JetSuitMaterial();

        // Spacesuit.
        public static final Item OXYGEN_MASK = new SpaceSuit(SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings());
        public static final Item SPACE_SUIT = new SpaceSuit(SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings());
        public static final Item SPACE_PANTS = new SpaceSuit(SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings());
        public static final Item SPACE_BOOTS = new SpaceSuit(SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings());

        // Netherite Spacesuit.
        public static final Item NETHERITE_OXYGEN_MASK = new NetheriteSpaceSuit(NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().fireproof());
        public static final Item NETHERITE_SPACE_SUIT = new NetheriteSpaceSuit(NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().fireproof());
        public static final Item NETHERITE_SPACE_PANTS = new NetheriteSpaceSuit(NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().fireproof());
        public static final Item NETHERITE_SPACE_BOOTS = new NetheriteSpaceSuit(NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().fireproof());

        public static final Item JET_SUIT_OXYGEN_MASK = new JetSuit(JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().fireproof());
        public static final Item JET_SUIT = new JetSuit(JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().fireproof());
        public static final Item JET_SUIT_PANTS = new JetSuit(JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().fireproof());
        public static final Item JET_SUIT_BOOTS = new JetSuit(JET_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().fireproof());

        public static final Item[] SPACE_SUIT_SET = { OXYGEN_MASK, SPACE_SUIT, SPACE_PANTS, SPACE_BOOTS };
        public static final Item[] NETHERITE_SPACE_SUIT_SET = { NETHERITE_OXYGEN_MASK, NETHERITE_SPACE_SUIT, NETHERITE_SPACE_PANTS, NETHERITE_SPACE_BOOTS };
        public static final Item[] JET_SUIT_SET = { JET_SUIT_OXYGEN_MASK, JET_SUIT, JET_SUIT_PANTS, JET_SUIT_BOOTS };

        public static void register() {

                // Spacesuit.
                ModItems.register("oxygen_mask", OXYGEN_MASK);
                ModItems.register("space_suit", SPACE_SUIT);
                ModItems.register("space_pants", SPACE_PANTS);
                ModItems.register("space_boots", SPACE_BOOTS);

                // Netherite Spacesuit.
                ModItems.register("netherite_oxygen_mask", NETHERITE_OXYGEN_MASK);
                ModItems.register("netherite_space_suit", NETHERITE_SPACE_SUIT);
                ModItems.register("netherite_space_pants", NETHERITE_SPACE_PANTS);
                ModItems.register("netherite_space_boots", NETHERITE_SPACE_BOOTS);

                // Jet Suit.
                ModItems.register("jet_suit_oxygen_mask", JET_SUIT_OXYGEN_MASK);
                ModItems.register("jet_suit", JET_SUIT);
                ModItems.register("jet_suit_pants", JET_SUIT_PANTS);
                ModItems.register("jet_suit_boots", JET_SUIT_BOOTS);
        }
}