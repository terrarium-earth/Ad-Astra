package earth.terrarium.ad_astra.common.compat.rei.oxygen_conversion;

import earth.terrarium.ad_astra.client.screen.ConversionScreen;
import earth.terrarium.ad_astra.common.block.machine.entity.OxygenLoaderBlockEntity;
import earth.terrarium.ad_astra.common.compat.rei.BaseClickArea;
import earth.terrarium.ad_astra.common.compat.rei.REICategories;

import java.awt.*;

public class OxygenConversionScreenClickArea extends BaseClickArea<ConversionScreen> {

    @Override
    public Rectangle getBounds(ConversionScreen screen) {
        return screen.getRecipeBounds();
    }

    @Override
    public boolean test(ClickAreaContext<ConversionScreen> context) {
        return super.test(context) && context.getScreen().getMenu().getMachine() instanceof OxygenLoaderBlockEntity;
    }

    @Override
    public Result getSuccess(ConversionScreen screen) {
        return Result.success().category(REICategories.OXYGEN_CONVERSION_CATEGORY);
    }
}
