package earth.terrarium.ad_astra.common.compat.rei.cryo_freezer;

import earth.terrarium.ad_astra.client.screen.CryoFreezerScreen;
import earth.terrarium.ad_astra.common.compat.rei.BaseClickArea;
import earth.terrarium.ad_astra.common.compat.rei.REICategories;

import java.awt.*;

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
