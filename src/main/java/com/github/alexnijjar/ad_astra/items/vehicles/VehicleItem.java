package com.github.alexnijjar.ad_astra.items.vehicles;

import java.util.List;

import com.github.alexnijjar.ad_astra.items.FluidContainingItem;
import com.github.alexnijjar.ad_astra.items.HoldableOverHead;
import com.github.alexnijjar.ad_astra.util.FluidUtils;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public abstract class VehicleItem extends Item implements FluidContainingItem, HoldableOverHead {

	public VehicleItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		long fuel = FluidUtils.dropletsToMillibuckets(this.getAmount(stack));
		tooltip.add(new TranslatableText("tooltip.ad_astra.vehicle_fuel", fuel, FluidUtils.dropletsToMillibuckets(this.getTankSize())).setStyle(Style.EMPTY.withColor(fuel > 0 ? Formatting.GREEN : Formatting.RED)));
	}

	@Override
	public boolean allowNbtUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
		return true;
	}
}
