package earth.terrarium.ad_astra.common.compat.jei.guihandler;

import earth.terrarium.ad_astra.client.screen.OxygenDistributorScreen;
import earth.terrarium.ad_astra.common.compat.jei.category.OxygenConversionCategory;
import mezz.jei.api.recipe.RecipeType;

import java.awt.*;

public class OxygenDistributorGuiContainerHandler extends BaseGuiContainerHandler<OxygenDistributorScreen> {

    @Override
    public Rectangle getRecipeClickableAreaBounds(OxygenDistributorScreen screen) {
        return screen.getRecipeBounds();
    }

    @Override
    protected RecipeType<?> getRecipeType(OxygenDistributorScreen screen) {
        return OxygenConversionCategory.RECIPE;
    }
}
