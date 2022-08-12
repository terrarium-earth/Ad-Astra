package com.github.alexnijjar.ad_astra.compat.emi;

import com.github.alexnijjar.ad_astra.util.ModIdentifier;
import dev.emi.emi.EmiPort;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.widget.WidgetHolder;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.stream.Collectors;

public class EmiTextures {
	public static final Identifier ENERGY_EMPTY = new ModIdentifier("textures/gui/energy_bar_empty.png");
	public static final Identifier ENERGY_FULL = new ModIdentifier("textures/gui/energy_full.png");
	public static final Identifier FLUID_TANK_BACK = new ModIdentifier("textures/gui/fluid_tank_back.png");
	public static final Identifier FLUID_TANK_OVERLAY = new ModIdentifier("textures/gui/fluid_tank.png");

	public static final EmiTexture EMPTY_ENERGY_TEXTURE = new EmiTexture(ENERGY_EMPTY, 0, 0, 19, 64, 19, 64, 19, 64);
	public static final EmiTexture FULL_ENERGY_TEXTURE = new EmiTexture(ENERGY_FULL, 0, 0, 13, 46, 13, 46, 13, 46);
	public static final EmiTexture FLUID_TANK_EMPTY_TEXTURE = new EmiTexture(FLUID_TANK_BACK, 0, 0, 24, 59, 24, 59, 24, 59);
	public static final EmiTexture FLUID_TANK_OVERLAY_TEXTURE = new EmiTexture(FLUID_TANK_OVERLAY, 0, 0, 12, 46, 12, 46, 12, 46);

	public static void createEnergyWidget(WidgetHolder widgets, int x, int y, int energyPerTick, boolean increase) {
		widgets.addTexture(EmiTextures.EMPTY_ENERGY_TEXTURE, x, y).tooltip((mx, my) -> {
			return List.of(TooltipComponent.of(EmiPort.ordered(EmiPort.translatable((increase ? "rei.tooltip.ad_astra.energy_generating" : "rei.tooltip.ad_astra.energy_using"), energyPerTick))));
		});
		widgets.addAnimatedTexture(EmiTextures.FULL_ENERGY_TEXTURE, x + 3, y + 8, 50 * 5000 / energyPerTick, false, true, !increase);
	}

	public static void createFluidWidget(WidgetHolder widgets, int x, int y, FluidVariant fluid, int speed, boolean increase) {
		widgets.addTexture(EmiTextures.FLUID_TANK_EMPTY_TEXTURE, x, y).tooltip((mx, my) -> {
			return FluidVariantRendering.getTooltip(fluid).stream().map(t -> TooltipComponent.of(t.asOrderedText())).collect(Collectors.toList());
		});

		widgets.add(new EmiFluidBarWidget(x, y, fluid, speed, increase));
	}
}
