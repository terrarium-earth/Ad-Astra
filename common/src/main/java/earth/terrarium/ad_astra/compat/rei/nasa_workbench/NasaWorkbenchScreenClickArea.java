package earth.terrarium.ad_astra.compat.rei.nasa_workbench;

import java.awt.Rectangle;

import earth.terrarium.ad_astra.client.screen.NasaWorkbenchScreen;
import earth.terrarium.ad_astra.compat.rei.BaseClickArea;
import earth.terrarium.ad_astra.compat.rei.REICategories;

public class NasaWorkbenchScreenClickArea extends BaseClickArea<NasaWorkbenchScreen> {

	@Override
	public Rectangle getBounds(NasaWorkbenchScreen screen) {
		return screen.getRecipeBounds();
	}

	@Override
	public Result getSuccess(NasaWorkbenchScreen screen) {
		return category(REICategories.NASA_WORKBENCH_CATEGORY);
	}
}
