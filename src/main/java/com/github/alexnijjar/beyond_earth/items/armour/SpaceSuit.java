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
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class SpaceSuit extends ArmorItem {

    // 1 days of oxygen.
    public static final int BASE_OXYGEN = 24000;
    public static final int MAX_OXYGEN = 24000;

    public SpaceSuit(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt()) {
            if (stack.isOf(ModArmour.SPACE_SUIT) || stack.isOf(ModArmour.NETHERITE_SPACE_SUIT) || stack.isOf(ModArmour.JET_SUIT)) {
                NbtCompound nbt = stack.getNbt();
                if (nbt.contains("Oxygen")) {
                    int oxygen = nbt.getInt("Oxygen");
                    tooltip.add(Text.translatable("tooltip.beyond_earth.space_suit", oxygen, getMaxOxygen()).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
                } else {
                    tooltip.add(Text.translatable("tooltip.beyond_earth.space_suit", 0, getMaxOxygen()).setStyle(Style.EMPTY.withColor(Formatting.RED)));
                }
            }
        }
    }

    public int getMaxOxygen() {
        return MAX_OXYGEN;
    }
}