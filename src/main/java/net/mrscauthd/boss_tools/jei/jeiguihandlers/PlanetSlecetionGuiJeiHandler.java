package net.mrscauthd.boss_tools.jei.jeiguihandlers;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rectangle2d;
import net.mrscauthd.boss_tools.gui.screens.planetselection.PlanetSelectionGuiWindow;

import java.util.*;

public class PlanetSlecetionGuiJeiHandler implements IGuiContainerHandler<PlanetSelectionGuiWindow> {

	public PlanetSlecetionGuiJeiHandler() {

	}

	@Override
	public List<Rectangle2d> getGuiExtraAreas(PlanetSelectionGuiWindow containerScreen) {
		List<Rectangle2d> list = new ArrayList<>();

		list.add(new Rectangle2d(0,0,containerScreen.width,containerScreen.height));

		return list;
	}
}
