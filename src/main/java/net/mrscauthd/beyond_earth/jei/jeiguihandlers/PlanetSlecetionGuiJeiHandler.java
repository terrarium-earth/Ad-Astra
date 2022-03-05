package net.mrscauthd.beyond_earth.jei.jeiguihandlers;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import net.minecraft.client.renderer.Rect2i;
import net.mrscauthd.beyond_earth.gui.screens.planetselection.PlanetSelectionGuiWindow;

public class PlanetSlecetionGuiJeiHandler implements IGuiContainerHandler<PlanetSelectionGuiWindow> {

	public PlanetSlecetionGuiJeiHandler() {

	}

	@Override
	public List<Rect2i> getGuiExtraAreas(PlanetSelectionGuiWindow containerScreen) {
		List<Rect2i> list = new ArrayList<>();

		list.add(new Rect2i(0, 0, containerScreen.width, containerScreen.height));

		return list;
	}
}
