package earth.terrarium.ad_astra.compat.rei.oxygen_conversion;

import java.awt.Rectangle;

import earth.terrarium.ad_astra.block.machine.entity.OxygenLoaderBlockEntity;
import earth.terrarium.ad_astra.client.screen.ConversionScreen;
import earth.terrarium.ad_astra.compat.rei.BaseClickArea;
import earth.terrarium.ad_astra.compat.rei.REICategories;

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
