package com.github.alexnijjar.beyond_earth.items.armour;

import java.util.List;

import com.github.alexnijjar.beyond_earth.registry.ModArmour;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class SpaceSuit extends ArmorItem {

    // 1 days of oxygen.
    public static final int MAX_OXYGEN = 24000;
    // 2 days oxygen.
    public static final int MAX_OXYGEN_NETHERITE = 48000;

    public SpaceSuit(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.isOf(ModArmour.SPACE_SUIT) || stack.isOf(ModArmour.NETHERITE_SPACE_SUIT)) {
            boolean isNetherite = stack.isOf(ModArmour.NETHERITE_SPACE_SUIT);
            NbtCompound nbt = stack.getNbt();
            if (nbt.contains("Oxygen")) {
                int oxygen = nbt.getInt("Oxygen");
                tooltip.add(new TranslatableText("tooltip.beyond_earth.space_suit", oxygen, getMaxOxygen(isNetherite)).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
            } else {
                tooltip.add(new TranslatableText("tooltip.beyond_earth.space_suit", 0, getMaxOxygen(isNetherite)).setStyle(Style.EMPTY.withColor(Formatting.RED)));
            }
        }
    }

    public static int getMaxOxygen(boolean isNetherite) {
        return isNetherite ? MAX_OXYGEN_NETHERITE : MAX_OXYGEN;
    }
}