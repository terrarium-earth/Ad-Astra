package com.github.alexnijjar.ad_astra.items.armour;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface ModArmourItem {
    @Nullable
    default String getArmorTexture(ItemStack stack, LivingEntity entity, EquipmentSlot slot, String type) {
        return null;
    }
}
