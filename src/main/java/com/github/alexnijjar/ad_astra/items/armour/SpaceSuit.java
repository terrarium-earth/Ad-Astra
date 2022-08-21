package com.github.alexnijjar.ad_astra.items.armour;

import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;
import com.github.alexnijjar.ad_astra.items.FluidContainingItem;
import com.github.alexnijjar.ad_astra.registry.ModItems;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class SpaceSuit extends ArmorItem implements FluidContainingItem {

    public static final long TANK_SIZE = AdAstra.CONFIG.spaceSuit.spaceSuitTankSize;

    public SpaceSuit(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings) {
        super(material, slot, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.isOf(ModItems.SPACE_SUIT) || stack.isOf(ModItems.NETHERITE_SPACE_SUIT) || stack.isOf(ModItems.JET_SUIT)) {
            long oxygen = FluidUtils.dropletsToMillibuckets(this.getAmount(stack));
            tooltip.add(Text.translatable("tooltip.ad_astra.space_suit", oxygen, FluidUtils.dropletsToMillibuckets(getTankSize())).setStyle(Style.EMPTY.withColor(oxygen > 0 ? Formatting.GREEN : Formatting.RED)));
        }
    }

    @Override
    public long getTankSize() {
        return TANK_SIZE;
    }
}