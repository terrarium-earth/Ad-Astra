package com.github.alexnijjar.beyond_earth.items.vehicles;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public abstract class VehicleItem extends Item {

	public VehicleItem(Settings settings) {
		super(settings);
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		if (stack.hasNbt()) {
			NbtCompound nbt = stack.getNbt();
			if (nbt.contains("Fuel")) {
				int fuel = nbt.getInt("Fuel");
				tooltip.add(new TranslatableText("tooltip.beyond_earth.vehicle_fuel", fuel, getMaxFuel()).setStyle(Style.EMPTY.withColor(fuel > 0 ? Formatting.GREEN : Formatting.RED)));
			}
		} else {
			tooltip.add(new TranslatableText("tooltip.beyond_earth.vehicle_fuel", 0, getMaxFuel()).setStyle(Style.EMPTY.withColor(Formatting.RED)));
		}

	}

	public abstract int getMaxFuel();
}
