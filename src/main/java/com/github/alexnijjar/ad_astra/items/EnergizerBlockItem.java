package com.github.alexnijjar.ad_astra.items;

import java.util.List;

import com.github.alexnijjar.ad_astra.AdAstra;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
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
		tooltip.add(Text.translatable("gauge_text.ad_astra.storage", energy, AdAstra.CONFIG.energizer.maxEnergy).setStyle(Style.EMPTY.withColor(energy > 0 ? Formatting.GREEN : Formatting.RED)));
		super.appendTooltip(stack, world, tooltip, context);
	}
}
