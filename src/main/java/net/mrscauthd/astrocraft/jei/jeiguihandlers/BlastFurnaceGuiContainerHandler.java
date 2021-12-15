package net.mrscauthd.astrocraft.jei.jeiguihandlers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.constants.VanillaRecipeCategoryUid;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.mrscauthd.astrocraft.gauge.GaugeTextHelper;
import net.mrscauthd.astrocraft.gui.helper.GuiHelper;
import net.mrscauthd.astrocraft.gui.screens.blastfurnace.BlastFurnaceGuiWindow;
import net.mrscauthd.astrocraft.jei.JeiPlugin.BlastingFurnaceJeiCategory;

public class BlastFurnaceGuiContainerHandler implements IGuiContainerHandler<BlastFurnaceGuiWindow> {

	public BlastFurnaceGuiContainerHandler() {

	}

	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(BlastFurnaceGuiWindow containerScreen, double mouseX, double mouseY) {
		return Collections.singleton(new IGuiClickableArea() {
			@Override
			public Rect2i getArea() {
				return GuiHelper.getArrowBounds(BlastFurnaceGuiWindow.ARROW_LEFT, BlastFurnaceGuiWindow.ARROW_TOP).toRect2i();
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) {
				recipesGui.showCategories(Arrays.asList(BlastingFurnaceJeiCategory.Uid, VanillaRecipeCategoryUid.FUEL));
			}

			@Override
			public List<Component> getTooltipStrings() {
				List<Component> list = new ArrayList<>();
				list.add(GaugeTextHelper.getStorageText(containerScreen.getMenu().getBlockEntity().getCookTimeGaugeValue()).build());
				list.add(new TranslatableComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

 

}
