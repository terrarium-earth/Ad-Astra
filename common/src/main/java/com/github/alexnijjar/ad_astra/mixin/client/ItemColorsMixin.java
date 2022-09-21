package com.github.alexnijjar.ad_astra.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import com.github.alexnijjar.ad_astra.registry.ModItems;

import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemConvertible;

@Mixin(ItemColors.class)
public class ItemColorsMixin {

    @ModifyVariable(method = "create", at = @At("STORE"))
    private static ItemColors adastra_create(ItemColors itemColors) {
        itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), new ItemConvertible[] { ModItems.SPACE_HELMET.get(), ModItems.SPACE_SUIT.get(), ModItems.SPACE_PANTS.get(), ModItems.SPACE_BOOTS.get() });
        itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), new ItemConvertible[] { ModItems.NETHERITE_SPACE_HELMET.get() });
        itemColors.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), new ItemConvertible[] { ModItems.JET_SUIT_HELMET.get() });
        return itemColors;
    }
}
