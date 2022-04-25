package net.mrscauthd.beyond_earth.registry;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.mrscauthd.beyond_earth.items.armour.NetheriteSpaceSuit;
import net.mrscauthd.beyond_earth.items.armour.NetheriteSpaceSuitMaterial;
import net.mrscauthd.beyond_earth.items.armour.SpaceSuit;
import net.mrscauthd.beyond_earth.items.armour.SpaceSuitMaterial;

public class ModArmour {

        public static final ArmorMaterial SPACE_SUIT_ARMOUR_MATERIAL = new SpaceSuitMaterial();
        public static final ArmorMaterial NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL = new NetheriteSpaceSuitMaterial();

        // Spacesuit.
        public static final Item OXYGEN_MASK = new SpaceSuit(SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD, new Item.Settings().group(ModItems.ITEM_GROUP_NORMAL));
        public static final Item SPACE_SUIT = new SpaceSuit(SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST, new Item.Settings().group(ModItems.ITEM_GROUP_NORMAL));
        public static final Item SPACE_PANTS = new SpaceSuit(SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS, new Item.Settings().group(ModItems.ITEM_GROUP_NORMAL));
        public static final Item SPACE_BOOTS = new SpaceSuit(SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET, new Item.Settings().group(ModItems.ITEM_GROUP_NORMAL));

        // Netherite Spacesuit.
        public static final Item NETHERITE_OXYGEN_MASK = new NetheriteSpaceSuit(NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.HEAD,
                        new Item.Settings().group(ModItems.ITEM_GROUP_NORMAL).fireproof());
        public static final Item NETHERITE_SPACE_SUIT = new NetheriteSpaceSuit(NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.CHEST,
                        new Item.Settings().group(ModItems.ITEM_GROUP_NORMAL).fireproof());
        public static final Item NETHERITE_SPACE_PANTS = new NetheriteSpaceSuit(NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.LEGS,
                        new Item.Settings().group(ModItems.ITEM_GROUP_NORMAL).fireproof());
        public static final Item NETHERITE_SPACE_BOOTS = new NetheriteSpaceSuit(NETHERITE_SPACE_SUIT_ARMOUR_MATERIAL, EquipmentSlot.FEET,
                        new Item.Settings().group(ModItems.ITEM_GROUP_NORMAL).fireproof());

        @Environment(EnvType.CLIENT)
        public static final Item[] SPACE_SUIT_SET = { OXYGEN_MASK, SPACE_SUIT, SPACE_PANTS, SPACE_BOOTS };
        @Environment(EnvType.CLIENT)
        public static final Item[] NETHERITE_SPACE_SUIT_SET = { NETHERITE_OXYGEN_MASK, NETHERITE_SPACE_SUIT, NETHERITE_SPACE_PANTS, NETHERITE_SPACE_BOOTS };

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
        }
}