package earth.terrarium.ad_astra.compat.rei.cryo_freezer;

import java.awt.Rectangle;

import earth.terrarium.ad_astra.client.screen.CryoFreezerScreen;
import earth.terrarium.ad_astra.compat.rei.BaseClickArea;
import earth.terrarium.ad_astra.compat.rei.REICategories;

public class CryoFreezerConversionScreenClickArea extends BaseClickArea<CryoFreezerScreen> {

	@Override
	public Rectangle getBounds(CryoFreezerScreen screen) {
		return screen.getSnowFlakeBounds();
	}

	@Override
	public Result getSuccess(CryoFreezerScreen screen) {
		return category(REICategories.CRYO_FREEZER_CONVERSION_CATEGORY);
	}
}
