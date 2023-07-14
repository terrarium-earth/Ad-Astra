package earth.terrarium.ad_astra.common.compat.rei.oxygen_conversion;

import earth.terrarium.ad_astra.client.screen.OxygenDistributorScreen;
import earth.terrarium.ad_astra.common.block.machine.entity.OxygenDistributorBlockEntity;
import earth.terrarium.ad_astra.common.compat.rei.BaseClickArea;
import earth.terrarium.ad_astra.common.compat.rei.REICategories;

import java.awt.*;

public class OxygenDistributorScreenClickArea extends BaseClickArea<OxygenDistributorScreen> {

    @Override
    public Rectangle getBounds(OxygenDistributorScreen screen) {
        return screen.getRecipeBounds();
    }

    @Override
    public boolean test(ClickAreaContext<OxygenDistributorScreen> context) {
        return super.test(context) && context.getScreen().getMenu().getMachine() instanceof OxygenDistributorBlockEntity;
    }

    @Override
    public Result getSuccess(OxygenDistributorScreen screen) {
        return Result.success().category(REICategories.OXYGEN_CONVERSION_CATEGORY);
    }
}
