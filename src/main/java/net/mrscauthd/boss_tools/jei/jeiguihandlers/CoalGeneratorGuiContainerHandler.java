package net.mrscauthd.boss_tools.jei.jeiguihandlers;

/*
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.gauge.GaugeValueHelper;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gui.screens.coalgenerator.CoalGeneratorGuiWindow;
import net.mrscauthd.boss_tools.gui.helper.GuiHelper;
import net.mrscauthd.boss_tools.jei.JeiPlugin.CoalGeneratorJeiCategory;*/

public class CoalGeneratorGuiContainerHandler /* implements IGuiContainerHandler<CoalGeneratorGuiWindow>*/ {
	/*
	public CoalGeneratorGuiContainerHandler() {

	}
	@Override
	public Collection<IGuiClickableArea> getGuiClickableAreas(CoalGeneratorGuiWindow containerScreen, double mouseX, double mouseY) {
		return Collections.singleton(new IGuiClickableArea() {
			@Override
			public Rectangle2d getArea() {
				return GuiHelper.getFireBounds(CoalGeneratorGuiWindow.FIRE_LEFT, CoalGeneratorGuiWindow.FIRE_TOP);
			}

			@Override
			public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) {
				recipesGui.showCategories(Arrays.asList(CoalGeneratorJeiCategory.Uid));
			}

			@Override
			public List<ITextComponent> getTooltipStrings() {
				List<ITextComponent> list = new ArrayList<>();
				list.add(GaugeTextHelper.getStorageText(GaugeValueHelper.getBurnTime(containerScreen.getTileEntity().getPowerSystemGenerating())).build());
				list.add(new TranslationTextComponent("jei.tooltip.show.recipes"));
				return list;
			}
		});

	}

 */

}