package earth.terrarium.ad_astra.common.compat.rei.fuel_conversion;

import earth.terrarium.ad_astra.client.screen.ConversionScreen;
import earth.terrarium.ad_astra.common.block.machine.entity.FuelRefineryBlockEntity;
import earth.terrarium.ad_astra.common.compat.rei.BaseClickArea;
import earth.terrarium.ad_astra.common.compat.rei.REICategories;

import java.awt.*;

public class FuelConversionScreenClickArea extends BaseClickArea<ConversionScreen> {

    @Override
    public Rectangle getBounds(ConversionScreen screen) {
        return screen.getRecipeBounds();
    }

    @Override
    public boolean test(ClickAreaContext<ConversionScreen> context) {
        return super.test(context) && context.getScreen().getMenu().getMachine() instanceof FuelRefineryBlockEntity;
    }

    @Override
    public Result getSuccess(ConversionScreen screen) {
        return Result.success().category(REICategories.FUEL_CONVERSION_CATEGORY);
    }
}
