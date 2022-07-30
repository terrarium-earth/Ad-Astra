package com.github.alexnijjar.beyond_earth.items;

import java.util.List;

import com.github.alexnijjar.beyond_earth.BeyondEarth;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class EnergizerBlockItem extends MachineBlockItem {
    public EnergizerBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        long energy = 0;
        if (stack.hasNbt() && stack.getNbt().contains("energy")) {
            energy = stack.getNbt().getLong("energy");
        }
        tooltip.add(new TranslatableText("gauge_text.beyond_earth.storage", energy, BeyondEarth.CONFIG.energizer.maxEnergy).setStyle(Style.EMPTY.withColor(energy > 0 ? Formatting.GREEN : Formatting.RED)));
        super.appendTooltip(stack, world, tooltip, context);
    }
}
